<%-- 
    Document   : navbar
    Created on : Feb 18, 2025, 2:20:57 AM
    Author     : Thanuja Fernando
--%>

<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<nav class="navbar navbar-expand-lg fixed-top  shadow-sm" style="background-color: var(--mainColor)">
    <div class="container">
        <!-- Logo -->
        <a class="navbar-brand fw-bold text-dark d-flex align-items-center" href="#">
            <img src="<%= request.getContextPath()%>/assets/images/main-logo.png" alt="Logo"  height="60" class="me-2">
        </a>

        <!-- Toggle button for mobile view -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Navbar Links -->
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto me-4">
                <li class="nav-item">
                    <a class="nav-link text-dark" href="index.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-dark" href="#">Services</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-dark" href="about.jsp">About Us</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-dark" href="help.jsp">Help</a>
                </li>
            </ul>

            <!-- Right Section -->
            <div class="d-flex align-items-center">
                <%
                    // Assuming 'user' session attribute indicates logged-in user
                    User user = (User) session.getAttribute("user");
                %>
                <a href="booking.jsp" class="btn btn-dark me-2 custom-btn-clr">Book a ride</a>

                <%
                    if (user != null) { // User is logged in
                %>

                <!-- Avatar Dropdown -->
                <div class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <!-- Avatar Image -->
                        <img src="assets/images/avatar.jpg" alt="User Avatar" class="rounded-circle" width="40" height="40">
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><p class="dropdown-item"><% out.print(user.getName()); %></p></li>
                        <li><a class="dropdown-item" href="my-bookings.jsp">My Bookings</a></li>
                        <li><a class="dropdown-item" href="#">View Profile</a></li>
                        <li><a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#logoutModal">Logout</a></li>
                    </ul>
                </div>

                <%                } else { // User is not logged in
                %>

                <a href="login.jsp" class="btn btn-dark me-2 custom-btn-clr">Sign In</a>

                <%
                    }
                %>

            </div>
        </div>
    </div>
</nav>


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
                <button type="button" class="btn btn-secondary theme-btn" data-bs-d ismiss="modal">Cancel</button>
                <form action="logout" method="get">
                    <button type="submit" class="btn custom-btn-clr">Logout</button>
                </form>
            </div>
        </div>
    </div>
</div>