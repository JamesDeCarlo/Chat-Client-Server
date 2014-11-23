package com.cst242.finalproject.server.model;

import com.cst242.finalproject.server.viewcontroller.PrimaryWindow;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author James DeCarlo
 */
public class ServerRoom {    
    private final static int MAX_USERS = 25;
    private final static ServerRoomThread[] roomThreads = new ServerRoomThread[MAX_USERS];
    
    private String roomName;
    private int port;    
        
    private ServerSocket serverSocket;
    private Socket clientSocket;

    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public ServerRoom(String roomName, int port, PrimaryWindow window) {
        
        this.roomName = roomName;
        this.port = port;
                
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            window.appendLog("Error creating %s Room: %s%n", roomName, new Date());
            return;
        }
        
        for(;;){
            try{
                this.clientSocket = this.serverSocket.accept();
                int i;
                for(i = 0; i < MAX_USERS; i++){
                    if(ServerRoom.roomThreads[i] == null){
                        roomThreads[i] = new ServerRoomThread(window, clientSocket, roomThreads);
                        roomThreads[i].start();
                        break;
                    }
                }
                if(i == MAX_USERS){
                    window.appendLog("Room %s has reached its capacity of %d: %s%n", this.roomName, MAX_USERS, new Date());
                    
                    // Room is filled close connection to client
                    this.clientSocket.close();                    
                }
                
                
            } catch (IOException e){
                window.appendLog("Failed to connect client to room: %s%n", new Date());
            }
        }
        
    }

    public String getRoomName() {
        return roomName;
    }

    public int getPort() {
        return port;
    }
    
    
    
}
