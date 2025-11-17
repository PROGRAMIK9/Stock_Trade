package com.stocktrading.interfaces;

import com.stocktrading.models.User;

/**
 * Interface demonstrating abstraction for authentication operations
 */
public interface AuthService {
    User login(String username, String password);
    boolean register(String username, String password, String email);
    void logout(User user);
    boolean isAuthenticated(User user);
    boolean userExists(String username);
    boolean changePassword(User user, String oldPassword, String newPassword);  
}
