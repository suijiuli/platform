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
        // 创建表头
        var thead = table.createTHead();
        var headerRow = thead.insertRow();
        var headers = ["商品ID", "商家", "商品名称", "剩余数量", "价格"];
        headers.forEach(function(header) {
            var th = document.createElement('th');
            th.textContent = header;
            headerRow.appendChild(th);
        });
        // 创建表体
        var tbody = table.createTBody();
        goodsData.forEach(function(goods) {
            var row = tbody.insertRow();
            Object.values(goods).forEach(function(cellData, index) {
                var cell = row.insertCell();
                cell.textContent = cellData;
                if(index === 0) { // 假设商品ID是第一个单元格
                    cell.onclick = function() { window.location.href = ('detail?goodsid='+goods.goodsId); };
                    cell.style.cursor = 'pointer'; // 将鼠标悬停时的样式改为手形
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
        border-collapse: collapse; /* 边框合并，表格看起来更整洁 */
    }

    th, td {
        border: 1px solid #dddddd; /* 设置边框颜色为柔和的灰色 */
        padding: 8px; /* 单元格内边距 */
        text-align: left; /* 文本左对齐 */
    }

    th {
        background-color: #4CAF; /* 表头背景色 */
        color: white; /* 表头文字颜色 */
    }

    tr:hover {
        background-color: #f5f5f5f5; /* 鼠标悬停时的行背景色 */
    }

    /* 新增CSS规则，用于设置鼠标悬停在商品ID上时的样式 */
    td:hover {
        background-color: #ffcccc; /* 红色高亮背景 */
    }
</style>
</html>