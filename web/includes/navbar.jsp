<%-- 
    Document   : navbar
    Created on : Feb 18, 2025, 2:20:57 AM
    Author     : Thanuja Fernando
--%>

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
                    <a class="nav-link text-dark" href="#">About Us</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-dark" href="#">Help</a>
                </li>
            </ul>

            <!-- Right Section -->
            <div class="d-flex align-items-center">
                <%
                    // Assuming 'user' session attribute indicates logged-in user
                    Object user = session.getAttribute("user");
                %>
                <a href="#" class="btn btn-dark me-2 custom-btn-clr">Book a ride</a>
                <a href="login.jsp" class="btn btn-dark me-2 custom-btn-clr">Sign In</a>

                <!-- Avatar Dropdown -->
                <div class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <!-- Avatar Image -->
                        <img src="assets/images/avatar.jpg" alt="User Avatar" class="rounded-circle" width="40" height="40">
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">View Profile</a></li>
                        <li><a class="dropdown-item" href="#">Logout</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>
