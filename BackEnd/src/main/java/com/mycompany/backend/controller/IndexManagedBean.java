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
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import org.entity.Recipe;
import org.entity.Report;
import org.entity.User;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@ViewScoped

public class IndexManagedBean  implements Serializable{

    UserModel userModel = new UserModel();
    RecipeModel recipeModel =  new RecipeModel();
    ReportModel reportModel =  new ReportModel();
    int numberUser =  0;
    int numberRecipe = 0;
    int numberReport = 0;
    
    public IndexManagedBean() {
        getUserNumber();
        getRecipeNumber();
        getReportNumber();
        
    }
    
    public void getUserNumber(){
        
        List<User> list = new ArrayList<>();
        list = userModel.getUsersNomrmal();
        if(list != null){
            numberUser = list.size();
        }
    }
    
    public void getRecipeNumber(){
        List<Recipe> list = new ArrayList<>();
        list = recipeModel.getAllRecipe();
        if(list != null){
            numberRecipe = list.size();
        }
    }
    
    public void getReportNumber(){
        List<Report> list = new ArrayList<>();
        list =  reportModel.getListReports();
        if(list != null){
            numberReport =  list.size();
        }
        
        
    }
    
    //get and set

    public int getNumberUser() {
        return numberUser;
    }

    public void setNumberUser(int numberUser) {
        this.numberUser = numberUser;
    }

    public int getNumberRecipe() {
        return numberRecipe;
    }

    public void setNumberRecipe(int numberRecipe) {
        this.numberRecipe = numberRecipe;
    }

    public int getNumberReport() {
        return numberReport;
    }

    public void setNumberReport(int numberReport) {
        this.numberReport = numberReport;
    }
    
}
