/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package persistance.impl;

import java.util.List;
import model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import util.DBConnection;

/**
 *
 * @author Thanuja Fernando
 */
public class UserDAOImplTest {

    private UserDAOImpl userDAO;
    private Connection conn;
    private static final String DEFAULT_ROLE = "USER";

    @BeforeClass
    public static void setUpClass() throws SQLException {
        // Assuming your DBConnection handles test database connection
    }

    @AfterClass
    public static void tearDownClass() throws SQLException {
        // Any cleanup of test database connection if needed
    }

    @Before
    public void setUp() throws SQLException {
        userDAO = new UserDAOImpl();
        conn = DBConnection.getInstance().getConnection();
        // Clean the table before each test
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM users");
            stmt.execute("ALTER TABLE users AUTO_INCREMENT = 1");
        }
    }

    @After
    public void tearDown() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    @Test
    public void testRegisterUser() throws SQLException {
        User user = new User("John Doe", "123456789V", "john@example.com", 123456789, "password123");
        user.setRole(DEFAULT_ROLE);  // Set role
        boolean result = userDAO.registerUser(user);
        assertTrue("User should be registered successfully", result);

        // Verify user exists
        boolean exists = userDAO.checkUserExists("john@example.com");
        assertTrue("User should exist after registration", exists);
    }

    @Test
    public void testCheckUserExists() throws SQLException {
        // Test with non-existing user
        boolean result1 = userDAO.checkUserExists("nonexistent@example.com");
        assertFalse("Non-existent user should return false", result1);

        // Add a user and test
        User user = new User("Jane Doe", "987654321V", "jane@example.com", 987654321, "pass456");
        user.setRole(DEFAULT_ROLE);  // Set role
        userDAO.registerUser(user);
        boolean result2 = userDAO.checkUserExists("jane@example.com");
        assertTrue("Existing user should return true", result2);
    }

    @Test
    public void testAuthenticateUser() throws SQLException {
        // Add a test user
        User user = new User("Test User", "111111111V", "test@example.com", 111111111, "secret");
        user.setRole(DEFAULT_ROLE);  // Set role
        userDAO.registerUser(user);

        // Test successful authentication
        User authUser = userDAO.authenticateUser("test@example.com", "secret");
        assertNotNull("Authentication should succeed with correct credentials", authUser);
        assertEquals("Email should match", "test@example.com", authUser.getEmail());

        // Test failed authentication
        User failedAuth = userDAO.authenticateUser("test@example.com", "wrongpass");
        assertNull("Authentication should fail with wrong password", failedAuth);
    }

    @Test
    public void testDeleteUser() throws SQLException {
        // Add a test user
        User user = new User("Delete Me", "222222222V", "delete@example.com", 222222222, "deletepass");
        user.setRole(DEFAULT_ROLE);  // Set role
        userDAO.registerUser(user);
        User createdUser = userDAO.authenticateUser("delete@example.com", "deletepass");

        // Delete and verify
        boolean result = userDAO.deleteUser(createdUser.getId());
        assertTrue("Delete should succeed", result);

        // Verify user is gone
        User deletedUser = userDAO.getUserById(createdUser.getId());
        assertNull("User should not exist after deletion", deletedUser);
    }

    @Test
    public void testGetAllUsers() throws SQLException {
        // Add multiple users
        User user1 = new User("User1", "333333333V", "user1@example.com", 333333333, "pass1");
        user1.setRole(DEFAULT_ROLE);
        User user2 = new User("User2", "444444444V", "user2@example.com", 444444444, "pass2");
        user2.setRole(DEFAULT_ROLE);
        userDAO.registerUser(user1);
        userDAO.registerUser(user2);

        List<User> users = userDAO.getAllUsers();
        assertEquals("Should return 2 users", 2, users.size());
    }

    @Test
    public void testUpdateUser() throws SQLException {
        // Add a test user
        User user = new User("Old Name", "555555555V", "old@example.com", 555555555, "oldpass");
        user.setRole(DEFAULT_ROLE);  // Set role
        userDAO.registerUser(user);
        User createdUser = userDAO.authenticateUser("old@example.com", "oldpass");

        // Update user
        createdUser.setName("New Name");
        createdUser.setPhone(999999999);
        createdUser.setRole(DEFAULT_ROLE);  // Ensure role remains set
        boolean result = userDAO.updateUser(createdUser);
        assertTrue("Update should succeed", result);

        // Verify update
        User updatedUser = userDAO.getUserById(createdUser.getId());
        assertEquals("Name should be updated", "New Name", updatedUser.getName());
        assertEquals("Phone should be updated", 999999999, updatedUser.getPhone());
    }

    @Test
    public void testGetUserById() throws SQLException {
        // Add a test user
        User user = new User("Get Me", "666666666V", "get@example.com", 666666666, "getpass");
        user.setRole(DEFAULT_ROLE);  // Set role
        userDAO.registerUser(user);
        User createdUser = userDAO.authenticateUser("get@example.com", "getpass");

        // Get and verify
        User foundUser = userDAO.getUserById(createdUser.getId());
        assertNotNull("User should be found", foundUser);
        assertEquals("Email should match", "get@example.com", foundUser.getEmail());

        // Test non-existent user
        User notFoundUser = userDAO.getUserById(999);
        assertNull("Non-existent user should return null", notFoundUser);
    }
}
