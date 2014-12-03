package com.cst242.finalproject.server.model;

import com.cst242.finalproject.server.viewcontroller.PrimaryWindow;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class sets up the chat room and waits for new client connections.
 * 
 * @author James DeCarlo
 */
public class ServerRoom extends Thread {

    private final static int MAX_USERS = 25;
    private final static ServerRoomThread[] roomThreads = new ServerRoomThread[MAX_USERS];

    private final PrimaryWindow window;

    private final String roomName;
    private final int port;

    private ServerSocket serverSocket;
    private Socket clientSocket;

    /**
     * Creates a new server room object.
     * 
     * @param roomName The name of the chat room.
     * @param port The port number of the chat room.
     * @param window The primary window for logging.
     */
    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public ServerRoom(String roomName, int port, PrimaryWindow window) {

        this.roomName = roomName;
        this.port = port;
        this.window = window;

    }

    /**
     * Gets the name of the chat room.
     * @return The name of the chat room.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Gets the port number of the chat room.
     * @return The port number of the chat room.
     */
    public int getPort() {
        return port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            window.appendLog("Room %s started: %s%n", this.roomName, new Date());
        } catch (IOException ex) {
            window.appendLog("Error creating %s Room: %s%n", roomName, new Date());
            return;
        }

        for (;;) {
            try {
                this.clientSocket = this.serverSocket.accept();
                int i;
                for (i = 0; i < MAX_USERS; i++) {
                    if (ServerRoom.roomThreads[i] == null) {
                        roomThreads[i] = new ServerRoomThread(window, clientSocket, roomThreads, roomName);
                        roomThreads[i].start();
                        break;
                    }
                }
                if (i == MAX_USERS) {
                    window.appendLog("Room %s has reached its capacity of %d: %s%n", this.roomName, MAX_USERS, new Date());

                    // Room is filled close connection to client
                    this.clientSocket.close();
                }

            } catch (IOException e) {
                window.appendLog("Failed to connect client to room: %s%n", new Date());
            }
        }
    }

    /**
     * Shuts down the chat room should be called when closing down the server.
     */
    public void shutDown() {
        try {
            if (this.serverSocket != null) {
                this.serverSocket.close();
            }                        
        } catch (IOException ex) {
            Logger.getLogger(ServerRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // Shutdown all running threads
        for(int i = 0; i < roomThreads.length; i++){
            if(roomThreads[i] != null){
                roomThreads[i].shutDown();                
            }
        }
    }
}
