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
import org.dao.RecipeDAO;
import org.dao.ReportDAO;
import org.entity.Recipe;
import org.entity.Report;
import util.JSFutil;

/**
 *
 * @author Nguyen Hoai Nam
 */
public class UserModel {

    //count all user 
    public long countUser() {
        return UserDAO.getInstance().getNumberUserNomal();
    }
    
    //count all ban user
    public long countBanUser() {
        return UserDAO.getInstance().getNumberBanUser();
    }
    
    public long countNumberResultSearch(String name, int flag, String role) {
        if (flag == 2) {
            return UserDAO.getInstance().getNumberResultSearchUserNomal(name,role);
        } else {
            return UserDAO.getInstance().getNumberResultSearchAndFillUserNomal(name, flag,role);
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

    public boolean removeAdmin(String userId) throws DAOException{
        User user = getUserByID(userId);
        if (user !=null && user.getEmail() !=null){
                String contentMail = "Dear "+user.getDisplayName()+",\r\n\r\n"+"I want to notice to you that your account in DailyCook is removed. "
                                            +"Because you are violated the rule of DailyCook.\r\n\r\nDailyCook";
                JSFutil.sentMail(user.getEmail(), JSFutil.EMAIL, JSFutil.PASSWORD, "Notification From DailyCook!!!", 
                        contentMail);
            }
        return UserDAO.getInstance().banUser(userId, User.DELETED_FLAG);
    }
    
    public boolean banUser(String userId) throws DAOException {
        User user = getUserByID(userId);
        int flag = User.BAN_FLAG_ONCE;
        long time = 0;
        String notiFlag = "";
        switch(user.getNumberBans()){
            case 0:
                flag = User.BAN_FLAG_ONCE;
                time = User.BAN_FIRST_TIME;
                notiFlag = "BANNED ONCE";
                break;
            case 1:
                flag = User.BAN_FLAG_SECOND;
                time = User.BAN_SECOND_TIME;
                notiFlag = "BANNED SECOND";
                break;
            case 2:
                flag = User.DELETED_FLAG;
                notiFlag = "DELETED";
                break;
            default:
                return true;
        }
        //ban in DB and notify to server
        long banToTime = 0;
        banToTime = TimeUtils.getCurrentGMTTime() + time;        
        if (UserDAO.getInstance().banUser(userId, flag, banToTime)) {
            NotiServer.getInstance().notiBanUser(userId);
            
            //sent mail
            if (user !=null && user.getEmail() !=null){
                String notiDelete = "";
                if (flag == User.DELETED_FLAG)
                    notiDelete = "All recipes in DailyCook is also removed.";
                String contentMail = "Dear "+user.getDisplayName()+",\r\n\r\n"+"I want to notice to you that your account in DailyCook is '"
                                            +notiFlag
                                            +"'. Because you are violated the rule of DailyCook."
                                            +notiDelete+"\r\n\r\nDailyCook";
                JSFutil.sentMail(user.getEmail(), JSFutil.EMAIL, JSFutil.PASSWORD, "Notification From DailyCook!!!", 
                        contentMail);
            }
            
            //remove all recipes of user
            if (flag == User.DELETED_FLAG){
                List<Recipe> recipes = RecipeDAO.getInstance().getRecipeOfUser(userId);
                for (Recipe recipe : recipes){
                    if (recipe.getStatusFlag() != Recipe.REMOVED_FLAG){
                        if (recipe.getStatusFlag() == Recipe.REPORTED_FLAG){
                            List<Report> reports = ReportDAO.getInstance().getListCheckingReportByRecipe(recipe.getId());
                            for (Report rp : reports){
                                ReportDAO.getInstance().delete(rp.getId(), Report.class);
                            }
                        }
                        RecipeDAO.getInstance().updateRecipeStatus(recipe.getId(), Recipe.REMOVED_FLAG);
                    }
                }

            }
            
            return true;
        } 
        return false;
    }
    public boolean banAdminUser(String userId) throws DAOException {
        User user = getUserByID(userId);
        int flag = User.DELETED_FLAG;
        long time = 0;
        long banToTime = 0;
        banToTime = TimeUtils.getCurrentGMTTime() + time;
        if (UserDAO.getInstance().banUser(userId, flag, banToTime)) {
            NotiServer.getInstance().notiBanUser(userId);
            return true;
        } 
        return false;
    }

    public boolean unBanUser(String userId) throws DAOException {
        User user = getUserByID(userId);
        //sent mail
        if (user !=null && user.getEmail() !=null){
                String contentMail = "Dear "+user.getDisplayName()+",\r\n\r\n"+"I want to notice to you that your account in DailyCook is unbanned. "
                                            +"\r\n\r\nDailyCook";
                JSFutil.sentMail(user.getEmail(), JSFutil.EMAIL, JSFutil.PASSWORD, "Notification From DailyCook!!!", 
                        contentMail);
            }
        return UserDAO.getInstance().unBanUser(userId);
    }

    public String getUserName(String id) throws DAOException {
        
        if (id != null || !"".equals(id)) {
            User u = UserDAO.getInstance().getUser(id);
            String name = u.getDisplayName();
            return name;
        }
        return "Not available";
    }
}
