/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.RecipeModel;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import org.dao.RecipeDAO;
import org.entity.Recipe;

/**
 *
 * @author KhanhDN
 */
@ManagedBean
@ViewScoped
public class StatisticsRecipeManagedBean implements Serializable{

    /**
     * Creates a new instance of StatisticsRecipeManagedBean
     */
    RecipeModel recipeModel = new RecipeModel();
    String dateFrom;
    String dateTo;
    long countRecipeApproved = 0,
        countRecipeRemoved = 0,
        countRecipes = 0;
    
    public StatisticsRecipeManagedBean() {
        countRecipes = getCountAllRecipe();
        countRecipeApproved = getCountRecipeFollowFlag(Recipe.APPROVED_FLAG);
        countRecipeRemoved = getCountRecipeFollowFlag(Recipe.REMOVED_FLAG);
    }

    public long getCountRecipeApproved() {
        return countRecipeApproved;
    }

    public long getCountRecipeRemoved() {
        return countRecipeRemoved;
    }

    public long getCountRecipes() {
        return countRecipes;
    }
    
    private long getCountRecipeFollowFlag(int flag){
        return RecipeDAO.getInstance().getNumberRecipeWithFlag(flag);
    }
    
    private long getCountAllRecipe(){
        return RecipeDAO.getInstance().getNumberRecipe();
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
    
    public long getCountCreatedRecipe(int month) throws ParseException{
        getDateFromToOfMoth(month);
        long from = getTime(dateFrom);
        long to = getTime(dateTo);
        return RecipeDAO.getInstance().getNumberCreatedRecipe(from, to);
    }
    
    public long getCountDeletedRecipe(int month) throws ParseException{
        getDateFromToOfMoth(month);
        long from = getTime(dateFrom);
        long to = getTime(dateTo);
        return RecipeDAO.getInstance().getNumberDeletedRecipe(from, to);
    }
}
