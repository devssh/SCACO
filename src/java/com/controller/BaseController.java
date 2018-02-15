package com.controller;



import com.model.Doc;
import com.service.AttrServiceImpl;
import com.service.AuthWebServiceImpl;
import com.service.MailSenderServiceImpl;
import com.service.PWebServiceImpl;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author devaji
 */

@Controller
public class BaseController {
    
    AuthWebServiceImpl awsi=new AuthWebServiceImpl();     
    PWebServiceImpl pwsi =new PWebServiceImpl(); 
    AttrServiceImpl asi=new AttrServiceImpl();
    
    @RequestMapping(value = {"/"},method = RequestMethod.GET)
    public String login(ModelMap model){
        
        model.addAttribute("msg","Login");
        return "login";
    }
    
    @RequestMapping(value = {"/logout"},method = RequestMethod.POST)
    public String logout(ModelMap model,
            @RequestParam(value = "key") String key
            ){
        
        model.addAttribute("msg",awsi.invalidateKey(key));
        return "login";
    }
    
    @RequestMapping(value = {"/profile"},method = RequestMethod.POST)
    public String profile(ModelMap model,
            @RequestParam(value = "key") String key
            ){
        String b="<b>",be="</b>";
        
        Doc doc=asi.getDoc(awsi.getDoc(key));
        model.addAttribute("username",b+doc.getUsername()+be);
        model.addAttribute("n",b+doc.getPub1()+be);
        model.addAttribute("e",b+doc.getPub2()+be);
        model.addAttribute("d",b+doc.getPriv()+be);
        model.addAttribute("key",key);
        model.addAttribute("user",awsi.getDoc(key));
        model.addAttribute("docs",awsi.getDocs());
        
        return "profile";
    }
    
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String index(ModelMap model,
            @RequestParam(value = "user") String user,
            @RequestParam(value = "pass") String pass,
            @RequestParam(value = "key",required = false,defaultValue = "") String key
            ){
        boolean validate=awsi.trySess(user, key);
        if(awsi.tryLogin(user, pass)||validate){
            model.addAttribute("msg","Logged in as <b>"+user+"</b>");
            model.addAttribute("key",awsi.assignKey(user));
            try{
            long time=System.currentTimeMillis();
            List list=pwsi.getPatients(user);                       
            model.addAttribute("patients",list);
            model.addAttribute("readTime","Read-Time "+(System.currentTimeMillis()-time)+"ms");
            model.addAttribute("count",list.size());
            }catch(Exception e){}
            return "index";
        }        
        
        if(validate)
            model.addAttribute("msg","You have logged out");
        else
            model.addAttribute("msg","Incorrect username or password!");
        
        return "login";
    }
    
