<%-- 
    Document   : help
    Created on : Mar 13, 2025, 7:58:49 PM
    Author     : Thanuja Fernando
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="includes/globalStyles.jsp" %>
        <title>Help</title>
    </head>
    <body>
        <%@include file="includes/navbar.jsp" %>
        <div class="container mt-5">
            <h2 class="mb-4 text-center">Help & Support - Mega City Cab</h2>

            <div class="accordion" id="helpAccordion">
                <!-- User Registration -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingOne">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne">
                            How to Register an Account with Mega City Cab?
                        </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse show" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <p>To create an account with Mega City Cab, follow these steps:</p>
                            <ol>
                                <li>Click on the <strong>Register</strong> link in the navigation bar.</li>
                                <li>Fill in the registration form with the following details:</li>
                                <ul>
                                    <li>Full Name</li>
                                    <li>NIC (National Identity Card number)</li>
                                    <li>Email Address (must be unique)</li>
                                    <li>Phone Number (numeric only)</li>
                                    <li>Password</li>
                                </ul>
                                <li>Click the <strong>Register</strong> button to submit the form.</li>
                            </ol>
                            <p>Upon successful registration, you will be redirected to the <strong>Login</strong> page with a confirmation message. If the email is already registered, you’ll be asked to use a different email.</p>
                        </div>
                    </div>
                </div>

                <!-- Login Process -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingTwo">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo">
                            How to Log In to My Mega City Cab Account?
                        </button>
                    </h2>
                    <div id="collapseTwo" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <p>To log in to your Mega City Cab account:</p>
                            <ol>
                                <li>Click on the <strong>Login</strong> link in the navigation bar.</li>
                                <li>Enter your registered <strong>Email Address</strong> and <strong>Password</strong>.</li>
                                <li>Click the <strong>Login</strong> button.</li>
                            </ol>
                            <p>If you enter incorrect credentials, an error message will appear. If you’ve forgotten your password, please contact customer support for assistance.</p>
                        </div>
                    </div>
                </div>

                <!-- Booking a Vehicle -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingThree">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree">
                            How to Book a Ride with Mega City Cab?
                        </button>
                    </h2>
                    <div id="collapseThree" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <p>To book a ride with Mega City Cab, follow these steps:</p>
                            <ol>
                                <li>Log in to your account using your email and password.</li>
                                <li>Navigate to the <strong>Book a Ride</strong> page (available in the navigation bar).</li>
                                <li>Fill in the booking form with the following details:</li>
                                <ul>
                                    <li>Pickup Location</li>
                                    <li>Destination</li>
                                    <li>Vehicle Type (e.g., Car, Van, Bike)</li>
                                    <li>Preferred Date and Time</li>
                                </ul>
                                <li>Click the <strong>Book Now</strong> button to submit your request.</li>
                            </ol>
                            <p>Once the booking is confirmed, a driver will be assigned (if available), and you’ll receive a booking confirmation with details such as the booking ID, driver name, and estimated cost.</p>
                        </div>
                    </div>
                </div>

                <!-- Viewing Bookings -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingFour">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour">
                            How to View My Bookings?
                        </button>
                    </h2>
                    <div id="collapseFour" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <p>To view your bookings with Mega City Cab:</p>
                            <ol>
                                <li>Log in to your account.</li>
                                <li>Click on the <strong>My Bookings</strong> link in the navigation bar.</li>
                                <li>You will see a list of all your bookings, including:</li>
                                <ul>
                                    <li>Booking ID</li>
                                    <li>Pickup and Destination Locations</li>
                                    <li>Vehicle Type</li>
                                    <li>Driver Details (if assigned)</li>
                                    <li>Booking Status (e.g., Pending, Confirmed, Completed)</li>
                                    <li>Total Cost (if completed)</li>
                                </ul>
                            </ol>
                            <p>You can click on a specific booking to view more details or download a receipt.</p>
                        </div>
                    </div>
                </div>

                <!-- Payment & Billing -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingFive">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFive">
                            How are Payments Calculated for My Rides?
                        </button>
                    </h2>
                    <div id="collapseFive" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <p>The cost of your ride with Mega City Cab is calculated based on the following factors:</p>
                            <ul>
                                <li><strong>Distance Traveled:</strong> The distance between your pickup and destination locations (calculated using a mapping service).</li>
                                <li><strong>Vehicle Type:</strong> Different rates apply for different vehicle types (e.g., Car, Van, Bike).</li>
                                <li><strong>Base Fare:</strong> A fixed starting fee for each ride.</li>
                                <li><strong>Additional Charges:</strong> Includes taxes or surcharges (if applicable).</li>
                            </ul>
                            <p>After your ride is completed, the total cost will be displayed in the <strong>My Bookings</strong> section under the specific booking. You can also download a receipt for your records.</p>
                        </div>
                    </div>
                </div>

                <!-- Contact Support -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingSix">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseSix">
                            How to Contact Mega City Cab Support?
                        </button>
                    </h2>
                    <div id="collapseSix" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <p>If you need assistance, our Mega City Cab support team is here to help:</p>
                            <ul>
                                <li><strong>Email:</strong> <a href="mailto:support@megacitycab.com">support@megacitycab.com</a></li>
                                <li><strong>Phone:</strong> <a href="tel:+94123456789">+94 123 456 789</a></li>
                                <li><strong>Operating Hours:</strong> Monday to Sunday, 8:00 AM to 8:00 PM</li>
                            </ul>
                            <p>We aim to respond to all inquiries within 24 hours. For urgent issues, please call our support line.</p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="text-center mt-4">
                <a href="index.jsp" class="btn btn-custom">Back to Home</a>
            </div>
        </div>
    </body>
</html>
