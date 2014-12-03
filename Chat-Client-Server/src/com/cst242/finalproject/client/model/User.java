package com.cst242.finalproject.client.model;

/**
 * This is a POJO class for a user object, which also handles password 
 * encryption.
 * 
 * @author James DeCarlo
 */
public class User {
    private int accountNumber;
    private String loginId;
    private int hashedPassword;
    private String firstName;
    private String lastName;
    private String screenName;

    /**
     * Creates the base {@code User} object.
     */
    public User(){
        
    }
    
    /**
     * Create the base {@code User} object. The password given will be hashed. 
     * 
     * @param loginId Users login id
     * @param password Users password. Will be hashed.
     */
    public User(String loginId, String password) {
        this.loginId = loginId;
        this.hashedPassword = this.hashPassword(password);
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
     * @param password Users password
     */
    public void setHashedPassword(String password) {
        this.hashedPassword = this.hashPassword(password);
    }
    
    /**
     * Sets the hashed password.
     * @param hashedPassword The hashed password.
     */
    public void setHashedPassword(int hashedPassword){
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
    
    private int hashPassword(String password){
        return password.hashCode();
    }
}
