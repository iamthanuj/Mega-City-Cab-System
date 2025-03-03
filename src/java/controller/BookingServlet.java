/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import service.model.Vehicle;

/**
 *
 * @author Thanuja Fernando
 */
public class BookingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        BookingDAOImpl bimpl = new BookingDAOImpl();
        
        
        // Get form parameters
        int userId = Integer.parseInt(request.getParameter("userId"));
        String vehicleType = request.getParameter("vehicleType");
        String startLocation = request.getParameter("startLocation");
        String endLocation = request.getParameter("endLocation");
        double distance = Double.parseDouble(request.getParameter("distance"));
        double totalCost = Double.parseDouble(request.getParameter("estimatedCost"));
        String datetimeStr = request.getParameter("datetime");
        String address = request.getParameter("address");

        // Validate userId against session (security check)
//        Integer  = (Integer) request.getSession().getAttribute("user");
        
        User user = (User) request.getSession().getAttribute("user");
        Integer sessionUserId = user.getId();
        
        if (sessionUserId == null || sessionUserId != userId) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized user ID");
            return;
        }

        try {
           
            LocalDateTime datetime = LocalDateTime.parse(datetimeStr);

           
            Vehicle vehicle = VehicleFactory.getVehicle(vehicleType);
            if (vehicle == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid vehicle type: " + vehicleType);
                return;
            }

            // Create Booking object
            Booking booking = new Booking(userId, vehicle, distance, totalCost, startLocation, endLocation, datetime, address);

            // Save booking to database
            boolean success = bimpl.addBooking(booking);
            if (success) {
                request.getSession().setAttribute("latestBooking", booking);
                response.sendRedirect("confirm.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save booking");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid numeric input: " + e.getMessage());
        } catch (SQLException e) {
            throw new ServletException("Database error while saving booking: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid datetime format: " + e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
