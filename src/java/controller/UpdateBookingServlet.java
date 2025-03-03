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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String vehicleType = request.getParameter("vehicleType");
        double distance = Double.parseDouble(request.getParameter("distance"));
        double totalCost = Double.parseDouble(request.getParameter("totalCost"));
        String startLocation = request.getParameter("startLocation");
        String endLocation = request.getParameter("endLocation");
        String datetimeStr = request.getParameter("datetime");
        String address = request.getParameter("address");

        User user = (User) request.getSession().getAttribute("user");
        Integer sessionUserId = user.getId();

        if (sessionUserId == null || sessionUserId != userId) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized user ID");
            return;
        }

        try {
            LocalDateTime datetime = LocalDateTime.parse(datetimeStr);
            Booking booking = new Booking(userId, VehicleFactory.getVehicle(vehicleType), distance, totalCost,
                    startLocation, endLocation, datetime, address);
            booking.setBookingId(bookingId);
            boolean success = bimpl.updateBooking(booking);

            if (success) {
                response.sendRedirect("my-bookings.jsp?message=updated");
            } else {
                response.sendRedirect("my-bookings.jsp?error=updateFailed");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error while updating booking: " + e.getMessage(), e);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
