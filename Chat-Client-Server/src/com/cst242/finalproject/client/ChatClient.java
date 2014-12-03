package com.cst242.finalproject.client;

import com.cst242.finalproject.client.viewcontroller.PrimaryWindow;

/**
 * This is the main class where the program for the chat client begins.
 * 
 * @author James DeCarlo
 */
public class ChatClient {

    /**
     * This is the main method where the chat client begins.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrimaryWindow pw = new PrimaryWindow();
        pw.setVisible(true);
                
    }
    
}
