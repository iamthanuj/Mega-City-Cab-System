/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistance.dao;

import model.User;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Thanuja Fernando
 */
public interface UserDAO {
    boolean registerUser(User user)throws SQLException;
    boolean checkUserExists(String email) throws SQLException;
    User authenticateUser(String email, String password);
    List<User> getAllUsers() throws SQLException;
    boolean updateUser(User user) throws SQLException;
    boolean deleteUser(int userId) throws SQLException;
    User getUserById(int userId) throws SQLException;
}
