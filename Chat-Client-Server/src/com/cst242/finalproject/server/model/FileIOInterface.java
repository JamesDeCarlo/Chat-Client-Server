package com.cst242.finalproject.server.model;

/**
 * This is the interface for the File I/O used by the chat server
 * 
 * @author James DeCarlo
 */
public interface FileIOInterface {

    /**
     * Save a new {@code User} object to the "userlist.dat" file if and only 
     * if the user {@code loginId} is unique in the "userlist.dat" file. To 
     * create the {@code User} object you need to user all the given parameter 
     * they can not be null and also create a unique {@code accountNumber} 
     * starting at 1001 and incrementing from there.
     *  
     * @param loginId User login id must be unique not null
     * @param hashedPassword User encrypted hashed password can't be null
     * @param firstName User first name can't be null
     * @param lastName User last name can't be null
     * @param screenName User screen name can't be null
     * @return Returns {@code true} if the user is added to the file 
     * successfully. Else it returns {@code false}.
     */
    public boolean register(String loginId, int hashedPassword, 
            String firstName, String lastName, String screenName);
    
    
    /**
     * Checks to see if the credentials given match one of the records in the 
     * "userlist.dat" file if so returns the {@code User} object. If the 
     * credentials do not match returns null.
     * 
     * @param loginId Users login id
     * @param hashedPassword Users hashed password
     * @return {@code User} if credentials match null otherwise.
     */
    public User loginUser(String loginId, int hashedPassword);
    
    /**
     * Update the user information in the "userlist.dat" file. The account 
     * number and login id will not be changed. Use the accountNumber to
     * update the proper user information.
     * 
     * @param accountNumber Users account number to locate the record
     * @param hashedPassword Users hashed password new or current
     * @param firstName Users first name new or current
     * @param lastName Users last name new or current
     * @param screenName Users screen name new or current
     * @return Returns true if updated successfully otherwise false;
     */
    public boolean updateUser(int accountNumber, int hashedPassword, 
            String firstName, String lastName, String screenName);
    
}
