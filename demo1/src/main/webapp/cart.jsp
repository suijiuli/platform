<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%String user = (String) request.getAttribute("user");%>
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
        var headers = ["商品ID", "商家", "商品名称", "剩余数量", "价格", "操作"];
        headers.forEach(function(header) {
            var th = document.createElement('th');
            th.textContent = header;
            headerRow.appendChild(th);
        });

        var tbody = table.createTBody();
        goodsData.forEach(function(goods, index) {
            var row = tbody.insertRow();
            Object.keys(goods).forEach(function(key) {
                var cell = row.insertCell();
                cell.textContent = goods[key];
            });

            // 添加操作列
            var operationCell = row.insertCell();
            var deleteButton = document.createElement('button');
            deleteButton.textContent = '移出购物车';
            deleteButton.onclick = function() {
                window.location.href=('offcart?username=<%=user%>&goodsid='+goods.goodsId)
            };
            operationCell.appendChild(deleteButton);


            var quantityInput = document.createElement('input');
            quantityInput.type = 'number';
            quantityInput.min = '1';
            quantityInput.value = '1';
            operationCell.appendChild(quantityInput);

            var addButton = document.createElement('button');
            addButton.textContent = '购买';
            addButton.disabled = goods.quantity < parseInt(quantityInput.value);
            addButton.onclick = function() {window.location.href=('buy?username=<%=user%>&goodsid='+goods.goodsId+'&quantity='+quantityInput.value)

            };
            operationCell.appendChild(addButton);
            quantityInput.addEventListener('change', function() {
                addButton.disabled = goods.quantity < parseInt(quantityInput.value);
            });
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
</style>
</html>
