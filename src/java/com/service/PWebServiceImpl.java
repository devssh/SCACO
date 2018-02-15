package com.service;


import com.crypto.CryptoServiceImpl;
import com.model.Doc;
import com.model.Patient;
import com.model.PatientC;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author devaji
 */
public class PWebServiceImpl {
    Configuration configuration = new Configuration().configure("hibernate.cfg.xml")
            .addAnnotatedClass(com.model.Patient.class)
            .addAnnotatedClass(com.model.PatientC.class)
            .addAnnotatedClass(com.model.Doc.class)
            .configure();
    StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties());
    SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
    
    
    
    
    public void createTPatient(String name,String sym,String dia,String doc){
        
        
        Patient user=new Patient();
        user.setName(name);
        user.setSymptoms(sym);
        user.setDiagnosis(dia);
        user.setDate(new Date());
        user.setDoc(doc);
        
        savePatient(user);
        
        Session session=sessionFactory.openSession();
        Doc docObj=(Doc)session.get(Doc.class, doc);
        String[] keys=docObj.getKeys();
        
        session.close();
        
        
        PatientC userc=new PatientC();
        try{            
            CryptoServiceImpl csi=new CryptoServiceImpl(keys[0],keys[1],keys[2]);
            userc.encrypt(user.getId(),user.getDate(),
                    csi.encrypt(name),csi.encrypt(sym),csi.encrypt(dia));
            
        }catch(IOException e){e.printStackTrace();}
        
        savePatient(userc);
        
        
    }
    
    public void deleteTPatient(int id){
        
        
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        
        Patient user=session.get(Patient.class, id);
        session.delete(user);
        
        session.getTransaction().commit();
        session.close();
    }
    
    public void deleteAllTPatient(){
        
        
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        
        Criteria crit=session.createCriteria(Patient.class);
        List<Patient> list=crit.list();
        for(Patient patient:list)
        session.delete(patient);
        
        session.getTransaction().commit();
        session.close();
    }
    
    
    
    public List getPatients(String doc){
        Session session=sessionFactory.openSession();
        Criteria criteria=session.createCriteria(Patient.class)
                .add(Restrictions.eq("doc",doc));
        List list=criteria.list();
        session.close();
        return list;
    }
    
    public List getPatientC(){
        Session session=sessionFactory.openSession();
        Criteria criteria=session.createCriteria(PatientC.class);
        List list=criteria.list();
        session.close();
        return list;
    }
    
    public String trimUpper(String line){
        return line.trim().toUpperCase();
    }
    
    public String[] trimUpper(String line[]){
        for(int i=0;i<line.length;i++){
            line[i]=trimUpper(line[i]);
        }
        
        return line;
    }
    
    public String removeHashedOldRecordNo(String temp){
        
        if(temp.contains("#")){
            temp=temp.split("#")[0].trim();
        }
        return temp;
    }
    
    public void savePatient(Patient pat){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        
        session.save(pat);
        
        session.getTransaction().commit();
        session.close();
    }
    public void savePatient(PatientC pat){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        
        session.save(pat);
        
        session.getTransaction().commit();
        session.close();
    }
    
    public void insertSampleData() throws Exception{
        int min=0,max=Integer.MAX_VALUE;
            
            Scanner s=new Scanner(getClass().getClassLoader().getResourceAsStream("records.csv"));
            try{
            while(true){
                String line[]=s.nextLine().split(",;,;,",-1);
                
                line=trimUpper(line);
                
                
                int id=-1;
                try{id=Integer.parseInt(line[0]);}catch(Exception e){}
                if(id>=min&&id<=max){
                    if(line[11].trim().length()>0){
                    Patient patient=new Patient();
                    
                    patient.setName(removeHashedOldRecordNo(line[2]));
                    patient.setDate(new SimpleDateFormat("dd MMM yyyy").parse(line[1]));
                    patient.setSymptoms(line[9]);
                    patient.setDiagnosis(line[11]);
                    
                    savePatient(patient);
                    }
                    
                }
            }
            
            }catch(NoSuchElementException e){
                
            }
            
        
        
    }
    
    
}
