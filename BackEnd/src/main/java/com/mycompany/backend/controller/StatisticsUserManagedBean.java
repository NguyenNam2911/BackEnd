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
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;
import org.apache.log4j.Logger;
import org.dao.DAOException;
import org.dao.UserDAO;
import org.entity.ActivityLog.Count;
import org.entity.User;

/**
 *
 * @author KhanhDN
 */
@ManagedBean
@SessionScoped
public class StatisticsUserManagedBean implements Serializable {

    /**
     * Creates a new instance of StatisticsUserManagedBean
     */
    public static final long TIME_DAY = 24 * 60 * 60 * 1000L;

    UserModel userModel = new UserModel();
    String dateFrom;
    String dateTo;
    long countUserActive = 0,
            countUserBanOnce = 0,
            countUserBanSecond = 0,
            countUserDeleted = 0,
            countUsers = 0;
//    long dfrom;
//    long dto;
    String mondayText;
    Date mondayDate;
    String sundayText;
    Date sundayDate;
    List<Count> listCountView;
    Logger logger = Logger.getLogger(LoginManagedBean.class);

    public StatisticsUserManagedBean() throws ParseException, DAOException {
        countUsers = getCountAllUser();
        countUserActive = getCountUserFollowFlag(User.ACTIVE_FLAG);
        countUserBanOnce = getCountUserFollowFlag(User.BAN_FLAG_ONCE);
        countUserBanSecond = getCountUserFollowFlag(User.BAN_FLAG_SECOND);
        countUserDeleted = getCountUserFollowFlag(User.DELETED_FLAG);

        //get date from to
        mondayDate = getMondayCurrentWeek();
        mondayText = getTextDate(mondayDate);
        sundayDate = getDate(6);
        sundayText = getTextDate(sundayDate);

        //get list count view
        listCountView = getCountUserViewByDay(mondayDate, sundayDate);
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
    private long getCountUserFollowFlag(int flag) {
        return UserDAO.getInstance().getNumberUserWithFlag(flag);
    }

    private long getCountAllUser() {
        return UserDAO.getInstance().getNumberUser();
    }

    private long getTime(String string_date) throws ParseException {
//        String string_date = "12-December-2012";

        SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
        Date d = f.parse(string_date);
        long milliseconds = d.getTime();
        return milliseconds;
    }

    private void getDateFromToOfMoth(int month) {
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
    public long getCountRegisteredUser(int month) throws ParseException {
        getDateFromToOfMoth(month);
        long from = getTime(dateFrom);
        long to = getTime(dateTo);
        return UserDAO.getInstance().getNumberRegisteredUser(from, to);
    }

    public List<Count> getCountUserViewByDay(Date from, Date to) throws DAOException {
        Iterator<Count> iter = userModel.getCountUserViewByDay(from, to);
        List<Count> myList = new ArrayList<Count>();
        while (iter.hasNext()) {
            myList.add(iter.next());
        }
        return myList;
    }

    public Date getDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mondayDate);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }

    public Date getMondayCurrentWeek() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date date = c.getTime();
        return date;
    }

    public String getTextDate(Date date) {
        DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
        String dateText = (String) DATE_FORMAT.format(date);
        return dateText;
    }

    public void updateCalendar(int i) throws DAOException {
        mondayDate = getDate(7 * i);
        mondayText = getTextDate(mondayDate);
        sundayDate = getDate(6);
        sundayText = getTextDate(sundayDate);
        listCountView = getCountUserViewByDay(mondayDate, sundayDate);
    }

    public int getCountById(int i) {
        if (i >= listCountView.size()) {
            return 0;
        }
        return listCountView.get(i).getCount();
    }

    //get and set
    public long getCountUserActive() {
        return countUserActive;
    }

    public long getCountUserBanOnce() {
        return countUserBanOnce;
    }

    public long getCountUserBanSecond() {
        return countUserBanSecond;
    }

    public long getCountUserDeleted() {
        return countUserDeleted;
    }

    public long getCountUsers() {
        return countUsers;
    }

    public List<Count> getListCountView() {
        return listCountView;
    }

    public void setListCountView(List<Count> listCountView) {
        this.listCountView = listCountView;
    }

    public String getMondayText() {
        return mondayText;
    }

    public void setMondayText(String mondayText) {
        this.mondayText = mondayText;
    }

    public String getSundayText() {
        return sundayText;
    }

    public void setSundayText(String sundayText) {
        this.sundayText = sundayText;
    }

}
