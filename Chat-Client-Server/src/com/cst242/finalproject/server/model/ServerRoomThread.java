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
 * This is the thread that handles all communications with the chat client in 
 * the chat room.
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
    
    private final String roomName;

    /**
     * Creates a new Server room thread.
     * 
     * @param window The primary window for logging.
     * @param clientSocket The client socket created on connection.
     * @param roomThreads The server room threads for inter thread 
     * communication.
     * @param roomName The name of the chat room.
     */
    public ServerRoomThread(PrimaryWindow window, Socket clientSocket, ServerRoomThread[] roomThreads, String roomName) {
        this.window = window;
        this.clientSocket = clientSocket;
        this.roomThreads = roomThreads;
        this.MAX_USERS = roomThreads.length;
        this.roomName = roomName;
    }

    @Override
    public void run() {
        try {
            fromClient = new DataInputStream(clientSocket.getInputStream());
            toClient = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {

            // remove thread from list and close streams
            synchronized (this) {
                for (int i = 0; i < this.MAX_USERS; i++) {
                    if (roomThreads[i] == this) {
                        roomThreads[i] = null;
                    }
                }
            }

            window.appendLog("Failed to create client data streams in chat room: %s%n", new Date());
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerRoomThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }

        window.appendLog("Client connected to Room %s: %s%n", this.roomName, new Date());
        
        // loop and wait for message then broadcast to clients in all threads
        try {
            for (;;) {
                String msg = fromClient.readUTF();

                synchronized (this) {
                    for (int i = 0; i < this.MAX_USERS; i++) {
                        if (roomThreads[i] != null) {
                            roomThreads[i].toClient.writeUTF(msg);
                        }
                    }
                }

            }
        } catch (IOException ex) {
            // remove thread from list and close streams
            synchronized (this) {
                for (int i = 0; i < this.MAX_USERS; i++) {
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

    /**
     * Shuts down the server thread and all of its connections and sockets.
     * Should be called when server is shutting down or client disconnects from
     * the chat room.
     */
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
