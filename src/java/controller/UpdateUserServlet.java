/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import persistance.dao.UserDAO;
import persistance.impl.UserDAOImpl;

/**
 *
 * @author Thanuja Fernando
 */
public class UpdateUserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User admin = (User) request.getSession().getAttribute("user");
        if (admin == null || !"admin".equals(admin.getRole())) {
            response.sendRedirect("login.jsp?error=unauthorized");
            return;
        }

        int userId = Integer.parseInt(request.getParameter("userId"));
        String name = request.getParameter("name");
        String nic = request.getParameter("nic");
        String email = request.getParameter("email");
        int phone = Integer.parseInt(request.getParameter("phone"));
        String role = request.getParameter("role");

        try {
            User user = userDAO.getUserById(userId);
            if (user != null) {
                user.setName(name);
                user.setNic(nic);
                user.setEmail(email);
                user.setPhone(phone);
                user.setRole(role);
                boolean success = userDAO.updateUser(user);
                if (success) {
                    response.sendRedirect("manage-users.jsp?message=userUpdated");
                } else {
                    response.sendRedirect("manage-users.jsp?error=updateFailed");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } catch (SQLException e) {
            throw new ServletException("Error updating user: " + e.getMessage(), e);
        }
    }

}
