/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.model;

import com.mycompany.backend.notification_server.NotiServer;
import java.util.ArrayList;
import java.util.List;
import org.dao.DAOException;
import org.dao.UserDAO;
import org.entity.User;
import org.TimeUtils;

/**
 *
 * @author Nguyen Hoai Nam
 */
public class UserModel {

    //count all user 
    public long countUser() {
        return UserDAO.getInstance().getNumberUserNomal();
    }

    public long countNumberResultSearch(String name, int flag) {
        if (flag == 2) {
            return UserDAO.getInstance().getNumberResultSearchUserNomal(name);
        } else {
            return UserDAO.getInstance().getNumberResultSearchAndFillUserNomal(name, flag);
        }
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

    public List<User> getUserNormalByName(String name, int page, String order, int flag) throws DAOException {
        List<User> users = new ArrayList<>();
        if (flag == 2) {
            users = UserDAO.getInstance().searchAllUserNomal(name, page * 10, 10, order);
        } else {
            users = UserDAO.getInstance().searchAndFillAllUserNomal(name, page * 10, 10, order, flag);
        }

        return users;
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

    public boolean banUser(String userId, int flag){
        return UserDAO.getInstance().banUser(userId, flag);
    }
    
    public boolean banUser(String userId) throws DAOException {
        User user = getUserByID(userId);
        int flag = User.BAN_FLAG_ONCE;
        long time = 0;
        switch(user.getNumberBans()){
            case 0:
                flag = User.BAN_FLAG_ONCE;
                time = User.BAN_FIRST_TIME;
                break;
            case 1:
                flag = User.BAN_FLAG_SECOND;
                time = User.BAN_SECOND_TIME;
                break;
            case 2:
                flag = User.DELETED_FLAG;
                break;
            default:
                return true;
        }
        long banToTime = 0;
        banToTime = TimeUtils.getCurrentGMTTime() + time;
        if (UserDAO.getInstance().banUser(userId, flag, banToTime)) {
            NotiServer.getInstance().notiBanUser(userId);
            return true;
        } 
        return false;
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
