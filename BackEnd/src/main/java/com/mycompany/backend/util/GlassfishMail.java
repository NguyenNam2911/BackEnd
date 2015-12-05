/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;

/**
 *
 * @author duyetpt
 */
public class GlassfishMail {
    
    public void sendMessage(String to, String from, String pass, String title, String content) throws NamingException {
        javax.naming.InitialContext ctx = new javax.naming.InitialContext();
        javax.mail.Session mailSession = (javax.mail.Session) ctx.lookup("mail/dailycook");
        Message message = new MimeMessage(mailSession);
        try {
//            System.out.println(mailSession.getProperties().getProperty("mail.smtp.port"));
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(title);
            message.setText(content);
            Transport.send(message);
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
