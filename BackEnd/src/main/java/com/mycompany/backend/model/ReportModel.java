/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.model;

import java.util.List;
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

    public boolean removeReportStatus(String id) {
        return ReportDAO.getInstance().updateReportStatus(id, Report.REMOVE_FLAG);
    }

    public Report getReportByID(String id) {
        return ReportDAO.getInstance().getReport(id);
    }

}
