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
public class Client {

    Socket serverSocket;
    DataInputStream fromServer;
    DataOutputStream toServer;

    public Client(String host, int port) throws IOException {
        this.serverSocket = new Socket(host, port);
        this.fromServer = new DataInputStream(this.serverSocket.getInputStream());
        this.toServer = new DataOutputStream(this.serverSocket.getOutputStream());
    }

    /**
     * Registers a new user with the server.
     * 
     * @param user 
     * @return {@code true} if user is added to the server successfully.
     * @throws IOException if gets disconnected from the server.
     */
    public boolean registerUser(User user) throws IOException {
        // Build msg to send to server
        StringBuilder msg = new StringBuilder();
        msg.append("REGISTER ");
        msg.append(user.getLoginId());
        msg.append(" ");
        msg.append(user.getHashedPassword());
        msg.append(" ");
        msg.append(user.getFirstName());
        msg.append(" ");
        msg.append(user.getLastName());
        msg.append(" ");
        msg.append(user.getScreenName());

        this.toServer.writeUTF(msg.toString());

        String returnMsg = this.fromServer.readUTF();
        if (returnMsg.equals("SUCCESS")) {
            return true;
        }
        return false;
    }

    /**
     * Logs a user in to the server and receives information to fill the user
     * object.
     * 
     * @param user a user object with the loginId and hashedPassword set
     * @return a {@code User} object fully filled in 
     * @throws IOException if gets disconnected from the server.
     */
    public User login(User user) throws IOException{
        // Create message to send to server
        StringBuilder msg = new StringBuilder();
        msg.append("LOGIN ");
        msg.append(user.getLoginId());
        msg.append(" ");
        msg.append(user.getHashedPassword());
        
        //Send msg to server
        this.toServer.writeUTF(msg.toString());
        
        String returnMsg[] = Helper.splitString(this.fromServer.readUTF());
        
        if(returnMsg[0].equals("USER")){
            User retUser = new User();
            retUser.setLoginId(user.getLoginId());
            retUser.setHashedPassword(user.getHashedPassword());
            retUser.setAccountNumber(Integer.parseInt(returnMsg[1]));
            retUser.setFirstName(returnMsg[2]);
            retUser.setLastName(returnMsg[3]);
            retUser.setScreenName(returnMsg[4]);
            return retUser;
        }
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
