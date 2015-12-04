/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.AdminModel;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.EncryptDataException;
import org.EncryptHelper;
import org.dao.DAOException;
import org.entity.User;
import util.JSFutil;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@SessionScoped
public class AdminDetailManagedBean implements Serializable {

    /**
     * Creates a new instance of AdminDetailManagedBean
     */
    User userAdmin;
    AdminModel adminModel;
    String oldPass;
    String newPass;
    String rePass;

    public AdminDetailManagedBean() {
        userAdmin = new User();
        adminModel = new AdminModel();
        oldPass = "";
        newPass = "";
        rePass = "";
    }

    public void adminDetail(String email) throws DAOException {
        userAdmin = adminModel.getAdminByEmail(email);
        JSFutil.navigate("change_pass");
    }

    public void changePass() throws DAOException, EncryptDataException {
        if (userAdmin.getPassword().equals(EncryptHelper.encrypt(oldPass))) {
            if (!userAdmin.getPassword().equals(newPass)) {
                if (newPass.equals(rePass)) {
                    adminModel.resetPass(userAdmin.getId(),EncryptHelper.encrypt(newPass));
                    userAdmin = adminModel.getAdminByEmail(userAdmin.getEmail());
                    oldPass = "";
                    newPass = "";
                    rePass = "";
                    JSFutil.addSuccessMessageById("frmMain:txtSuccess", "Password have been changed");
                } else {
                    JSFutil.addErrorMessageById("frmMain:txtRePass", "Password not match");
                }

            }else {
                JSFutil.addErrorMessageById("frmMain:txtCrrentPass", "New passWord the same current password");
            }

        } else {
            JSFutil.addErrorMessageById("frmMain:txtCrrentPass", "PassWord incorect");
        }

    }
//    getter and setter

    public User getUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(User userAdmin) {
        this.userAdmin = userAdmin;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getRePass() {
        return rePass;
    }

    public void setRePass(String rePass) {
        this.rePass = rePass;
    }

}
