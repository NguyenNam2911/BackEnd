/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.model;

import com.mycompany.backend.notification_server.NotiServer;
import java.util.ArrayList;
import java.util.List;
import org.TimeUtils;
import org.dao.DAOException;
import org.dao.RecipeDAO;
import org.dao.ReportDAO;
import org.dao.UserDAO;
import org.entity.Recipe;
import org.entity.Report;
import org.entity.User;
import util.JSFutil;

/**
 *
 * @author Nguyen Hoai Nam
 */
public class RecipeModel {

    //get all recipe namnh
    public List<Recipe> getAllRecipe() {
        List<Recipe> list = new ArrayList<>();
        list = RecipeDAO.getInstance().getAllRecipe();
        return list;
    }
    //get recipe follow page
    public List<Recipe> getRecipes(int page) {
        List<Recipe> list = new ArrayList<>();
        list = RecipeDAO.getInstance().getRecipes(page * 10, 10);
        return list;
    }
    // search recipe
    public List<Recipe> searchRecipeByTitle(String title, int page, int flag,  String order) throws DAOException {
        List<Recipe> list = new ArrayList<>();
        if(flag == 2){
            list = RecipeDAO.getInstance().searchAllRecipeByName(title, page * 10, 10,order);
        }
        else{
            list = RecipeDAO.getInstance().searchAllAndFilterRecipeByName(title, page * 10, 10, flag, order);
        }
        return list;
    }
    
    //get number result recipe
    public long getSearchResultNumber(String title, int flag_active) throws DAOException {
        if(flag_active == 2){
            return RecipeDAO.getInstance().getSearchAllResultNumber(title);
        }
        else{
            return RecipeDAO.getInstance().getSearchAndFilterResultNumber(title, flag_active);
        }
        
    }
//get recipe by id
    public Recipe getRecipeByID(String id) {
        return RecipeDAO.getInstance().getRecipe(id);
    }
// remove recipe
    public boolean removeRecipe(String id) throws DAOException {
        if (id !=null && !id.equals("")){
            NotiServer.getInstance().notiRemoveRecipe(id);
            
            //send mail
            Recipe recipe = getRecipeByID(id);
            if (recipe.getOwner() !=null && !recipe.getOwner().equals("")){
                User user = UserDAO.getInstance().getUser(recipe.getOwner());
                if (user !=null && user.getEmail() !=null){
                    String contentMail = "Dear "+user.getDisplayName()+",\r\n\r\n"+"I want to notice to you that your recipe '"
                                                +recipe.getTitle()
                                                +"' is removed. Because the content of your recipe is not appropriate to DailyCook.\r\n\\r\\nDailyCook";
                    JSFutil.sentMail(user.getEmail(), "nguyenhoainam301193@gmail.com", "namhot123", "Notification From DailyCook!!!", 
                            contentMail);
                }
            }
            return RecipeDAO.getInstance().updateRecipeStatus(id, Recipe.REMOVED_FLAG);
        }
        return false;
    }
    //get Number all recipe
    public long getNumberRecipe() {
        return RecipeDAO.getInstance().getNumberRecipe();
    }
    //Update recipe
    public boolean updateRecipeReported(String id) {
        return RecipeDAO.getInstance().updateRecipeStatus(id, Recipe.APPROVED_FLAG);
    }
    
    //get report by recipeId
    public Report getReportByRecipe(String recipeId) throws DAOException{
        return ReportDAO.getInstance().getReportByRecipe(recipeId);
    }
}
