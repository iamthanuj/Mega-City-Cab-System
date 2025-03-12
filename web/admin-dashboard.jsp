<%-- 
    Document   : admin-dashboard
    Created on : Mar 12, 2025, 12:52:22 AM
    Author     : Thanuja Fernando
--%>

<%@page import="service.model.Driver"%>
<%@page import="persistance.impl.DriverDAOImpl"%>
<%@page import="persistance.dao.DriverDAO"%>
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
        <div class="sidebar">
            <h3>Admin Panel</h3>
            <a href="admin-dashboard.jsp">Manage Bookings</a>
            <a href="manage-drivers.jsp">Manage Drivers</a>
            <a href="logout">Logout</a>
        </div>
        <div class="content">
            <h2>Manage Bookings</h2>
            <%
                User user = (User) session.getAttribute("user");
                if (user == null || !"admin".equals(user.getRole())) {
                    response.sendRedirect("login.jsp?error=unauthorized");
                    return;
                }

                System.out.println("Admin user: " + user.getName());

                // Display success or error messages
                String message = request.getParameter("message");
                String error = request.getParameter("error");
                if ("driverAssigned".equals(message)) {
            %>
            <div class="alert alert-success">Driver assigned successfully!</div>
            <%
            } else if ("assignFailed".equals(error)) {
            %>
            <div class="alert alert-danger">Failed to assign driver.</div>
            <%
                }
            %>

            <%
                BookingDAO bookingDAO = new BookingDAOImpl();
                DriverDAO driverDAO = new DriverDAOImpl();
                List<Booking> allBookings = null;
                List<Driver> allDrivers = null;
                try {
                    allBookings = bookingDAO.getAllBookings();
                    allDrivers = driverDAO.getAllDrivers();
                } catch (SQLException e) {
            %>
            <div class="alert alert-danger">Error loading data: <%= e.getMessage()%></div>
            <%
                    return;
                }
            %>
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
                        <th>Driver</th>
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
                            <form action="assignDriver" method="POST">
                                <input type="hidden" name="bookingId" value="<%= booking.getBookingId()%>">
                                <select name="driverId" class="driver-select" onchange="this.form.submit()">
                                    <option value="" <%= (booking.getDriverId() == null) ? "selected" : ""%>>Unassigned</option>
                                    <%
                                        for (Driver driver : allDrivers) {
                                            String selected = (booking.getDriverId() != null && booking.getDriverId().equals(driver.getId())) ? "selected" : "";
                                    %>
                                    <option value="<%= driver.getId()%>" <%= selected%>><%= driver.getName()%> (ID: <%= driver.getId()%>)</option>
                                    <%
                                        }
                                    %>
                                </select>
                            </form>
                        </td>
                        <td>
                            <form action="deleteBooking" method="POST" style="display:inline;">
                                <input type="hidden" name="bookingId" value="<%= booking.getBookingId()%>">
                                <button type="submit" class="btn btn-danger btn-sm custom-btn-clr" onclick="return confirm('Are you sure?');">Delete</button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="12" class="text-center">No bookings found.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </body>
</html>
