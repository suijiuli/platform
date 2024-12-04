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

@WebServlet("/indexcart")
public class IndexCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("user");
        Connection conn = null;
        List<Goods> GoodsList= new ArrayList<>();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false", "root", "123456");
            PreparedStatement pstmt1 = conn.prepareStatement("SELECT * FROM cart WHERE name = ? ");
            pstmt1.setString(1,username);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()){
                Goods goods = new Goods();
                goods.goodsId=rs1.getString("goodsid");
                PreparedStatement pstmt2 = conn.prepareStatement("SELECT * FROM goods WHERE goodsid = ? ");
                pstmt2.setString(1, goods.goodsId);
                ResultSet rs2 = pstmt2.executeQuery();
                while(rs2.next()){
                    goods.user=rs2.getString("name");
                    goods.goodsName=rs2.getString("goods");
                    goods.quantity=rs2.getInt("quantity");
                    goods.price=rs2.getBigDecimal("price");
                    GoodsList.add(goods);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Gson gson=new Gson();
        String json=gson.toJson(GoodsList);
        req.setAttribute("user",username);
        req.setAttribute("json", json);
        req.getRequestDispatcher("cart.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
