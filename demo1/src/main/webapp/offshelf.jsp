<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%String user = request.getParameter("user");%>

<button onclick="window.location.href=('loginservlet?username=+ <%=user%>+')" >返回主页</button>
<form action="/offshelf" method="post">
    <input type="hidden" name="user" value="<%= user %>">
    待下架商品ID:<input type="text" name="goodsID">
    下架数量:<input type="number"  name="quantity" min="0">
    <input type="submit" value="下架">
</form>

</body>
</html>
