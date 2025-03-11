/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistance.dao;

import service.model.Driver;
import java.sql.SQLException;

/**
 *
 * @author Thanuja Fernando
 */
public interface DriverDAO {

    boolean addDriver(Driver driver) throws SQLException;
}
