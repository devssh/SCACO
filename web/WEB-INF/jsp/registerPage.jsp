
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML >

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script  type="text/javascript" src="./scripts/jquery-2.2.2.js"></script>
        <script  type="text/javascript" src="./scripts/jquery.debounce-1.0.5.js"></script>
        <title>Register</title>
        <link rel="stylesheet" type="text/css" href="./scripts/myStyle.css">
        
        
    </head>

    <body>
        <script  type="text/javascript" src="./scripts/myScript.js"></script>
        
        <div class="lefty">
            <form id="details" action="register" method="post">
                <input type="text" name ="user" id="user" placeholder="username" required="true"/>&nbsp;&nbsp;<a id="validUser"></a> <br>          
                <input type="email" name="email" id="email" placeholder="email@gmail.com" required="true"/><br>
                <input type="password" name="pass" id="pass" pattern=".{6,}" placeholder="password" required="true" title="Minimum 6 characters."/><br>
                <input type="submit" id="register" id="register" required="true"/>                
            </form>
        </div>
            
        
    </body>
</html>