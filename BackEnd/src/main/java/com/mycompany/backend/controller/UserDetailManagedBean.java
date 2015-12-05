/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.UserModel;
import com.mycompany.backend.util.JSFutil;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.TimeUtils;
import org.dao.DAOException;
import org.dao.RecipeDAO;
import org.entity.User;

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
    long numberRecipe;

    public UserDetailManagedBean() {
        userModel = new UserModel();
        
        if (userSelected == null) {
            JSFutil.navigate("user_view.xhtml");
        }
    }

    public void banUser() throws DAOException {
        userModel.banUser(userSelected.getId());
        userSelected = userModel.getUserByID(userSelected.getId());
    }

    public void unBanUser() throws DAOException {
        userModel.unBanUser(userSelected.getId());
        userSelected = userModel.getUserByID(userSelected.getId());
    }

    public String convertTime(long time) {
        return TimeUtils.convertTime(time);

    }

    public void link() {
        JSFutil.navigate("user_detail");

    }

    public void userDetail(String id) throws DAOException {
        if (id != null) {
            userSelected = userModel.getUserByID(id);
            numberRecipe = RecipeDAO.getInstance().getNumberRecipeByOwner(userSelected.getId());
            JSFutil.navigate("user_detail");
        }else{
            JSFutil.navigate("error");
        }

    }
    //get and set

    public long getNumberRecipe() {
        return numberRecipe;
    }

    public void setNumberRecipe(long numberRecipe) {
        this.numberRecipe = numberRecipe;
    }

    public User getUserSelected() {
        return userSelected;
    }

    public void setUserSelected(User userSelected) {
        this.userSelected = userSelected;
    }

}
