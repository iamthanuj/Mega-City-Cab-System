/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author Thanuja Fernando
 */


public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAOImpl();

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
            if (userDAO.checkUserExists(email)) {
                request.setAttribute("errorMessage", "Email already registered!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                if (userDAO.registerUser(newUser)) {
                    request.setAttribute("successMessage", "Registration successful! Please log in.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage","Registration failed. Try again.");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
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
