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
import org.dao.DAOException;
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
    
    static final int NUMBER_RECORDS = 10;
    
    private UserModel userModel = new UserModel();
    private List<User> listBanUser = new ArrayList<>();
    private String searchText;
    private String filterText;
    private String sortText;
    private int filter;
    private int flag;
    private int currentPage;
    private int pagePrevious;
    private int pageMiddle;
    private int pageNext;
    private long numberPage; 

    public UserBanManagedBean() throws DAOException {
        filterText = "all";
        flag = 3;
        searchText = "";
//        sortBy = "display_name";
//        stringSearch = search;
        sortText = "display_name";
        currentPage = 0;
        pagePrevious = 0;
        pageMiddle = 1;
        pageNext = 2;
//        typePageBtn = 1;
        long n = userModel.countBanUser();
        numberPage = getNumberPage(n);
        listBanUser = userModel.getUserNormalByName(searchText, currentPage, sortText, flag);
    }
    
    public String convertTime(long time){
        return TimeUtils.convertTime(time);
    }
    
    public void unbanUser(String userId) throws DAOException{
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
    
    // phan trang
    private long getNumberPage(long number){
        long n;
        if (number % NUMBER_RECORDS == 0) {
            n = (number / NUMBER_RECORDS) - 1;
        } else {
            n = number / NUMBER_RECORDS;
        }
        return n;
    }
    
    public void updateUsers(int page, int changeNumber) throws DAOException {
        listBanUser = userModel.getUserNormalByName(searchText, page, sortText, flag);
        currentPage = page;
        
        //change page number
        if (changeNumber == 1 || changeNumber ==-1){
            if (currentPage > 1 && currentPage < numberPage)
            changePageNumber(changeNumber);
        }
        if (changeNumber ==-2 && currentPage == pagePrevious && currentPage >1){
            changePageNumber(-1);
        }
        if (changeNumber ==2 && currentPage == pageNext && currentPage < (int)numberPage){
            changePageNumber(1);
        }
        if (changeNumber == -3){
            pagePrevious = 0;
            changePageNumber(0);
        }
        if (changeNumber == 3 && numberPage>3){
            pagePrevious = (int)numberPage - 2;
            changePageNumber(0);
        }
    }
    
    private void changePageNumber(int changeNumber){
        pagePrevious += changeNumber;
        pageMiddle = pagePrevious + 1;
        pageNext = pageMiddle +1;
    }
    
    //get and set
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

    public int getCurrentPage() {
        return currentPage;
    }

    public long getNumberPage() {
        return numberPage;
    }

    public int getPagePrevious() {
        return pagePrevious;
    }

    public int getPageMiddle() {
        return pageMiddle;
    }

    public int getPageNext() {
        return pageNext;
    }
    
   
}
