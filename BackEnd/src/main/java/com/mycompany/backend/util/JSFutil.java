/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;
import org.entity.User;

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

    public static boolean isUserLogged() {
        //looks session for user
        HttpSession session = getSession();
        User user = (User) session.getAttribute("user");
        return user != null;
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

    public static void addErrorMessageById(String id, String msg) {
        FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
    }

    public static void addSuccessMessageById(String id, String msg) {
        FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
    }

    public static void sentMail(String to, String from, String pass, String title, String content) {
        GlassfishMail gmail = new GlassfishMail();
        try {
            gmail.sendMessage(to, from, pass, title, content);
        } catch (NamingException ex) {
            Logger.getLogger(JSFutil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String ramdomString(int leng) {
        return RandomStringUtils.randomAlphanumeric(leng);
    }
}
