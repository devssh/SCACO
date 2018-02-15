<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML >

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Read Records</title>
        <link rel="stylesheet" type="text/css" href="./scripts/myStyle.css">
    </head>
    <body>
        
        <div class="clear">
            Enter details:
        </div> <br><br>
    
        <div>    

            ${msg}
            <form action="create" method="post">    
                <input type="text" name="name" placeholder="Name" onkeypress="this.style.width = ((this.value.length + 1) * 8) + 'px';"/><br><br>
                <input type="text" name="sym" placeholder="Symptoms"><br><br>
                <input type="text" name="dia" placeholder="Diagnosis"><br><br>  
                <input type="text" disabled="true" value="${date}" class="long-text"><br><br>
                <input type="hidden" name="key" value="${key}" />
                <input type="submit" value="Create"/>

            </form>

        </div>

        <div class="righty">

            <form action="login" method="post">
            <input name="user" type="hidden" value="${user}" />
            <input name="pass" type="hidden" value="" />
            <input name="key" type="hidden" value="${key}" />
            <input type="submit" value="Back" />
            </form>

        </div>
    </body>
    
</html>