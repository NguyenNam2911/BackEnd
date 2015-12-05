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
import org.dao.RecipeDAO;
import org.entity.Recipe;
import org.entity.User;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@SessionScoped
public class RecipeManagedBean implements Serializable {

    /**
     * Creates a new instance of RecipeManagedBean
     */
    private Recipe recipe;
    private final RecipeModel recipeModel;
    private List<Recipe> recipes = new ArrayList<>();
    private String search;
    private String filter;
    private UserModel userModel;
    private int page;
    private long numberP;
    private int typePageBtn;
    private String stringSearch;
    int flag_active;
    private String sortBy;
    private String stringSort;

    //method
    public RecipeManagedBean() throws DAOException {
        recipeModel = new RecipeModel();
        recipe = new Recipe();
        userModel = new UserModel();
        page = 0;
        stringSearch = "";
        search = "";
        typePageBtn = 1;
        filter = "All";
        flag_active = filter();
        sortBy = "date";
        stringSort = sort();
        long n = recipeModel.getNumberRecipe();
        numberP = getNumberPage(n);
        recipes = recipeModel.searchRecipeByTitle(stringSearch, page, flag_active, stringSort);

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

    private int filter() {
        if (!"All".equals(filter)) {
            switch (filter) {
                case "Approve":
                    return Recipe.APPROVED_FLAG;
                case "Report":
                    return Recipe.REPORTED_FLAG;
                case "Removed":
                    return Recipe.REMOVED_FLAG;
            }

        }
        return 2;
    }

    private String sort() {
        if (!sortBy.equals("date")) {
            switch (sortBy) {
                case "owner":
                    return Recipe.SORT_BY_OWNER;
                case "title":
                    return Recipe.SORT_BY_TITLE;
                case "favorite":
                    return Recipe.SORT_BY_FAVORITE_NUMBER;
            }
        }
        return Recipe.SORT_BY_DATE;
    }

    public void searchRecipe() throws DAOException {

        if (!search.equals("") || !filter.equals("All") || !sortBy.equals("date")) {
            flag_active = filter();
            stringSort = sort();
            stringSearch = search;
            page = 0;
            typePageBtn = 1;
            long n = recipeModel.getSearchResultNumber(stringSearch, flag_active);
            numberP = getNumberPage(n);
            recipes = recipeModel.searchRecipeByTitle(stringSearch, page, flag_active, stringSort);
            search = "";
            filter = "All";
            sortBy = "date";
        } else {
            page = 0;
            typePageBtn = 1;
            stringSearch = "";
            flag_active = 2;
            stringSort = sort();
            long n = recipeModel.getNumberRecipe();
            numberP = getNumberPage(n);
            recipes = recipeModel.searchRecipeByTitle(stringSearch, page, flag_active, stringSort);

        }
    }

    public void updateRecipes(int page) throws DAOException {
        recipes = recipeModel.searchRecipeByTitle(stringSearch, page, flag_active, stringSort);
        this.page = page;
        if (page == 0) {
            typePageBtn = 1;
        } else if (page == numberP) {
            if (numberP >= 2) {
                typePageBtn = 3;
            } else {
                typePageBtn = 4;
            }

        } else {
            typePageBtn = 2;
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
    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public long getNumberP() {
        return numberP;
    }

    public void setNumberP(long numberP) {
        this.numberP = numberP;
    }

    public int getTypePageBtn() {
        return typePageBtn;
    }

    public void setTypePageBtn(int typePageBtn) {
        this.typePageBtn = typePageBtn;
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
