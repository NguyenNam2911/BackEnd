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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.TimeUtils;
import org.entity.User;


/**
 *
 * @author KhanhDN
 */
@ManagedBean
@SessionScoped
public class UserBanManagedBean {

    /**
     * Creates a new instance of UserBanManagedBean
     */
    UserModel userModel = new UserModel();
    List<User> listBanUser = new ArrayList<>();
    String searchText;
    String filterText;
    int filter;

    public UserBanManagedBean() {
        listBanUser = userModel.getBanUser();
    }
    
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
    
    public String convertTime(long time){
        return TimeUtils.convertTime(time);
    }
    
    public void unbanUser(String userId){
        userModel.unBanUser(userId);
        listBanUser = userModel.getBanUser();
    }
    
    public void searchBanUser(){
       List<User> users = new ArrayList<>();
       if (searchText != null){
           for (User user : listBanUser){
               if (user.getDisplayName().contains(searchText)){
                    users.add(user);
               }
           }
           listBanUser = users;
       }
    }
    
    public void filterBanUser(){
        List<User> users = new ArrayList<>();
        listBanUser = userModel.getBanUser();
        if (filterText != null){
           try{
               filter = Integer.parseInt(filterText);
               if (filter != 0 && filter !=-1 && filter !=-2)
                   filter=2;
           }catch(Exception ex){
               filter=2;
           }
       }else{
            filter=2;
       }
       
        if (filter!=2 && filter!=1){
                for (User user : listBanUser){
                    if (user.getActiveFlag() == filter)
                        users.add(user);
                }
           listBanUser = users;
        }
    }
}
