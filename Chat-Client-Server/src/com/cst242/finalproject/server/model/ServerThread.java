package com.cst242.finalproject.server.model;

import com.cst242.finalproject.server.viewcontroller.PrimaryWindow;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author James DeCarlo
 */
public class ServerThread extends Thread {
    PrimaryWindow window;
    
    List<ServerRoom> rooms;
    
    Socket clientSocket;
    DataInputStream fromClient;
    DataOutputStream toClient;

    public ServerThread(Socket clientSocket, PrimaryWindow window) {
        this.clientSocket = clientSocket;
        this.window = window;
        
        // Start default rooms and add to list array
        rooms = new ArrayList<>();
        rooms.add(new ServerRoom("All_Chat", 9091, window));
        rooms.add(new ServerRoom("Everything_Java", 9092, window));
        rooms.add(new ServerRoom("Computer_Science", 9093, window));
        rooms.add(new ServerRoom("Conspiracy", 9094, window));
    }
    
    
    
    @Override
    public void run() {
        try {
            fromClient = new DataInputStream(this.clientSocket.getInputStream());
            toClient = new DataOutputStream(this.clientSocket.getOutputStream());
            window.appendLog("Client Connected:  %s%n", new Date());

            for (;;) {
                // Start listening for request
                String request = fromClient.readUTF();
                this.processRequest(Helper.splitString(request));
            }

        } catch (IOException ex) {
            window.appendLog("Client disconected : %s%n", new Date());                        
        }
    }

    public void processRequest(String[] request) {
        if (request[0].equals("REGISTER")) {
            this.register(request);
        }
    }

    /**
     * Registers a new user
     * 
     * @param request
     */
    public void register(String[] request) {
        FileIO fileIO = new FileIO();

        try {
            if (request.length == 6 & fileIO.register(request[1], Integer.parseInt(request[2]), request[3], request[4], request[5])) {
                window.appendLog("Registered new user %s : %s%n", request[1], new Date());
                toClient.writeUTF("SUCCESS");
            } else {
                toClient.writeUTF("FAILED");
            }
        } catch (IOException e) {
            window.appendLog("Failed to send register status message to client: %s%n", new Date());
        }
    }

    /**
     * Logs a user in to the server
     * 
     * @param request
     */
    public void login(String[] request) {
        FileIO fileIO = new FileIO();
        try {
            if (request.length != 3) {
                toClient.writeUTF("FAILED");
            } else{
                User user = fileIO.loginUser(request[1], Integer.parseInt(request[2]));
                
                if(user == null){
                    toClient.writeUTF("FAILED");
                } else {
                    StringBuilder retmsg = new StringBuilder();
                    retmsg.append("USER ");
                    retmsg.append(user.getAccountNumber());
                    retmsg.append(" ");
                    retmsg.append(user.getFirstName());
                    retmsg.append(" ");
                    retmsg.append(user.getLastName());
                    retmsg.append(" ");
                    retmsg.append(user.getScreenName());
                    
                    toClient.writeUTF(retmsg.toString());
                }
            }
        } catch (IOException e) {
            window.appendLog("Failed to send login message to client : %s", new Date());
        }
    }
}
