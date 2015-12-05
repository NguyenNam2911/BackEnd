/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.UserModel;
import com.mycompany.backend.util.JSFutil;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.TimeUtils;
import org.apache.log4j.Logger;
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
    Logger logger = Logger.getLogger(LoginManagedBean.class);

    public UserDetailManagedBean() {
        userModel = new UserModel();

        if (userSelected == null) {
            JSFutil.navigate("user_view.xhtml");
        }
    }

    public void banUser() {
        try {
            userModel.banUser(userSelected.getId());
            userSelected = userModel.getUserByID(userSelected.getId());
        } catch (DAOException ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex.getMessage());
            JSFutil.navigate("error");
        }
    }

    public void unBanUser() {
        try {
            userModel.unBanUser(userSelected.getId());
            userSelected = userModel.getUserByID(userSelected.getId());
        } catch (DAOException ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex.getMessage());
            JSFutil.navigate("error");
        }
    }

    public String convertTime(long time) {
        return TimeUtils.convertTime(time);

    }

    public void link() {
        JSFutil.navigate("user_detail");

    }

    public void userDetail(String id) {
        if (id != null) {
            try {
                userSelected = userModel.getUserByID(id);
                numberRecipe = RecipeDAO.getInstance().getNumberRecipeByOwner(userSelected.getId());
                JSFutil.navigate("user_detail");
            } catch (DAOException ex) {
                logger.error(ex);
                JSFutil.setSessionValue("error", ex.getMessage());
                JSFutil.navigate("error");
            }
        } else {
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
