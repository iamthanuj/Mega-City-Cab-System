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
import java.util.ArrayList;
import java.util.List;
import model.User;
import util.DBConnection;

/**
 *
 * @author Thanuja Fernando
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public boolean registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (FullName, NIC, Email, Phone, Password, Role) VALUES (?, ?, ?, ?, ?, ?)";
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

    @Override
    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE UserId = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(rs.getString("FullName"), rs.getString("NIC"), rs.getString("Email"), rs.getInt("Phone"), rs.getString("Password"));
                user.setId(rs.getInt("UserId"));
                user.setRole(rs.getString("Role"));
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET FullName = ?, NIC = ?, Email = ?, Phone = ?, Password = ?, Role = ? WHERE UserId = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getNic());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getPhone());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, user.getRole());
            pstmt.setInt(7, user.getId());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    @Override
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE UserId = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(rs.getString("FullName"), rs.getString("NIC"), rs.getString("Email"),
                            rs.getInt("Phone"), rs.getString("Password"));
                    user.setId(rs.getInt("UserId"));
                    user.setRole(rs.getString("Role"));
                    return user;
                }
            }
        }
        return null;
    }

}
