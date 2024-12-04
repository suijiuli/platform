<!DOCTYPE html>
<html lang="en">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form action="/registservlet" method="post">
        用户名:<input type="text" name="username" id="username"><br>
        密码:<input type="password" name="password" id="pwd"><br>
        确认密码:<input type="password" name="password1" id="pwd1"><br>
        <button  id="submit" >提交</button>
    </form>
        <button onclick="tologin()">返回登录</button>
</body>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script>
    document.getElementById('submit').disabled=true;
    function tologin(){
        window.location.href='login.jsp'
    }
    function checkPasswords(){
        if(document.getElementById('pwd').value!==document.getElementById('pwd1').value){
            document.getElementById('submit').disabled=true;
        }
        else if((document.getElementById('pwd').value!=null)&&(document.getElementById('pwd1').value!=null)){document.getElementById('submit').disabled=false;}
    }
    document.getElementById('pwd').addEventListener('input', checkPasswords);
    document.getElementById('pwd1').addEventListener('input', checkPasswords);
</script>
</html>