/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.UserModel;
import com.mycompany.backend.util.JSFutil;
import org.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.TimeUtils;
import org.apache.log4j.Logger;
import org.dao.DAOException;
import org.dao.RecipeDAO;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@SessionScoped
public class UserManagedBean {

    /**
     * Creates a new instance of UserManagedBean
     */
    private User user = new User();
    private UserModel userModel = new UserModel();
    private List<User> users = new ArrayList<>();
    private String search;
    private String filter;
    private String sortBy;
    private int page;
    private long numberP;
    private User userSelected = new User();
    private int typePageBtn;
    private String stringSearch;
    private String stringSort;
    private int flag_Active;
    Logger logger = Logger.getLogger(LoginManagedBean.class);

// method
    public UserManagedBean() {
        try {
            filter = "all";
            flag_Active = filter();
            search = "";
            sortBy = "date";
            stringSearch = search;
            stringSort = sort();
            page = 0;
            typePageBtn = 1;
            long n = userModel.countUser();
            numberP = getNumberPage(n);
            users = userModel.getUserNormalByName(stringSearch, page, stringSort, flag_Active);
        } catch (Exception ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex);
            JSFutil.navigate("error");;
        }
    }

    public void searchUser() {
        if (!search.equals("") || !sortBy.equals("date") || !filter.equals("all")) {
            try {
                stringSort = sort();
                stringSearch = search;
                flag_Active = filter();
                page = 0;
                typePageBtn = 1;
                long n = userModel.countNumberResultSearch(stringSearch, flag_Active, User.NORMAL_USER_ROLE);
                numberP = getNumberPage(n);
                users = userModel.getUserNormalByName(stringSearch, page, stringSort, flag_Active);
                search = "";
                filter = "All";
                sortBy = "date";
            } catch (Exception ex) {
                logger.error(ex);
                JSFutil.setSessionValue("error", ex);
                JSFutil.navigate("error");
            }

        } else {
            try {
                page = 0;
                typePageBtn = 1;
                stringSearch = search;
                stringSort = sort();
                flag_Active = filter();
                long n = userModel.countUser();
                numberP = getNumberPage(n);
                users = userModel.getUserNormalByName(stringSearch, page, stringSort, flag_Active);
            } catch (Exception ex) {
                logger.error(ex);
                JSFutil.setSessionValue("error", ex);
                JSFutil.navigate("error");
            }

        }
    }

    public void updateUsers(int page) {
        try {
            users = userModel.getUserNormalByName(stringSearch, page, stringSort, flag_Active);
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
        } catch (Exception ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex);
            JSFutil.navigate("error");
        }

    }
     public void banUserDetail(String id) {
        try {
            userModel.banUser(id);
            users = userModel.getUserNormalByName(stringSearch, page, stringSort, flag_Active);
            JSFutil.navigate("user_view");
        } catch (Exception ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex);
            JSFutil.navigate("error");
        }
    }
     
    public  void updateUserDetail(){
        try {
            users = userModel.getUserNormalByName(stringSearch, page, stringSort, flag_Active);
        } catch (DAOException ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex);
            JSFutil.navigate("error");
        }
    }
     
    public void unBanUserDetail(String id) {
        try {
            userModel.unBanUser(id);
            users = userModel.getUserNormalByName(stringSearch, page, stringSort, flag_Active);
            JSFutil.navigate("user_view");
        } catch (Exception ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex);
            JSFutil.navigate("error");
        }
    }


    private String sort() {
        if (!sortBy.equals("date")) {
            switch (sortBy) {
                case "n_ban":
                    return User.SORT_BY_BAN;
                case "name":
                    return User.SORT_BY_Name;
                case "n_recipe":
                    return User.SORT_BY_RECIPE;
            }
        }
        return User.SORT_BY_DATE;
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

    private int filter() {

        if (!"all".equals(filter)) {
            switch (filter) {
                case "active":
                    return User.ACTIVE_FLAG;
                case "bannedOnce":
                    return User.BAN_FLAG_ONCE;
                case "bannedSecond":
                    return User.BAN_FLAG_SECOND;
                case "deleted":
                    return User.DELETED_FLAG;

            }

        }
        return 2;
    }

    public String convertTime(long time) {
        return TimeUtils.convertTime(time);
    }

    public long getNumberRecipe(String id) {
        try {
            return RecipeDAO.getInstance().getNumberRecipeByOwner(id);
        } catch (Exception ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex);
            JSFutil.navigate("error");
        }
        return 0;
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
