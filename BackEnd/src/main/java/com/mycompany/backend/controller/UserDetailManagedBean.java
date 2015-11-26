/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.UserModel;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.TimeUtils;
import org.dao.DAOException;
import org.dao.UserDAO;
import org.entity.User;
import util.JSFutil;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@SessionScoped
public class UserDetailManagedBean {

    /**
     * Creates a new instance of UserDetailManagedBean
     */
    //method
    User userSelected;
    UserModel userModel;

    public UserDetailManagedBean() {
        userModel = new UserModel();
        userSelected = new User();
    }

    public void banUser() throws DAOException {
        userModel.banUser(userSelected.getId());
        userSelected = userModel.getUserByID(userSelected.getId());
    }
    
    public void unBanUser() throws DAOException{
        UserDAO.getInstance().unBanUser(userSelected.getId());
        userSelected = userModel.getUserByID(userSelected.getId());
    }

    public String convertTime(long time) {
        return TimeUtils.convertTime(time);

    }

    public void link() {
        JSFutil.navigate("user_detail?faces-redirect=true");

    }

    public void userDetail(String id) throws DAOException {
        userSelected = userModel.getUserByID(id);
        JSFutil.navigate("user_detail?faces-redirect=true");

    }
    //get and set

    public User getUserSelected() {
        return userSelected;
    }

    public void setUserSelected(User userSelected) {
        this.userSelected = userSelected;
    }

}
