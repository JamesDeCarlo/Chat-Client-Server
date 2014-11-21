package com.cst242.finalproject.server.model;

import com.cst242.finalproject.server.viewcontroller.PrimaryWindow;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author James DeCarlo
 */
public class ServerRoom {    
    private String roomName;
    private int port;    
        
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public ServerRoom(String roomName, int port, PrimaryWindow window) {
        
        this.roomName = roomName;
        this.port = port;
                
    }

    public String getRoomName() {
        return roomName;
    }

    public int getPort() {
        return port;
    }
    
    
    
}
