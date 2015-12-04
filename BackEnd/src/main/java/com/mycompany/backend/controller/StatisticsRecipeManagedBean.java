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
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;
import org.dao.DAOException;
import org.dao.RecipeDAO;
import org.entity.Recipe;
import org.entity.Tag;

/**
 *
 * @author KhanhDN
 */
@ManagedBean
@SessionScoped
public class StatisticsRecipeManagedBean implements Serializable{

    /**
     * Creates a new instance of StatisticsRecipeManagedBean
     */
    
    RecipeModel recipeModel = new RecipeModel();
    String dateFrom;
    String dateTo;
    long countRecipeApproved = 0,
        countRecipeRemoved = 0,
        countRecipeReported = 0,
        countRecipes = 0;
    List<Tag> listTags;
    List<Recipe> listRecipes;
    
    public StatisticsRecipeManagedBean() throws DAOException {
        countRecipes = getCountAllRecipe();
        countRecipeApproved = getCountRecipeFollowFlag(Recipe.APPROVED_FLAG);
        countRecipeRemoved = getCountRecipeFollowFlag(Recipe.REMOVED_FLAG);
        countRecipeReported = getCountRecipeFollowFlag(Recipe.REPORTED_FLAG);
        listTags = getTopTag(10);
        listRecipes = getTopRecipe(10);
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
    
    //get top tags in tagging
    public List<Tag> getTopTag(int top) throws DAOException{
        List<Tag> myList = new ArrayList<Tag>();
        Iterator<Tag> tags = recipeModel.getTopTags(top);
        while (tags.hasNext())
            myList.add(tags.next());
        return myList;
    }
    
    //get top recipes in tagging
    public List<Recipe> getTopRecipe(int top) throws DAOException{
        List<Recipe> myList = new ArrayList<Recipe>();
        Iterator<Recipe> recipes = recipeModel.getTopRecipes(top);
        while (recipes.hasNext())
            myList.add(recipes.next());
        return myList;
    }
    
    //get and set
    public long getCountRecipeApproved() {
        return countRecipeApproved;
    }

    public long getCountRecipeRemoved() {
        return countRecipeRemoved;
    }

    public long getCountRecipes() {
        return countRecipes;
    }

    public List<Tag> getListTags() {
        return listTags;
    }

    public void setListTags(List<Tag> listTags) {
        this.listTags = listTags;
    }

    public List<Recipe> getListRecipes() {
        return listRecipes;
    }

    public void setListRecipes(List<Recipe> listRecipes) {
        this.listRecipes = listRecipes;
    }

    public long getCountRecipeReported() {
        return countRecipeReported;
    }

    public void setCountRecipeReported(long countRecipeReported) {
        this.countRecipeReported = countRecipeReported;
    }
    
    
}
