package com.desktopapp;

public class MyPasswordValidator {
    
    public static boolean Validate(String password) {
        if (password.length() < 5)
            return false;
        
        if (password.length() > 12)
            return true;
        
        return password.chars().anyMatch(c -> c >= '0' && c <= '9');
    }
}
