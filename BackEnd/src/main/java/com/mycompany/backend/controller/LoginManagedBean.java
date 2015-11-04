/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;


import com.mycompany.backend.model.LoginModel;
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
public class LoginManagedBean {

    /**
     * Creates a new instance of LoginManagedBean
     */
    User user = new User();
    LoginModel loginModel = new LoginModel();
    boolean pass;
    boolean email;
    boolean flagAdmin;

    public void checkLogin() throws DAOException {
        User user_check = loginModel.CheckLogin(user);
        if (user_check != null) {
            if (user_check.getPassword().equals(user.getPassword())) {
                user = user_check;
                flagAdmin = user.getRole().equals("admin");
                JSFutil.navigate("index?faces-redirect=true");
            } else {
                pass = true;
                email = false;
            }
        } else {
            email = true;
            pass = false;
        }
    }

    public void logOut() {
        JSFutil.navigate("login?faces-redirect=true");
    }

    // contructer
    public LoginManagedBean() {
        pass = false;
        email = false;
        flagAdmin = false;
    }
    
    public boolean isFlagAdmin() {
        return flagAdmin;
    }

    //get and set
    public void setFlagAdmin(boolean flagAdmin) {    
        this.flagAdmin = flagAdmin;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
