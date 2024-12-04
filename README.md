20213442921  滕凤军
创建一个goods类存储商品信息。
GoodsDetail是后来实现浏览记录日志添加的，所以不存在goods类中，单独做了一个表单存储。
在jsp页面中通过form表单提交数据。
在LoginServlet中使用session判断登录状态。并从数据库中请求数据request.setAttribute()转发数据。
OnShelfSevlet和OffShelfServlet管理商品的上架下架。
IndexServlet查询除了自己上架以外的商品，并在index.jsp打印。
在index.jsp可以实现点击
移除购物车（OffCart）和购买（BuyServlet）功能。
