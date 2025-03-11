/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistance.impl;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, driver.getName());
            pstmt.setString(2, driver.getLicenseNumber());
            pstmt.setInt(3, driver.getPhone());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public List<Driver> getAllDrivers() throws SQLException {
        String sql = "SELECT * FROM drivers";
        List<Driver> drivers = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Driver driver = new Driver(rs.getString("Name"), rs.getString("LicenseNumber"),
                        rs.getInt("Phone"));
                driver.setId(rs.getInt("DriverId"));
                drivers.add(driver);
            }
            return drivers;
        }
    }

}
