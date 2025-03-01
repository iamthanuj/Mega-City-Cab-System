/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistance.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import persistance.dao.BookingDAO;
import service.model.Booking;
import util.DBConnection;

/**
 *
 * @author Thanuja Fernando
 */
public class BookingDAOImpl implements BookingDAO {

    @Override
    public boolean addBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings (UserId, VehicleType, Distance, TotalCost, StartLocation, EndLocation, DateTime, Address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, booking.getUserId());                     
            pstmt.setString(2, booking.getVehicle().getType());       
            pstmt.setDouble(3, booking.getDistance());                
            pstmt.setDouble(4, booking.getTotalCost());              
            pstmt.setString(5, booking.getStartLocation());           
            pstmt.setString(6, booking.getEndLocation());             
            pstmt.setTimestamp(7, Timestamp.valueOf(booking.getDatetime())); 
            pstmt.setString(8, booking.getAddress());                 

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            throw new SQLException("Failed to add booking: " + e.getMessage(), e);
        }
    }

    @Override
    public Booking getBookingById(int bookingId) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Booking> getAllBookings() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean deleteBooking(int bookingId) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
