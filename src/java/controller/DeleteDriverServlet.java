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

/**
 *
 * @author Thanuja Fernando
 */
public class DeleteDriverServlet extends HttpServlet {

    private final DriverDAO driverDAO = new DriverDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int driverId = Integer.parseInt(request.getParameter("driverId"));

        try {
            // Check if the driver is assigned to any bookings
            boolean hasBookings = driverDAO.hasAssignedBookings(driverId);
            System.out.println("Driver ID: " + driverId + " has assigned bookings: " + hasBookings); // Debug log
            if (hasBookings) {
                response.sendRedirect("manage-drivers.jsp?error=cannotDeleteAssigned");
                return;
            }

            // If no bookings, proceed with deletion
            boolean deleted = driverDAO.deleteDriver(driverId);
            if (deleted) {
                response.sendRedirect("manage-drivers.jsp?message=driverDeleted");
            } else {
                response.sendRedirect("manage-drivers.jsp?error=deleteFailed");
            }
        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
            // Redirect with a specific error key if deletion fails due to SQL constraints
            String errorKey = e.getMessage().contains("foreign key constraint fails")
                    ? "cannotDeleteAssigned"
                    : "deleteFailed";
            response.sendRedirect("manage-drivers.jsp?error=" + errorKey);
        } catch (Exception e) {
            // Handle any other unexpected errors
            e.printStackTrace();
            response.sendRedirect("manage-drivers.jsp?error=deleteFailed");
        }
    }

}
