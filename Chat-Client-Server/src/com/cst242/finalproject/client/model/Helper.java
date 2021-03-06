package com.cst242.finalproject.client.model;

import java.util.Date;

/**
 * This class is a assortment of static methods for validations and various
 * other methods as needed.
 * 
 * @author James DeCarlo
 */
public class Helper {
    
    /**
     * Checks that string is not {@code null} and that it contains no 
     * white spaces and no '\'.
     * @param str the string to check
     * @return {@code true} if the string is not empty and has no spaces and '\'
     */
    public static boolean validateInput(String str){
        if(str == null || str.length() == 0){
            return false;
        }
        
        int strLen = str.length();
        for(int i = 0; i < strLen; i++){
            char c = str.charAt(i);
            if(Character.isWhitespace(c) || c == '\\'){
                return false;
            }
        }               
        return true;
    }
    
    /**
     * Splits a given string by white spaces into an array.
     * @param str The string to be split.
     * @return An array of the given string split by white spaces.
     */
    public static String[] splitString(String str){
        return str.split("\\s+");
    }
    
    /**
     * Creates a timestamp in the following format (hh:mm:ss).
     * 
     * @return the current timestamp
     */
    public static String currentTimeStamp(){        
        Date now = new Date();
                        
        return String.format("(%02d:%02d:%02d)", now.getHours(), now.getMinutes(), now.getSeconds());
    }
}
