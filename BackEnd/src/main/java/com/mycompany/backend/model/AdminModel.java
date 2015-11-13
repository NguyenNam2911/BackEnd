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
    public List<User> getUsersAdmin(){
        List<User> users = UserDAO.getInstance().getAllUser();
        List<User> users_admin = new ArrayList<>();
        for (User user : users) {
            if(user.getRole().equals(User.ADMIN_ROLE)){
                users_admin.add(user);
            }
            
            
        }
        return users_admin;
    }
    //add new admin namnh
    public  void insertAdmin(User user) throws DAOException{
        UserDAO.getInstance().save(user);
    }
    //reset pass
    public boolean resetPass(String id,String pass){
        return UserDAO.getInstance().updateAdminPassWord(id, pass);
    }
    //get admin by email
    public User  getAdminByEmail(String email) throws DAOException{
        return UserDAO.getInstance().getUserInfoByEmail(email);
    }
    
}
