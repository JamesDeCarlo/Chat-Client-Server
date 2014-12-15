package com.cst242.finalproject.client.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is responsible for communicating with one of the servers chat
 * rooms.
 * 
 * @author James DeCarlo
 */
public class ClientRoom {
    
    private final Socket serverSocket;
    private final DataInputStream fromServer;
    private final DataOutputStream toServer;
    private final User user;
    
    /**
     * Creates a new client room object with the socket and data streams 
     * initialized.
     * 
     * @param host the host name of the server
     * @param port the port number of the room
     * @param user the user object fully filled in
     * 
     * @throws IOException if the room is not available
     */
    public ClientRoom(String host, int port, User user) throws IOException {
        this.user = user;
        this.serverSocket = new Socket(host, port);
        this.fromServer = new DataInputStream(this.serverSocket.getInputStream());
        this.toServer = new DataOutputStream(this.serverSocket.getOutputStream());
        this.sendMessage("Joined the room!!!");
    }
    
    /**
     * Sends a chat message to the server
     * 
     * @param msg the message to be sent
     * @throws java.io.IOException if disconnected from chat room
     */
    public final void sendMessage(String msg) throws IOException{
        this.toServer.writeUTF(String.format("MESSAGE %s %s", user.getScreenName(), msg));
        this.toServer.flush();
    }
    
    /**
     * Receives a chat message from the server
     * 
     * @return the message from the server needs to be formated
     * @throws java.io.IOException if disconnected from chat room
     */
    public String receiveMessage() throws IOException{                  
        return this.fromServer.readUTF();
    }
    
     /**
     * Closes the steams and socket should be called at the end.
     */
    public void close() {
        if (this.toServer != null) {
            try {
                this.toServer.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (this.fromServer != null) {
            try {
                this.fromServer.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (this.serverSocket != null) {
            try {
                this.serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
