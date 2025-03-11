<%-- 
    Document   : booking
    Created on : Feb 27, 2025, 4:02:28 AM
    Author     : Thanuja Fernando
--%>

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


        <% 
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>

        <div class="booking-container">
            <form class="container mt-4" action="booking" method="POST" >
                <div class="card booking-card p-4 shadow-lg">
                    <h5 class="fw-bold">Select a vehicle</h5>
                    <div class="d-flex flex-wrap gap-2 my-3">
                        <div class="vehicle-option  selected" data-vehicle="Flex">Flex</div>
                        <div class="vehicle-option" data-vehicle="Car">Car</div>
                        <div class="vehicle-option" data-vehicle="Van">Van</div>
                    </div>
                    <input type="hidden" name="vehicleType" id="selectedVehicle" value="Flex">
                    <input type="hidden" name="startLocation" id="startLocationInput">
                    <input type="hidden" name="endLocation" id="endLocationInput">
                    <input type="hidden" name="distance" id="distance">
                    <input type="hidden" name="estimatedCost" id="estimatedCostInput" value="0.00">
                    <input type="hidden" name="userId" id="userId" value="<%=user.getId()%>">

                    <div class="vehicle-details">
                        <strong class="d-block" id="vehicle-name">Suzuki Alto 
                            <a href="#" class="text-decoration-none">View</a>
                        </strong>
                        <span class="float-end text-primary fw-bold">Est. LKR <span id="estimated-cost">0.00</span></span>
                        <div class="mt-2 text-secondary">
                            <small id="vehicle-ac">✔ Air Conditioned</small> &nbsp; 
                            <small id="vehicle-seats">👤 4 passengers</small> &nbsp;
                            <small>🛄 Limited baggage</small>
                        </div>
                    </div>

                    <div class="row mt-3 g-2">
                        <div class="col-12 col-md-5">
                            <label for="start-location" class="form-label">Start Location</label>
                            <select id="start-location" class="form-select" required>
                                <option value="Colombo">Colombo</option>
                                <option value="Kandy">Kandy</option>
                                <option value="Galle">Galle</option>
                                <option value="Negombo">Negombo</option>
                                <option value="Jaffna">Jaffna</option>
                                <option value="Nuwara Eliya">Nuwara Eliya</option>
                                <option value="Anuradhapura">Anuradhapura</option>
                            </select>
                        </div>
                        <div class="col-12 col-md-5">
                            <label for="end-location" class="form-label">End Location</label>
                            <select id="end-location" class="form-select" required>
                                <option value="Colombo">Colombo</option>
                                <option value="Kandy">Kandy</option>
                                <option value="Galle">Galle</option>
                                <option value="Negombo">Negombo</option>
                                <option value="Jaffna">Jaffna</option>
                                <option value="Nuwara Eliya">Nuwara Eliya</option>
                                <option value="Anuradhapura">Anuradhapura</option>
                            </select>
                        </div>
                        <div class="col-12 col-md-3">
                            <label for="datetime" class="form-label">Date & Time</label>
                            <input type="datetime-local" class="form-control" id="datetime" name="datetime" required>
                        </div>
                        <div class="col-12 col-md-3">
                            <label for="address" class="form-label">Address</label>
                            <input type="text" class="form-control" id="address" name="address" required>
                        </div>
                        <div class="col-12 col-md-2 d-grid">
                            <button type="submit" class="btn theme-btn">Book Now</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
