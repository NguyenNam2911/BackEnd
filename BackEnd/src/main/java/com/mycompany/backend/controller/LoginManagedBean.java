/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;


import com.mycompany.backend.model.LoginModel;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.dao.DAOException;
import org.entity.User;
import util.JSFutil;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@SessionScoped
public class LoginManagedBean implements Serializable{

    /**
     * Creates a new instance of LoginManagedBean
     */
    User user = new User();
    LoginModel loginModel = new LoginModel();
    boolean flagAdmin;
    //method
    public void checkLogin() throws DAOException {
        User user_check = loginModel.CheckLogin(user);
        if (user_check != null) {
            if (user_check.getPassword().equals(user.getPassword())) {
                user = user_check;
                flagAdmin = user.getRole().equals(User.ADMIN_ROLE);
                JSFutil.setSessionValue("user", user);
                JSFutil.navigate("index");
            } else {

                JSFutil.addErrorMessage("PassWord incorect");
            }
        } else {

            JSFutil.addErrorMessage("Email incorect");
        }
    }

    public void logOut() {
        JSFutil.setSessionValue("user", null);
        JSFutil.navigate("logout");
    }
    public void preResetPass(){
        JSFutil.navigate("forgot_pass");
    }
 
    // contructer
    public LoginManagedBean() {
        flagAdmin = false;
    }
    
    //get and set
    public boolean isFlagAdmin() {
        return flagAdmin;
    }

    
    public void setFlagAdmin(boolean flagAdmin) {    
        this.flagAdmin = flagAdmin;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
