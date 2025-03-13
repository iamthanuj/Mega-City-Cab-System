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
            <a href="manage-users.jsp">Manage Users</a>
            <a href="#" data-bs-toggle="modal" data-bs-target="#logoutModal">Logout</a>
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
                if ("driverAssigned".equals(message) || "statusUpdated".equals(message) || "bookingDeleted".equals(message)) {
            %>
            <div class="alert alert-success">
                <% if ("driverAssigned".equals(message)) { %>Driver assigned successfully!<% } %>
                <% if ("statusUpdated".equals(message)) { %>Status updated successfully!<% } %>
                <% if ("bookingDeleted".equals(message)) { %>Booking deleted successfully!<% } %>
            </div>
            <%
            } else if ("assignFailed".equals(error) || "updateFailed".equals(error) || "deleteFailed".equals(error)) {
            %>
            <div class="alert alert-danger">
                <% if ("assignFailed".equals(error)) { %>Failed to assign driver.<% } %>
                <% if ("updateFailed".equals(error)) { %>Failed to update status.<% } %>
                <% if ("deleteFailed".equals(error)) { %>Failed to delete booking.<% } %>
            </div>
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
                            <form action="updateStatus" method="POST" id="statusForm<%= booking.getBookingId()%>">
                                <input type="hidden" name="bookingId" value="<%= booking.getBookingId()%>">
                                <select name="status" class="status-select" onchange="this.form.setAttribute('data-changed', 'true');">
                                    <option value="Pending" <%= "Pending".equals(booking.getStatus()) ? "selected" : ""%>>Pending</option>
                                    <option value="Confirmed" <%= "Confirmed".equals(booking.getStatus()) ? "selected" : ""%>>Confirmed</option>
                                </select>
                                <button type="button" class="btn btn-primary btn-sm custom-btn-clr d-none" 
                                        id="statusSubmit<%= booking.getBookingId()%>"
                                        data-bs-toggle="modal" 
                                        data-bs-target="#statusModal<%= booking.getBookingId()%>">Update Status</button>
                            </form>
                        </td>
                        <td>
                            <form action="assignDriver" method="POST" id="driverForm<%= booking.getBookingId()%>">
                                <input type="hidden" name="bookingId" value="<%= booking.getBookingId()%>">
                                <select name="driverId" class="driver-select" onchange="this.form.setAttribute('data-changed', 'true');">
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
                                <button type="button" class="btn btn-primary btn-sm custom-btn-clr d-none" 
                                        id="driverSubmit<%= booking.getBookingId()%>"
                                        data-bs-toggle="modal" 
                                        data-bs-target="#driverModal<%= booking.getBookingId()%>">Assign Driver</button>
                            </form>
                        </td>
                        <td>
                            <form action="deleteBooking" method="POST" id="deleteForm<%= booking.getBookingId()%>">
                                <input type="hidden" name="bookingId" value="<%= booking.getBookingId()%>">
                                <button type="button" class="btn btn-sm custom-btn-clr" 
                                        data-bs-toggle="modal" 
                                        data-bs-target="#deleteModal<%= booking.getBookingId()%>">Delete</button>
                            </form>
                        </td>
                    </tr>

                    <!-- Update Status Confirmation Modal -->
                <div class="modal fade" id="statusModal<%= booking.getBookingId()%>" tabindex="-1" 
                     aria-labelledby="statusModalLabel<%= booking.getBookingId()%>" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="statusModalLabel<%= booking.getBookingId()%>">Confirm Status Update</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                Are you sure you want to update the status of this booking?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary theme-btn" data-bs-dismiss="modal">Cancel</button>
                                <button type="submit" form="statusForm<%= booking.getBookingId()%>" class="btn custom-btn-clr">Update</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Assign Driver Confirmation Modal -->
                <div class="modal fade" id="driverModal<%= booking.getBookingId()%>" tabindex="-1" 
                     aria-labelledby="driverModalLabel<%= booking.getBookingId()%>" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="driverModalLabel<%= booking.getBookingId()%>">Confirm Driver Assignment</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                Are you sure you want to assign this driver to the booking?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary theme-btn" data-bs-dismiss="modal">Cancel</button>
                                <button type="submit" form="driverForm<%= booking.getBookingId()%>" class="btn custom-btn-clr">Assign</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Delete Booking Confirmation Modal -->
                <div class="modal fade" id="deleteModal<%= booking.getBookingId()%>" tabindex="-1" 
                     aria-labelledby="deleteModalLabel<%= booking.getBookingId()%>" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="deleteModalLabel<%= booking.getBookingId()%>">Confirm Delete</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                Are you sure you want to delete this booking?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary theme-btn" data-bs-dismiss="modal">Cancel</button>
                                <button type="submit" form="deleteForm<%= booking.getBookingId()%>" class="btn custom-btn-clr">Delete</button>
                            </div>
                        </div>
                    </div>
                </div>
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

        <!-- Logout Confirmation Modal -->
        <div class="modal fade" id="logoutModal" tabindex="-1" aria-labelledby="logoutModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="logoutModalLabel">Confirm Logout</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Are you sure you want to log out?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary theme-btn" data-bs-dismiss="modal">Cancel</button>
                        <form action="logout" method="get">
                            <button type="submit" class="btn custom-btn-clr">Logout</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- JavaScript to handle dropdown change detection -->
        <script>
            document.querySelectorAll('.status-select, .driver-select').forEach(select => {
                select.addEventListener('change', function () {
                    const form = this.closest('form');
                    if (form.getAttribute('data-changed') === 'true') {
                        const submitButton = form.querySelector('button[type="button"]');
                        submitButton.click();
                    }
                });
            });
        </script>
    </body>
</html>
