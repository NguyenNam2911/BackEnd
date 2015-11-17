/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.model;

import java.util.ArrayList;
import java.util.List;
import org.TimeUtils;
import org.dao.RecipeDAO;
import org.entity.Recipe;

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

    public Recipe getRecipeByID(String id) {
        return RecipeDAO.getInstance().getRecipe(id);
    }

    public boolean removeRecipe(String id) {
        return RecipeDAO.getInstance().updateRecipeStatus(id, Recipe.REMOVED_FLAG, TimeUtils.getCurrentGMTTime());
    }
    
    public long countRecipe(){
        return RecipeDAO.getInstance().getNumberRecipe();
    }
    
    public boolean updateRecipeReported(String id){
        return RecipeDAO.getInstance().updateRecipeStatus(id, Recipe.APPROVED_FLAG);
    }
}
