package com.cst242.finalproject.server.model;

import java.io.Serializable;

/**
 * This is the base user class it is serializable so it can be saved to file.
 * 
 * @author James DeCarlo
 */
public class User implements Serializable {
    private int accountNumber;
    private String loginId;
    private int hashedPassword;
    private String firstName;
    private String lastName;
    private String screenName;

    /**
     * Creates the base {@code User} object. 
     * 
     * @param accountNumber User account number not null
     * @param loginId User login id not null
     * @param hashedPassword User hashed password not null
     * @param firstName User first name not null
     * @param lastName User last name not null
     * @param screenName User screen name not null
     */
    public User(int accountNumber, String loginId, int hashedPassword, String firstName, String lastName, String screenName) {
        this.accountNumber = accountNumber;
        this.loginId = loginId;
        this.hashedPassword = hashedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.screenName = screenName;
    }

    /**
     * Returns the users account number
     * @return Users account number
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * Set the users account number
     * @param accountNumber User account number 
     */
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Returns the users login id
     * @return Users login id
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * Sets the users login id
     * @param loginId Users login id
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * Returns users hashed password
     * @return Users hashed password
     */
    public int getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Sets users hashed password
     * @param hashedPassword Users hashed password
     */
    public void setHashedPassword(int hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * Returns users first name
     * @return Users first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets users first name
     * @param firstName Users first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns users last name
     * @return Users last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets users last name
     * @param lastName Users last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns users screen name
     * @return Users screen name
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     * Sets users screen name
     * @param screenName Users screen name
     */
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }            
}
