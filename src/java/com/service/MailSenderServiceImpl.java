package com.service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author devaji
 */
public class MailSenderServiceImpl {
    public static boolean sendMail(String to[],String subject,String message){
        
        String from="deva3sood@gmail.com";
        String password=edit here;
        
        String host="smtp.gmail.com";
        
        Properties props=System.getProperties();
        props.put("mail.smtp.starttls.enable",true);
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.user",from);
        props.put("mail.smtp.password",password);
        props.put("mail.smtp.port",587);
        props.put("mail.smtp.auth",true);
        
        Session session=Session.getDefaultInstance(props, null);
        MimeMessage mimeMessage=new MimeMessage(session);
        try{
            mimeMessage.setFrom(new InternetAddress(from));
            InternetAddress ia[]=new InternetAddress[to.length];
        
            for (int i = 0; i < to.length; i++) {
                ia[i]=new InternetAddress(to[i]);
            }
            
            for (int i = 0; i < ia.length; i++) {
                mimeMessage.addRecipients(Message.RecipientType.TO, ia);
            }
            
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            
            Transport transport=session.getTransport("smtp");
            transport.connect(host,from,password);
            
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
            
            return true;
            
        
        }catch(Exception e){e.printStackTrace();}
        return false;
    }
}
