/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.AdminModel;
import com.mycompany.backend.util.JSFutil;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import org.EncryptDataException;
import org.EncryptHelper;
import org.apache.log4j.Logger;
import org.dao.DAOException;
import org.entity.User;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@ViewScoped
public class ForgotPassManagedBean implements Serializable {

    /**
     * Creates a new instance of ForgotPassManagedBean
     */
    User user;
    AdminModel adminModel;
    boolean successFlag;
    Logger logger = Logger.getLogger(LoginManagedBean.class);

    public ForgotPassManagedBean() {
        user = new User();
        adminModel = new AdminModel();
        successFlag = false;
        JSFutil.setSessionValue("user", null);
    }

    public void resetPassWord() {
        try {
            User userAdmin = adminModel.getAdminByEmail(user.getEmail());
            if (userAdmin != null) {
                String pass = JSFutil.ramdomString(8);
                adminModel.resetPass(userAdmin.getId(), EncryptHelper.encrypt(pass));
                try {
                    JSFutil.sentMail(userAdmin.getEmail(), JSFutil.EMAIL, JSFutil.PASSWORD, "Welcome to dalycook management", pass);

                } catch (Exception e) {
                    JSFutil.addErrorMessageById("frInput:txtEmail", e.getMessage());
                }

                successFlag = true;
            } else {
                JSFutil.addErrorMessageById("frInput:txtEmail", "Email do not exist");
            }
            user = new User();
        } catch (DAOException | EncryptDataException ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex.getMessage());
            JSFutil.navigate("error");
        }
    }

    //get and set
    public boolean isSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(boolean successFlag) {
        this.successFlag = successFlag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
