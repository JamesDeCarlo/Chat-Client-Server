package com.cst242.finalproject.server.model;

import com.cst242.finalproject.server.viewcontroller.PrimaryWindow;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James DeCarlo
 */
public class ServerRoomThread extends Thread {

    private final PrimaryWindow window;

    private final Socket clientSocket;
    private DataInputStream fromClient;
    private DataOutputStream toClient;

    private final ServerRoomThread[] roomThreads;
    private final int MAX_USERS;

    public ServerRoomThread(PrimaryWindow window, Socket clientSocket, ServerRoomThread[] roomThreads) {
        this.window = window;
        this.clientSocket = clientSocket;
        this.roomThreads = roomThreads;
        this.MAX_USERS = roomThreads.length;
    }

    @Override
    public void run() {
        try {
            fromClient = new DataInputStream(clientSocket.getInputStream());
            toClient = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {

            // remove thread from list and close streams
            synchronized (this) {
                for (int i = 0; i < roomThreads.length; i++) {
                    if (roomThreads[i] == this) {
                        roomThreads[i] = null;
                    }
                }
            }

            window.appendLog("Failed to create client data streams in chat room: %s", new Date());
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerRoomThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }

        // loop and wait for message then broadcast to clients in all threads
        try {
            for (;;) {
                String msg = fromClient.readUTF();

                synchronized (this) {
                    for (int i = 0; i < roomThreads.length; i++) {
                        if (roomThreads[i] != null) {
                            roomThreads[i].toClient.writeUTF(msg);
                        }
                    }
                }

            }
        } catch (IOException ex) {
            // remove thread from list and close streams
            synchronized (this) {
                for (int i = 0; i < roomThreads.length; i++) {
                    if (roomThreads[i] == this) {
                        roomThreads[i] = null;
                    }
                }
            }
            try {
                this.fromClient.close();
                this.toClient.close();
                this.clientSocket.close();
            } catch (IOException e) {
                // Do nothing here
            }
        }

    }

    public void shutDown() {
        try {
            this.fromClient.close();
            this.toClient.close();
            this.clientSocket.close();
        } catch (IOException e) {
            // do nothing
        }
    }
}
