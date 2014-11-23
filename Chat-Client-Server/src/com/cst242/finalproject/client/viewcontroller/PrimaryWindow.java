package com.cst242.finalproject.client.viewcontroller;

import com.cst242.finalproject.client.model.Client;
import com.cst242.finalproject.client.model.Helper;
import com.cst242.finalproject.client.model.Room;
import com.cst242.finalproject.client.model.User;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author James DeCarlo
 */
public class PrimaryWindow extends JFrame implements ActionListener {

    private final static int JWIDTH = 280, JHEIGHT = 400;
    private final static int X = 0, Y = 0;
    private final LoginPanel loginPanel;
    private final RegistrationPanel regPanel;
    private final PrimaryPanel primaryPanel;
    private final StatusPanel statusPanel;
    private final PreferencesPanel prefPanel;
    private final SelectRoomsPanel srPanel;
    private final CreateNewRoomPanel cnrPanel;

    private final static String HOST = "localhost";
    private final static int PORT = 9090;

    private Client client;
    private User user;

    private List<Room> rooms ;
    private DefaultListModel listModel;
    private List<JFrame> chatWindows;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public PrimaryWindow() {
        super("Chat Client");
        this.setSize(JWIDTH, JHEIGHT);
        this.setLocation(X, Y);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Lock Jframe size
        this.setResizable(false);

        // Create frame holder panel to set to box layout and hold all our panels
        JPanel framePanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(framePanel, BoxLayout.Y_AXIS);
        framePanel.setLayout(boxLayout);
        this.add(framePanel, BorderLayout.CENTER);

        // Set the icon image for the application
        Image image = Toolkit.getDefaultToolkit().getImage("c_icon.png");
        this.setIconImage(image);

        //Initialize image label
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Border paddedBorder = BorderFactory.createEmptyBorder(50, 0, 40, 0);
        imageLabel.setBorder(paddedBorder);
        framePanel.add(imageLabel);

        // Initialize primary panel
        this.primaryPanel = new PrimaryPanel();
        this.primaryPanel.getBtnLogin().addActionListener(this);
        this.primaryPanel.getBtnRegister().addActionListener(this);
        this.primaryPanel.getBtnExit().addActionListener(this);
        this.primaryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        framePanel.add(this.primaryPanel);

        // Initialize login panel 
        this.loginPanel = new LoginPanel();
        this.loginPanel.getBtnCancel().addActionListener(this);
        this.loginPanel.getBtnSubmit().addActionListener(this);
        this.loginPanel.setVisible(false);
        framePanel.add(this.loginPanel);

        // Initialize registration panel
        this.regPanel = new RegistrationPanel();
        this.regPanel.getBtnSubmit().addActionListener(this);
        this.regPanel.getBtnCancel().addActionListener(this);
        this.regPanel.setVisible(false);
        framePanel.add(this.regPanel);

        // Initialize status panel
        this.statusPanel = new StatusPanel();
        this.statusPanel.getBtnPreferences().addActionListener(this);
        this.statusPanel.getBtnSelectRooms().addActionListener(this);
        this.statusPanel.getBtnLogout().addActionListener(this);
        this.statusPanel.setVisible(false);
        framePanel.add(this.statusPanel);

        // Initialize the preferences panel
        this.prefPanel = new PreferencesPanel();
        this.prefPanel.getBtnSubmit().addActionListener(this);
        this.prefPanel.getBtnCancel().addActionListener(this);
        this.prefPanel.setVisible(false);
        framePanel.add(this.prefPanel);

        // Initialize the Select Rooms Panel
        this.srPanel = new SelectRoomsPanel();
        this.srPanel.getBtnEnter().addActionListener(this);
        this.srPanel.getBtnCancel().addActionListener(this);
        this.srPanel.getBtnCreateNewRoom().addActionListener(this);
        this.srPanel.setVisible(false);
        framePanel.add(this.srPanel);

        // Initialize the Create New Rooms panel
        this.cnrPanel = new CreateNewRoomPanel();
        this.cnrPanel.getBtnCreateRoom().addActionListener(this);
        this.cnrPanel.getBtnCancel().addActionListener(this);
        this.cnrPanel.setVisible(false);
        framePanel.add(this.cnrPanel);

        
        // Initialize variables
        this.chatWindows = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("primaryLogin")) {
            this.primaryPanel.setVisible(false);
            this.loginPanel.setVisible(true);
            this.loginPanel.getTxtLoginId().requestFocus();
            this.getRootPane().setDefaultButton(this.loginPanel.getBtnSubmit());

        } else if (e.getActionCommand().equals("primaryRegister")) {
            this.primaryPanel.setVisible(false);
            this.regPanel.setVisible(true);
            this.regPanel.getTxtLoginId().requestFocus();
            this.getRootPane().setDefaultButton(this.regPanel.getBtnSubmit());

        } else if (e.getActionCommand().equals("primaryExit")) {
            // Should close sockets and streams here before exit
            System.exit(200);

        } else if (e.getActionCommand().equals("loginSubmit")) {
            // Check all fields are filled in with no white spaces            
            String loginId = loginPanel.getTxtLoginId().getText().trim();
            String password = loginPanel.getTxtPassword().getText().trim();

            if (!Helper.validateInput(loginId) || !Helper.validateInput(password)) {
                this.showInputAlertMsgBox();
                this.loginPanel.getTxtLoginId().requestFocus();
            } else {
                // Check if loged in successfull if not display message box close connection
                user = new User();
                user.setLoginId(loginId);
                user.setHashedPassword(password);

                try {
                    client = new Client(HOST, PORT);

                    user = client.login(user);

                    if (user == null) {
                        this.showAlertMsgBox("Login id and password combination not recognized.\nPlease try again.");
                        this.loginPanel.getTxtLoginId().requestFocus();
                        client.close();
                    } else {
                        this.loginPanel.getTxtLoginId().setText("");
                        this.loginPanel.getTxtPassword().setText("");

                        this.statusPanel.getLblGreeting().setText(String.format("Hello %s %s", user.getFirstName(), user.getLastName()));
                        this.statusPanel.getLblScreenName().setText(String.format("Screen name: %s", user.getScreenName()));

                        this.loginPanel.setVisible(false);
                        this.statusPanel.setVisible(true);
                    }
                } catch (IOException ex) {
                    this.showAlertMsgBox("Server not available.\n Please try again later");

                    this.loginPanel.getTxtLoginId().setText("");
                    this.loginPanel.getTxtPassword().setText("");

                    this.loginPanel.setVisible(false);
                    this.primaryPanel.setVisible(true);
                }

            }

        } else if (e.getActionCommand().equals("loginCancel")) {
            this.loginPanel.getTxtLoginId().setText("");
            this.loginPanel.getTxtPassword().setText("");
            this.loginPanel.setVisible(false);
            this.primaryPanel.setVisible(true);

        } else if (e.getActionCommand().equals("registerSubmit")) {
            // Check all fields are filled in with no white spaces
            String loginId = this.regPanel.getTxtLoginId().getText().trim();
            String password = this.regPanel.getTxtPassword().getText().trim();
            String confirm = this.regPanel.getTxtConfirm().getText().trim();
            String firstName = this.regPanel.getTxtFirstName().getText().trim();
            String lastName = this.regPanel.getTxtLastName().getText().trim();
            String screenName = this.regPanel.getTxtScreenName().getText().trim();

            if (!Helper.validateInput(loginId) | !Helper.validateInput(password)
                    | !Helper.validateInput(confirm) | !Helper.validateInput(firstName)
                    | !Helper.validateInput(lastName) | !Helper.validateInput(screenName)) {
                this.showInputAlertMsgBox();
                this.regPanel.getTxtLoginId().requestFocus();
            } else if (!password.equals(confirm)) {
                this.showPassConfirmAlertMsgBox();
                this.regPanel.getTxtPassword().requestFocus();
            } else {
                //create user object so password is hashed
                User regUser = new User();
                regUser.setLoginId(loginId);
                regUser.setHashedPassword(password);
                regUser.setFirstName(firstName);
                regUser.setLastName(lastName);
                regUser.setScreenName(screenName);

                Boolean result;

                try {
                    // open connection to the server
                    client = new Client(HOST, PORT);

                    // register user
                    result = client.registerUser(regUser);

                    // close connection and sockets
                    client.close();

                } catch (IOException ex) {
                    this.showAlertMsgBox("Server not available.\n Please try again later");
                    this.clearRegistrationPanel();
                    this.regPanel.setVisible(false);
                    this.primaryPanel.setVisible(true);
                    return;
                }

                // check result if failed display registration failied dialog
                // If succeded show alertdialog succeded and clear fields go to primary panel
                if (result) {
                    this.showAlertMsgBox("Registration Succeded you may now login.");
                    this.clearRegistrationPanel();
                    this.regPanel.setVisible(false);
                    this.primaryPanel.setVisible(true);
                } else {
                    this.showAlertMsgBox("Registration failed please choose a unique login id.");
                    this.regPanel.getTxtLoginId().requestFocus();
                }
            }

        } else if (e.getActionCommand().equals("registerCancel")) {
            this.clearRegistrationPanel();
            this.regPanel.setVisible(false);
            this.primaryPanel.setVisible(true);

        } else if (e.getActionCommand().equals("statusPreferences")) {
            // Load prefernces panel text fields except for pass and confirm with data from user object
            this.prefPanel.getTxtLoginId().setText(user.getLoginId());
            this.prefPanel.getTxtFirstName().setText(user.getFirstName());
            this.prefPanel.getTxtLastName().setText(user.getLastName());
            this.prefPanel.getTxtScreenName().setText(user.getScreenName());

            // Display preferences panel hide staus panel
            this.statusPanel.setVisible(false);
            this.prefPanel.setVisible(true);

        } else if (e.getActionCommand().equals("statusSelectRooms")) {
            
            
            //display select rooms panel hide status panel
            this.statusPanel.setVisible(false);
            this.srPanel.setVisible(true);

            // Add list of rooms to the list box in select rooms panel            
            this.updateRoomsList();            

        } else if (e.getActionCommand().equals("statusLogout")) {
            // remove user from all chat rooms

            // close sockets and streams
            client.close();

            // hide status panel show primary panel
            this.statusPanel.setVisible(false);
            this.primaryPanel.setVisible(true);

        } else if (e.getActionCommand().equals("prefSubmit")) {
            // check that all fields except pass and confirm are valid
            // check if pass text field has text if so check confirm and update user object password

            // send update message to the server
        } else if (e.getActionCommand().equals("prefCancel")) {
            // hide preferences panel show status panel
            this.prefPanel.setVisible(false);
            this.statusPanel.setVisible(true);

        } else if (e.getActionCommand().equals("selectRoomsEnter")) {
            // Check if something is selected if not display dialog
            if(this.srPanel.getLstSelectRoom().getSelectedIndex() == -1){
                this.showAlertMsgBox("You must select a room first");
                return;
            }
            
            // Remove room from the list and start new chat window
            String selected = (String)this.srPanel.getLstSelectRoom().getSelectedValue();
            Room room = null;
            
            for(Room r: this.rooms){
                if(r.getRoomName().equals(selected)){
                    room = r;
                }
            }
            
            @SuppressWarnings("null")
            JFrame chatWindow = new ChatWindow(room.getRoomName(), HOST, room.getPort(), user);
            
            chatWindow.addWindowListener(new WindowListener() {

                @Override
                public void windowOpened(WindowEvent e) {
                    
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    for(int i = 0; i < chatWindows.size(); i++){
                        if(!chatWindows.get(i).isShowing()){
                            chatWindows.remove(i);
                        }
                    }
                    
                    // upadate the list
                    updateRoomsList();
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
            });
            this.chatWindows.add(chatWindow);
            
            chatWindow.setVisible(true);
            
            this.updateRoomsList();
            
        } else if (e.getActionCommand().equals("selectRoomsCancel")) {
            // hide select rooms panel show status panel
            this.srPanel.setVisible(false);
            this.statusPanel.setVisible(true);

        } else if (e.getActionCommand().equals("selectRoomsCreateNewRoom")) {
            // Hide select rooms panel show create new room panel
            this.srPanel.setVisible(false);
            this.cnrPanel.setVisible(true);
            // set focus on text box
            this.cnrPanel.getTxtRoomName().requestFocus();
            // set create new room button as the default button
            this.getRootPane().setDefaultButton(this.cnrPanel.getBtnCreateRoom());

        } else if (e.getActionCommand().equals("createNewRoomCreateRoom")) {
            // check if input is valid
            if(!Helper.validateInput(this.cnrPanel.getTxtRoomName().getText().trim())){
                this.showInputAlertMsgBox();
                this.cnrPanel.getTxtRoomName().requestFocus();
                return;
            }
            
            try {
                if(client.createRoom(this.cnrPanel.getTxtRoomName().getText().trim(), user)){
                    this.showAlertMsgBox("Room Created Successfully.");
                    this.cnrPanel.setVisible(false);
                    this.srPanel.setVisible(true);
                    this.updateRoomsList();
                } else {
                    this.showAlertMsgBox("Room name is not unique try a diffrent name.");
                    this.cnrPanel.getTxtRoomName().requestFocus();
                }
            } catch (IOException ex) {
               this.showAlertMsgBox("Server not available.\n Please try again later");
               this.cnrPanel.setVisible(false);
               this.primaryPanel.setVisible(true);
            }
            
        } else if (e.getActionCommand().equals("createNewRoomCancel")) {
            // clear text box 
            this.cnrPanel.getTxtRoomName().setText("");

            // hide create new room panel and show select rooms panel
            this.cnrPanel.setVisible(false);
            this.srPanel.setVisible(true);
            
            // updated the room list in case another user has added a room
            this.updateRoomsList();
        }
    }

