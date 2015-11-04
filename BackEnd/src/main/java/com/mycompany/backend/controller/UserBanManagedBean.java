/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;


import com.mycompany.backend.model.UserModel;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.entity.User;


/**
 *
 * @author KhanhDN
 */
@ManagedBean
@ViewScoped
public class UserBanManagedBean {

    /**
     * Creates a new instance of UserBanManagedBean
     */
    UserModel userModel = new UserModel();
    List<User> listBanUser = new ArrayList<>();
    String searchText;
    String filterText;
    int filter;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<User> getListBanUser() {
        return listBanUser;
    }

    public void setListBanUser(List<User> listBanUser) {
        this.listBanUser = listBanUser;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }
    
    public UserBanManagedBean() {
        listBanUser = userModel.getBanUser();
    }
    
    
}
