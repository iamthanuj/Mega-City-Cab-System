<%-- 
    Document   : manage-drivers
    Created on : Mar 12, 2025, 10:29:55 PM
    Author     : Thanuja Fernando
--%>

<%@page import="model.User"%>
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
            <a href="logout">Logout</a>
        </div>
        <div class="content">
            <h2>Manage Drivers</h2>
            <%
                User user = (User) session.getAttribute("user");
                if (user == null || !"admin".equals(user.getRole())) {
                    response.sendRedirect("login.jsp?error=unauthorized");
                    return;
                }
            %>
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
                    String message = request.getParameter("message");
                    if ("driverAdded".equals(message)) {
                %>
                <div class="alert alert-success mt-3">Driver added successfully!</div>
                <%
                    }
                %>
            </div>
        </div>
    </body>
</html>
