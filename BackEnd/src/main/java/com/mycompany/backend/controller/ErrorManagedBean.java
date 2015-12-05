/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.controller;

import com.mycompany.backend.util.JSFutil;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nguyen Hoai Nam
 */
@ManagedBean
@ViewScoped
public class ErrorManagedBean {

    /**
     * Creates a new instance of ErrorManagedBean
     */
    String message;

    public ErrorManagedBean() {
        HttpSession session = JSFutil.getSession();
        message = (String) session.getAttribute("error");
        JSFutil.setSessionValue("error", null);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
