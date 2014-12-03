package com.cst242.finalproject.client.model;

/**
 * This is a POJO class for a room object
 * 
 * @author James DeCarlo
 */
public class Room {
   private String roomName;
   private int port;   

    /**
     * Creates a room object with all fields filled in
     * 
     * @param roomName The name of the room
     * @param port  The port number of the room
     */
    public Room(String roomName, int port) {
        this.roomName = roomName;
        this.port = port;
    }

    /**
     * Gets the room name of the room.
     * 
     * @return The room name
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Sets the room name of the room.
     * 
     * @param roomName The room name
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Gets the port number of the room.
     * 
     * @return The port number
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the port number of the room.
     * 
     * @param port the port number
     */
    public void setPort(int port) {
        this.port = port;
    }
   
   
}
