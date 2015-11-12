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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
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
    List<Recipe> listRecipe = new ArrayList<>();
    String dateFrom;
    String dateTo;
    int countRecipeApproved = 0,
        countRecipeRemoved = 0;
    
    public StatisticsRecipeManagedBean() {
        listRecipe = recipeModel.getAllRecipe();
        countRecipeApproved = getCountRecipeFollowFlag(Recipe.APPROVED_FLAG);
        countRecipeRemoved = getCountRecipeFollowFlag(Recipe.REMOVED_FLAG);
    }

    public List<Recipe> getListRecipe() {
        return listRecipe;
    }

    public void setListRecipe(List<Recipe> listRecipe) {
        this.listRecipe = listRecipe;
    }

    public int getCountRecipeApproved() {
        return countRecipeApproved;
    }

    public int getCountRecipeRemoved() {
        return countRecipeRemoved;
    }
    
    private int getCountRecipeFollowFlag(int flag){
        listRecipe = recipeModel.getAllRecipe();
        int count =0;
        for (Recipe recipe : listRecipe){
            if (recipe.getStatusFlag() == flag)
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
    
    public long getCountCreatedRecipe(int month) throws ParseException{
        getDateFromToOfMoth(month);
        long from = getTime(dateFrom);
        long to = getTime(dateTo);
        return RecipeDAO.getInstance().getNumberCreatedRecipe(from, to);
    }
}
