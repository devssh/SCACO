<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
   response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
   response.addHeader("Pragma", "no-cache"); 
   response.addDateHeader ("Expires", 0);
%>
   
<!DOCTYPE HTML >

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Read Records</title>
        <link rel="stylesheet" type="text/css" href="./scripts/myStyle.css">
        
        
    </head>

    <body>
        
    ${msg}<br>
    ${result}<br>
    
    
    <div class="righty">
        <form id="logout" action="logout" method="post">
            <input type="hidden" name="key" value="${key}" />            
            <input  value="Logout" type="submit" form="logout"/> 
        </form>
    </div>
    
    <div class="righty">
        <form id="profile" action="profile" method="post">
            <input type="hidden" name="key" value="${key}" />            
            <input  value="Account details" type="submit" /> 
        </form>
    </div>
    
    
    <div>
    
        <form id="createPage" action="createPage" method="post">
            <input type="submit" value="Create Records">
            <input type="hidden" name="key" value="${key}" />
        </form>
    </div>
    
    
    <div>
        <form id="deleteForm" action="delete" method="post">     
            <input type="hidden" name="key" value="${key}" />
        </form>  
     
        <form  action="deleteAll" method="post">
            <input type="submit" value="Delete All records"/>
            <input type="hidden" name="key" value="${key}" />
        </form>
    </div>
    
    
    <div>
        <form  action="sampleAll" method="post">
            <input disabled="true" type="hidden" value="Insert Sample Database"/>
        </form>
    
    </div>
        
        <div>
        <form  action="common" method="post">            
            <input type="hidden" name="key" value="${key}"/>
            <input type="submit" value="Access Common Cloud"/>
        </form>
    
    </div>
    
    <br>
    <br>
    
    <div class="next">
    
    ${count} Records in ${readTime} <br>
    
    <table >
    <th>ID/ Delete</th><th>Name</th><th>Date-Time</th><th>Symptoms</th><th>Diagnosis</th>
    <c:forEach items="${patients}" var="patient">
        <tr>
            <td><input type="submit" form="deleteForm" name="id"
                       value=<c:out value="${patient.getId()}" />></td>
            <td><c:out value="${patient.getName()}" /></td>
            <td><c:out value="${patient.getDate()}" /></td>
            <td><c:out value="${patient.getSymptoms()}" /></td>
            <td><c:out value="${patient.getDiagnosis()}" /></td>
                   
        </tr> 
    </c:forEach>
    </table>
    
    </div>
    <br>
    <br>
    
    
    
    
    </body>
</html>


