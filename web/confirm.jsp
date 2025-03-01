<%-- 
    Document   : confirm
    Created on : Mar 2, 2025, 2:58:04 AM
    Author     : Thanuja Fernando
--%>

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

                <%-- Retrieve booking from session --%>
                <c:set var="booking" value="${sessionScope.latestBooking}" />

                <c:if test="${booking != null}">
                    <dl class="receipt-details">
                        <dt>User ID:</dt>
                        <dd>${booking.userId}</dd>

                        <dt>Vehicle:</dt>
                        <dd>${booking.vehicle.name} (${booking.vehicle.type})</dd>

                        <dt>Distance:</dt>
                        <dd>${booking.distance} km</dd>

                        <dt>Total Cost:</dt>
                        <dd>LKR ${booking.totalCost}</dd>

                        <dt>Start Location:</dt>
                        <dd>${booking.startLocation}</dd>

                        <dt>End Location:</dt>
                        <dd>${booking.endLocation}</dd>

                        <dt>Date & Time:</dt>
                        <dd>${booking.datetime}</dd>

                        <dt>Address:</dt>
                        <dd>${booking.address}</dd>
                    </dl>

                    <%-- Clear the booking from session after displaying --%>
                    <% session.removeAttribute("latestBooking");%>
                </c:if>

                <c:if test="${booking == null}">
                    <p class="text-danger">No booking details available.</p>
                </c:if>
            </div>

            <div class="text-center no-print">
                <button class="btn btn-dark" onclick="printReceipt()">Print Receipt</button>
                <a href="/booking.jsp" class="btn btn-secondary">Book Another</a>
            </div>
        </div>
    </body>
</html>
