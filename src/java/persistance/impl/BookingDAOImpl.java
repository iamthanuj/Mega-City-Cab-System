/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistance.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import persistance.dao.BookingDAO;
import service.factory.VehicleFactory;
import service.model.Booking;
import service.model.Vehicle;
import util.DBConnection;

/**
 *
 * @author Thanuja Fernando
 */
public class BookingDAOImpl implements BookingDAO {

    @Override
    public boolean addBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings (UserId, VehicleType, Distance, TotalCost, StartLocation, EndLocation, DateTime, Address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
    public List<Booking> getAllBookings(int userId) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE UserId = ? ORDER BY datetime DESC";
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bookings.add(createBookingFromResultSet(rs));
            }
            return bookings;
        }
    }

    @Override
    public boolean deleteBooking(int bookingId) throws SQLException {
        String sql = "DELETE FROM bookings WHERE BookingId = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    @Override
    public boolean updateBooking(Booking booking) throws SQLException {
        String sql = "UPDATE bookings SET VehicleType = ?, Distance = ?, TotalCost = ?, "
                + "StartLocation = ?, EndLocation = ?, DateTime = ?, Address = ? "
                + "WHERE BookingId = ? AND UserId = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, booking.getVehicle().getType());
            pstmt.setDouble(2, booking.getDistance());
            pstmt.setDouble(3, booking.getTotalCost());
            pstmt.setString(4, booking.getStartLocation());
            pstmt.setString(5, booking.getEndLocation());
            pstmt.setTimestamp(6, Timestamp.valueOf(booking.getDatetime()));
            pstmt.setString(7, booking.getAddress());
            pstmt.setInt(8, booking.getId());
            pstmt.setInt(9, booking.getUserId());
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    @Override
    public List<Booking> getAllBookings() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    private Booking createBookingFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        String vehicleType = rs.getString("vehicle_type");
        double distance = rs.getDouble("distance");
        double totalCost = rs.getDouble("total_cost");
        String startLocation = rs.getString("start_location");
        String endLocation = rs.getString("end_location");
        Timestamp datetime = rs.getTimestamp("datetime");
        String address = rs.getString("address");

        Vehicle vehicle = VehicleFactory.getVehicle(vehicleType);
        Booking booking = new Booking(userId, vehicle, distance, totalCost, startLocation, endLocation, 
                                     datetime.toLocalDateTime(), address);
        booking.setId(id);
        return booking;
    }

}
