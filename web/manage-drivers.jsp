<%-- 
    Document   : manage-drivers
    Created on : Mar 12, 2025, 10:29:55 PM
    Author     : Thanuja Fernando
--%>

<%@page import="service.model.Driver"%>
<%@page import="java.util.List"%>
<%@page import="persistance.impl.DriverDAOImpl"%>
<%@page import="persistance.dao.DriverDAO"%>
<%@page import="model.User"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="includes/globalStyles.jsp" %>
        <title>Manage Drivers</title>
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
            <h2>Manage Drivers</h2>
            <%
                User admin = (User) session.getAttribute("user");
                if (admin == null || !"admin".equals(admin.getRole())) {
                    response.sendRedirect("login.jsp?error=unauthorized");
                    return;
                }

                System.out.println("Admin user: " + admin.getName());

                // Display success or error messages
                String message = request.getParameter("message");
                String error = request.getParameter("error");
                System.out.println("Message: " + message + ", Error: " + error); // Debug log
                if ("driverAdded".equals(message)) {
            %>
            <div class="alert alert-success">Driver added successfully!</div>
            <%
            } else if ("driverUpdated".equals(message)) {
            %>
            <div class="alert alert-success">Driver updated successfully!</div>
            <%
            } else if ("driverDeleted".equals(message)) {
            %>
            <div class="alert alert-success">Driver deleted successfully!</div>
            <%
            } else if ("addFailed".equals(error)) {
            %>
            <div class="alert alert-danger">Failed to add driver.</div>
            <%
            } else if ("updateFailed".equals(error)) {
            %>
            <div class="alert alert-danger">Failed to update driver.</div>
            <%
            } else if ("deleteFailed".equals(error)) {
            %>
            <div class="alert alert-danger">Failed to delete driver.</div>
            <%
            } else if ("cannotDeleteAssigned".equals(error)) {
            %>
            <div class="alert alert-danger">Cannot delete driver: Driver is assigned to existing bookings.</div>
            <%
                }
            %>

            <!-- Add Driver Form -->
            <h3>Add New Driver</h3>
            <form action="addDriver" method="POST" class="mb-4">
                <div class="row">
                    <div class="col-md-3">
                        <input type="text" name="name" class="form-control white-inputs" placeholder="Full Name" required>
                    </div>
                    <div class="col-md-3">
                        <input type="text" name="licenseNumber" class="form-control white-inputs" placeholder="License Number" required>
                    </div>
                    <div class="col-md-3">
                        <input type="text" name="phone" class="form-control white-inputs" placeholder="Phone" required>
                    </div>
                    <div class="col-md-3">
                        <button type="submit" class="btn custom-btn-clr mt-2">Add Driver</button>
                    </div>
                </div>
            </form>

            <%
                DriverDAO driverDAO = new DriverDAOImpl();
                List<Driver> allDrivers = null;
                try {
                    allDrivers = driverDAO.getAllDrivers();
                } catch (SQLException e) {
            %>
            <div class="alert alert-danger">Error loading drivers: <%= e.getMessage()%></div>
            <%
                    return;
                }
            %>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Driver ID</th>
                        <th>Full Name</th>
                        <th>License Number</th>
                        <th>Phone</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (allDrivers != null && !allDrivers.isEmpty()) {
                            for (Driver driver : allDrivers) {
                    %>
                    <tr>
                        <td><%= driver.getId()%></td>
                <form action="updateDriver" method="POST" id="updateForm<%= driver.getId()%>">
                    <td><input type="text" name="name" value="<%= driver.getName()%>" required></td>
                    <td><input type="text" name="licenseNumber" value="<%= driver.getLicenseNumber()%>" required></td>
                    <td><input type="text" name="phone" value="<%= driver.getPhone()%>" required></td>
                    <td>
                        <input type="hidden" name="driverId" value="<%= driver.getId()%>">
                        <button type="button" class="btn btn-sm custom-btn-clr" 
                                data-bs-toggle="modal" data-bs-target="#updateModal<%= driver.getId()%>">Update</button>
                        <button type="button" class="btn btn-sm theme-btn" 
                                data-bs-toggle="modal" data-bs-target="#deleteModal<%= driver.getId()%>">Delete</button>
                    </td>
                </form>
                </tr>

                <!-- Update Confirmation Modal -->
                <div class="modal fade" id="updateModal<%= driver.getId()%>" tabindex="-1" 
                     aria-labelledby="updateModalLabel<%= driver.getId()%>" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="updateModalLabel<%= driver.getId()%>">Confirm Update</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                Are you sure you want to update this driver?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary theme-btn" data-bs-dismiss="modal">Cancel</button>
                                <button type="submit" form="updateForm<%= driver.getId()%>" class="btn custom-btn-clr">Update</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Delete Confirmation Modal -->
                <div class="modal fade" id="deleteModal<%= driver.getId()%>" tabindex="-1" 
                     aria-labelledby="deleteModalLabel<%= driver.getId()%>" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="deleteModalLabel<%= driver.getId()%>">Confirm Delete</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                Are you sure you want to delete this driver?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary theme-btn" data-bs-dismiss="modal">Cancel</button>
                                <form action="deleteDriver" method="POST">
                                    <input type="hidden" name="driverId" value="<%= driver.getId()%>">
                                    <button type="submit" class="btn custom-btn-clr">Delete</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">No drivers found.</td>
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
    </body>
</html>
