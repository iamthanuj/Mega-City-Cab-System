<%-- 
    Document   : register
    Created on : Feb 19, 2025, 12:44:31 AM
    Author     : Thanuja Fernando
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="includes/globalStyles.jsp" %>
        <title>Register</title>
    </head>
    <body class="login-page">
        <%@include file="includes/navbar.jsp" %>
        <div class="login-container">
            <h2>Register</h2>
            <form action="register" method="post">
                <div class="mb-3">
                    <input type="text" class="form-control dark-inputs" name="name"  placeholder="Full Name" required>
                </div>
                <div class="mb-3">
                    <input type="text" class="form-control dark-inputs" name="nic"  placeholder="NIC" required>
                </div>
                <div class="mb-3">
                    <input type="email" class="form-control dark-inputs" name="email"  placeholder="Email" required>
                </div>
                <div class="mb-3">
                    <input type="text" class="form-control dark-inputs" name="phone"  placeholder="Phone" required>
                </div>
                <div class="mb-3">
                    <input type="password" class="form-control dark-inputs" name="password" placeholder="Password" required>
                </div>
                <button type="submit" class="btn btn-custom">Register</button>

            </form>
            <div class="mt-3">
                <p>Already have an account ?</p>
                <p><a class="text-white" href="login.jsp">Login Here</a></p>
            </div>
            <% if (request.getAttribute("errorMessage") != null) {%>
            <p class="text-danger"><%= request.getAttribute("errorMessage")%></p>
            <% }%>
        </div>
    </body>
</html>
