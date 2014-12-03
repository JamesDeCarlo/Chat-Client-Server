package com.cst242.finalproject.client.viewcontroller;

import com.cst242.finalproject.client.model.ClientRoom;
import com.cst242.finalproject.client.model.Helper;
import com.cst242.finalproject.client.model.User;
import com.sun.glass.events.KeyEvent;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JFrame;

/**
 * 
 * @author James DeCarlo
 */
public class ChatWindow extends javax.swing.JFrame implements ActionListener, Runnable, KeyListener {

    private ClientRoom room;
    private boolean shiftPressed = false;
    
    /**
     * Creates new form ChatWindow
     *
     * @param roomName
     * @param host
     * @param port
     * @param user
     */
    @SuppressWarnings({"LeakingThisInConstructor", "CallToThreadStartDuringObjectConstruction"})
    public ChatWindow(String roomName, String host, int port, User user) {
        super(roomName);
        initComponents();

        // Center the jframe in the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width - this.getSize().width) / 2, (dim.height - this.getSize().height) / 2);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set image icon for jframe
        Image image = Toolkit.getDefaultToolkit().getImage("c_icon.png");
        this.setIconImage(image);

        // Set focus in text box
        this.txtInput.requestFocus();

        // create button listener for send button and exit button
        this.btnSend.addActionListener(this);
        this.btnExitRoom.addActionListener(this);

        try {
            room = new ClientRoom(host, port, user);
        } catch (IOException ex) {
            this.txtChatDisplay.append("Chat room closed\n");
            this.txtChatDisplay.setCaretPosition(this.txtChatDisplay.getDocument().getLength());
            return;
        }

        
        this.txtInput.addKeyListener(this);

        Thread thread = new Thread(this);
        thread.start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtChatDisplay = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtInput = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();
        btnExitRoom = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtChatDisplay.setEditable(false);
        txtChatDisplay.setColumns(20);
        txtChatDisplay.setLineWrap(true);
        txtChatDisplay.setRows(5);
        txtChatDisplay.setWrapStyleWord(true);
        txtChatDisplay.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Chat Window"));
        jScrollPane1.setViewportView(txtChatDisplay);

        txtInput.setColumns(20);
        txtInput.setLineWrap(true);
        txtInput.setRows(5);
        txtInput.setToolTipText("Press shift+enter for a new line");
        txtInput.setWrapStyleWord(true);
        txtInput.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Enter Chat"));
        jScrollPane2.setViewportView(txtInput);

        btnSend.setText("Send");

        btnExitRoom.setText("Exit Room");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnExitRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExitRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExitRoom;
    private javax.swing.JButton btnSend;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtChatDisplay;
    private javax.swing.JTextArea txtInput;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Send")) {
            this.sendMessage();
            
        } else if (e.getActionCommand().equals("Exit Room")) {
            // close sockets and streams
            if (this.room != null) {
                this.room.close();
            }

            // destroy window
            this.dispose();
        }
    }

    private void sendMessage(){
        try {
                // send message
                this.room.sendMessage(this.txtInput.getText());
            } catch (IOException ex) {
                this.txtChatDisplay.append("Chat room closed\n");
                this.txtChatDisplay.setCaretPosition(this.txtChatDisplay.getDocument().getLength());
            }

            // clear the text box
            this.txtInput.setText("");

            // set focus back to input text box
            this.txtInput.requestFocus();
    }
    
    @Override
    public void run() {
        try {
            for (;;) {
                String msg = room.receiveMessage();
                String[] arr = msg.split("\\s+", 2);

                if (arr[0].equals("MESSAGE")) {
                    arr = arr[1].split("\\s+", 2);

                    msg = String.format(">>>> %s %s:%n", arr[0], Helper.currentTimeStamp());

                    this.txtChatDisplay.append(msg);

                    msg = String.format("%s%n", arr[1]);

                    this.txtChatDisplay.append(msg);

                    this.txtChatDisplay.setCaretPosition(this.txtChatDisplay.getDocument().getLength());

                }
            }
        } catch (IOException e) {
            this.txtChatDisplay.append("Chat room closed\n");
            this.txtChatDisplay.setCaretPosition(this.txtChatDisplay.getDocument().getLength());
        }

    }

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if(shiftPressed){
                this.txtInput.append("\n");
            } else {
                this.sendMessage();
            }
        }
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT){
            this.shiftPressed = true;
        }
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT){
            this.shiftPressed = false;
        }
    }
    
    
}
