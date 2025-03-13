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
import service.model.Driver;

/**
 *
 * @author Thanuja Fernando
 */
public class DriverDAOImplTest {
    
    public DriverDAOImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addDriver method, of class DriverDAOImpl.
     */
    @Test
    public void testAddDriver() throws Exception {
        System.out.println("addDriver");
        Driver driver = null;
        DriverDAOImpl instance = new DriverDAOImpl();
        boolean expResult = false;
        boolean result = instance.addDriver(driver);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllDrivers method, of class DriverDAOImpl.
     */
    @Test
    public void testGetAllDrivers() throws Exception {
        System.out.println("getAllDrivers");
        DriverDAOImpl instance = new DriverDAOImpl();
        List<Driver> expResult = null;
        List<Driver> result = instance.getAllDrivers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateDriver method, of class DriverDAOImpl.
     */
    @Test
    public void testUpdateDriver() throws Exception {
        System.out.println("updateDriver");
        Driver driver = null;
        DriverDAOImpl instance = new DriverDAOImpl();
        boolean expResult = false;
        boolean result = instance.updateDriver(driver);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteDriver method, of class DriverDAOImpl.
     */
    @Test
    public void testDeleteDriver() throws Exception {
        System.out.println("deleteDriver");
        int driverId = 0;
        DriverDAOImpl instance = new DriverDAOImpl();
        boolean expResult = false;
        boolean result = instance.deleteDriver(driverId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDriverById method, of class DriverDAOImpl.
     */
    @Test
    public void testGetDriverById() throws Exception {
        System.out.println("getDriverById");
        int id = 0;
        DriverDAOImpl instance = new DriverDAOImpl();
        Driver expResult = null;
        Driver result = instance.getDriverById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasAssignedBookings method, of class DriverDAOImpl.
     */
    @Test
    public void testHasAssignedBookings() throws Exception {
        System.out.println("hasAssignedBookings");
        int driverId = 0;
        DriverDAOImpl instance = new DriverDAOImpl();
        boolean expResult = false;
        boolean result = instance.hasAssignedBookings(driverId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
