/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import persistance.impl.BookingDAOImpl;

/**
 *
 * @author Thanuja Fernando
 */
public class DeleteBookingServlet extends HttpServlet {
    
     BookingDAOImpl bimpl = new BookingDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        
        User user = (User) request.getSession().getAttribute("user");
        Integer sessionUserId = user.getId();

        if (sessionUserId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            boolean success = bimpl.deleteBooking(bookingId);
            if (success) {
                response.sendRedirect("my-bookings.jsp?message=deleted");
            } else {
                response.sendRedirect("my-bookings.jsp?error=deleteFailed");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error while deleting booking: " + e.getMessage(), e);
        }
    }

}
