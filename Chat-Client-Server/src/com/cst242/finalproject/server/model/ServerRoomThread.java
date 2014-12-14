package com.cst242.finalproject.server.model;

import com.cst242.finalproject.server.viewcontroller.PrimaryWindow;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.List;
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

    private final List<DataOutputStream> streams;

    private final Socket clientSocket;
    private DataInputStream fromClient;
    private DataOutputStream toClient;
    private final String roomName;
    private String screenName;

    /**
     * Creates a new Server room thread.
     *
     * @param window The primary window for logging.
     * @param clientSocket The client socket created on connection.
     * @param roomName The name of the chat room.
     */
    public ServerRoomThread(PrimaryWindow window, Socket clientSocket,List<DataOutputStream> streams, String roomName) {
        this.window = window;
        this.clientSocket = clientSocket;
        this.streams = streams;
        this.roomName = roomName;
    }

    @Override
    public void run() {
        try {
            fromClient = new DataInputStream(clientSocket.getInputStream());
            toClient = new DataOutputStream(clientSocket.getOutputStream());

            // add output stream for broadcasting
            synchronized (this) {
                streams.add(toClient);
            }
            
            window.appendLog("Client connected to room %s: %s%n", this.roomName, new Date());

            // loop and wait for message then broadcast to clients in all threads
            for (;;) {
                String msg = fromClient.readUTF();
                this.broadcastMessage(msg);
            }

        } catch (IOException e) {
            // do nothing
        } finally {
            this.shutDown();
        }
    }

    private void broadcastMessage(String msg) throws IOException {
        synchronized (this) {
            for (DataOutputStream s : streams) {
                s.writeUTF(msg);
            }
        }
    }

    /**
     * Shuts down the server thread and all of its connections and sockets.
     * Should be called when server is shutting down or client disconnects from
     * the chat room.
     */
    public void shutDown() {

        if (this.toClient != null) {
            streams.remove(this.toClient);
        }

        try {
            if (this.fromClient != null) {
                this.fromClient.close();
            }
            if (this.toClient != null) {
                this.toClient.close();
            }
            if (this.clientSocket != null) {
                this.clientSocket.close();
            }
        } catch (IOException e) {
            // do nothing
        }
        window.appendLog("Client disconected from room %s: %s%n", this.roomName, new Date());
    }

    public void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerRoomThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
