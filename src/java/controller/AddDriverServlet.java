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
import model.User;
import persistance.dao.DriverDAO;
import persistance.impl.DriverDAOImpl;
import persistance.impl.UserDAOImpl;
import service.model.Driver;

/**
 *
 * @author Thanuja Fernando
 */
public class AddDriverServlet extends HttpServlet {

    private DriverDAO driverDAO = new DriverDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String licenseNumber = request.getParameter("licenseNumber"); // Matches the form and Driver class
        int phone = Integer.parseInt(request.getParameter("phone"));

        Driver driver = new Driver(name, licenseNumber, phone);

        try {
            boolean success = driverDAO.addDriver(driver);
            if (success) {
                response.sendRedirect("manage-drivers.jsp?message=driverAdded");
            } else {
                response.sendRedirect("manage-drivers.jsp?error=addFailed");
            }
        } catch (SQLException e) {
            throw new ServletException("Error adding driver: " + e.getMessage(), e);
        }
    }
}
