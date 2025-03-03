<%-- 
    Document   : confirm
    Created on : Mar 2, 2025, 2:58:04 AM
    Author     : Thanuja Fernando
--%>

<%@page import="service.model.Booking"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="includes/globalStyles.jsp" %>
        <title>Confirmation</title>
    </head>
    <body>
        <%@include file="includes/navbar.jsp" %>
        <div class="container mt-4">
            <div class="receipt-container" id="receipt">
                <div class="receipt-header">
                    <h2>Booking Receipt</h2>
                    <p>Thank you for your booking!</p>
                </div>

                <% 
                    Booking booking = (Booking) session.getAttribute("latestBooking");

                    if (booking != null) {
                %>
                <dl class="receipt-details">
                    <dt>User ID:</dt>
                    <dd><%= booking.getUserId()%></dd>

                    <dt>Vehicle:</dt>
                    <dd><%= booking.getVehicle().getName()%> (<%= booking.getVehicle().getType()%>)</dd>

                    <dt>Distance:</dt>
                    <dd><%= booking.getDistance()%> km</dd>

                    <dt>Total Cost:</dt>
                    <dd>LKR <%= booking.getTotalCost()%></dd>

                    <dt>Start Location:</dt>
                    <dd><%= booking.getStartLocation()%></dd>

                    <dt>End Location:</dt>
                    <dd><%= booking.getEndLocation()%></dd>

                    <dt>Date & Time:</dt>
                    <dd><%= booking.getDatetime()%></dd>

                    <dt>Address:</dt>
                    <dd><%= booking.getAddress()%></dd>
                </dl>

                <%
                    // Clear the booking from session after displaying
                    session.removeAttribute("latestBooking");
                %>
                <%
                } else {
                %>
                <p class="text-danger">No booking details available.</p>
                <%
                    }
                %>
            </div>

            <div class="text-center no-print">
                <button class="btn btn-dark" onclick="printReceipt()">Print Receipt</button>
                <a href="/booking.jsp" class="btn btn-secondary">Book Another</a>
            </div>
        </div>
    </body>
</html>
