/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.model;

import org.dao.DAOException;
import org.dao.UserDAO;
import org.entity.User;



/**
 *
 * @author Nguyen Hoai Nam
 */
public class LoginModel {

    public User CheckLogin(String email) throws DAOException {
        User u = UserDAO.getInstance().getUserInfoByEmail(email);
        if (u != null) {
            if (u.getRole().equals(User.ADMIN_ROLE) || u.getRole().equals(User.SUPER_ADMIN_ROLE)) {
                if(u.getActiveFlag() == User.ACTIVE_FLAG){
                    return u;
                }
                else{
                    u = null;
                }
                
            } else {
                u = null;
            }

        }

        return u;
    }

}
