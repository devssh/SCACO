/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.crypto.CryptoServiceImpl;
import com.model.ABE;
import com.model.Doc;
import com.model.Patient;
import com.model.PatientC;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;


/**
 *
 * @author devaji
 */
public class AttrServiceImpl {
    Configuration configuration = new Configuration().configure("hibernate.cfg.xml")
            .addAnnotatedClass(com.model.Doc.class)
            .addAnnotatedClass(com.model.PatientC.class)
            .addAnnotatedClass(com.model.ABE.class)
            .configure();
    StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties());
    SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
    
    public boolean validate(int fileId,String receiver,String attr){
        
        try{
        ABE abe=getABE(fileId,receiver);
        
        if(attr.equals(abe.getAttr())){
            return true;
        }
        
        }catch(Exception e){}
        
        return false;
    }
    
    public Patient decrypt(int fid,String attr,String rec){
        Patient pat=new Patient();
        PatientC patc=getPatientById(fid);
        ABE abe=getABE(fid, rec);        
        String sender=abe.getSender();
        Doc doc=getDoc(sender);
        
        try{
        CryptoServiceImpl csi=new CryptoServiceImpl(doc.getPub1(),
                doc.getPub2(), doc.getPriv());
        pat.decrypt(fid, patc.getDate(),csi.decrypt(patc.getName()),
                csi.decrypt(patc.getSymptoms()), csi.decrypt(patc.getDiagnosis()));
        pat.setDoc(rec);
        }catch(Exception e){pat.setName("Error");}
        
        return pat;
    }
    
    public ABE getABE(int fileId,String rec){
        Session session=sessionFactory.openSession();
        
        Criteria crit=session.createCriteria(ABE.class);
        crit
            .add(Restrictions.eq("fileId", fileId))
            .add(Restrictions.eq("receiver", rec));
        
        ABE abe=(ABE)crit.list().get(0);
        
        session.close();
                
        return abe;
    }
    
    public String createAttr(int fileId,String send,String rec){
        try{
        getABE(fileId, rec);
        }catch(Exception e){
        ABE abe=new ABE();
        
        String attr=customMD5(getPatientById(fileId).getName().substring(0,3)
                +(System.nanoTime()%10) )
                .substring(0,5);
        abe.setAttr(attr);
        
        abe.setFileId(fileId);
        abe.setReceiver(rec);
        abe.setSender(send);
        
        saveABE(abe);
        
        return attr;
        }
        return "Error the attribute has been sent before.";
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
    
    public void saveABE(ABE abe){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        
        session.save(abe);
        
        session.getTransaction().commit();
        session.close();
    }
    
    public PatientC getPatientById(int id){
        Session session=sessionFactory.openSession();
                
        PatientC pat=(PatientC)session.get(PatientC.class, id);
        
        session.close();
        return pat;
    }
    
    public Doc getDoc(String user){
        Session session=sessionFactory.openSession();
        
        Doc doc=(Doc)session.get(Doc.class, user);
        
        session.close();
        
        return doc;
        
    }
    
}
