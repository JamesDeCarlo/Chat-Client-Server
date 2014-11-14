package com.cst242.finalproject.client.model;

/**
 * This class is a assortment of static methods for validations and various
 * other methods as needed.
 * 
 * @author James DeCarlo
 */
public class Helper {
    
    /**
     * Checks that string is not {@code null} and that it contains no 
     * white spaces.
     * @param str the string to check
     * @return {@code true} if the string is not empty and has no spaces
     */
    public static boolean validateInput(String str){
        if(str == null || str.length() == 0){
            return false;
        }
        
        int strLen = str.length();
        for(int i = 0; i < strLen; i++){
            if(Character.isWhitespace(str.charAt(i))){
                return false;
            }
        }
        
        
        return true;
    }
    
    public static String[] splitString(String str){
        return str.split("\\s+");
    }
}
