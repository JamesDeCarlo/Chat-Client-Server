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
    
    public FileIOTest() {
    }

    /**
     * Test of register method, of class FileIO.
     */
    @Test
    public void testRegister() {
        System.out.println("register");
        String loginId = "test232";
        int hashedPassword = 6969;
        String firstName = "John";
        String lastName = "Doe";
        String screenName = "sir_john_doe";
        FileIO instance = new FileIO();
        boolean expResult = true;
        boolean result = instance.register(loginId, hashedPassword, firstName, lastName, screenName);
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
        
        loginId = "test212";
        hashedPassword = 6969;
        
        if(instance.loginUser(loginId, hashedPassword) == null){
            fail("Login Failed");
        }
        
    }

    /**
     * Test of updateUser method, of class FileIO.
     */
    @Test
    public void testUpdateUser() {
        System.out.println("updateUser");
        
        User user = new FileIO().loginUser("test212", 6969);
        
        if(user == null){
            fail("Failed to login user");
            
        }
        
        int accountNumber = user.getAccountNumber();
        int hashedPassword = 6969;
        String firstName = "Jane";
        String lastName = "Doe";
        String screenName = "Jane_Doe";
        FileIO instance = new FileIO();
        boolean expResult = true;
        boolean result = instance.updateUser(accountNumber, hashedPassword, firstName, lastName, screenName);
        assertEquals(expResult, result);
        
        user = new FileIO().loginUser("test212", 6969);
        if(!firstName.equals(user.getFirstName()) || !screenName.equals(user.getScreenName())){
            fail("Failed to update the user");
        }
    }
       
}
