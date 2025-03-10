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
import java.time.LocalDateTime;
import model.User;
import persistance.impl.BookingDAOImpl;
import service.factory.VehicleFactory;
import service.model.Booking;

/**
 *
 * @author Thanuja Fernando
 */
public class UpdateBookingServlet extends HttpServlet {

    BookingDAOImpl bimpl = new BookingDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));

        User user = (User) request.getSession().getAttribute("user");
        Integer sessionUserId = user.getId();

        if (sessionUserId == null || sessionUserId != userId) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized user ID");
            return;
        }
        
        try {

            Booking existingBooking = bimpl.getBookingById(bookingId);
            if (existingBooking == null || existingBooking.getUserId() != userId) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found or not owned by user");
                return;
            }

            String datetimeStr = request.getParameter("datetime");
            String address = request.getParameter("address");

            if (datetimeStr == null || address == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "DateTime and Address are required");
                return;
            }
            
            LocalDateTime datetime = LocalDateTime.parse(datetimeStr);
            existingBooking.setDatetime(datetime);
            existingBooking.setAdress(address);

            boolean success = bimpl.updateBooking(existingBooking);

            if (success) {
                response.sendRedirect("my-bookings.jsp?message=updated");
            } else {
                response.sendRedirect("my-bookings.jsp?error=updateFailed");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error while updating booking:"+ e.getMessage(), e);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
