<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%String user = request.getParameter("user");%>

<button onclick="window.location.href=('loginservlet?username=+ <%=user%>+')" >返回主页</button>
<form action="/onshelf" method="post">
    <input type="hidden" name="user" value="<%= user %>">
    待上架商品名称:<input type="text" name="goodsname">
    待上架数量:<input type="number"  name="quantity" min="0">
    商品定价:<input type="number" name="price" min="0" step="0.01">
    商品详细信息:<input type="text" name="detail">
    <input type="submit" value="上架">
</form>

</body>
</html>
