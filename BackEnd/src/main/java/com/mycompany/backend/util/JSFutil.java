/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;
import org.mail.MailCompose;
import org.mail.MailManagement;
import org.mail.MailWorker;

/**
 *
 * @author Nguyen Hoai Nam
 */
public class JSFutil {

    public static final String EMAIL = "dailycookapp@gmail.com";
    public static final String PASSWORD = "dailycook2015";
    
    public static void navigate(String page) {
        FacesContext fc = FacesContext.getCurrentInstance();
        NavigationHandler nh = fc.getApplication().getNavigationHandler();
        nh.handleNavigation(fc, null, page);
    }

    public static HttpSession getSession() {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(true);
        return session;
    }

    public static void setSessionValue(String strKey, Object objValue) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put(strKey, objValue);
        System.out.println("time" + context.getExternalContext().getSessionMaxInactiveInterval());
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    
    public static void addErrorMessageById(String id, String msg){
        FacesContext.getCurrentInstance().addMessage(id,new FacesMessage(FacesMessage.SEVERITY_ERROR, msg,msg));
    }
    public static void addSuccessMessageById(String id, String msg){
        FacesContext.getCurrentInstance().addMessage(id,new FacesMessage(FacesMessage.SEVERITY_INFO, msg,msg));
    }
    
    public static void sentMail(String to, String from, String pass,String title, String content) {
        MailCompose mailCompose = new MailCompose();
        mailCompose.setTo(to);
        mailCompose.setFrom(from);
        mailCompose.setPassword(pass);
        mailCompose.setContent(content);
        mailCompose.setTitle(title);
        MailManagement.getInstance().addMail(mailCompose);
        Thread mailWorker = new MailWorker();
        mailWorker.start();
    }
    public static String ramdomString(int leng){
        return RandomStringUtils.randomAlphanumeric(leng);
    }
}