    private void clearRegistrationPanel() {
        this.regPanel.getTxtLoginId().setText("");
        this.regPanel.getTxtPassword().setText("");
        this.regPanel.getTxtConfirm().setText("");
        this.regPanel.getTxtFirstName().setText("");
        this.regPanel.getTxtLastName().setText("");
        this.regPanel.getTxtScreenName().setText("");
    }

    private void showAlertMsgBox(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private void showInputAlertMsgBox() {
        JOptionPane.showMessageDialog(this, "All fields must be filled in and have no spaces.");
    }

    private void showPassConfirmAlertMsgBox() {
        JOptionPane.showMessageDialog(this, "Your passwords do not match.\nPlease check your input.");
    }

    private void updateRoomsList() {
        try {
            // query the server for room list
            rooms = client.getRegisteredRooms(user);
            
            // Create default list model and populate
            listModel = new DefaultListModel();
            
            for(Room room: rooms){                
                listModel.addElement(room.getRoomName());
            }                        
            
            for(int i = 0; i < listModel.getSize(); i++){
                for(JFrame cw: this.chatWindows){
                    if(cw.getTitle().equals(listModel.get(i))){
                        listModel.remove(i);
                    }
                }
            }
            
            this.srPanel.getLstSelectRoom().setModel(listModel);
            
        } catch (IOException e) {
            // server disconected go back to the login screen
            this.showAlertMsgBox("Server not available.\n Please try again later");
            this.srPanel.setVisible(false);
            this.primaryPanel.setVisible(true);
            client.close();
        }
    }
}
