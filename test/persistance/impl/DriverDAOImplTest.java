/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package persistance.impl;

import java.util.List;
import org.junit.*;
import java.sql.*;
import static org.junit.Assert.*;
import persistance.dao.DriverDAO;
import service.model.Driver;
import util.DBConnection;

/**
 *
 * @author Thanuja Fernando
 */
public class DriverDAOImplTest {

    private DriverDAO driverDAO;
    private Connection conn;

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
        driverDAO = new DriverDAOImpl();
        conn = DBConnection.getInstance().getConnection();

        // Clean the drivers table before each test
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM drivers");
            stmt.execute("ALTER TABLE drivers AUTO_INCREMENT = 1");
            // Ensure bookings table is clean for hasAssignedBookings test
            stmt.execute("DELETE FROM bookings");
            stmt.execute("ALTER TABLE bookings AUTO_INCREMENT = 1");
        }
    }

    @After
    public void tearDown() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    @Test
    public void testAddDriver() throws SQLException {
        Driver driver = new Driver("Saman Kumara", "B9876543", 0772223333);

        boolean result = driverDAO.addDriver(driver);
        assertTrue("Driver should be added successfully", result);
        assertTrue("Driver ID should be set after insertion", driver.getId() > 0);
        System.out.println("testAddDriver: Driver added successfully with ID: " + driver.getId());

        // Verify driver exists
        Driver retrieved = driverDAO.getDriverById(driver.getId());
        assertNotNull("Driver should be retrievable", retrieved);
        assertEquals("Name should match", "Saman Kumara", retrieved.getName());
        System.out.println("testAddDriver: Driver retrieved - Name: " + retrieved.getName() +
                ", License: " + retrieved.getLicenseNumber() + ", Phone: " + retrieved.getPhone());
    }

    @Test
    public void testGetDriverById() throws SQLException {
        Driver driver = new Driver("Priya Fernando", "B4567891", 0713334444);
        driverDAO.addDriver(driver);

        Driver retrieved = driverDAO.getDriverById(driver.getId());
        assertNotNull("Driver should be found", retrieved);
        assertEquals("LicenseNumber should match", "B4567891", retrieved.getLicenseNumber());
        System.out.println("testGetDriverById: Driver retrieved with ID: " + retrieved.getId() +
                ", Name: " + retrieved.getName() + ", License: " + retrieved.getLicenseNumber());

        // Test non-existent driver
        Driver notFound = driverDAO.getDriverById(999);
        assertNull("Non-existent driver should return null", notFound);
        System.out.println("testGetDriverById: Non-existent driver (ID 999) correctly returned null");
    }

    @Test
    public void testGetAllDrivers() throws SQLException {
        Driver driver1 = new Driver("Nimal Wijesinghe", "B1234567", 0774445555);
        Driver driver2 = new Driver("Kumari Silva", "B2345678", 0775556666);
        driverDAO.addDriver(driver1);
        driverDAO.addDriver(driver2);

        List<Driver> allDrivers = driverDAO.getAllDrivers();
        assertEquals("Should return all 2 drivers", 2, allDrivers.size());
        assertEquals("First driver ID should match", driver1.getId(), allDrivers.get(0).getId());
        System.out.println("testGetAllDrivers: Retrieved " + allDrivers.size() +
                " drivers, First driver ID: " + allDrivers.get(0).getId() +
                ", Name: " + allDrivers.get(0).getName());
    }

    @Test
    public void testUpdateDriver() throws SQLException {
        Driver driver = new Driver("Ranil Jayawardena", "B3456789", 776667777);
        driverDAO.addDriver(driver);

        // Update driver details
        driver.setName("Ranil Perera");
        driver.setLicenseNumber("B9876543");
        driver.setPhone(778889999);
        boolean result = driverDAO.updateDriver(driver);
        assertTrue("Update should succeed", result);
        System.out.println("testUpdateDriver: Driver with ID " + driver.getId() + " updated successfully");

        // Verify update
        Driver updated = driverDAO.getDriverById(driver.getId());
        assertEquals("Name should be updated", "Ranil Perera", updated.getName());
        assertEquals("LicenseNumber should be updated", "B9876543", updated.getLicenseNumber());
        assertEquals("Phone should be updated", 778889999, updated.getPhone());
        System.out.println("testUpdateDriver: Verified update - Name: " + updated.getName() +
                ", License: " + updated.getLicenseNumber() + ", Phone: " + updated.getPhone());
    }

    @Test
    public void testDeleteDriver() throws SQLException {
        Driver driver = new Driver("Dilani Mendis", "B5678901", 779990000);
        driverDAO.addDriver(driver);

        boolean result = driverDAO.deleteDriver(driver.getId());
        assertTrue("Delete should succeed", result);
        System.out.println("testDeleteDriver: Driver with ID " + driver.getId() + " deleted successfully");

        Driver deleted = driverDAO.getDriverById(driver.getId());
        assertNull("Driver should not exist after deletion", deleted);
        System.out.println("testDeleteDriver: Confirmed driver ID " + driver.getId() + " no longer exists");
    }

    @Test
    public void testHasAssignedBookings() throws SQLException {
        Driver driver = new Driver("Chaminda Ranasinghe", "B6789012", 0771112222);
        driverDAO.addDriver(driver);

        // Initially, no bookings assigned
        boolean hasBookings = driverDAO.hasAssignedBookings(driver.getId());
        assertFalse("Driver should have no assigned bookings initially", hasBookings);
        System.out.println("testHasAssignedBookings: Driver ID " + driver.getId() + " has no bookings initially");

        // Add a booking referencing this driver
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO bookings (UserId, VehicleType, Distance, TotalCost, StartLocation, EndLocation, DateTime, Address, Status, DriverId) " +
                    "VALUES (1, 'Car', 10.0, 1000.0, 'Colombo', 'Kandy', NOW(), '10 Galle Road', 'Pending', " + driver.getId() + ")");
        }

        hasBookings = driverDAO.hasAssignedBookings(driver.getId());
        assertTrue("Driver should have assigned bookings", hasBookings);
        System.out.println("testHasAssignedBookings: Driver ID " + driver.getId() + " now has assigned bookings");
    }
}
