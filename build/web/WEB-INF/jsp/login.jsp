<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML >

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script  type="text/javascript" src="./scripts/jquery-2.2.2.js"></script>
        <title>Welcome!</title>
        <link rel="stylesheet" type="text/css" href="./scripts/myStyle.css">
        
    </head>

    <body>
        
        <h1>${msg}</h1>
        
        <div class="lefty">
        <form action="login" method="post">
            <input type="text" id="user" name="user" placeholder="username"/>
            <input type="password" id="pass" name="pass" placeholder="password"/>
            <br>
            <input type="submit" value="Login"/>
        </form>
        </div>    
        
        
        <div class="righty">
        <form action="registerPage" method="get">
            
            <input type="submit" value="Register"/>
        </form>
        </div>
        
        <hr>
        
        
        
    </body>
</html>
