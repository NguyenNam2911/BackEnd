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
import javax.faces.bean.ViewScoped;
import org.TimeUtils;
import org.dao.DAOException;
import org.entity.Recipe;
import org.entity.User;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@ViewScoped
public class RecipeManagedBean implements Serializable {

    /**
     * Creates a new instance of RecipeManagedBean
     */
    private Recipe recipe;
    private final RecipeModel recipeModel;
    private List<Recipe> recipes = new ArrayList<>();
    private List<Recipe> listRecipe = new ArrayList<>();
    private String search;
    private String filter;
    private UserModel userModel;

    //method
    public String convertTime(long time) {
        return TimeUtils.convertTime(time);
    }

    public String getOwnerName(String id) throws DAOException {
        userModel = new UserModel();
        String name = userModel.getUserName(id);
        return name;
    }

    public User getUserById(String id) throws DAOException {
        return userModel.getUserByID(id);
    }

    public void filter() {
        List<Recipe> filter_Recipe = new ArrayList<>();
        recipes = listRecipe;
        if (!"Filter".equals(filter)) {
            switch (filter) {
                case "Approve":
                    for (Recipe r : recipes) {
                        if (r.getStatusFlag() == 1) {
                            filter_Recipe.add(r);
                        }
                    }
                    break;
                case "Report":
                    for (Recipe r : recipes) {
                        if (r.getStatusFlag() == 0) {
                            filter_Recipe.add(r);
                        }
                    }
                    break;
                case "Removed":
                    for (Recipe r : recipes) {
                        if (r.getStatusFlag() == -1) {
                            filter_Recipe.add(r);
                        }
                    }
                    break;
            }
            recipes = filter_Recipe;
            filter = "Filter";
        }
    }

    public void searchRecipe() {
        List<Recipe> search_recipe = new ArrayList<>();
        recipes = listRecipe;
        if (search != null) {
            for (Recipe r : recipes) {
                if (r.getTitle().contains(search)) {
                    search_recipe.add(r);
                }
            }
            recipes = search_recipe;
        }
        search = "";
    }

    public RecipeManagedBean() {
        recipeModel = new RecipeModel();
        listRecipe = recipeModel.getAllRecipe();
        recipes = listRecipe;
        recipe = new Recipe();
        userModel = new UserModel();
    }
    //get and set

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

}
