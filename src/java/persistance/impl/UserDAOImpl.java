/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistance.impl;

import persistance.dao.UserDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;
import util.DBConnection;

/**
 *
 * @author Thanuja Fernando
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public boolean registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (FullName, NIC,Email,Phone,Password,Role) VALUES (?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getNic());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getPhone());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, user.getRole());

            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        }
    }

    @Override
    public boolean checkUserExists(String email) throws SQLException {
        String sql = "SELECT UserId FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    @Override
    public User authenticateUser(String email, String password) {
        User user = null;
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                String storedPassword = rs.getString("Password");

                if (storedPassword.equals(password)) {
                    user = new User(rs.getString("FullName"), rs.getString("NIC"), rs.getString("Email"), rs.getInt("Phone"), rs.getString("Password"));
                    user.setId(rs.getInt("UserId"));
                    user.setRole(rs.getString("Role"));
                    return user;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
