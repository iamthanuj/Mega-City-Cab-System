/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import persistance.dao.UserDAO;
import persistance.impl.UserDAOImpl;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import model.User;
import service.services.UserService;

/**
 *
 * @author Thanuja Fernando
 */
public class RegisterServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form parameters
        String name = request.getParameter("name");
        String nic = request.getParameter("nic");
        String email = request.getParameter("email");
        int phone;
        try {
            phone = Integer.parseInt(request.getParameter("phone"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid phone number format!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        String password = request.getParameter("password");

        // Validate inputs
        if (name == null || name.trim().isEmpty() || nic == null || nic.trim().isEmpty()
                || email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Create new user and set default role
        User newUser = new User(name, nic, email, phone, password);
        newUser.setRole("user"); // Set default role for new users

        try {
            // Check if email already exists
            if (userService.checkUserExists(email)) {
                request.setAttribute("errorMessage", "Email already registered!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                // Attempt to register the user
                if (userService.registerUser(newUser)) {
                    request.setAttribute("successMessage", "Registration successful! Please log in.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Registration failed. Please try again.");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
