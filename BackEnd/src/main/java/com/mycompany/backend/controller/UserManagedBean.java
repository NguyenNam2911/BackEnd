/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.UserModel;
import org.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.TimeUtils;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@ViewScoped
public class UserManagedBean {

    /**
     * Creates a new instance of UserManagedBean
     */
    User user = new User();
    UserModel userModel = new UserModel();
    List<User> users = new ArrayList<>();
    String search;
    String filter;
    User userSelected = new User();

// method
    public String convertTime(long time) {
        return TimeUtils.convertTime(time);
    }

    public UserManagedBean() {
        users = userModel.getUsersNomrmal();
        filter = "Filter";
    }

    public void searchUser() {
        List<User> search_user = new ArrayList<>();
        users = userModel.getUsersNomrmal();
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

    public void filter() {
        List<User> filter_user = new ArrayList<>();
        users = userModel.getUsersNomrmal();
        if (!"Filter".equals(filter)) {
            switch (filter) {
                case "Active":
                    for (User user : users) {
                        if (user.getActiveFlag() == 1) {
                            filter_user.add(user);
                        }
                    }
                    break;
                case "Banned":
                    for (User user : users) {
                        if (user.getActiveFlag() == 0) {
                            filter_user.add(user);
                        }
                    }
                    break;
            }
            users = filter_user;
            filter = "Filter";
        }
    }
//get and set

    public User getUserSelected() {
        return userSelected;
    }

    public void setUserSelected(User userSelected) {
        this.userSelected = userSelected;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String Filter) {
        this.filter = Filter;
    }

    public String getSearch() {
        return search;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
