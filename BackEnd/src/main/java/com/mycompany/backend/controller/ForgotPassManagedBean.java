/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.AdminModel;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import org.dao.DAOException;
import org.entity.User;
import util.JSFutil;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@ViewScoped
public class ForgotPassManagedBean implements Serializable{

    /**
     * Creates a new instance of ForgotPassManagedBean
     */
    User user;
    AdminModel adminModel;
    boolean successFlag;
    
    public ForgotPassManagedBean() {
        user = new User();
        adminModel =  new AdminModel();
        successFlag = false;
    }
    public void resetPassWord() throws DAOException {
        User userAdmin =  adminModel.getAdminByEmail(user.getEmail());
        if(userAdmin != null){
            String pass = JSFutil.ramdomString(8);
            adminModel.resetPass(userAdmin.getId(), pass);
            JSFutil.sentMail(userAdmin.getEmail(), "nguyenhoainam301193@gmail.com", "namhot123", "Welcome to dalycook management", pass);
            successFlag = true;
        }
        else{
            JSFutil.addErrorMessageById("frInput:txtEmail", "Email do not exist");
        }
        user = new User();
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
