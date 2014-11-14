package com.cst242.finalproject.server.model;

/**
 * This class implements the {@code FileIOInterface} refer to that for 
 * documentation.
 * 
 * @author James DeCarlo
 */
public class FileIO implements FileIOInterface{

    @Override
    public boolean register(String loginId, int hashedPassword, String firstName, String lastName, String screenName) {
        return false;
    }

    @Override
    public boolean deregister(int accountNumber) {
        return false;
    }

    @Override
    public User loginUser(String loginId, int hashedPassword) {
        return null;
    }

    @Override
    public boolean updateUser(String accountNumber, int hashedPassword, String firstName, String lastName, String screenName) {
        return false;
    }
    
}
