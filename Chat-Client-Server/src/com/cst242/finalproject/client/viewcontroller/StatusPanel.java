/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cst242.finalproject.client.viewcontroller;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author James DeCarlo
 */
public class StatusPanel extends javax.swing.JPanel {

    /**
     * Creates new form StatusPanel
     */
    public StatusPanel() {
        initComponents();
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }

    public JButton getBtnPreferences() {
        return btnPreferences;
    }

    public JButton getBtnSelectRooms() {
        return btnSelectRooms;
    }

    public JLabel getLblGreeting() {
        return lblGreeting;
    }

    public JLabel getLblScreenName() {
        return lblScreenName;
    }   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblGreeting = new javax.swing.JLabel();
        lblScreenName = new javax.swing.JLabel();
        btnPreferences = new javax.swing.JButton();
        btnSelectRooms = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        lblGreeting.setText("Hello firstName lastName ");

        lblScreenName.setText("Screen name: screenName");

        btnPreferences.setText("Preferences");
        btnPreferences.setToolTipText("Click to edit preferences");
        btnPreferences.setActionCommand("statusPreferences");

        btnSelectRooms.setText("Select Room(s)");
        btnSelectRooms.setToolTipText("Click to select rooms");
        btnSelectRooms.setActionCommand("statusSelectRooms");

        btnLogout.setText("Logout");
        btnLogout.setToolTipText("Click to log out");
        btnLogout.setActionCommand("statusLogout");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblScreenName)
                    .addComponent(lblGreeting))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSelectRooms, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(btnPreferences, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(btnLogout)
                .addContainerGap(111, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblGreeting)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblScreenName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPreferences)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSelectRooms)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPreferences;
    private javax.swing.JButton btnSelectRooms;
    private javax.swing.JLabel lblGreeting;
    private javax.swing.JLabel lblScreenName;
    // End of variables declaration//GEN-END:variables
}
