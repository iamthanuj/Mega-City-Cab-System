<%-- 
    Document   : admin-dashboard
    Created on : Mar 12, 2025, 12:52:22 AM
    Author     : Thanuja Fernando
--%>

<%@page import="java.util.List"%>
<%@page import="service.model.Booking"%>
<%@page import="persistance.dao.BookingDAO"%>
<%@page import="persistance.impl.BookingDAOImpl"%>
<%@page import="model.User"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="includes/globalStyles.jsp" %>
        <title>Admin Dashboard</title>
    </head>
    <body>
        <%@include file="includes/navbar.jsp" %>

        <%            if (user == null || !"admin".equals(user.getRole())) {
                response.sendRedirect("login.jsp?error=unauthorized");
                return;
            }

            BookingDAO bookingDAO = new BookingDAOImpl();
            List<Booking> allBookings = null;
            try {
                allBookings = bookingDAO.getAllBookings(); // Fetch all bookings
            } catch (SQLException e) {
        %>
        <div class="alert alert-danger">Error loading bookings: <%= e.getMessage()%></div>
        <%
                return;
            }
        %>
        <div class="admin-section">
            <h2>Manage Bookings</h2>
            <table class="table table-striped booking-table">
                <thead>
                    <tr>
                        <th>Booking ID</th>
                        <th>User ID</th>
                        <th>Vehicle</th>
                        <th>Distance (km)</th>
                        <th>Total Cost (LKR)</th>
                        <th>Start Location</th>
                        <th>End Location</th>
                        <th>Date & Time</th>
                        <th>Address</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (allBookings != null && !allBookings.isEmpty()) {
                            for (Booking booking : allBookings) {
                    %>
                    <tr>
                        <td><%= booking.getBookingId()%></td>
                        <td><%= booking.getUserId()%></td>
                        <td><%= booking.getVehicle().getName()%> (<%= booking.getVehicle().getType()%>)</td>
                        <td><%= booking.getDistance()%></td>
                        <td><%= booking.getTotalCost()%></td>
                        <td><%= booking.getStartLocation()%></td>
                        <td><%= booking.getEndLocation()%></td>
                        <td><%= booking.getDatetime()%></td>
                        <td><%= booking.getAddress()%></td>
                        <td>
                            <form action="updateStatus" method="POST">
                                <input type="hidden" name="bookingId" value="<%= booking.getBookingId()%>">
                                <select name="status" class="status-select" onchange="this.form.submit()">
                                    <option value="Pending" <%= "Pending".equals(booking.getStatus()) ? "selected" : ""%>>Pending</option>
                                    <option value="Confirmed" <%= "Confirmed".equals(booking.getStatus()) ? "selected" : ""%>>Confirmed</option>
                                </select>
                            </form>
                        </td>
                        <td>
                            <form action="deleteBooking" method="POST" style="display:inline;">
                                <input type="hidden" name="bookingId" value="<%= booking.getBookingId()%>">
                                <button type="submit" class="btn btn-danger btn-sm custom-btn-clr" onclick="return confirm('Are you sure you want to delete this booking?');">Delete</button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="11" class="text-center">No bookings found.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>

            <!-- Driver Management Section -->
            <h2>Add Driver</h2>
            <div class="driver-form">
                <form action="addDriver" method="POST">
                    <div class="mb-3">
                        <label for="driverName" class="form-label">Driver Name</label>
                        <input type="text" id="driverName" name="driverName" class="form-control dark-inputs" required>
                    </div>
                    <div class="mb-3">
                        <label for="driverLicense" class="form-label">License Number</label>
                        <input type="text" id="driverLicense" name="driverLicense" class="form-control dark-inputs" required>
                    </div>
                    <div class="mb-3">
                        <label for="driverPhone" class="form-label">Phone Number</label>
                        <input type="number" id="driverPhone" name="driverPhone" class="form-control dark-inputs" required>
                    </div>
                    <button type="submit" class="btn btn-custom">Add Driver</button>
                </form>
                <%
                    String driverMessage = request.getParameter("message");
                    if ("driverAdded".equals(driverMessage)) {
                %>
                <div class="alert alert-success mt-3">Driver added successfully!</div>
                <%
                    }
                %>
            </div>
        </div>
    </body>
</html>
