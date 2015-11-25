/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.model.RecipeModel;
import com.mycompany.backend.model.ReportModel;
import com.mycompany.backend.model.UserModel;
import com.mycompany.backend.notification_server.NotiServer;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
    List<Report> listReport = new ArrayList<>();
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
    
    public User getUser(String id) throws DAOException{
        return userModel.getUserByID(id);
    }
    
    public Recipe getRecipe(String id){
        return recipeModel.getRecipeByID(id);
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
       listReport = reportModel.getListReports();
       if (searchText != null){
           for (Report report : listReport){
               if (getRecipeName(report.getId()).contains(searchText)){
                    reports.add(report);
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
    
    public void approveReportStatus(String reportId, String adminId) throws DAOException{
        
        //approve report, remove recipe
        reportModel.approveReportStatus(reportId);
        Report report = reportModel.getReportByID(reportId);
        recipeModel.removeRecipe(report.getRecipe());
        
        //remove other the same reports
        List<Report>reports = reportModel.getListReports();
        for(Report rp : reports){
            if (rp.getRecipe().equals(report.getRecipe()) && rp.getStatus()==0)
                reportModel.removeReportStatus(rp.getId());
        }
        
        //check ban user
        Recipe recipe = RecipeDAO.getInstance().getRecipe(report.getRecipe());  
        userModel.increaseReportOfUser(recipe.getOwner());
        User user = UserDAO.getInstance().getUser(recipe.getOwner());
        int nReport = user.getNumberReport();
        if (user.getActiveFlag() != User.DELETED_FLAG)
            if (nReport == 3 || nReport == 6 || nReport >= 9)
                userModel.banUser(user.getId());
        
        //update report: verify_by, verify_time
        updateAdminReport(reportId, adminId);
        listReport = reportModel.getListReports();
    }
    
    public void removeReportStatus(String reportId) throws DAOException{
        Report report = reportModel.getReportByID(reportId);
        recipeModel.updateRecipeReported(report.getRecipe());
        reportModel.removeReportStatus(reportId);
        listReport = reportModel.getListReports();
    }
    
    public boolean updateAdminReport(String reportId, String adminId){
        return reportModel.updateAdminReport(reportId, adminId);
    }
}
