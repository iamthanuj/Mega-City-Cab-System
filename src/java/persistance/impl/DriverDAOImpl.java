/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistance.impl;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import persistance.dao.DriverDAO;
import service.model.Driver;
import util.DBConnection;

/**
 *
 * @author Thanuja Fernando
 */
public class DriverDAOImpl implements DriverDAO {

    @Override
    public boolean addDriver(Driver driver) throws SQLException {
        String sql = "INSERT INTO drivers (Name, LicenseNumber, Phone) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, driver.getName());
            pstmt.setString(2, driver.getLicenseNumber()); // Matches the Driver class field
            pstmt.setInt(3, driver.getPhone());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        driver.setId(generatedKeys.getInt(1));
                    }
                }
            }
            return rowsAffected > 0;
        }
    }

    @Override
    public List<Driver> getAllDrivers() throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM drivers";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Driver driver = new Driver(
                        rs.getString("Name"),
                        rs.getString("LicenseNumber"), // Matches the DB column name
                        rs.getInt("Phone")
                );
                driver.setId(rs.getInt("DriverId"));
                drivers.add(driver);
            }
        }
        return drivers;
    }

    @Override
    public boolean updateDriver(Driver driver) throws SQLException {
        String sql = "UPDATE drivers SET Name = ?, LicenseNumber = ?, Phone = ? WHERE DriverId = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, driver.getName());
            pstmt.setString(2, driver.getLicenseNumber()); // Matches the Driver class field
            pstmt.setInt(3, driver.getPhone());
            pstmt.setInt(4, driver.getId());
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    @Override
    public boolean deleteDriver(int driverId) throws SQLException {
        String sql = "DELETE FROM drivers WHERE DriverId = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, driverId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        }
    }

}
