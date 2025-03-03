/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistance.dao;

import service.model.Booking;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Thanuja Fernando
 */
public interface BookingDAO {

    boolean addBooking(Booking booking) throws SQLException;

    Booking getBookingById(int bookingId) throws SQLException;

    List<Booking> getAllBookings() throws SQLException;
    
    List<Booking> getAllBookings(int userId) throws SQLException;

    boolean deleteBooking(int bookingId) throws SQLException;
    
    boolean updateBooking(Booking booking) throws SQLException;
    
}
