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
    private int page;
    private int nPage = 0;
    private long numberP;
    private int type;

    //method
    public RecipeManagedBean() {
        recipeModel = new RecipeModel();
        page = 0;
        listRecipe = recipeModel.getRecipes(page);
        recipes = listRecipe;
        recipe = new Recipe();
        userModel = new UserModel();
        long n =  recipeModel.getNumberRecipe();
        numberP = getNumberPage(n);
        type = 1;
    }

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

    public void updateRecipes(int page) {
        recipes = recipeModel.getRecipes(page);
        this.page = page;
        if (page == 0) {
            type = 1;
        } else if (page == numberP) {
            type = 3;

        } else {
            type = 2;
        }

    }

    public final long getNumberPage(long number) {
        long n;
        if (number % 10 == 0) {
            n = (number / 10) - 1;
        } else {
            n = number / 10;
        }
        return n;
    }

    //get and set
    public long getNumberP() {
        return numberP;
    }

    public void setNumberP(long numberP) {
        this.numberP = numberP;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getnPage() {
        return nPage;
    }

    public void setnPage(int nPage) {
        this.nPage = nPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

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
