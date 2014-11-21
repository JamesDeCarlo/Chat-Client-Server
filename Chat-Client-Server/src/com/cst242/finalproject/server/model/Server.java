package com.cst242.finalproject.server.model;

import com.cst242.finalproject.server.viewcontroller.PrimaryWindow;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James DeCarlo
 */
public class Server {

    private static final int PORT = 9090;

    private PrimaryWindow window;

    private ServerSocket serverSocket;
    private Socket clientSocket;

    public Server(PrimaryWindow window) {
        try {
            serverSocket = new ServerSocket(PORT);
            window.appendLog("Server started:  %s%n", new Date());
            for (;;) {
                clientSocket = serverSocket.accept();
                Thread thread = new ServerThread(clientSocket, window);
                thread.start();                
            }
        } catch (IOException ex) {
            window.appendLog("Server error exit and restart to try again:  %s%n", ex);
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.window = window;
    }

    
}
