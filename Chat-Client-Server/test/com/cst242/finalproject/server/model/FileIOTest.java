/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cst242.finalproject.server.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author James Decarlo
 */
public class FileIOTest {
    
    /**
     * Constructor for FileIOTest class 
     */
    public FileIOTest() {
    }

    /**
     * Test of register method, of class FileIO.
     */
    @Test
    public void testRegister() {
        System.out.println("register");
        String loginId = "test25";
        int hashedPassword = 6973;
        String firstName = "Tom";
        String lastName = "Jones";
        String screenName = "Tom_Jones";
        FileIO instance1 = new FileIO();
        boolean expResult = false;
        boolean result = instance1.register(loginId, hashedPassword, firstName, lastName, screenName);
        assertEquals(expResult, result);        
    }    
        
    /**
     * Test of loginUser method, of class FileIO.
     */
    
    @Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String loginId = "unknown";
        int hashedPassword = 0;
        FileIO instance = new FileIO();
        User expResult = null;
        User result = instance.loginUser(loginId, hashedPassword);
        assertEquals(expResult, result);
        
        System.out.println("loginUser2");
        loginId = "test25";
        hashedPassword = 6973;
        
        result = instance.loginUser(loginId, hashedPassword);
        assertEquals(expResult, result);        
        
    }

    /**
     * Test of updateUser method, of class FileIO.
     */
    @Test
    public void testUpdateUser() {
        System.out.println("updateUser");
        
        User user = new FileIO().loginUser("test25", 6955);
        
        int accountNumber = user.getAccountNumber();
        int hashedPassword = 6955;
        String firstName = "Evan111";
        String lastName = "Vice111";
        String screenName = "EVice255";
        FileIO instance = new FileIO();
        boolean expResult = true;
        boolean result = instance.updateUser(accountNumber, hashedPassword, firstName, lastName, screenName);
        assertEquals(expResult, result);
        
        user = new FileIO().loginUser("test25", 6955);
        if(!firstName.equals(user.getFirstName()) || !screenName.equals(user.getScreenName())){
            fail("Failed to update the user");
        }
    }
       
}
