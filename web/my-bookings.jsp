<%-- 
    Document   : my-bookings
    Created on : Mar 3, 2025, 6:22:22 PM
    Author     : Thanuja Fernando
--%>

<%@page import="java.util.List"%>
<%@page import="service.model.Booking"%>
<%@page import="persistance.impl.BookingDAOImpl"%>
<%@page import="persistance.dao.BookingDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="includes/globalStyles.jsp" %>
        <title>Booking</title>
    </head>
    <body>
        <%@include file="includes/navbar.jsp" %>

        <%            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            BookingDAO bookingDAO = new BookingDAOImpl();
            List<Booking> bookings = bookingDAO.getAllBookings(user.getId());
        %>
        <div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Vehicle</th>
                        <th>Distance (km)</th>
                        <th>Total Cost (LKR)</th>
                        <th>Start Location</th>
                        <th>End Location</th>
                        <th>Date & Time</th>
                        <th>Address</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (bookings != null && !bookings.isEmpty()) {
                            for (Booking booking : bookings) {
                    %>
                    <tr>
                        <td><%= booking.getVehicle().getName()%> (<%= booking.getVehicle().getType()%>)</td>
                        <td><%= booking.getDistance()%></td>
                        <td><%= booking.getTotalCost()%></td>
                        <td><%= booking.getStartLocation()%></td>
                        <td><%= booking.getEndLocation()%></td>
                        <td><%= booking.getDatetime()%></td>
                        <td><%= booking.getAddress()%></td>
                        <td>
                            <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#updateModal<%= booking.getId()%>">Update</button>
                            <form action="/deleteBooking" method="POST" style="display:inline;">
                                <input type="hidden" name="bookingId" value="<%= booking.getId()%>">
                                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this booking?');">Delete</button>
                            </form>
                        </td>
                    </tr>

                    <!-- Update Modal -->
                <div class="modal fade" id="updateModal<%= booking.getId()%>" tabindex="-1" aria-labelledby="updateModalLabel<%= booking.getId()%>" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="updateModalLabel<%= booking.getId()%>">Update Booking</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="/updateBooking" method="POST">
                                    <input type="hidden" name="bookingId" value="<%= booking.getId()%>">
                                    <input type="hidden" name="userId" value="<%=user.getId() %>">

                                    <div class="mb-3">
                                        <label for="vehicleType<%= booking.getId()%>" class="form-label">Vehicle Type</label>
                                        <input type="text" id="vehicleType<%= booking.getId()%>" name="vehicleType" class="form-control" value="<%= booking.getVehicle().getType()%>" readonly>
                                    </div>

                                    <div class="mb-3">
                                        <label for="distance<%= booking.getId()%>" class="form-label">Distance (km)</label>
                                        <input type="number" id="distance<%= booking.getId()%>" name="distance" class="form-control" value="<%= booking.getDistance()%>" step="0.01" required>
                                    </div>

                                    <div class="mb-3">
                                        <label for="totalCost<%= booking.getId()%>" class="form-label">Total Cost (LKR)</label>
                                        <input type="number" id="totalCost<%= booking.getId()%>" name="totalCost" class="form-control" value="<%= booking.getTotalCost()%>" step="0.01" required>
                                    </div>

                                    <div class="mb-3">
                                        <label for="startLocation<%= booking.getId()%>" class="form-label">Start Location</label>
                                        <input type="text" id="startLocation<%= booking.getId()%>" name="startLocation" class="form-control" value="<%= booking.getStartLocation()%>" required>
                                    </div>

                                    <div class="mb-3">
                                        <label for="endLocation<%= booking.getId()%>" class="form-label">End Location</label>
                                        <input type="text" id="endLocation<%= booking.getId()%>" name="endLocation" class="form-control" value="<%= booking.getEndLocation()%>" required>
                                    </div>

                                    <div class="mb-3">
                                        <label for="datetime<%= booking.getId()%>" class="form-label">Date & Time</label>
                                        <input type="datetime-local" id="datetime<%= booking.getId()%>" name="datetime" class="form-control" value="<%= booking.getDatetime().toString().substring(0, 16)%>" required>
                                    </div>

                                    <div class="mb-3">
                                        <label for="address<%= booking.getId()%>" class="form-label">Address</label>
                                        <input type="text" id="address<%= booking.getId()%>" name="address" class="form-control" value="<%= booking.getAddress()%>" required>
                                    </div>

                                    <button type="submit" class="btn btn-dark">Save Changes</button>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="8" class="text-center">No bookings found.</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
            <a href="/booking.jsp" class="btn btn-dark">Add New Booking</a>
        </div>
    </body>
</html>
