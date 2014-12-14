package com.cst242.finalproject.server.model;

import com.cst242.finalproject.server.viewcontroller.PrimaryWindow;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class sets up the chat room and waits for new client connections.
 *
 * @author James DeCarlo
 */
public class ServerRoom extends Thread {

    private final PrimaryWindow window;

    private final String roomName;
    private final int port;

    private final List<DataOutputStream> streams = new ArrayList<>();
    private ServerSocket serverSocket;

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
     *
     * @return The name of the chat room.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Gets the port number of the chat room.
     *
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

        try {
            for (;;) {
                new ServerRoomThread(window, serverSocket.accept(),this.streams, roomName).start();
            }
        } catch (IOException ex) {
            // do nothing let the thread die
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
    }
}
