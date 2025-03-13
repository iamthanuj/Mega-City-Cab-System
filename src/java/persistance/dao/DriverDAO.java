/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistance.dao;

import service.model.Driver;
import java.sql.SQLException;
import java.util.List;
import service.model.Driver;

/**
 *
 * @author Thanuja Fernando
 */
public interface DriverDAO {

    boolean addDriver(Driver driver) throws SQLException;

    List<Driver> getAllDrivers() throws SQLException;

    public boolean updateDriver(Driver driver) throws SQLException;

    public boolean deleteDriver(int driverId) throws SQLException;

    public Driver getDriverById(int id) throws SQLException;

    public boolean hasAssignedBookings(int driverId) throws SQLException;
}
