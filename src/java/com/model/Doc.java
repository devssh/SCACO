/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author devaji
 */
@Entity
@Table(name = "TDoc")
public class Doc {
    @Id
    private String username;
    @Column
    private String password;
    @Column
    private String sessKey;
    @Column
    private String email;
    @Column(length = 1024)
    private String pub1;
    @Column(length = 1024)
    private String pub2;
    @Column(length = 1024)
    private String priv;
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessKey() {
        return sessKey;
    }

    public void setSessKey(String sessKey) {
        this.sessKey = sessKey;
    }
    
    public String getPub1() {
        return pub1;
    }

    public void setPub1(String pub1) {
        this.pub1 = pub1;
    }

    public String getPub2() {
        return pub2;
    }

    public void setPub2(String pub2) {
        this.pub2 = pub2;
    }

    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv;
    }
    
    public String[] getKeys() {
        String[] keys=new String[3];
        keys[0]= pub1;
        keys[1] = pub2;
        keys[2] = priv;
        
        return keys;
    }
    
    public void setKeys(String pub1,String pub2,String priv) {
        this.pub1 = pub1;
        this.pub2 = pub2;
        this.priv = priv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
