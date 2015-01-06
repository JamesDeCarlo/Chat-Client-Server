package com.cst242.finalproject.server.viewcontroller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

/**
 * This class is the primary GUI for the chat server.
 * 
 * @author James DeCarlo
 */
public class PrimaryWindow extends JFrame {

    // Gui Constants
    private final static int JWIDTH = 800, JHEIGHT = 500;
    private final static Dimension screenResolution = 
            Toolkit.getDefaultToolkit().getScreenSize();
    private final static int X = screenResolution.width / 2 - JWIDTH / 2, 
            Y = screenResolution.height / 2 - JHEIGHT / 2;

    // Declare Gui parts
    private final JTextArea textLog;

    private static enum ReturnCode {

        SUCCESS(200), IO_EXC(404), IO_CLOSE(405);
        private final int value;

        private ReturnCode(int value) {
            this.value = value;
        }

    };

    /**
     * Initializes the Primary window for the chat server.
     */
    public PrimaryWindow() {
        // Set up JFrame
        super("Chat Server");
        this.setSize(JWIDTH, JHEIGHT);
        this.setLocation(X, Y);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.textLog = new JTextArea();
        this.textLog.setEditable(false);
        this.textLog.setLineWrap(true);
        this.textLog.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(EtchedBorder.RAISED), 
                        "Chat Server Log Window"));

        JScrollPane jsp = new JScrollPane(this.textLog);

        this.add(jsp, BorderLayout.CENTER);

        this.setIconImage(Toolkit.getDefaultToolkit().getImage("s_icon.png"));

    }

    /**
     * Appends the text area for the log window.
     * 
     * @param formatedString see {@code String.format()} for details.
     * @param args an variable field of given arguments that corresponds to the
     * formated string.
     */
    public void appendLog(String formatedString, Object... args) {
        synchronized (this) {
            this.textLog.append(String.format(formatedString, args));
            this.textLog.setCaretPosition(textLog.getDocument().getLength());
        }
    }
}