/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.RecipeModel;
import com.mycompany.backend.model.UserModel;
import com.mycompany.backend.util.JSFutil;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.TimeUtils;
import org.apache.log4j.Logger;
import org.dao.DAOException;
import org.entity.Recipe;
import org.entity.User;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@SessionScoped
public class RecipeByUserManagedBean {

    /**
     * Creates a new instance of RecipeByUserManagedBean
     */
    Logger logger = Logger.getLogger(LoginManagedBean.class);
    User owner = new User();
    UserModel userMode = new UserModel();
    List<Recipe> recipes;
    String search;
    String stringSearch;
    String filter;
    String sortBy;
    String stringSort;
    int flag_active;
    RecipeModel recipeModel = new RecipeModel();
    long numberP;
    int page;
    int typePageBtn;

    public RecipeByUserManagedBean() {

    }

    public void getRecipeByUserManagedBean(String id) {

        try {
            owner = userMode.getUserByID(id);
            recipes = new ArrayList<>();
            filter = "all";
            sortBy = "date";
            search = "";
            typePageBtn = 1;
            stringSearch = search;
            flag_active = filter();
            stringSort = sort();
            long n = recipeModel.getSearchByOwerResultNumber(stringSearch, id, flag_active);
            numberP = getNumberPage(n);
            page = 0;
            recipes = recipeModel.searchRecipeByOwner(stringSearch, owner.getId(), page, flag_active, stringSort);
            JSFutil.navigate("recipe_by_user");
        } catch (Exception ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex.getMessage());
            JSFutil.navigate("error");
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
                case "title":
                    return Recipe.SORT_BY_TITLE;
                case "favorite":
                    return Recipe.SORT_BY_FAVORITE_NUMBER;
            }
        }
        return Recipe.SORT_BY_DATE;
    }

    public void searchRecipe() {
        if (!search.equals("") || !filter.equals("All") || !sortBy.equals("date")) {
            try {
                flag_active = filter();
                stringSort = sort();
                stringSearch = search;
                page = 0;
                typePageBtn = 1;
                long n = recipeModel.getSearchByOwerResultNumber(search, owner.getId(), flag_active);
                numberP = getNumberPage(n);
                recipes = recipeModel.searchRecipeByOwner(stringSearch, owner.getId(), page, flag_active, stringSort);
                search = "";
                filter = "All";
                sortBy = "date";
            } catch (Exception ex) {
                logger.error(ex);
                JSFutil.setSessionValue("error", ex);
                JSFutil.navigate("error");
            }
        } else {
            try {
                page = 0;
                typePageBtn = 1;
                stringSearch = "";
                flag_active = 2;
                stringSort = sort();
                long n = recipeModel.getNumberRecipe();
                numberP = getNumberPage(n);
                recipes = recipeModel.searchRecipeByOwner(stringSearch, owner.getId(), page, flag_active, stringSort);
            } catch (Exception ex) {
                logger.error(ex);
                JSFutil.setSessionValue("error", ex);
                JSFutil.navigate("error");
            }

        }
    }

    public void updateRecipes(int page) {
        try {
            recipes = recipeModel.searchRecipeByOwner(stringSearch, owner.getId(), page, flag_active, stringSort);
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
        } catch (Exception ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex);
            JSFutil.navigate("error");
        }

    }

    public String convertTime(long time) {
        return TimeUtils.convertTime(time);
    }
//getter and setter

    public int getTypePageBtn() {
        return typePageBtn;
    }

    public void setTypePageBtn(int typePageBtn) {
        this.typePageBtn = typePageBtn;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public UserModel getUserMode() {
        return userMode;
    }

    public void setUserMode(UserModel userMode) {
        this.userMode = userMode;
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

    public String getStringSearch() {
        return stringSearch;
    }

    public void setStringSearch(String stringSearch) {
        this.stringSearch = stringSearch;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getStringSort() {
        return stringSort;
    }

    public void setStringSort(String stringSort) {
        this.stringSort = stringSort;
    }

    public int getFlag_active() {
        return flag_active;
    }

    public void setFlag_active(int flag_active) {
        this.flag_active = flag_active;
    }

    public RecipeModel getRecipeModel() {
        return recipeModel;
    }

    public void setRecipeModel(RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
    }

    public long getNumberP() {
        return numberP;
    }

    public void setNumberP(long numberP) {
        this.numberP = numberP;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

}
