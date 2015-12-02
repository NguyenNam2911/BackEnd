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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;

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

    public IndexManagedBean() {
        numberUser = userModel.countUser();
        numberReport = reportModel.countCheckingReport();
        numberRecipe = recipeModel.getNumberRecipe();
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
   
}
