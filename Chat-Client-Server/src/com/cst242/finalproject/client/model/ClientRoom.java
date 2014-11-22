package com.cst242.finalproject.client.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James DeCarlo
 */
public class ClientRoom {
    
    Socket serverSocket;
    DataInputStream fromServer;
    DataOutputStream toServer;
    User user;
    
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
    }
    
    /**
     * Sends a chat message to the server
     * 
     * @param msg the message to be sent
     */
    public void sendMessage(String msg){
        
    }
    
    /**
     * Receives a chat message from the server
     * 
     * @return the message formated with time stamp
     */
    public String receiveMessage(){
        return null;
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