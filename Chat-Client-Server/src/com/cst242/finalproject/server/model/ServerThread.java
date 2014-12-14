package com.cst242.finalproject.server.model;

import com.cst242.finalproject.server.viewcontroller.PrimaryWindow;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the main server thread class. It is responsible for handling request
 * from the client.
 * 
 * @author James DeCarlo
 */
public class ServerThread extends Thread {

    private final PrimaryWindow window;

    private final List<ServerRoom> rooms;

    private final Socket clientSocket;
    private DataInputStream fromClient;
    private DataOutputStream toClient;

    /**
     * Create a new server thread object.
     * 
     * @param clientSocket The client socket created on connection.
     * @param window The primary window for logging purposes.
     * @param rooms The chat rooms list so we can add to it on client request.
     */
    public ServerThread(Socket clientSocket, PrimaryWindow window, List<ServerRoom> rooms) {
        this.clientSocket = clientSocket;
        this.window = window;
        this.rooms = rooms;
    }

    @Override
    public void run() {
        try {
            fromClient = new DataInputStream(this.clientSocket.getInputStream());
            toClient = new DataOutputStream(this.clientSocket.getOutputStream());
            window.appendLog("Client Connected:  %s%n", new Date());

            for (;;) {
                // Start listening for request
                String request = fromClient.readUTF();
                this.processRequest(Helper.splitString(request));
                
            }

        } catch (IOException ex) {
            window.appendLog("Client disconected : %s%n", new Date());
            
            // close streams
            try {
                if (fromClient != null) {
                    fromClient.close();
                }                
            } catch (IOException e) {
                //do nothing
            }
            
            try{
            if (toClient != null) {
                    toClient.close();
                }
            } catch (IOException e){
                // do nothing
            }
            
            
            try{
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch(IOException e){
                //do nothing
            }
        }
    }

    /**
     * Process the clients request and calls the corresponding method.
     *
     * @param request the request array
     */
    public void processRequest(String[] request) {
        if (request[0].equals("REGISTER")) {
            this.register(request);
        } else if (request[0].equals("LOGIN")) {
            this.login(request);
        } else if (request[0].equals("ROOMS")) {
            this.getRooms(request);
        } else if (request[0].equals("CREATE_ROOM")) {
            this.createRoom(request);
        } else if (request[0].equals("UPDATE")){
            this.updateUser(request);
        }
    }

    /**
     * Registers a new user
     *
     * @param request the request array
     */
    public void register(String[] request) {
        FileIO fileIO = new FileIO();

        try {
            if (request.length == 6 & fileIO.register(request[1], Integer.parseInt(request[2]), request[3], request[4], request[5])) {
                window.appendLog("Registered new user %s : %s%n", request[1], new Date());
                toClient.writeUTF("SUCCESS");
            } else {
                toClient.writeUTF("FAILED");
            }
        } catch (IOException e) {
            window.appendLog("Failed to send register status message to client: %s%n", new Date());
        }
    }

    /**
     * Logs a user in to the server
     *
     * @param request the request array
     */
    public void login(String[] request) {
        FileIO fileIO = new FileIO();
        try {
            if (request.length != 3) {
                toClient.writeUTF("FAILED");
            } else {
                User user = fileIO.loginUser(request[1], Integer.parseInt(request[2]));

                if (user == null) {
                    toClient.writeUTF("FAILED");
                } else {
                    StringBuilder retmsg = new StringBuilder();
                    retmsg.append("USER ");
                    retmsg.append(user.getAccountNumber());
                    retmsg.append(" ");
                    retmsg.append(user.getFirstName());
                    retmsg.append(" ");
                    retmsg.append(user.getLastName());
                    retmsg.append(" ");
                    retmsg.append(user.getScreenName());

                    toClient.writeUTF(retmsg.toString());

                    window.appendLog("User '%s' loged in to the chat server: %s%n", user.getLoginId(), new Date());
                }
                toClient.flush();
            }
        } catch (IOException e) {
            window.appendLog("Failed to send login message to client : %s%n", new Date());
        }
    }
    
    /**
     * Sends success or failed if the user is updated.
     * 
     * @param request the request array
     */
    public void updateUser(String[] request){
        FileIO fileIO = new FileIO();
        try {
            if(request.length == 6 & fileIO.updateUser(Integer.parseInt(request[1]), Integer.parseInt(request[2]), request[3], 
                    request[4], request[5])) {
                
                    StringBuilder retmsg = new StringBuilder();
                    retmsg.append("SUCCESS ");
                    retmsg.append(request[1]);
                    retmsg.append(" ");
                    retmsg.append(request[2]);
                    retmsg.append(" ");
                    retmsg.append(request[3]);
                    retmsg.append(" ");
                    retmsg.append(request[4]);
                    retmsg.append(" ");
                    retmsg.append(request[5]);
                    toClient.writeUTF(retmsg.toString());
            }         
            window.appendLog("User '%s' has been updated to the server: %s%n" , request[5], new Date());

        } catch(IOException e){
            window.appendLog("Failed to update client: %s%n", new Date());            
        } 
}        

    /**
     * Sends the list of available rooms to the user.
     *
     * @param request the request array
     */
    public void getRooms(String[] request) {

        try {
            if (request.length != 2) {
                toClient.writeUTF("FAILED");
                toClient.flush();
                return;
            }

            for (ServerRoom room : this.rooms) {
                String msg = String.format("ROOM %s %d", room.getRoomName(), room.getPort());
                toClient.writeUTF(msg);
                toClient.flush();
            }

            toClient.writeUTF("END");
            toClient.flush();
            window.appendLog("Sent rooms list to User %s: %s%n", request[1], new Date());

        } catch (IOException e) {
            window.appendLog("Failed to send room list to user %s: %s%n", request[1], new Date());
        }
    }

    /**
     * Checks if room name is unique if so creates new chat room
     *
     * @param request the request array
     */
    public void createRoom(String[] request) {
        synchronized (this) {
            try {
                if (request.length != 3) {
                    toClient.writeUTF("FAILED");
                    toClient.flush();
                    return;
                }

                for (ServerRoom room : rooms) {
                    if (room.getRoomName().toLowerCase().equals(request[1].toLowerCase())) {
                        toClient.writeUTF("FAILED");
                        toClient.flush();
                        return;
                    }
                }

                // room is unique create room and send success to client
                ServerRoom room = new ServerRoom(request[1], rooms.get(rooms.size() - 1).getPort() + 1, window);
                room.start();

                this.sleep();

                this.rooms.add(room);
                
                toClient.writeUTF("SUCCESS");
                toClient.flush();
                
                window.appendLog("New room %s created by user %s: %s%n", request[1], request[2], new Date());

            } catch (IOException e) {
                window.appendLog("Failed to send create room message: %s%n", new Date());
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
