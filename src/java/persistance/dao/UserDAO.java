/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistance.dao;

import model.User;
import java.sql.SQLException;

/**
 *
 * @author Thanuja Fernando
 */
public interface UserDAO {
    boolean registerUser(User user)throws SQLException;
    boolean checkUserExists(String email) throws SQLException;
    User authenticateUser(String email, String password);
    boolean deleteUser(int userId) throws SQLException;
}
