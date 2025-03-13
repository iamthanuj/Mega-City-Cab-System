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

        int driverId;
        try {
            driverId = Integer.parseInt(request.getParameter("driverId"));
        } catch (NumberFormatException e) {
            response.sendRedirect("manage-drivers.jsp?error=invalidDriverId");
            return;
        }

        try {

            boolean success = driverDAO.deleteDriver(driverId);
            if (success) {
                response.sendRedirect("manage-drivers.jsp?message=driverDeleted");
            } else {
                response.sendRedirect("manage-drivers.jsp?error=deleteFailed");
            }
        } catch (SQLException e) {
            throw new ServletException("Error deleting driver: " + e.getMessage(), e);
        }
    }

}
