/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cst242.finalproject.server.viewcontroller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James
 */

    public class ServerThread implements Runnable{
        PrimaryWindow window;
        Socket clientSocket;
        public ServerThread(Socket clientSocket, PrimaryWindow window) {
            this.clientSocket = clientSocket;           
            this.window = window;
        }

        
        @Override
        public void run() {
            try {
                DataInputStream fromClient = new DataInputStream(this.clientSocket.getInputStream());
                DataOutputStream toClient = new DataOutputStream(this.clientSocket.getOutputStream());
                window.appendLog("Client Connected:  %s%n", new Date());
                
            } catch (IOException ex) {
                window.appendLog("Error creating data streams in new thread:  %s%n", ex);
                Logger.getLogger(PrimaryWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
