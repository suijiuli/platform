
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%String user = (String) request.getAttribute("user");%>
Hello,<%=user%>
<%String balance = (String) request.getAttribute("balance");%>
余额:<%=balance%>
<button onclick=window.location.href=('onshelf.jsp?user=<%=user%>')>上架商品</button><br>
<button onclick=window.location.href=('offshelf.jsp?user=<%=user%>')>下架商品</button><br>
<button onclick=window.location.href=('changeprice.jsp?user=<%=user%>')>修改商品价格</button><br>
<button onclick=window.location.href=('index?user=<%=user%>')>查看可购商品</button><br>
<button onclick=window.location.href=('indexcart?user=<%=user%>')>查看购物车</button><br>
<button onclick=window.location.href=('loginout')>注销</button><br>
<button onclick=window.location.href=('buyorder')>查看购买订单</button><br>
<button onclick=window.location.href=('sellorder')>查看出售订单</button><br>
<button onclick=window.location.href=('logs')>查看日志</button><br>
<div id="goodsTableContainer"></div>
</body>
<script>
    var json = <%= request.getAttribute("json") %>;

    var goodsData = typeof json === 'string' ? JSON.parse(json) : json;

    window.onload = function() {
        var table = document.createElement('table');
        table.style.width = '100%';

        var thead = table.createTHead();
        var headerRow = thead.insertRow();
        var headers = ["商品ID", "商家", "商品名称", "剩余数量", "价格"];
        headers.forEach(function(header) {
            var th = document.createElement('th');
            th.textContent = header;
            headerRow.appendChild(th);
        });

        var tbody = table.createTBody();
        goodsData.forEach(function(goods) {
            var row = tbody.insertRow();
            Object.values(goods).forEach(function(cellData) {
                var cell = row.insertCell();
                cell.textContent = cellData;
            });
        });

        document.getElementById('goodsTableContainer').appendChild(table);
    }

</script>
<style>
    table {
        width: 100%;
        border-collapse: collapse;
    }

    th, td {
        border: 1px solid #dddddd;
        padding: 8px;
        text-align: left;
    }

    th {
        background-color: #4CAF;
        color: white;
    }

    tr:hover {
        background-color: #f5f5f5f5;
    }
</style>
</html>
