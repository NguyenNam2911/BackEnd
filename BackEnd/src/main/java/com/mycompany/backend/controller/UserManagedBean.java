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
import org.dao.DAOException;

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
    String sortBy;
    int page;
    long numberP;
    User userSelected = new User();
    int typePageBtn;
    String stringSearch;
    String stringSort;

// method
    public UserManagedBean() throws DAOException {
        filter = "Filter";
        search = "";
        sortBy = "display_name";
        stringSearch = search;
        stringSort = sortBy;
        page = 0;
        typePageBtn = 1;
        long n = userModel.countUser();
        numberP = getNumberPage(n);
        users = userModel.getUserNormalByName(stringSearch, page, stringSort);
    }

    public void searchUser() throws DAOException {
        if (!search.equals("") || !sortBy.equals("display_name")) {
            stringSort = sort();
            stringSearch = search;
            page = 0;
            typePageBtn = 1;
            long n = userModel.countNumberResultSearch(stringSearch);
            numberP = getNumberPage(n);
            users = userModel.getUserNormalByName(stringSearch, page, stringSort);
            search = "";
            filter = "All";
            sortBy = "display_name";
        } else {
            page = 0;
            typePageBtn = 1;
            stringSearch = search;
            stringSort = sort();
            long n = userModel.countUser();
            numberP = getNumberPage(n);
            users = userModel.getUserNormalByName(stringSearch, page, stringSort);

        }
    }

    public void updateUsers(int page) throws DAOException {
        users = userModel.getUserNormalByName(stringSearch, page, stringSort);
        this.page = page;
        if (page == 0) {
            typePageBtn = 1;
        } else if (page == numberP) {
            if (numberP > 2) {
                typePageBtn = 3;
            } else {
                typePageBtn = 4;
            }

        } else {
            typePageBtn = 2;
        }

    }

    private String sort() {
        if (!sortBy.equals("name")) {
            switch (sortBy) {
                case "n_ban":
                    return User.SORT_BY_BAN;
                case "date":
                    return User.SORT_BY_DATE;
                case "n_recipe":
                    return User.SORT_BY_RECIPE;
            }
        }
//        return User.SORT_BY_NAME;
        return "display_name";
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
                        if (user.getActiveFlag() == 0 || user.getActiveFlag() == -1) {
                            filter_user.add(user);
                        }
                    }
                    break;
                case "Deleted":
                    for (User user : users) {
                        if (user.getActiveFlag() == -2) {
                            filter_user.add(user);
                        }
                    }
                    break;

            }
            users = filter_user;
            filter = "Filter";
        }
    }

    public String convertTime(long time) {
        return TimeUtils.convertTime(time);
    }

//get and set
    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public int getTypePageBtn() {
        return typePageBtn;
    }

    public void setTypePageBtn(int typePageBtn) {
        this.typePageBtn = typePageBtn;
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
