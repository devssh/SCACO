$(document).ready(function(){
    
    $("#user").keyup($.debounce(function(){
        $.post("isUserValid",
        {
          user: $("#user").val()
        },
        function(data,status){
            
            if(data==="true"){
                $("#validUser").html("* Username has been taken.").addClass("red");
            }else{
                $("#validUser").html("Username is available.").addClass("green");
            }
             
        });
   
               
    },2000));
    
    

});



