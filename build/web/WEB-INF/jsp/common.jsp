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
    <p class="red">${result}</p>
    
    
    
        
    <div class="righty">
        <form  action="login" method="post">    
            <input name="user" type="hidden" value="${user}" />
            <input name="pass" type="hidden" value="" />
            <input type="hidden" name="key" value="${key}"/>
            <input type="submit" value="Back"/>
        </form>
    
    </div>
            
    <div>
        <form  action="send" method="post">
            <input type="number" name="fid" placeholder="ID"/>
            <input type="text" name="rec" placeholder="username"/>
            <input type="hidden" name="key" value="${key}"/>
            <input type="submit" value="Create permission"/>
        </form>
    
    </div>
    
    <br><br><br>
    
    <div>
        <form action="decrypt" method="post">
            <input type="number" name="fid" placeholder="ID"/>
            <input type="text" name="attr" placeholder="Attribute"/>
            <input type="hidden" name="key" value="${key}"/>
            <input type="submit" value="Access Record"/>
        </form>
    </div>
    
    <br><br>
    
    <div class="next">
    
    ${count} Records in ${readTime} <br>
    
    <table >
    <th>ID</th><th>Name</th><th>Symptoms</th><th>Diagnosis</th>
    <c:forEach items="${patients}" var="patient">
        <tr>
            <td><input type="submit" form="deleteForm" name="id"
                       value=<c:out value="${patient.getId()}" />></td>
            <td><c:out value="${patient.getName()}" /></td>
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


