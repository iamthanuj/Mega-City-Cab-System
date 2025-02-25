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

        String name = request.getParameter("name");
        String nic = request.getParameter("nic");
        String email = request.getParameter("email");
        int phone = Integer.parseInt(request.getParameter("phone"));
        String password = request.getParameter("password");

        User newUser = new User(name, nic, email, phone, password);

        try {
//            if (userService.checkUserExists(email)) {
//                request.setAttribute("errorMessage", "Email already registered!");
//                request.getRequestDispatcher("register.jsp").forward(request, response);
//            } else {
//                if (userService.registerUser(newUser)) {
//                    request.setAttribute("successMessage", "Registration successful! Please log in.");
//                    request.getRequestDispatcher("login.jsp").forward(request, response);
//                } else {
//                    request.setAttribute("errorMessage","Registration failed. Try again.");
//                    request.getRequestDispatcher("register.jsp").forward(request, response);
//                }
//            }
            if (userService.registerUser(newUser)) {
                request.setAttribute("successMessage", "Registration successful! Please log in.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Email already registered!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
