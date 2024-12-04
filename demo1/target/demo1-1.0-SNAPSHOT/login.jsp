<!DOCTYPE html>
<html lang="en">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form action="/loginservlet" method="post">
        用户名:<input type="text" name="username"><br>
        密码:<input type="password" name="password"><br>
        <input type="submit" value="登录">
    </form>
    <button id="regist" onclick="regist()">注册</button>
<script>
    function regist(){
        window.location.href='regist.jsp'
    }

</script>
</body>
</html>