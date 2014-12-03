package com.cst242.finalproject.server.model;

import com.cst242.finalproject.server.viewcontroller.PrimaryWindow;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is sets up the server socket and spawns new server threads.
 * It also starts 4 default chat rooms by spawning new server room threads.
 * 
 * @author James DeCarlo
 */
public class Server implements WindowListener {

    private static final int PORT = 9090;

    private ServerSocket serverSocket;

    private List<ServerRoom> rooms;

    /**
     * Starts the server listening on port 9090 and creates four default chat 
     * rooms that increment in number starting with port 9091.
     * 
     * @param window The primary window for logging purposes.
     */
    @SuppressWarnings({"CallToThreadStartDuringObjectConstruction", "LeakingThisInConstructor"})
    public Server(PrimaryWindow window) {
        try {
            // add window listener to shutdown threads when closing
            window.addWindowListener(this);

            serverSocket = new ServerSocket(PORT);
            window.appendLog("Server started:  %s%n", new Date());

            // Start default rooms and add to list array
            rooms = new ArrayList<>();

            ServerRoom room = new ServerRoom("All_Chat", 9091, window);
            room.start();
            rooms.add(room);

            this.sleep();

            ServerRoom room2 = new ServerRoom("Everything_Java", 9092, window);
            room2.start();
            rooms.add(room2);

            this.sleep();

            ServerRoom room3 = new ServerRoom("Computer_Science", 9093, window);
            room3.start();
            rooms.add(room3);

            this.sleep();

            ServerRoom room4 = new ServerRoom("Conspiracy", 9094, window);
            room4.start();
            rooms.add(room4);

        } catch (IOException ex) {
            window.appendLog("Server error exit and restart to try again:  %s%n", ex);
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        for (;;) {
            try {
                Socket clientSocket = serverSocket.accept();
                 new ServerThread(clientSocket, window, rooms).start();                
            } catch (SocketException e) {
                // window is closing so break from loop
                break;
            } catch (IOException ex) {
                this.sleep();
                window.appendLog("Failed to connect client: %s", new Date());
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

        // close chat rooms
        for (ServerRoom room : this.rooms) {
            room.shutDown();
        }

        // close server socket
        if (this.serverSocket != null) {
            try {
                this.serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
