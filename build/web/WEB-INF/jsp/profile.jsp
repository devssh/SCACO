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

    <body><b>
        <div class="wrapit">
            Account Details<br><br>
            Username:&nbsp;&nbsp;${username}<br><br>
            Public key:&nbsp;&nbsp;<br><br>
            &nbsp;&nbsp;n:&nbsp;&nbsp;${n}<br><br>
            &nbsp;&nbsp;e:&nbsp;&nbsp;${e}<br><br>
            Private key:<br>&nbsp;&nbsp;${d}<br><br>
            Current Session Key:&nbsp;&nbsp;<b>${key}</b><br><br>
        </div>
        
        <div class="righty">

            <form action="login" method="post">
            <input name="user" type="hidden" value="${user}" />
            <input name="pass" type="hidden" value="" />
            <input name="key" type="hidden" value="${key}" />
            <input type="submit" value="Back" />
            </form>

        </div>
            
            <br><br>
            
            <div class="next">
            User List:<br><br>
            <b>            
                <c:forEach items="${docs}" var="doc">
                    Public Key &nbsp;&nbsp;<c:out value="${doc.getUsername()}" />
                    &nbsp;&nbsp;<c:out value="${doc.getPub1()}" />
                    &nbsp;&nbsp;<c:out value="${doc.getPub2()}" /><br>
                </c:forEach>
            </b>
        </div>
            </b>
    </body>
    
    

</html>