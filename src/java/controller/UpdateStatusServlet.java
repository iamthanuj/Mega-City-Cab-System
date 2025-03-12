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
public class UpdateStatusServlet extends HttpServlet {

    private final BookingDAO bookingDAO = new BookingDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        // Debug: Log user status
        System.out.println("User in session: " + (user != null ? user.getEmail() : "null"));

        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect("login.jsp?error=unauthorized");
            return;
        }

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String status = request.getParameter("status");

        try {
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking != null) {
                booking.setStatus(status);
                boolean success = bookingDAO.updateBooking(booking);
                if (success) {
                    response.sendRedirect("admin-dashboard.jsp?message=statusUpdated");
                } else {
                    response.sendRedirect("admin-dashboard.jsp?error=updateFailed");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found");
            }
        } catch (SQLException e) {
            throw new ServletException("Error updating status: " + e.getMessage(), e);
        }
    }

}
