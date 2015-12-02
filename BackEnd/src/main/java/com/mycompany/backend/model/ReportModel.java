/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.model;

import java.util.ArrayList;
import java.util.List;
import org.TimeUtils;
import org.dao.DAOException;
import org.dao.ReportDAO;
import org.entity.Report;

/**
 *
 * @author KhanhDN
 */
public class ReportModel {

    public static final int NUMBER_RECORDS = 5;
    
    public List<Report> getListReports() throws DAOException {
        List<Report> listReport = ReportDAO.getInstance().getAllReport();
        return listReport;
    }

    public boolean approveReportStatus(String id) {
        return ReportDAO.getInstance().updateReportStatus(id, Report.APPROVE_FLAG);
    }

    public void removeReportStatus(String id) throws DAOException {
        ReportDAO.getInstance().delete(id, Report.class);
    }

    public Report getReportByID(String id) throws DAOException {
        return ReportDAO.getInstance().getReport(id);
    }

    public boolean updateAdminReport(String reportId, String adminId){
        return ReportDAO.getInstance().updateAdminReport(reportId, adminId, TimeUtils.getCurrentGMTTime());
    }
    
    public long countReport(){
        return ReportDAO.getInstance().getNumberReport();
    }
    
    public long countCheckingReport(){
        return ReportDAO.getInstance().getNumberCheckingReport();
    }
    
    public void addReport(Report report) throws DAOException{
        ReportDAO.getInstance().save(report);
    }
    
    public List<Report> getListCheckingReportByRecipe(String recipeId) throws DAOException{
        return ReportDAO.getInstance().getListCheckingReportByRecipe(recipeId);
    }
    
    public long countNumberResultSearch(int flag) {
        return ReportDAO.getInstance().getNumberResultSearchAndFillReport(flag);
    }
    
     public List<Report> getReportSearchAndFillter(int page, String order, int flag) throws DAOException {
        List<Report> reports = new ArrayList<>();
        reports = ReportDAO.getInstance().searchAndFillAllReport(page * NUMBER_RECORDS, NUMBER_RECORDS, order, flag);
        return reports;
    }
}
