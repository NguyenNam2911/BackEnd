/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.model;

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

    public List<Report> getListReports() {
        List<Report> listReport = ReportDAO.getInstance().getAllReport();
        return listReport;
    }

    public boolean approveReportStatus(String id) {
        return ReportDAO.getInstance().updateReportStatus(id, Report.APPROVE_FLAG);
    }

    public void removeReportStatus(String id) throws DAOException {
        ReportDAO.getInstance().delete(id, Report.class);
    }

    public Report getReportByID(String id) {
        return ReportDAO.getInstance().getReport(id);
    }

    public boolean updateAdminReport(String reportId, String adminId){
        return ReportDAO.getInstance().updateAdminReport(reportId, adminId, TimeUtils.getCurrentGMTTime());
    }
    
    public long countReport(){
        return ReportDAO.getInstance().getNumberReport();
    }
}
