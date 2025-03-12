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
import persistance.dao.BookingDAO;
import persistance.impl.BookingDAOImpl;
import service.model.Booking;

/**
 *
 * @author Thanuja Fernando
 */
public class AssignDriverServlet extends HttpServlet {

    private final BookingDAO bookingDAO = new BookingDAOImpl();

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        System.out.println("User in session: " + (user != null ? user.getEmail() : "null"));

        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect("login.jsp?error=unauthorized");
            return;
        }

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String driverIdStr = request.getParameter("driverId");
        Integer driverId = driverIdStr.isEmpty() ? null : Integer.parseInt(driverIdStr);

        try {
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking != null) {
                System.out.println("Setting DriverId for Booking " + bookingId + " to: " + driverId);
                booking.setDriverId(driverId);
                boolean success = bookingDAO.updateBooking(booking);
                if (success) {
                    System.out.println("Driver assignment successful for Booking " + bookingId);
                    response.sendRedirect("admin-dashboard.jsp?message=driverAssigned");
                } else {
                    System.out.println("Driver assignment failed for Booking " + bookingId);
                    response.sendRedirect("admin-dashboard.jsp?error=assignFailed");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found");
            }
        } catch (SQLException e) {
            throw new ServletException("Error assigning driver: " + e.getMessage(), e);
        }
    }
}
