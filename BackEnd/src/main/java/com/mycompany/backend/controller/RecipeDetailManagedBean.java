/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;


import com.mycompany.backend.model.UserModel;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.TimeUtils;
import org.dao.DAOException;
import org.entity.Recipe;
import org.entity.User;
import util.JSFutil;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@SessionScoped
public class RecipeDetailManagedBean {

    /**
     * Creates a new instance of RecipeDetailManagedBean
     */
    
    Recipe recipe;
    UserModel userModel;
    User user;
    
    
     //method
    public User getOwner(String id) throws DAOException{
        userModel = new UserModel();
        return userModel.getUserByID(id);
    }
    
    public String getOwnerName(String id) throws DAOException{
        userModel = new UserModel();
        String name = userModel.getUserName(id);
        return name;
    }
    public RecipeDetailManagedBean() {
        recipe = new Recipe();
    }
    
    public void recipeDetail(Recipe r){
        recipe = r;
        JSFutil.navigate("recipe_detail?faces-redirect=true");
        
    }
   
    public String convertTime(long time) {
        return TimeUtils.convertTime(time);
        
    }

    //get and set

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    
    
}
