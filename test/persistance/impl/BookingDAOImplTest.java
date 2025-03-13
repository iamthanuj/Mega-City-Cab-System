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

        // Clean the tables before each test
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM bookings");
            stmt.execute("ALTER TABLE bookings AUTO_INCREMENT = 1");
            stmt.execute("DELETE FROM users");
            stmt.execute("ALTER TABLE users AUTO_INCREMENT = 1");
        }

        // Create a test user
        User testUser = new User("Test User", "123456789V", "test@example.com", 123456789, "password");
        testUser.setRole(DEFAULT_ROLE);
        userDAO.registerUser(testUser);
        testUserId = userDAO.authenticateUser("test@example.com", "password").getId();
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
        Booking booking = new Booking(testUserId, vehicle, 10.0, 100.0,
                "Start Point", "End Point", LocalDateTime.now(), "123 Main St");

        boolean result = bookingDAO.addBooking(booking);
        assertTrue("Booking should be added successfully", result);
        assertTrue("Booking ID should be set after insertion", booking.getBookingId() > 0);

        // Verify booking exists
        Booking retrieved = bookingDAO.getBookingById(booking.getBookingId());
        assertNotNull("Booking should be retrievable", retrieved);
        assertEquals("User ID should match", testUserId, retrieved.getUserId());
    }

    @Test
    public void testGetBookingById() throws SQLException {
        Vehicle vehicle = VehicleFactory.getVehicle(DEFAULT_VEHICLE_TYPE);
        Booking booking = new Booking(testUserId, vehicle, 15.0, 150.0,
                "Location A", "Location B", LocalDateTime.now(), "456 Oak St");
        bookingDAO.addBooking(booking);

        Booking retrieved = bookingDAO.getBookingById(booking.getBookingId());
        assertNotNull("Booking should be found", retrieved);
        assertEquals("Distance should match", 15.0, retrieved.getDistance(), 0.001);
        assertEquals("Start location should match", "Location A", retrieved.getStartLocation());

        // Test non-existent booking
        Booking notFound = bookingDAO.getBookingById(999);
        assertNull("Non-existent booking should return null", notFound);
    }

    @Test
    public void testGetAllBookingsForUser() throws SQLException {
        Vehicle vehicle = VehicleFactory.getVehicle(DEFAULT_VEHICLE_TYPE);
        // Add multiple bookings for test user
        Booking booking1 = new Booking(testUserId, vehicle, 20.0, 200.0,
                "Point A", "Point B", LocalDateTime.now(), "789 Pine St");
        Booking booking2 = new Booking(testUserId, vehicle, 30.0, 300.0,
                "Point C", "Point D", LocalDateTime.now().minusDays(1), "101 Elm St");

        bookingDAO.addBooking(booking1);
        bookingDAO.addBooking(booking2);

        List<Booking> userBookings = bookingDAO.getAllBookings(testUserId);
        assertEquals("Should return 2 bookings for user", 2, userBookings.size());
        assertEquals("Latest booking should be first", booking1.getBookingId(), userBookings.get(0).getBookingId());
    }

    @Test
    public void testGetAllBookings() throws SQLException {
        Vehicle vehicle = VehicleFactory.getVehicle(DEFAULT_VEHICLE_TYPE);
        Booking booking1 = new Booking(testUserId, vehicle, 25.0, 250.0,
                "Place A", "Place B", LocalDateTime.now(), "303 Cedar St");
        Booking booking2 = new Booking(testUserId, vehicle, 35.0, 350.0,
                "Place C", "Place D", LocalDateTime.now().minusHours(1), "404 Maple St");

        bookingDAO.addBooking(booking1);
        bookingDAO.addBooking(booking2);

        List<Booking> allBookings = bookingDAO.getAllBookings();
        assertEquals("Should return all 2 bookings", 2, allBookings.size());
        assertEquals("Latest booking should be first", booking1.getBookingId(), allBookings.get(0).getBookingId());
    }

    @Test
    public void testDeleteBooking() throws SQLException {
        Vehicle vehicle = VehicleFactory.getVehicle(DEFAULT_VEHICLE_TYPE);
        Booking booking = new Booking(testUserId, vehicle, 50.0, 500.0,
                "Origin", "Destination", LocalDateTime.now(), "505 Spruce St");
        bookingDAO.addBooking(booking);

        boolean result = bookingDAO.deleteBooking(booking.getBookingId());
        assertTrue("Delete should succeed", result);

        Booking deleted = bookingDAO.getBookingById(booking.getBookingId());
        assertNull("Booking should not exist after deletion", deleted);
    }

   @Test
    public void testUpdateBooking() throws SQLException {
        Vehicle vehicle = VehicleFactory.getVehicle(DEFAULT_VEHICLE_TYPE);
        Booking originalBooking = new Booking(testUserId, vehicle, 60.0, 600.0, 
            "Old Start", "Old End", LocalDateTime.now(), "606 Willow St");
        bookingDAO.addBooking(originalBooking);
        
        // Create a test driver for this specific test
        int testDriverId;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO drivers (Name, LicenseNumber, Phone) VALUES ('Test Driver', 'D12345', '1234567890')");
            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            rs.next();
            testDriverId = rs.getInt(1);
        }
        
        // Create updated booking with same ID and user ID
        Booking updatedBooking = new Booking(testUserId, vehicle, 70.0, 700.0, 
            "New Start", "New End", LocalDateTime.now().plusHours(1), "606 Willow St");
        updatedBooking.setBookingId(originalBooking.getBookingId());
        updatedBooking.setStatus("Confirmed");
        updatedBooking.setDriverId(testDriverId); // Use the valid test driver ID
        
        boolean result = bookingDAO.updateBooking(updatedBooking);
        assertTrue("Update should succeed", result);
        
        // Verify update
        Booking retrieved = bookingDAO.getBookingById(originalBooking.getBookingId());
        assertEquals("Distance should be updated", 70.0, retrieved.getDistance(), 0.001);
        assertEquals("Start location should be updated", "New Start", retrieved.getStartLocation());
        assertEquals("Status should be updated", "Confirmed", retrieved.getStatus());
        assertEquals("Driver ID should be updated", Integer.valueOf(testDriverId), retrieved.getDriverId());
        
        // Clean up: First remove the booking, then the driver
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM bookings WHERE BookingId = " + originalBooking.getBookingId());
            stmt.execute("DELETE FROM drivers WHERE DriverId = " + testDriverId);
        }
    }
}
