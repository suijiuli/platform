<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%String user = request.getParameter("user");%>

<button onclick="window.location.href=('loginservlet?username=+ <%=user%>+')" >返回主页</button>
<form action="/changeprice" method="post">
    <input type="hidden" name="user" value="<%= user %>">
    待修改商品ID:<input type="text" name="goodsID">
    修改价格为:<input type="number"  name="price" min="0" step="0.01">
    <input type="submit" value="修改">
</form>

</body>
</html>