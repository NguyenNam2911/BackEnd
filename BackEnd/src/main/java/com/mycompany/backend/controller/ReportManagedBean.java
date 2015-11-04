/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.RecipeModel;
import com.mycompany.backend.model.ReportModel;
import com.mycompany.backend.model.UserModel;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.TimeUtils;
import org.dao.DAOException;
import org.dao.RecipeDAO;
import org.dao.UserDAO;
import org.entity.Recipe;
import org.entity.Report;
import org.entity.User;

/**
 *
 * @author KhanhDN
 */
@ManagedBean
@ViewScoped
public class ReportManagedBean {

    /**
     * Creates a new instance of ReportManagedBean
     */
    ReportModel reportModel = new ReportModel();
    RecipeModel recipeModel = new RecipeModel();
    UserModel userModel = new UserModel();
    List<Report> listReport = new ArrayList<Report>();
    String searchText;
    String filterText;
    int filter;
    
    public ReportManagedBean() {
        listReport = reportModel.getListReports();
    }

    public List<Report> getListReport() {
        return listReport;
    }

    public void setListReport(List<Report> listReport) {
        this.listReport = listReport;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }
    
    public String getUserName(String id) throws DAOException{
        User user = userModel.getUserByID(id);
        if (user != null)
            return user.getDisplayName();
        return "";
    }
    
    public String getRecipeName(String id){
        Recipe recipe = recipeModel.getRecipeByID(id);
        if (recipe != null)
            return recipe.getTitle();
        return "";
    }
    
    public String convertTime(long time){
        return TimeUtils.convertTime(time);
    }
    
    public void searchReport(){
       List<Report> reports = new ArrayList<Report>();
       if (filterText != null){
           try{
               filter = Integer.parseInt(filterText);
               if (filter != 1 && filter !=0)
                   filter=-1;
           }catch(Exception ex){
               filter=-1;
           }
       }
       if (searchText != null){
           for (Report report : listReport){
               if (getRecipeName(report.getId()).contains(searchText)){
                   if (filter==-1){
                       reports.add(report);
                   }else{
                       if (report.getStatus() == filter)
                           reports.add(report);
                   }
               }
           }
           listReport = reports;
       }
    }
    
    public void filter(){
        List<Report> reports = new ArrayList<Report>();
        listReport = reportModel.getListReports();
        if (filterText != null){
           try{
               filter = Integer.parseInt(filterText);
               if (filter != 1 && filter !=0 && filter !=2)
                   filter=-1;
           }catch(Exception ex){
               filter=-1;
           }
       }else{
            filter=-1;
       }
       
        if (filter!=-1 && filter!=2){
                for (Report report : listReport){
                    if (report.getStatus() == filter)
                        reports.add(report);
                }
           listReport = reports;
        }
    }
    
    public void approveReportStatus(String id) throws DAOException{
        
        //remove recipe
        reportModel.approveReportStatus(id);
        Report report = reportModel.getReportByID(id);
        recipeModel.removeRecipe(report.getRecipe());
        
        //check ban user
        Recipe recipe = RecipeDAO.getInstance().getRecipe(report.getRecipe());
        User user = UserDAO.getInstance().getUser(recipe.getOwner());
        userModel.increaseReportOfUser(user.getId());
        switch(user.getNumberReport()){
            case 3:
                userModel.banUser(user.getId(), User.BAN_FLAG_ONCE);
                break;
            case 6:
                userModel.banUser(user.getId(), User.BAN_FLAG_SECOND);
                break;
            case 9:
                userModel.banUser(user.getId(), User.DELETED_FLAG);
                break;
        }
        listReport = reportModel.getListReports();
    }
    
    public void removeReportStatus(String id){
        reportModel.removeReportStatus(id);
        listReport = reportModel.getListReports();
    }
}
