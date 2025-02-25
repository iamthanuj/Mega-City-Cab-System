/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.factory;

import persistance.dao.UserDAO;
import persistance.impl.UserDAOImpl;

/**
 *
 * @author Thanuja Fernando
 */
public class DAOFactory {

    public static UserDAO getUserDAO() {
        return new UserDAOImpl();
    }
}
