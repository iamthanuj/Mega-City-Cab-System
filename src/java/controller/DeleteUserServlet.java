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
public class DeleteUserServlet extends HttpServlet {

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

        try {
            if (userId == admin.getId()) {
                response.sendRedirect("manage-users.jsp?error=cannotDeleteSelf");
                return;
            }
            boolean success = userDAO.deleteUser(userId);
            if (success) {
                response.sendRedirect("manage-users.jsp?message=userDeleted");
            } else {
                response.sendRedirect("manage-users.jsp?error=deleteFailed");
            }
        } catch (SQLException e) {
            throw new ServletException("Error deleting user: " + e.getMessage(), e);
        }
    }

}
