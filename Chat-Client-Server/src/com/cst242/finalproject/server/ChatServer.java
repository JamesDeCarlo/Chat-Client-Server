/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cst242.finalproject.server;

import com.cst242.finalproject.server.viewcontroller.PrimaryWindow;
import com.cst242.finalproject.server.viewcontroller.ServerThread;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
public class ChatServer {
    private static final int PORT = 9090;

    private static ServerSocket serverSocket;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrimaryWindow pw = new PrimaryWindow();
        pw.setVisible(true);
        
        if(SystemTray.isSupported()){
            SystemTray sysTray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("s_icon.png");
            PopupMenu menu = new PopupMenu();
            MenuItem openItem = new MenuItem("Open");
            MenuItem exitItem = new MenuItem("Exit");

            menu.add(openItem);
            menu.add(exitItem);

            openItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    pw.setVisible(true);
                }
            });

            exitItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            //create system tray icon.
            TrayIcon trayIcon = new TrayIcon(image, "Chat Server", menu);

            //add the tray icon to the system tray.
            try {
                sysTray.add(trayIcon);
            } catch (AWTException e) {
                //Should but to log file here
            }

         //add window minimized listener to hide the window to system tray
            pw.addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e){
                pw.setVisible(false);
            }
            });   
        }
        
        try {
            serverSocket = new ServerSocket(PORT);
            pw.appendLog("Server started:  %s%n", new Date());
            for(;;){
                Socket clientSocket = serverSocket.accept();
                new Thread(new ServerThread(clientSocket,pw)).start();
            }
        } catch (IOException ex) {
            pw.appendLog("Server error exit and restart to try again:  %s%n", ex);
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
