/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.services;

import model.User;
import persistance.dao.UserDAO;
import persistance.impl.UserDAOImpl;
import java.sql.SQLException;

/**
 *
 * @author Thanuja Fernando
 */
public class UserService {
    private UserDAO userDAO;
    
    public UserService() {
        this.userDAO = new UserDAOImpl();  
    }
    
    public boolean registerUser(User user) throws SQLException {
        if (userDAO.checkUserExists(user.getEmail())) {
            return false;  
        }
        return userDAO.registerUser(user);
    }
    
    public boolean checkUserExists(String email) throws SQLException {
        return userDAO.checkUserExists(email);  
    }
    
    public User authenticateUser(String email, String password) throws SQLException {
        return userDAO.authenticateUser(email, password);
    }
}
