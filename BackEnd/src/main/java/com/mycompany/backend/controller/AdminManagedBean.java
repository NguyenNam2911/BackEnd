/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.AdminModel;
import com.mycompany.backend.model.UserModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
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
    String stringSearch;
    String filter;
    String stringFilter;
    int page;
    long numberP;
    int typePageBtn;
    UserModel userModel;
    int flag;
    int flag_Active;
    String password;

    //method
    public AdminManagedBean() throws DAOException {
        addView = true;
        userAdmin = new User();
        userModel = new UserModel();
//        users = adminModel.getUsersAdmin();
        date = new Date();
        filter = "all";
        flag_Active = filter();
        search = "";
        stringSearch = search;
        page = 0;
        typePageBtn = 1;
        long n = userModel.countNumberResultSearch(stringSearch, flag_Active, User.ADMIN_ROLE);
        numberP = getNumberPage(n);
        users = adminModel.getUserAdminByName(stringSearch, page, flag_Active);
    }

    public void searchUser() throws DAOException {
        if (!search.equals("") || !filter.equals("all")) {
            stringSearch = search;
            flag_Active = filter();
            page = 0;
            typePageBtn = 1;
            long n = userModel.countNumberResultSearch(stringSearch, flag_Active, User.ADMIN_ROLE);
            numberP = getNumberPage(n);
            users = adminModel.getUserAdminByName(stringSearch, page, flag_Active);
            search = "";
            filter = "All";

        } else {
            page = 0;
            typePageBtn = 1;
            stringSearch = search;
            flag_Active = filter();
            long n = userModel.countNumberResultSearch(stringSearch, flag_Active, User.ADMIN_ROLE);
            numberP = getNumberPage(n);
            users = adminModel.getUserAdminByName(stringSearch, page, flag_Active);

        }
    }

    public final long getNumberPage(long number) {
        long n;
        if (number % 10 == 0) {
            n = (number / 10) - 1;
        } else {
            n = number / 10;
        }
        return n;
    }

    public void updateUsers(int page) throws DAOException {
        users = adminModel.getUserAdminByName(stringSearch, page, flag_Active);
        this.page = page;
        if (page == 0) {
            typePageBtn = 1;
        } else if (page == numberP) {
            if (numberP >= 2) {
                typePageBtn = 3;
            } else {
                typePageBtn = 4;
            }

        } else {
            typePageBtn = 2;
        }

    }

    private int filter() {

        if (!"all".equals(filter)) {
            switch (filter) {
                case "active":
                    return User.ACTIVE_FLAG;
                case "deleted":
                    return User.DELETED_FLAG;
            }

        }
        return 2;
    }

    public void save(ActionEvent event) throws DAOException, EncryptDataException {
        if (checkEmail(userAdmin.getEmail())) {
            if (checkName(userAdmin.getDisplayName())) {
                userAdmin.setRole(User.ADMIN_ROLE);  
                password = JSFutil.ramdomString(8);
                userAdmin.setPassword(EncryptHelper.encrypt(password));
//                userAdmin.setPassword("12345678");
                userAdmin.setRegisteredTime(date.getTime());
                adminModel = new AdminModel();
                adminModel.insertAdmin(userAdmin);
                filter = "all";
                flag_Active = filter();
                search = "";
                stringSearch = search;
                page = 0;
                typePageBtn = 1;
                long n = userModel.countNumberResultSearch(stringSearch, flag_Active, User.ADMIN_ROLE);
                numberP = getNumberPage(n);
                users = adminModel.getUserAdminByName(stringSearch, page, flag_Active);
                addView = true;
                JSFutil.sentMail(userAdmin.getEmail(), JSFutil.EMAIL, JSFutil.PASSWORD, "Welcome to dalycook management", password);
                userAdmin = new User();
                password = "";
                
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

    public void deleteUser(User u) throws DAOException {
        UserModel userModel = new UserModel();
        userModel.removeAdmin(u.getId());
        users = adminModel.getUsersAdmin();
    }

    //get and set
    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getNumberP() {
        return numberP;
    }

    public void setNumberP(long numberP) {
        this.numberP = numberP;
    }

    public int getTypePageBtn() {
        return typePageBtn;
    }

    public void setTypePageBtn(int typePageBtn) {
        this.typePageBtn = typePageBtn;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

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
