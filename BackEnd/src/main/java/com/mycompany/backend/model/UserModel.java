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
public class UserModel {

    //count all user 
    public long countUser() {
        return UserDAO.getInstance().getNumberUserNomal();
    }

    public List<User> getUsersNomrmal() {
        List<User> users = new ArrayList<>();
        List<User> users_normal = new ArrayList<>();
        users = UserDAO.getInstance().getAllUser();
        for (User user : users) {
            if (user.getRole().equals(User.NORMAL_USER_ROLE)) {
                users_normal.add(user);
            }
        }
        return users_normal;
    }

    public List<User> getBanUser() {
        List<User> normalUsers = new ArrayList<>();
        normalUsers = getUsersNomrmal();
        List<User> banUsers = new ArrayList<>();
        for (User user : normalUsers) {
            if (user.getActiveFlag() != User.ACTIVE_FLAG) {
                banUsers.add(user);
            }
        }
        return banUsers;
    }

    public User getUserByID(String id) throws DAOException {
        return UserDAO.getInstance().getUser(id);
    }

    public boolean increaseReportOfUser(String userId) {
        return UserDAO.getInstance().increateReportNumber(userId);
    }

    public boolean banUser(String userId, int flag) {
        return UserDAO.getInstance().banUser(userId, flag);
    }

    public boolean unBanUser(String userId) {
        return UserDAO.getInstance().unBanUser(userId);
    }

    public String getUserName(String id) throws DAOException {
        User u = UserDAO.getInstance().getUser(id);
        if (u != null) {
            String name = u.getDisplayName();
            return name;
        }
        return "Not available";
    }
}
