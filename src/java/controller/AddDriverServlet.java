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
        // Check admin role
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin access required");
            return;
        }

        String driverName = request.getParameter("driverName");
        String driverLicense = request.getParameter("driverLicense");
        int driverPhone = Integer.parseInt(request.getParameter("driverPhone"));

        try {
            Driver driver = new Driver(driverName, driverLicense, driverPhone);
            boolean success = driverDAO.addDriver(driver);
            if (success) {
                response.sendRedirect("admin-dashboard.jsp?message=driverAdded");
            } else {
                response.sendRedirect("admin-dashboard.jsp?error=driverAddFailed");
            }
        } catch (SQLException e) {
            throw new ServletException("Error adding driver: " + e.getMessage(), e);
        }
    }

}
