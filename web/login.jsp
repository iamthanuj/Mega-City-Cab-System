<%-- 
    Document   : login
    Created on : Feb 18, 2025, 11:50:56 PM
    Author     : Thanuja Fernando
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="includes/globalStyles.jsp" %>
        <title>Login Page</title>
    </head>
    <body class="login-page">
        <%@include file="includes/navbar.jsp" %>
        <div class="login-container">
            <h2>Login</h2>
            <form>
                <div class="mb-3">
                    <input type="email" class="form-control" placeholder="Email" required>
                </div>
                <div class="mb-3">
                    <input type="password" class="form-control" placeholder="Password" required>
                </div>
                <button type="submit" class="btn btn-custom">Login</button>
            </form>
            <div class="mt-3">
                <p>Don't have an account ?</p>
                <p><a class="text-white" href="register.jsp">Register Here</a></p>
            </div>
        </div>
    </body>
</html>
