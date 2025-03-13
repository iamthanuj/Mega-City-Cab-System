/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import persistance.dao.DriverDAO;
import persistance.impl.DriverDAOImpl;
import service.model.Driver;

/**
 *
 * @author Thanuja Fernando
 */
public class UpdateDriverServlet extends HttpServlet {

    private final DriverDAO driverDAO = new DriverDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int driverId = Integer.parseInt(request.getParameter("driverId"));
        String name = request.getParameter("name");
        int phone = Integer.parseInt(request.getParameter("phone"));

        Driver driver = new Driver(name, name, phone);
        driver.setId(driverId);

        try {
            boolean success = driverDAO.updateDriver(driver);
            if (success) {
                response.sendRedirect("manage-drivers.jsp?message=driverUpdated");
            } else {
                response.sendRedirect("manage-drivers.jsp?error=updateFailed");
            }
        } catch (SQLException e) {
            throw new ServletException("Error updating driver: " + e.getMessage(), e);
        }
    }

}
