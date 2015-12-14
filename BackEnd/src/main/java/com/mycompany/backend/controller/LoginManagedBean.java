/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.LoginModel;
import com.mycompany.backend.util.JSFutil;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.EncryptDataException;
import org.EncryptHelper;
import org.dao.DAOException;
import org.entity.User;
import org.apache.log4j.Logger;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@SessionScoped
public class LoginManagedBean implements Serializable {

    /**
     * Creates a new instance of LoginManagedBean
     */
    User user;
    LoginModel loginModel = new LoginModel();
    boolean flagAdmin;
    String email;
    String password;
    Logger logger = Logger.getLogger(LoginManagedBean.class);

    //method
    public void checkLogin() throws DAOException, EncryptDataException {
        
            User user_check = loginModel.CheckLogin(email);
            password = EncryptHelper.encrypt(password);
            if (user_check != null) {
                if (user_check.getPassword().equals(password)) {
                    user = user_check;
                    flagAdmin = user.getRole().equals(User.ADMIN_ROLE);
                    JSFutil.setSessionValue("user", user);
                    logger.error(email);
                    
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

    public void preResetPass() {
        JSFutil.navigate("forgot_pass");
    }

    // contructer
    public LoginManagedBean() {
        try {
            flagAdmin = false;
            JSFutil.setSessionValue("user", null);
        } catch (Exception ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex);
            JSFutil.navigate("error");
        }

    }

//get and set
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
