/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.RecipeModel;
import com.mycompany.backend.model.UserModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.TimeUtils;
import org.dao.DAOException;
import org.entity.Ingredient;
import org.entity.Recipe;
import org.entity.User;
import util.JSFutil;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@SessionScoped
public class RecipeDetailManagedBean implements Serializable{

    /**
     * Creates a new instance of RecipeDetailManagedBean
     */
    Recipe recipe;
    UserModel userModel;
    RecipeModel recipeModel;
    User user;
    List<Ingredient> listIngredients = new ArrayList<>();
    List<Recipe.Step> listSteps = new ArrayList<>();

    //method
    public User getOwner(String id) throws DAOException {
        userModel = new UserModel();
        return userModel.getUserByID(id);
    }

    public String getOwnerName(String id) throws DAOException {
        userModel = new UserModel();
        String name = userModel.getUserName(id);
        return name;
    }

    public RecipeDetailManagedBean() {
        recipeModel =  new RecipeModel();
        if(recipe == null){
//            JSFutil.navigate("recipe_view?faces-redirect=true");
        }
    }

    public void recipeDetail(String id) {
        if (id != null) {
            recipe = recipeModel.getRecipeByID(id);
            listIngredients = recipe.getIngredients();
            listSteps = recipe.getSteps();
            JSFutil.navigate("recipe_detail?faces-redirect=true");
        }
        else{
            JSFutil.navigate("recipe_view?faces-redirect=true");
        }
    }
    public void reportPage(){
        JSFutil.navigate("report_view?faces-redirect=true");
    }

    public String convertTime(long time) {
        return TimeUtils.convertTime(time);

    }
    public void removeRecipe(){
//        recipeModel.removeRecipe(recipe.getId());
//        recipe = recipeModel.getRecipeByID(recipe.getId());
    }

    //get and set
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<Ingredient> getListIngredients() {
        return listIngredients;
    }

    public void setListIngredients(List<Ingredient> listIngredients) {
        this.listIngredients = listIngredients;
    }

    public List<Recipe.Step> getListSteps() {
        return listSteps;
    }

    public void setListSteps(List<Recipe.Step> listSteps) {
        this.listSteps = listSteps;
    }
}
