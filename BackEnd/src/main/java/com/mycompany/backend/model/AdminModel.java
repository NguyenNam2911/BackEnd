/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.model;

import java.util.ArrayList;
import java.util.List;
import org.dao.DAOException;
import org.dao.UserDAO;
import org.entity.User;

/**
 *
 * @author Nguyen Hoai Nam
 */
public class AdminModel {

    //get list admin namnh
    public List<User> getUsersAdmin() {

        List<User> users_admin = UserDAO.getInstance().getAllUserAdmin();
        return users_admin;

    }

    public boolean checkUserAdminByEmail(String email) throws DAOException {
        User u = UserDAO.getInstance().getUserInfoByEmail(email);
        if (u == null) {
            return true;
        } else {
            return false;
        }
    }

    //add new admin namnh
    public void insertAdmin(User user) throws DAOException {
        UserDAO.getInstance().save(user);
    }

    //reset pass
    public boolean resetPass(String id, String pass) {
        return UserDAO.getInstance().updateAdminPassWord(id, pass);
    }

    //get admin by email
    public User getAdminByEmail(String email) throws DAOException {
        User user = UserDAO.getInstance().getUserInfoByEmail(email);
        if (user != null) {
            if (user.getRole().equals(User.ADMIN_ROLE) || user.getRole().equals(User.SUPER_ADMIN_ROLE)) {
                if (user.getActiveFlag() == User.ACTIVE_FLAG) {
                    return user;
                }
                user = null;
            } else {
                user = null;
            }
            return user;
        } else {
            return null;
        }

    }

    public List<User> getUserAdminByName(String name, int page, int flag) throws DAOException {
        List<User> users = new ArrayList<>();
        if (flag == 2) {
            users = UserDAO.getInstance().searchAllUserAdmin(name, page * 10, 10);
        } else {
            users = UserDAO.getInstance().searchAndFillAllUserAdmin(name, page * 10, 10, flag);
        }

        return users;
    }

}
