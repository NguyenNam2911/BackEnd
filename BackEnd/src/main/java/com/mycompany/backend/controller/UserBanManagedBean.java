/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.UserModel;
import com.mycompany.backend.util.JSFutil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.TimeUtils;
import org.apache.log4j.Logger;
import org.dao.DAOException;
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
    Logger logger = Logger.getLogger(LoginManagedBean.class);

    public UserBanManagedBean() {
        try {
            filterText = "3";
            flag = 3;
            searchText = "";
//        sortBy = "display_name";
//        stringSearch = search;
            sortText = "-registered_time";
            currentPage = 0;
            pagePrevious = 0;
            pageMiddle = 1;
            pageNext = 2;
//        typePageBtn = 1;
            long n = userModel.countBanUser();
            numberPage = getNumberPage(n);
            listBanUser = userModel.getUserNormalByName(searchText, currentPage, sortText, flag);
        } catch (Exception ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex.getMessage());
            JSFutil.navigate("error");
        }
    }

    public String convertTime(long time) {
        return TimeUtils.convertTime(time);
    }

    public void unbanUser(String userId){
        try {
            userModel = new UserModel();
            userModel.unBanUser(userId);
            listBanUser = userModel.getUserNormalByName(searchText, currentPage, sortText, flag);
        } catch (DAOException ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex.getMessage());
            JSFutil.navigate("error");
        }
    }

    public void searchBanUser() {
        try {
            flag = getFilterNumber();
            currentPage = 0;
            pagePrevious = 0;
            pageMiddle = 1;
            pageNext = 2;
            long n = userModel.countNumberResultSearch(searchText, flag, User.NORMAL_USER_ROLE);
            numberPage = getNumberPage(n);
            listBanUser = userModel.getUserNormalByName(searchText, currentPage, sortText, flag);
        } catch (Exception ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex.getMessage());
            JSFutil.navigate("error");
        }
    }

//    public void searchBanUser(){
//       List<User> users = new ArrayList<>();
//       if (searchText != null){
//           for (User user : listBanUser){
//               if (user.getDisplayName().contains(searchText)){
//                    users.add(user);
//               }
//           }
//           listBanUser = users;
//       }
//    }
    private int getFilterNumber() {
        int filterNumber;
        if (filterText != null) {
            try {
                filterNumber = Integer.parseInt(filterText);
            } catch (Exception ex) {
                filterNumber = 4;
            }
        } else {
            filterNumber = 4;
        }
        return filterNumber;
    }

    // phan trang
    private long getNumberPage(long number) {
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
        if (changeNumber == 1 || changeNumber == -1) {
            if (currentPage > 1 && currentPage < numberPage) {
                changePageNumber(changeNumber);
            }
        }
        if (changeNumber == -2 && currentPage == pagePrevious && currentPage > 1) {
            changePageNumber(-1);
        }
        if (changeNumber == 2 && currentPage == pageNext && currentPage < (int) numberPage) {
            changePageNumber(1);
        }
        if (changeNumber == -3) {
            pagePrevious = 0;
            changePageNumber(0);
        }
        if (changeNumber == 3 && numberPage > 3) {
            pagePrevious = (int) numberPage - 2;
            changePageNumber(0);
        }
    }

    private void changePageNumber(int changeNumber) {
        pagePrevious += changeNumber;
        pageMiddle = pagePrevious + 1;
        pageNext = pageMiddle + 1;
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

    public String getSortText() {
        return sortText;
    }

    public void setSortText(String sortText) {
        this.sortText = sortText;
    }

    public static int getNUMBER_RECORDS() {
        return NUMBER_RECORDS;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPagePrevious(int pagePrevious) {
        this.pagePrevious = pagePrevious;
    }

    public void setPageMiddle(int pageMiddle) {
        this.pageMiddle = pageMiddle;
    }

    public void setPageNext(int pageNext) {
        this.pageNext = pageNext;
    }

    public void setNumberPage(long numberPage) {
        this.numberPage = numberPage;
    }

}
