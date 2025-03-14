/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package persistance.impl;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import model.User;
import util.DBConnection;
import persistance.dao.BookingDAO;
import service.factory.VehicleFactory;
import service.model.Booking;
import service.model.Vehicle;

/**
 *
 * @author Thanuja Fernando
 */
public class BookingDAOImplTest {

    private BookingDAO bookingDAO;
    private UserDAOImpl userDAO;
    private Connection conn;
    private static final String DEFAULT_VEHICLE_TYPE = "Car";
    private static final String DEFAULT_STATUS = "Pending";
    private static final String DEFAULT_ROLE = "USER";
    private int testUserId;

    @BeforeClass
    public static void setUpClass() throws SQLException {
        // Assuming DBConnection is configured for test database
    }

    @AfterClass
    public static void tearDownClass() throws SQLException {
        // Any cleanup if needed
    }

    @Before
    public void setUp() throws SQLException {
        bookingDAO = new BookingDAOImpl();
        userDAO = new UserDAOImpl();
        conn = DBConnection.getInstance().getConnection();

       
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM bookings");
            stmt.execute("ALTER TABLE bookings AUTO_INCREMENT = 1");
            stmt.execute("DELETE FROM users");
            stmt.execute("ALTER TABLE users AUTO_INCREMENT = 1");
        }

        
        User testUser = new User("Nimal Perera", "199012345678", "nimal.perera@gmail.com", 0711234567, "password123");
        testUser.setRole(DEFAULT_ROLE);
        userDAO.registerUser(testUser);
        testUserId = userDAO.authenticateUser("nimal.perera@gmail.com", "password123").getId();
    }

    @After
    public void tearDown() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    @Test
    public void testAddBooking() throws SQLException {
        Vehicle vehicle = VehicleFactory.getVehicle(DEFAULT_VEHICLE_TYPE);
        Booking booking = new Booking(testUserId, vehicle, 10.0, 1000.0,
                "Colombo 7", "Nugegoda", LocalDateTime.now(), "25 Galle Road, Colombo");

        boolean result = bookingDAO.addBooking(booking);
        assertTrue("Booking should be added successfully", result);
        System.out.println("testAddBooking: Booking added successfully with ID: " + booking.getBookingId());

        assertTrue("Booking ID should be set after insertion", booking.getBookingId() > 0);
        System.out.println("testAddBooking: Booking ID confirmed as: " + booking.getBookingId());

        
        Booking retrieved = bookingDAO.getBookingById(booking.getBookingId());
        assertNotNull("Booking should be retrievable", retrieved);
        assertEquals("User ID should match", testUserId, retrieved.getUserId());
        System.out.println("testAddBooking: Booking retrieved successfully, User ID matches: " + retrieved.getUserId());
    }

    @Test
    public void testGetBookingById() throws SQLException {
        Vehicle vehicle = VehicleFactory.getVehicle(DEFAULT_VEHICLE_TYPE);
        Booking booking = new Booking(testUserId, vehicle, 15.0, 1500.0,
                "Kandy", "Peradeniya", LocalDateTime.now(), "10 Temple Road, Kandy");
        bookingDAO.addBooking(booking);

        Booking retrieved = bookingDAO.getBookingById(booking.getBookingId());
        assertNotNull("Booking should be found", retrieved);
        assertEquals("Distance should match", 15.0, retrieved.getDistance(), 0.001);
        assertEquals("Start location should match", "Kandy", retrieved.getStartLocation());
        System.out.println("testGetBookingById: Booking retrieved with ID: " + retrieved.getBookingId() +
                ", Distance: " + retrieved.getDistance() + ", Start Location: " + retrieved.getStartLocation());

        
        Booking notFound = bookingDAO.getBookingById(999);
        assertNull("Non-existent booking should return null", notFound);
        System.out.println("testGetBookingById: Non-existent booking (ID 999) correctly returned null");
    }

    @Test
    public void testGetAllBookingsForUser() throws SQLException {
        Vehicle vehicle = VehicleFactory.getVehicle(DEFAULT_VEHICLE_TYPE);
        
        Booking booking1 = new Booking(testUserId, vehicle, 20.0, 2000.0,
                "Galle", "Hikkaduwa", LocalDateTime.now(), "50 Matara Road, Galle");
        Booking booking2 = new Booking(testUserId, vehicle, 30.0, 3000.0,
                "Negombo", "Katunayake", LocalDateTime.now().minusDays(1), "15 Colombo Road, Negombo");

        bookingDAO.addBooking(booking1);
        bookingDAO.addBooking(booking2);

        List<Booking> userBookings = bookingDAO.getAllBookings(testUserId);
        assertEquals("Should return 2 bookings for user", 2, userBookings.size());
        assertEquals("Latest booking should be first", booking1.getBookingId(), userBookings.get(0).getBookingId());
        System.out.println("testGetAllBookingsForUser: Retrieved " + userBookings.size() +
                " bookings for user ID " + testUserId + ", Latest booking ID: " + userBookings.get(0).getBookingId());
    }

    @Test
    public void testGetAllBookings() throws SQLException {
        Vehicle vehicle = VehicleFactory.getVehicle(DEFAULT_VEHICLE_TYPE);
        Booking booking1 = new Booking(testUserId, vehicle, 25.0, 2500.0,
                "Ratnapura", "Balangoda", LocalDateTime.now(), "20 Main Street, Ratnapura");
        Booking booking2 = new Booking(testUserId, vehicle, 35.0, 3500.0,
                "Jaffna", "Chavakachcheri", LocalDateTime.now().minusHours(1), "30 Hospital Road, Jaffna");

        bookingDAO.addBooking(booking1);
        bookingDAO.addBooking(booking2);

        List<Booking> allBookings = bookingDAO.getAllBookings();
        assertEquals("Should return all 2 bookings", 2, allBookings.size());
        assertEquals("Latest booking should be first", booking1.getBookingId(), allBookings.get(0).getBookingId());
        System.out.println("testGetAllBookings: Retrieved " + allBookings.size() +
                " total bookings, Latest booking ID: " + allBookings.get(0).getBookingId());
    }

    @Test
    public void testDeleteBooking() throws SQLException {
        Vehicle vehicle = VehicleFactory.getVehicle(DEFAULT_VEHICLE_TYPE);
        Booking booking = new Booking(testUserId, vehicle, 50.0, 5000.0,
                "Matara", "Weligama", LocalDateTime.now(), "40 Beach Road, Matara");
        bookingDAO.addBooking(booking);

        boolean result = bookingDAO.deleteBooking(booking.getBookingId());
        assertTrue("Delete should succeed", result);
        System.out.println("testDeleteBooking: Booking with ID " + booking.getBookingId() + " deleted successfully");

        Booking deleted = bookingDAO.getBookingById(booking.getBookingId());
        assertNull("Booking should not exist after deletion", deleted);
        System.out.println("testDeleteBooking: Confirmed booking ID " + booking.getBookingId() + " no longer exists");
    }

   @Test
    public void testUpdateBooking() throws SQLException {
        Vehicle vehicle = VehicleFactory.getVehicle(DEFAULT_VEHICLE_TYPE);
        Booking originalBooking = new Booking(testUserId, vehicle, 60.0, 6000.0,
                "Battaramulla", "Rajagiriya", LocalDateTime.now(), "75 Nawala Road, Battaramulla");
        bookingDAO.addBooking(originalBooking);

        int testDriverId;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO drivers (Name, LicenseNumber, Phone) VALUES ('Kamal Silva', 'B1234567', '94771234567')");
            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            rs.next();
            testDriverId = rs.getInt(1);
        }
        Booking updatedBooking = new Booking(testUserId, vehicle, 70.0, 7000.0,
                "Dehiwala", "Mount Lavinia", LocalDateTime.now().plusHours(1), "85 Galle Road, Dehiwala");
        updatedBooking.setBookingId(originalBooking.getBookingId());
        updatedBooking.setStatus("Confirmed");
        updatedBooking.setDriverId(testDriverId);

        boolean result = bookingDAO.updateBooking(updatedBooking);
        assertTrue("Update should succeed", result);
        System.out.println("testUpdateBooking: Booking with ID " + updatedBooking.getBookingId() + " updated successfully");
        Booking retrieved = bookingDAO.getBookingById(originalBooking.getBookingId());
        assertEquals("Distance should be updated", 70.0, retrieved.getDistance(), 0.001);
        assertEquals("Start location should be updated", "Dehiwala", retrieved.getStartLocation());
        assertEquals("Status should be updated", "Confirmed", retrieved.getStatus());
        assertEquals("Driver ID should be updated", Integer.valueOf(testDriverId), retrieved.getDriverId());
        System.out.println("testUpdateBooking: Verified update - Distance: " + retrieved.getDistance() +
                ", Start Location: " + retrieved.getStartLocation() +
                ", Status: " + retrieved.getStatus() +
                ", Driver ID: " + retrieved.getDriverId());
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM bookings WHERE BookingId = " + originalBooking.getBookingId());
            stmt.execute("DELETE FROM drivers WHERE DriverId = " + testDriverId);
        }
    }
}
