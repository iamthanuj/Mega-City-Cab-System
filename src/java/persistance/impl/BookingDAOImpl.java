/*
 * Click nbfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://SystemFileSystem/Templates/Classes/Class.java to edit this template
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
        String sql = "INSERT INTO bookings (UserId, VehicleType, Distance, TotalCost, StartLocation, EndLocation, DateTime, Address, Status, DriverId) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, booking.getUserId());
            pstmt.setString(2, booking.getVehicle().getType());
            pstmt.setDouble(3, booking.getDistance());
            pstmt.setDouble(4, booking.getTotalCost());
            pstmt.setString(5, booking.getStartLocation());
            pstmt.setString(6, booking.getEndLocation());
            pstmt.setTimestamp(7, Timestamp.valueOf(booking.getDatetime()));
            pstmt.setString(8, booking.getAddress());
            pstmt.setString(9, booking.getStatus());
            if (booking.getDriverId() != null) {
                pstmt.setInt(10, booking.getDriverId());
            } else {
                pstmt.setNull(10, Types.INTEGER);
            }

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    booking.setBookingId(rs.getInt(1)); // Set the generated BookingId
                }
            }
            return rowsInserted > 0;
        } catch (SQLException e) {
            throw new SQLException("Failed to add booking: " + e.getMessage(), e);
        }
    }

    @Override
    public Booking getBookingById(int bookingId) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE BookingId = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createBookingFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new SQLException("Failed to get booking by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Booking> getAllBookings(int userId) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE UserId = ? ORDER BY DateTime DESC";
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bookings.add(createBookingFromResultSet(rs));
            }
            return bookings;
        } catch (SQLException e) {
            throw new SQLException("Failed to get all bookings: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Booking> getAllBookings() throws SQLException {
        String sql = "SELECT * FROM bookings ORDER BY DateTime DESC";
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        } catch (SQLException e) {
            throw new SQLException("Failed to delete booking: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateBooking(Booking booking) throws SQLException {
        String sql = "UPDATE bookings SET VehicleType = ?, Distance = ?, TotalCost = ?, "
                + "StartLocation = ?, EndLocation = ?, DateTime = ?, Address = ?, "
                + "Status = ?, DriverId = ? "
                + "WHERE BookingId = ? AND UserId = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, booking.getVehicle().getType());
            pstmt.setDouble(2, booking.getDistance());
            pstmt.setDouble(3, booking.getTotalCost());
            pstmt.setString(4, booking.getStartLocation());
            pstmt.setString(5, booking.getEndLocation());
            pstmt.setTimestamp(6, Timestamp.valueOf(booking.getDatetime()));
            pstmt.setString(7, booking.getAddress());
            pstmt.setString(8, booking.getStatus());
            if (booking.getDriverId() != null) {
                pstmt.setInt(9, booking.getDriverId());
            } else {
                pstmt.setNull(9, Types.INTEGER);
            }
            pstmt.setInt(10, booking.getBookingId());
            pstmt.setInt(11, booking.getUserId());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new SQLException("Failed to update booking: " + e.getMessage(), e);
        }
    }

    private Booking createBookingFromResultSet(ResultSet rs) throws SQLException {
        int bookingId = rs.getInt("BookingId");
        int userId = rs.getInt("UserId");
        String vehicleType = rs.getString("VehicleType");
        double distance = rs.getDouble("Distance");
        double totalCost = rs.getDouble("TotalCost");
        String startLocation = rs.getString("StartLocation");
        String endLocation = rs.getString("EndLocation");
        Timestamp datetime = rs.getTimestamp("DateTime");
        String address = rs.getString("Address");
        String status = rs.getString("Status");
        Integer driverId = rs.getObject("DriverId", Integer.class); // Handles null

        Vehicle vehicle = VehicleFactory.getVehicle(vehicleType);
        Booking booking = new Booking(userId, vehicle, distance, totalCost, startLocation,
                endLocation, datetime.toLocalDateTime(), address);
        booking.setBookingId(bookingId);
        booking.setStatus(status);
        booking.setDriverId(driverId);
        return booking;
    }
}
