/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.UserModel;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.dao.UserDAO;
import org.entity.User;

/**
 *
 * @author KhanhDN
 */
@ManagedBean
@ViewScoped
public class StatisticsUserManagedBean implements Serializable{

    /**
     * Creates a new instance of StatisticsUserManagedBean
     */
    UserModel userModel = new UserModel();
    List<User> listUser = new ArrayList<>();
    String dateFrom;
    String dateTo;
    int countUserActive = 0,
        countUserBanOnce = 0,
        countUserBanSecond = 0,
        countUserDeleted =0;
//    long dfrom;
//    long dto;
    
    public StatisticsUserManagedBean() throws ParseException {
        listUser = userModel.getUsersNomrmal();
        countUserActive = getCountUserFollowFlag(User.ACTIVE_FLAG);
        countUserBanOnce = getCountUserFollowFlag(User.BAN_FLAG_ONCE);
        countUserBanSecond = getCountUserFollowFlag(User.BAN_FLAG_SECOND);
        countUserDeleted = getCountUserFollowFlag(User.DELETED_FLAG);
    }

//    public long getDfrom() {
//        return dfrom;
//    }
//
//    public void setDfrom(long dfrom) {
//        this.dfrom = dfrom;
//    }
//
//    public long getDto() {
//        return dto;
//    }
//
//    public void setDto(long dto) {
//        this.dto = dto;
//    }

    public int getCountUserActive() {
        return countUserActive;
    }

    public int getCountUserBanOnce() {
        return countUserBanOnce;
    }

    public int getCountUserBanSecond() {
        return countUserBanSecond;
    }

    public int getCountUserDeleted() {
        return countUserDeleted;
    }
    
    public List<User> getListUser() {
        return listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }
    
    private int getCountUserFollowFlag(int flag){
        listUser = userModel.getUsersNomrmal();
        int count =0;
        for (User user : listUser){
            if (user.getActiveFlag() == flag)
                count++;
        }
        return count;
    }
    
    private long getTime(String string_date) throws ParseException{
//        String string_date = "12-December-2012";

        SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
        Date d = f.parse(string_date);
        long milliseconds = d.getTime();
        return milliseconds;
    }
    
    private void getDateFromToOfMoth(int month){
        //get from to
        Calendar calendar = Calendar.getInstance();
        // passing month-1 because 0-->jan, 1-->feb... 11-->dec
        int year = Calendar.getInstance().get(Calendar.YEAR);
        calendar.set(year, month - 1, 1);
        
        //date fisrt of month
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        Date date = calendar.getTime();
        DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
        dateFrom = DATE_FORMAT.format(date);
        
        //date last of month
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        date = calendar.getTime();
        DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
        dateTo = DATE_FORMAT.format(date);
    }
    
//    public void demo(int month) throws ParseException{
//        getDateFromToOfMoth(month);
//        dfrom = getTime(dateFrom);
//        dto = getTime(dateTo);
//    }
    
    public long getCountRegisteredUser(int month) throws ParseException{
        getDateFromToOfMoth(month);
        long from = getTime(dateFrom);
        long to = getTime(dateTo);
        return UserDAO.getInstance().getNumberRegisteredUser(from, to);
    }
    
}
