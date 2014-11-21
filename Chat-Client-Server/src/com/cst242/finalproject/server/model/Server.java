/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cst242.finalproject.server.model;

import com.cst242.finalproject.server.viewcontroller.PrimaryWindow;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James
 */
public class Server implements Runnable {

    private static final int PORT = 9090;

    PrimaryWindow window;

    ServerSocket serverSocket;
    Socket clientSocket;
    DataInputStream fromClient;
    DataOutputStream toClient;        

    public Server(PrimaryWindow window) {
        try {
            serverSocket = new ServerSocket(PORT);
            window.appendLog("Server started:  %s%n", new Date());
            for (;;) {
                clientSocket = serverSocket.accept();
                new Thread(this).start();                
            }
        } catch (IOException ex) {
            window.appendLog("Server error exit and restart to try again:  %s%n", ex);
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.window = window;
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
