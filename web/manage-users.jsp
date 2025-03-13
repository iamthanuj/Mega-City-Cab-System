<%-- 
    Document   : manage-users
    Created on : Mar 13, 2025, 1:10:56 AM
    Author     : Thanuja Fernando
--%>

<%@page import="java.util.List"%>
<%@page import="persistance.dao.UserDAO"%>
<%@page import="persistance.impl.UserDAOImpl"%>
<%@page import="model.User"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="includes/globalStyles.jsp" %>
        <title>Manage Users</title>
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
            <h2>Manage Users</h2>
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
                if ("userUpdated".equals(message)) {
            %>
            <div class="alert alert-success">User updated successfully!</div>
            <%
            } else if ("userDeleted".equals(message)) {
            %>
            <div class="alert alert-success">User deleted successfully!</div>
            <%
            } else if ("updateFailed".equals(error)) {
            %>
            <div class="alert alert-danger">Failed to update user.</div>
            <%
            } else if ("deleteFailed".equals(error)) {
            %>
            <div class="alert alert-danger">Failed to delete user.</div>
            <%
            } else if ("cannotDeleteSelf".equals(error)) {
            %>
            <div class="alert alert-danger">You cannot delete your own account.</div>
            <%
                }
            %>

            <%
                UserDAO userDAO = new UserDAOImpl();
                List<User> allUsers = null;
                try {
                    allUsers = userDAO.getAllUsers();
                } catch (SQLException e) {
            %>
            <div class="alert alert-danger">Error loading users: <%= e.getMessage()%></div>
            <%
                    return;
                }
            %>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Full Name</th>
                        <th>NIC</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (allUsers != null && !allUsers.isEmpty()) {
                            for (User user : allUsers) {
                    %>
                    <tr>
                <form action="updateUser" method="POST" id="updateForm<%= user.getId()%>">
                    <td><%= user.getId()%></td>
                    <td><input type="text" name="name" value="<%= user.getName()%>" required></td>
                    <td><input type="text" name="nic" value="<%= user.getNic()%>" required></td>
                    <td><input type="email" name="email" value="<%= user.getEmail()%>" required></td>
                    <td><input type="text" name="phone" value="<%= user.getPhone()%>" required></td>
                    <td>
                        <select name="role">
                            <option value="user" <%= "user".equals(user.getRole()) ? "selected" : ""%>>User</option>
                            <option value="admin" <%= "admin".equals(user.getRole()) ? "selected" : ""%>>Admin</option>
                        </select>
                    </td>
                    <td>
                        <input type="hidden" name="userId" value="<%= user.getId()%>">
                        <button type="button" class="btn btn-sm custom-btn-clr" 
                                data-bs-toggle="modal" data-bs-target="#updateModal<%= user.getId()%>">Update</button>
                        <button type="button" class="btn btn-sm theme-btn" 
                                data-bs-toggle="modal" data-bs-target="#deleteModal<%= user.getId()%>">Delete</button>
                    </td>
                </form>
                </tr>

                <!-- Update Confirmation Modal -->
                <div class="modal fade" id="updateModal<%= user.getId()%>" tabindex="-1" aria-labelledby="updateModalLabel<%= user.getId()%>" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="updateModalLabel<%= user.getId()%>">Confirm Update</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                Are you sure you want to update this user?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary theme-btn" data-bs-dismiss="modal">Cancel</button>
                                <button type="submit" form="updateForm<%= user.getId()%>" class="btn custom-btn-clr">Update</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Delete Confirmation Modal -->
                <div class="modal fade" id="deleteModal<%= user.getId()%>" tabindex="-1" aria-labelledby="deleteModalLabel<%= user.getId()%>" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="deleteModalLabel<%= user.getId()%>">Confirm Delete</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                Are you sure you want to delete this user?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary theme-btn" data-bs-dismiss="modal">Cancel</button>
                                <form action="deleteUser" method="POST">
                                    <input type="hidden" name="userId" value="<%= user.getId()%>">
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
                    <td colspan="7" class="text-center">No users found.</td>
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
