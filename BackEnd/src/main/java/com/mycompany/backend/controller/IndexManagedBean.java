/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.RecipeModel;
import com.mycompany.backend.model.ReportModel;
import com.mycompany.backend.model.UserModel;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import org.dao.DAOException;
import org.entity.ActivityLog.Count;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@SessionScoped

public class IndexManagedBean implements Serializable {

    UserModel userModel = new UserModel();
    RecipeModel recipeModel = new RecipeModel();
    ReportModel reportModel = new ReportModel();
    long numberUser = 0;
    long numberRecipe = 0;
    long numberReport = 0;
    long countNumber =0;
    Logger logger = Logger.getLogger(LoginManagedBean.class);

    public IndexManagedBean() throws DAOException {

        numberUser = userModel.countUser();
        numberReport = reportModel.countCheckingReport();
        numberRecipe = recipeModel.getNumberRecipe();
        countNumber = getCountView();
    }
    
    private long getCountView() throws DAOException{
        UserModel userModel = new UserModel();
        return userModel.getCountActivity();
    }

    //get and set
    public long getNumberUser() {
        return numberUser;
    }

    public void setNumberUser(long numberUser) {
        this.numberUser = numberUser;
    }

    public long getNumberRecipe() {
        return numberRecipe;
    }

    public void setNumberRecipe(long numberRecipe) {
        this.numberRecipe = numberRecipe;
    }

    public long getNumberReport() {
        return numberReport;
    }

    public void setNumberReport(long numberReport) {
        this.numberReport = numberReport;
    }

    public long getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(long countNumber) {
        this.countNumber = countNumber;
    }
    
    
}
