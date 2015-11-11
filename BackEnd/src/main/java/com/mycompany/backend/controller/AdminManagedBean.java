/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.AdminModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.dao.DAOException;
import org.entity.User;
import util.JSFutil;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@ViewScoped
public class AdminManagedBean extends Object implements Serializable {

    /**
     * Creates a new instance of AdminManagedBean
     */
    AdminModel adminModel = new AdminModel();
    List<User> users = new ArrayList<>();
    String search;
    User userAdmin;
    boolean addView;
    Date date;

    //method
    public void save(ActionEvent event) throws DAOException {
        if (checkEmail(userAdmin.getEmail())) {
            if (checkName(userAdmin.getDisplayName())) {
                userAdmin.setRole(User.ADMIN_ROLE);
                userAdmin.setPassword(RandomStringUtils.randomAlphanumeric(8));
                userAdmin.setPassword("12345678");
                userAdmin.setRegisteredTime(date.getTime());
                adminModel = new AdminModel();
                adminModel.insertAdmin(userAdmin);
                users = adminModel.getUsersAdmin();
                addView = true;
////        JSFutil.sentMail(userAdmin.getEmail(), "nguyenhoainam301193@gmail.com", "namhot123", "Welcome to dalycook management", userAdmin.getPassword());
                userAdmin = new User();
            } else {
                JSFutil.addErrorMessageById("frmMain:txtName", "Name already exists");

            }

        } else {

            JSFutil.addErrorMessageById("frmMain:txtEmail", "Email already registered");

        }

    }

    public boolean checkEmail(String email) {
        users = adminModel.getUsersAdmin();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return false;
            }

        }
        return true;
    }

    public boolean checkName(String name) {
        users = adminModel.getUsersAdmin();
        for (User user : users) {
            if (user.getDisplayName().equals(name)) {
                return false;
            }

        }
        return true;
    }

    public void cancel(ActionEvent event) {
        userAdmin = new User();
        addView = true;
    }

    public void preAdd(ActionEvent event) {
        userAdmin = new User();
        addView = false;
    }

    public void searchUser() {
        users = adminModel.getUsersAdmin();
        List<User> search_user = new ArrayList<>();
        if (search != null) {
            for (User user : users) {
                if (user.getDisplayName().contains(search)) {
                    search_user.add(user);
                }

            }
            users = search_user;
        }
        search = "";
    }

    public AdminManagedBean() {
        addView = true;
        userAdmin = new User();
        users = adminModel.getUsersAdmin();
        date = new Date();
    }

    //get and set
    public String getSearch() {
        return search;
    }

    public User getUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(User userAdmin) {
        this.userAdmin = userAdmin;
    }

    public boolean isAddView() {
        return addView;
    }

    public void setAddView(boolean addView) {
        this.addView = addView;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