    @RequestMapping(value = "/createPage",method = RequestMethod.POST)
    public String create(ModelMap model,
            @RequestParam(value = "key") String key
            ){
        model.addAttribute("date",new Date());
        model.addAttribute("user",awsi.getDoc(key));
        model.addAttribute("pass","pass");
        model.addAttribute("key",key);
        return "createPage";
    }
    
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String create(ModelMap model,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "sym",defaultValue = "N.A") String sym,
            @RequestParam(value = "dia",defaultValue = "N.A") String dia,
            @RequestParam(value = "key") String sessKey
    
    ){
        
        try{
        pwsi.createTPatient(name,sym,dia,awsi.getDoc(sessKey));
        model.addAttribute("result","Successfully inserted record for " +name);
        }catch(Exception e){
            model.addAttribute("result","Error inserting value."+e);
            e.printStackTrace();
        }
        
        
                
        return index(model,awsi.getDoc(sessKey),"",sessKey);
    }
    
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public String delete(ModelMap model,
            @RequestParam(value = "id") int id,
            @RequestParam(value = "key") String sessKey
    
    
    ){
        
        try{
        pwsi.deleteTPatient(id);
        model.addAttribute("result","Successfully deleted record " +id);
        }catch(Exception e){model.addAttribute("result","Error deleting value.");}
        
        
                
        return index(model,awsi.getDoc(sessKey),"",sessKey);
    }
    
    @RequestMapping(value = "/deleteAll",method = RequestMethod.POST)
    public String deleteAll(ModelMap model,
            @RequestParam(value = "key") String sessKey
    ){
        
        try{
            long time=System.currentTimeMillis();
        pwsi.deleteAllTPatient();
        model.addAttribute("result","Successfully deleted records\n"+
                "Delete-Time "+(System.currentTimeMillis()-time)+"ms");
        }catch(Exception e){model.addAttribute("result","Error deleting.");}
        
        
                
        return index(model,awsi.getDoc(sessKey),"",sessKey);
    }
    
    @RequestMapping(value = "/sampleAll",method = RequestMethod.POST)
    public String sampleRecords(ModelMap model) throws Exception{
        pwsi.insertSampleData();
        model.addAttribute("msg","Sample Values Inserted");
        model.addAttribute("user","user");
        model.addAttribute("pass","pass");
        return "result";
    }
    
    @RequestMapping(value="/registerPage",method=RequestMethod.GET)
    public String registerPage(ModelMap model){
        
        return "registerPage";
    }
    
    @RequestMapping(value="/register",method=RequestMethod.POST)
    public String register(ModelMap model,
            @RequestParam(value = "user") String user,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "pass") String pass
            )throws IOException{
        awsi.createDoc(user,email,pass);
        return "login";
    }
    
    @RequestMapping(value="/isUserValid",method=RequestMethod.POST)
    public String isUserValid(ModelMap model,
            @RequestParam(value = "user") String user
            )throws IOException{
        model.addAttribute("info",""+awsi.isUserExist(user));
        return "info";
    }
    
    @RequestMapping(value = "common",method = RequestMethod.POST)
    public String common(ModelMap model,
            @RequestParam(value = "key") String key
            ){
        if(!key.equals("Anonymous")){
            String user=awsi.getDoc(key);
            model.addAttribute("msg","Logged in as <b>"+user+"</b>");
            model.addAttribute("user",user);
            model.addAttribute("key",key);
        }
        
        try{
            long time=System.currentTimeMillis();
            List list=pwsi.getPatientC();                       
            model.addAttribute("patients",list);
            model.addAttribute("readTime","Read-Time "+(System.currentTimeMillis()-time)+"ms");
            model.addAttribute("count",list.size());
            }catch(Exception e){e.printStackTrace();}
        
        model.addAttribute(model);
        
        return "common";        
    }
    
    @RequestMapping(value = "decrypt",method = RequestMethod.POST)
    public String decrypt(ModelMap model,
            @RequestParam(value = "key") String key,
            @RequestParam(value = "attr") String attr,
            @RequestParam(value = "fid") int fid
            
            ){
        
        if(asi.validate(fid, awsi.getDoc(key), attr)){
            pwsi.savePatient(asi.decrypt(fid,attr,awsi.getDoc(key)));
            model.addAttribute("result","Decrypted value has been inserted.");        
            return common(model, key);
        }else{
            model.addAttribute("result","Invalid attribute.");
            return common(model, key);   
        }
    }
    
    @RequestMapping(value = "send",method = RequestMethod.POST)
    public String send(ModelMap model,
            @RequestParam(value = "key") String key,
            @RequestParam(value = "fid") int fid,
            @RequestParam(value = "rec") String rec
            
            ){
        String user=awsi.getDoc(key);
        if(rec.equals(user)){
            model.addAttribute("result","Can't send file to yourself.");
        }else if(awsi.isPatientExist(user,fid)){
            try{
                String attr=asi.createAttr(fid,user, rec);            
                String email[]=new String[1];
                Doc doc=asi.getDoc(rec);
                email[0]=doc.getEmail();
                MailSenderServiceImpl.sendMail(email, "New File Received","Id is "+fid+"\n"
                        + "Attribute is " + attr);
                model.addAttribute("result","Successfully sent mail to "+rec+" ."+attr);
            }catch(Exception e){model.addAttribute("result","Invalid username.");}
        }else{
            model.addAttribute("result","You don't have permission for the file.");
        }
        
        
        return common(model, key);
    }
    
    @RequestMapping(value = {"/error"},method = RequestMethod.GET)
    public String error(ModelMap model){
        
        
        return "error";
    }
    
    
}
