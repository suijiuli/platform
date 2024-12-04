package com.example.demo1;

import com.example.demo1.model.Goods;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("user");
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false", "root", "123456");
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM goods WHERE name != ? And quantity != 0");
            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();
            List<Goods> GoodsList= new ArrayList<>();
            while(rs.next()){
                Goods goods = new Goods();
                goods.goodsId=rs.getString("goodsid");
                goods.user=rs.getString("name");
                goods.goodsName=rs.getString("goods");
                goods.quantity=rs.getInt("quantity");
                goods.price=rs.getBigDecimal("price");
                GoodsList.add(goods);
            }
            Gson gson=new Gson();
            String json=gson.toJson(GoodsList);
            req.setAttribute("user",username);
            req.setAttribute("json", json);
            req.getRequestDispatcher("index.jsp").forward(req,resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
