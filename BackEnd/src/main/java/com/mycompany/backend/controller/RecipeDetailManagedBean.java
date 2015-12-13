/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.RecipeModel;
import com.mycompany.backend.model.UserModel;
import com.mycompany.backend.util.JSFutil;
import static com.mycompany.backend.util.JSFutil.getSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import org.TimeUtils;
import org.dao.DAOException;
import org.entity.Ingredient;
import org.entity.Recipe;
import org.entity.Report;
import org.entity.User;
import org.apache.log4j.Logger;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@SessionScoped
public class RecipeDetailManagedBean implements Serializable {

    /**
     * Creates a new instance of RecipeDetailManagedBean
     */
    Recipe recipe;
    Report report;
    UserModel userModel;
    RecipeModel recipeModel;
    User user;
    List<Ingredient> listIngredients = new ArrayList<>();
    List<Recipe.Step> listSteps = new ArrayList<>();
    List<String> listTag = new ArrayList<>();
    Logger logger = Logger.getLogger(LoginManagedBean.class);

    //method
    public User getOwner(String id) {
        try {
            userModel = new UserModel();
            return userModel.getUserByID(id);
        } catch (Exception ex) {
            logger.error(ex);
            JSFutil.setSessionValue("error", ex);
            JSFutil.navigate("error");
        }
        return null;
    }

    public String getOwnerName(String id) {
        if (id.equals("") || id == null) {
            return "Not available!";
        } else {
            try {
                userModel = new UserModel();
                String name = userModel.getUserName(id);
                return name;
            } catch (Exception ex) {
                logger.error(ex);
                JSFutil.setSessionValue("error", ex);
                JSFutil.navigate("error");
            }
        }
        return "Not available!";

    }

    public RecipeDetailManagedBean() {
            recipeModel = new RecipeModel();   
            if (recipe == null) {
                JSFutil.navigate("recipe_view.xhtml");
            }     
    }

    public void recipeDetail(String id) {
        if (id == null || id.equals("")) {
            JSFutil.navigate("recipe_view");
        } else {
            try {
                recipe = recipeModel.getRecipeByID(id);
                report = recipeModel.getReportByRecipe(id);
                listIngredients = recipe.getIngredients();
                listSteps = recipe.getSteps();
                listTag = recipe.getCategoryIds();
                JSFutil.navigate("recipe_detail");
            } catch (Exception ex) {
                logger.error(ex);
                JSFutil.setSessionValue("error", ex);
                JSFutil.navigate("error");
            }
        }
    }

    public void reportPage() {
        JSFutil.navigate("report_view");
    }

    public String convertTime(long time) {
        return TimeUtils.convertTime(time);
    }

    //get and set
    public List<String> getListTag() {
        return listTag;
    }

    public void setListTag(List<String> listTag) {
        this.listTag = listTag;
    }

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

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
