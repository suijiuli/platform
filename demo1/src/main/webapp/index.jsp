<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% String user= request.getSession().getAttribute("user").toString();%>

<button onclick="window.location.href=('loginservlet?username=+ <%=user%>+')" >返回主页</button>
<div id="goodsTableContainer"></div>
</body>
<script>
    var json = <%= request.getAttribute("json") %>;
    var goodsData = typeof json === 'string' ? JSON.parse(json) : json;
    function test() {
        console.log(goodsData);
    }
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
            Object.values(goods).forEach(function(cellData, index) {
                var cell = row.insertCell();
                cell.textContent = cellData;
                if(index === 0) {
                    cell.onclick = function() { window.location.href = ('detail?goodsid='+goods.goodsId); };
                    cell.style.cursor = 'pointer';
                }
            });
            var actionCell = row.insertCell();
            var actionButton = document.createElement('button');
            actionButton.textContent = '添加购物车';
            actionButton.onclick = function() {
                window.location.href=('cart?username=<%=user%>&goodsid='+goods.goodsId)
            };
            actionCell.appendChild(actionButton);

            var detailButton = document.createElement('button');
            detailButton.textContent = '详细信息';
            detailButton.onclick = function() {
                window.location.href = ('detail?goodsid='+goods.goodsId);
            };
            actionCell.appendChild(detailButton);
        });
        document.getElementById('goodsTableContainer').appendChild(table);
    };

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


    td:hover {
        background-color: #ffcccc;
    }
</style>
</html>