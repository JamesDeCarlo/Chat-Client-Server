/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cst242.finalproject.server.viewcontroller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author James
 */
public class PrimaryWindow extends JFrame {

    // Gui Constants
    private final static int JWIDTH = 800, JHEIGHT = 500;
    private final static Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
    private final static int X = screenResolution.width / 2 - JWIDTH / 2, Y = screenResolution.height / 2 - JHEIGHT / 2;    
    
    // Declare Gui parts
    private final JTextArea textLog;
    private final JTextField textInput;

    // I/O Streams
    private ServerSocket serverSocket;
    private Socket clientSocket;    

    private static enum ReturnCode {

        SUCCESS(200), IO_EXC(404), IO_CLOSE(405);
        private final int value;

        private ReturnCode(int value) {
            this.value = value;
        }

    };
    
    public PrimaryWindow() {
        // Set up JFrame
        super("Chat Server");
        this.setSize(JWIDTH, JHEIGHT);
        this.setLocation(X, Y);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.textInput = new JTextField();
        this.textInput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Enter Command"));
        
        this.textLog = new JTextArea();
        this.textLog.setEditable(false);
        this.textLog.setLineWrap(true);
        this.textLog.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Chat Server Log Window"));
        
        JScrollPane jsp = new JScrollPane(this.textLog);
        
        this.add(jsp, BorderLayout.CENTER);
        this.add(textInput, BorderLayout.SOUTH);

        this.setIconImage(Toolkit.getDefaultToolkit().getImage("s_icon.png"));
        
        
        
    }

    public void appendLog(String formatedString, Object... args){
        this.textLog.append(String.format(formatedString, args));
        
        this.textLog.setCaretPosition(textLog.getDocument().getLength());
    }                    
        
        
}
