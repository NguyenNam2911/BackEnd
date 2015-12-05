/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;
import org.entity.User;


/**
 *
 * @author HP 8470P
 */
public class SecurityFilter implements PhaseListener {

  

    @Override
    public void beforePhase(PhaseEvent event) {
       

    
    }
    

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext fc = event.getFacesContext();
        boolean loginPage = fc.getViewRoot().getViewId().lastIndexOf("login")
                > -1;
        boolean forgotPassPage = fc.getViewRoot().getViewId().lastIndexOf("forgot_pass")
                > -1;
        if ( !forgotPassPage  && !loginPage && !isUserLogged()) {
            JSFutil.navigate("login");
        }
        if(loginPage && isUserLogged()){
            JSFutil.navigate("index");
        }
    }

   
    

    private boolean isUserLogged() {
        //looks session for user
        HttpSession session = JSFutil.getSession();
        User user = (User) session.getAttribute("user");
        return user != null;
    }

    private void navigate(PhaseEvent event, String page) {
        FacesContext fc = event.getFacesContext();
        NavigationHandler nh = fc.getApplication().getNavigationHandler();
        nh.handleNavigation(fc, null, page);
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
