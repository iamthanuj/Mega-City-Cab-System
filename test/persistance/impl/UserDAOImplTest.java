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
        User user = new User("Nimal Perera", "199512345678", "nimal@example.com", 711234567, "pass@123");
        user.setRole(DEFAULT_ROLE);
        boolean result = userDAO.registerUser(user);
        assertTrue("User should be registered successfully", result);
        System.out.println("testRegisterUser: Registration success = " + result);

        boolean exists = userDAO.checkUserExists("nimal@example.com");
        assertTrue("User should exist after registration", exists);
        System.out.println("testRegisterUser: User exists = " + exists);
    }

    @Test
    public void testCheckUserExists() throws SQLException {
        boolean result1 = userDAO.checkUserExists("kasun@example.com");
        assertFalse("Non-existent user should return false", result1);
        System.out.println("testCheckUserExists: Non-existent user = " + result1);

        User user = new User("Kasun Silva", "199823456789", "kasun@example.com", 722345655, "kasun123");
        user.setRole(DEFAULT_ROLE);
        userDAO.registerUser(user);
        boolean result2 = userDAO.checkUserExists("kasun@example.com");
        assertTrue("Existing user should return true", result2);
        System.out.println("testCheckUserExists: Existing user = " + result2);
    }

    @Test
    public void testAuthenticateUser() throws SQLException {
        User user = new User("Amal Fernando", "199012387654", "amal@example.com", 767596551, "amal@secret");
        user.setRole(DEFAULT_ROLE);
        userDAO.registerUser(user);

        User authUser = userDAO.authenticateUser("amal@example.com", "amal@secret");
        assertNotNull("Authentication should succeed with correct credentials", authUser);
        assertEquals("Email should match", "amal@example.com", authUser.getEmail());
        System.out.println("testAuthenticateUser: Successful auth email = " + authUser.getEmail());

        User failedAuth = userDAO.authenticateUser("amal@example.com", "wrongpass");
        assertNull("Authentication should fail with wrong password", failedAuth);
        System.out.println("testAuthenticateUser: Failed auth = " + (failedAuth == null ? "null" : "not null"));
    }

    @Test
    public void testDeleteUser() throws SQLException {
        User user = new User("Sanduni Wijesinghe", "199712309876", "sanduni@example.com", 0761230111, "sandu123");
        user.setRole(DEFAULT_ROLE);
        userDAO.registerUser(user);
        User createdUser = userDAO.authenticateUser("sanduni@example.com", "sandu123");

        boolean result = userDAO.deleteUser(createdUser.getId());
        assertTrue("Delete should succeed", result);
        System.out.println("testDeleteUser: Delete success = " + result);

        User deletedUser = userDAO.getUserById(createdUser.getId());
        assertNull("User should not exist after deletion", deletedUser);
        System.out.println("testDeleteUser: Deleted user = " + (deletedUser == null ? "null" : "not null"));
    }

   @Test
    public void testGetAllUsers() throws SQLException {
        User user1 = new User("Dilshan Rajapakse", "198912345678", "dilshan@example.com", 071123111, "dil123");
        user1.setRole(DEFAULT_ROLE);
        User user2 = new User("Tharindu Gunawardena", "199423456789", "tharindu@example.com", 0722341111, "thar456");
        user2.setRole(DEFAULT_ROLE);
        userDAO.registerUser(user1);
        userDAO.registerUser(user2);

        List<User> users = userDAO.getAllUsers();
        assertEquals("Should return 2 users", 2, users.size());
        System.out.println("testGetAllUsers: Number of users = " + users.size());
    }

    @Test
    public void testUpdateUser() throws SQLException {
        User user = new User("Chamari Athukorala", "199312398765", "chamari@example.com", 771231111, "cham123");
        user.setRole(DEFAULT_ROLE);
        userDAO.registerUser(user);
        User createdUser = userDAO.authenticateUser("chamari@example.com", "cham123");

        createdUser.setName("Chamari Perera");
        createdUser.setPhone(781231111);
        createdUser.setRole(DEFAULT_ROLE);
        boolean result = userDAO.updateUser(createdUser);
        assertTrue("Update should succeed", result);
        System.out.println("testUpdateUser: Update success = " + result);

        User updatedUser = userDAO.getUserById(createdUser.getId());
        assertEquals("Name should be updated", "Chamari Perera", updatedUser.getName());
        assertEquals("Phone should be updated", 781231111, updatedUser.getPhone());
        System.out.println("testUpdateUser: Updated name = " + updatedUser.getName() + ", phone = " + updatedUser.getPhone());
    }

    @Test
    public void testGetUserById() throws SQLException {
        User user = new User("Saman Kumara", "198512376543", "saman@example.com", 071123111, "saman789");
        user.setRole(DEFAULT_ROLE);
        userDAO.registerUser(user);
        User createdUser = userDAO.authenticateUser("saman@example.com", "saman789");

        User foundUser = userDAO.getUserById(createdUser.getId());
        assertNotNull("User should be found", foundUser);
        assertEquals("Email should match", "saman@example.com", foundUser.getEmail());
        System.out.println("testGetUserById: Found user email = " + foundUser.getEmail());

        User notFoundUser = userDAO.getUserById(999);
        assertNull("Non-existent user should return null", notFoundUser);
        System.out.println("testGetUserById: Non-existent user = " + (notFoundUser == null ? "null" : "not null"));
    }
}
