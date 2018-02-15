/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;


import com.crypto.CryptoServiceImpl;
import com.model.Doc;
import com.model.Patient;
import java.io.IOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author devaji
 */
public class AuthWebServiceImpl {
    Configuration configuration = new Configuration().configure("hibernate.cfg.xml")
            .addAnnotatedClass(com.model.Doc.class)
            .addAnnotatedClass(com.model.Patient.class)
            .configure();
    StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties());
    SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
    
    
    
    public boolean tryLogin(String user,String pass){
        Session session =sessionFactory.openSession();
        
        try{Doc doc=(Doc)session.get(Doc.class, user);
        
        if(doc.getUsername().equals(user)&&doc.getPassword().equals(pass)){
            return true;
        }
        
        }catch(Exception e){
        session.close();
        }
        
        
        return false;
    }
    
    public boolean trySess(String user,String key){
        Session session =sessionFactory.openSession();
        
        try{Doc doc=(Doc)session.get(Doc.class, user);
        
        if(doc.getUsername().equals(user)&&doc.getSessKey().equals(key)){
            return true;
        }
        
        }catch(Exception e){
        session.close();
        }
        
        
        return false;
    }
    
    public String customMD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
    
    public void saveDoc(Doc doc){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        
        session.save(doc);
        
        session.getTransaction().commit();
        session.close();
    }
    
    public void updateDoc(Doc doc){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        
        session.update(doc);
        
        session.getTransaction().commit();
        session.close();
    }
    
    
    
    public boolean createDoc(String user,String email,String pass)throws IOException{
        
        Doc doc=new Doc();
        
        doc.setUsername(user);
        doc.setEmail(email);
        doc.setPassword(pass);
        doc.setSessKey(customMD5(user+System.nanoTime()));
        
        CryptoServiceImpl csi=new CryptoServiceImpl(1024);
        String keys[]=csi.storeKeys();
        doc.setKeys(keys[0],keys[1],keys[2]);
        
        saveDoc(doc);
        
        return false;
    }
    
    public String assignKey(String user){
        Session session=sessionFactory.openSession();
        
        Doc doc=session.get(Doc.class, user);
        
        doc.setSessKey(customMD5(doc.getUsername()+System.nanoTime()));
        String sessKey=doc.getSessKey();
        
        updateDoc(doc);
        
        session.close();
        
        return sessKey;
    }
    
    public String getDoc(String key){
        Session session=sessionFactory.openSession();
        
        Criteria crit=session.createCriteria(Doc.class)
                .add(Restrictions.like("sessKey", key));
        String doc=((Doc)crit.list().get(0)).getUsername();
        
        session.close();
        
        return doc;
        
    }
    
    public List getDocs(){
        
        Session session=sessionFactory.openSession();
        
        Criteria crit=session.createCriteria(Doc.class);
        
        List list=crit.list();
        
        session.close();
        
        return list;
    }
    
    public String invalidateKey(String key){
        assignKey(getDoc(key));
        return "Login";
    }
    
    public boolean isUserExist(String user){
        Session session=sessionFactory.openSession();
        boolean valid;
        try{
            Doc doc=(Doc)session.get(Doc.class, user);
            if(doc!=null)
                valid=true;
            else
                valid=false;
        }catch(Exception e){valid=false;}
        
        session.close();
        return valid;
    }
    
    public boolean isPatientExist(String user,int fid){
        Session session=sessionFactory.openSession();
        boolean valid=true;
        try{
            Patient pat=(Patient)session.get(Patient.class, fid);
            if(pat!=null){
                if(pat.getDoc().equals(user))
                   valid=true;
                else
                    valid=false;
            }else
                valid=false;
            
        }catch(Exception e){e.printStackTrace();}
        
        session.close();
        
        return valid;
    }
    
}
