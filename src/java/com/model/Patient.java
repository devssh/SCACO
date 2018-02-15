/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author devaji
 */

@Entity
@Table(name = "TPat")
public class Patient {    
    @Id @GeneratedValue    
    private int id;
    @Column
    private String name;
    @Column
    private Date date;
    @Column
    private String symptoms;
    @Column
    private String diagnosis;
    @Column
    private String doc;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }
    
    public void decrypt(int x,Date o,String a,String c,String d){
        id=x;
        date=o;
        name=a;
        diagnosis=d;
        symptoms=c;
        doc="Anon";
        
    }
    
    
    
    
    
    
    
}
