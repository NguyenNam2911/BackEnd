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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.TimeUtils;
import org.dao.DAOException;
import org.dao.RecipeDAO;
import org.dao.UserDAO;
import org.entity.Recipe;
import org.entity.Report;
import org.entity.User;
import util.JSFutil;

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
    String sortText;
    int flag;
    private int currentPage;
    private int pagePrevious;
    private int pageMiddle;
    private int pageNext;
    private long numberPage; 
    
     public ReportManagedBean() throws DAOException {
        filterText = "2";
        flag = 2;
        searchText = "";
        sortText = "-reported_time";
        currentPage = 0;
        pagePrevious = 0;
        pageMiddle = 1;
        pageNext = 2;
        long n = reportModel.countReport();
        numberPage = getNumberPage(n);
        listReport = reportModel.getReportSearchAndFillter(currentPage, sortText, flag);
    }
    
    public String getUserName(String id) throws DAOException{
        if(id != null && !id.equals("")){
            User user = userModel.getUserByID(id);
            if (user != null)
                return user.getDisplayName();
        }
        return "";
    }
    
    public String getCreatorId(String recipeId) throws DAOException{
        if (recipeId != null && !recipeId.equals("")){
            Recipe recipe = recipeModel.getRecipeByID(recipeId);
            if (recipe != null)
                return recipe.getOwner();
        }
        return "";
    }
    
    public User getUser(String id) throws DAOException{
        return userModel.getUserByID(id);
    }
    
    public Recipe getRecipe(String id){
        return recipeModel.getRecipeByID(id);
    }
    
    public String getRecipeName(String id){
        if(id != null && !id.equals("")){
        Recipe recipe = recipeModel.getRecipeByID(id);
        if (recipe != null)
            return recipe.getTitle();
        }
        return "";
    }
    
    public String convertTime(long time){
        return TimeUtils.convertTime(time);
    }
    
    public void searchReport() throws DAOException{
        flag = getFilterNumber();
        currentPage = 0;
        pagePrevious = 0;
        pageMiddle = 1;
        pageNext = 2;
        long n = reportModel.countNumberResultSearch(flag);
        numberPage = getNumberPage(n);
        listReport = reportModel.getReportSearchAndFillter(currentPage, sortText, flag);
    }
    
    private int getFilterNumber(){
        int filterNumber;
        if (filterText != null){
           try{
               filterNumber = Integer.parseInt(filterText);
           }catch(Exception ex){
               filterNumber=3;
           }
       }else{
            filterNumber=3;
       }
        return filterNumber;
    }
    
    public void approveReportStatus(String reportId, String adminId) throws DAOException{
        
        //approve report, remove recipe
        reportModel.approveReportStatus(reportId);
        Report report = reportModel.getReportByID(reportId);
        recipeModel.removeRecipe(report.getRecipe());
        
        //remove other the same reports
        List<Report> reports = reportModel.getListCheckingReportByRecipe(report.getRecipe());
        for(Report rp : reports){
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
        listReport = reportModel.getReportSearchAndFillter(currentPage, sortText, flag);
    }
    
    public void removeReportStatus(String reportId) throws DAOException{
        Report report = reportModel.getReportByID(reportId);
        recipeModel.updateRecipeReported(report.getRecipe());
        reportModel.removeReportStatus(reportId);
        listReport = reportModel.getReportSearchAndFillter(currentPage, sortText, flag);
    }
    
    public boolean updateAdminReport(String reportId, String adminId){
        return reportModel.updateAdminReport(reportId, adminId);
    }
    
    public void reportRecipe(String reporterId, String recipeId) throws DAOException{
        //create new report
        Report report = new Report();
        report.setReporter(reporterId);
        report.setRecipe(recipeId);
        report.setReason("Reported by Admin");
        
        //add new report
        reportModel.addReport(report);
        
        //approve report
        approveReportStatus(report.getId(), reporterId);
        JSFutil.navigate("recipe_view");
    }
    
    //phan trang
    public final long getNumberPage(long number) {
        long n;
        if (number % ReportModel.NUMBER_RECORDS == 0) {
            n = (number / ReportModel.NUMBER_RECORDS) - 1;
        } else {
            n = number / ReportModel.NUMBER_RECORDS;
        }
        return n;
    }
    
    public void updateUsers(int page, int changeNumber) throws DAOException {
        listReport = reportModel.getReportSearchAndFillter(page, sortText, flag);
        currentPage = page;
        
        //change page number
        if (changeNumber == 1 || changeNumber ==-1){
            if (currentPage > 1 && currentPage < numberPage)
            changePageNumber(changeNumber);
        }
        if (changeNumber ==-2 && currentPage == pagePrevious && currentPage >1){
            changePageNumber(-1);
        }
        if (changeNumber ==2 && currentPage == pageNext && currentPage < (int)numberPage){
            changePageNumber(1);
        }
        if (changeNumber == -3){
            pagePrevious = 0;
            changePageNumber(0);
        }
        if (changeNumber == 3 && numberPage>3){
            pagePrevious = (int)numberPage - 2;
            changePageNumber(0);
        }
    }
    
    private void changePageNumber(int changeNumber){
        pagePrevious += changeNumber;
        pageMiddle = pagePrevious + 1;
        pageNext = pageMiddle +1;
    }
    
    //get and set
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

    public String getSortText() {
        return sortText;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPagePrevious() {
        return pagePrevious;
    }

    public int getPageMiddle() {
        return pageMiddle;
    }

    public int getPageNext() {
        return pageNext;
    }

    public void setSortText(String sortText) {
        this.sortText = sortText;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPagePrevious(int pagePrevious) {
        this.pagePrevious = pagePrevious;
    }

    public void setPageMiddle(int pageMiddle) {
        this.pageMiddle = pageMiddle;
    }

    public void setPageNext(int pageNext) {
        this.pageNext = pageNext;
    }

    public void setNumberPage(long numberPage) {
        this.numberPage = numberPage;
    }

    public int getFlag() {
        return flag;
    }

    public long getNumberPage() {
        return numberPage;
    }
    
}
