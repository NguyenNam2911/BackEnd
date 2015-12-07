/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.RecipeModel;
import com.mycompany.backend.model.ReportModel;
import com.mycompany.backend.model.UserModel;
import com.mycompany.backend.util.JSFutil;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.dao.DAOException;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@ViewScoped

public class IndexManagedBean implements Serializable {

    UserModel userModel = new UserModel();
    RecipeModel recipeModel = new RecipeModel();
    ReportModel reportModel = new ReportModel();
    long numberUser = 0;
    long numberRecipe = 0;
    long numberReport = 0;
    long countNumber =0;
    Logger logger = Logger.getLogger(LoginManagedBean.class);

    public IndexManagedBean(){

        try {
            numberUser = userModel.countUser();
            numberReport = reportModel.countCheckingReport();
            numberRecipe = recipeModel.getNumberRecipe();
            countNumber = getCountView();
        } catch (Exception ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex.getMessage());
            JSFutil.navigate("error");
        }
    }
    
    private long getCountView() throws DAOException{
        UserModel um = new UserModel();
        return um.getCountActivity();
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
