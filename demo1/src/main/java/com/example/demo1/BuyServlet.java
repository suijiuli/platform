package com.example.demo1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;

@WebServlet("/buy")
public class BuyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        String username=req.getParameter("username");
        String goodsid=req.getParameter("goodsid");
        int quantity= Integer.parseInt(req.getParameter("quantity"));
        Connection conn = null;
        String merchant=null;String goodsname=null;int goodsquantity=0;BigDecimal price = null;BigDecimal balance=null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false", "root", "123456");
            PreparedStatement pstmt1 = conn.prepareStatement("SELECT * FROM goods WHERE goodsid = ?");
            pstmt1.setString(1,goodsid);
            ResultSet rs1 = pstmt1.executeQuery();
            if(rs1.next()){
                merchant=rs1.getString("name");
                goodsname=rs1.getString("goods");
                goodsquantity=rs1.getInt("quantity");
                price = rs1.getBigDecimal("price");
            }
            PreparedStatement pstmt2 = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            pstmt2.setString(1,username);
            ResultSet rs2 = pstmt2.executeQuery();
            if(rs2.next()){
            balance = rs2.getBigDecimal("balance");}

            if(balance.compareTo(price.multiply(BigDecimal.valueOf(quantity)))<0){
                PrintWriter out = resp.getWriter();
                out.println("<button onclick=\"window.location.href=('indexcart?user="+username+"')\" >余额不足，购买失败</button>");
            }
            else {
                PreparedStatement pstmt3 = conn.prepareStatement("UPDATE goods SET quantity = ? WHERE goodsid = ?");
                pstmt3.setInt(1,goodsquantity-quantity);
                pstmt3.setString(2,goodsid);
                pstmt3.executeUpdate();
                PreparedStatement pstmt4 = conn.prepareStatement("UPDATE users SET balance = ? WHERE username = ?");
                pstmt4.setBigDecimal(1,balance.subtract(price.multiply(BigDecimal.valueOf(quantity))));
                pstmt4.setString(2,username);
                pstmt4.executeUpdate();
                PreparedStatement pstmt5 = conn.prepareStatement("UPDATE users SET balance = balance + ? WHERE username = ?");
                pstmt5.setBigDecimal(1,price.multiply(BigDecimal.valueOf(quantity)));
                pstmt5.setString(2,merchant);
                pstmt5.executeUpdate();
                PreparedStatement pstmt6 = conn.prepareStatement("INSERT INTO orders (consumer,merchant,goodsname,goodsid,unitprice,quantity,totalprice,time) VALUES (?,?,?,?,?,?,?,?)");
                pstmt6.setString(1,username);
                pstmt6.setString(2,merchant);
                pstmt6.setString(3,goodsname);
                pstmt6.setString(4,goodsid);
                pstmt6.setBigDecimal(5,price);
                pstmt6.setInt(6,quantity);
                pstmt6.setBigDecimal(7,price.multiply(BigDecimal.valueOf(quantity)));
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                pstmt6.setTimestamp(8,currentTime);
                pstmt6.executeUpdate();
                PreparedStatement pstmt7=conn.prepareStatement("INSERT INTO logs (consumer,status,goodsid, merchant ,time) VALUES( ?, ?, ?, ?,?)");
                pstmt7.setString(1, (String) req.getSession().getAttribute("user"));
                pstmt7.setString(2,"购买");
                pstmt7.setString(3,goodsid);
                pstmt7.setString(4,merchant);
                pstmt7.setTimestamp(5,currentTime);
                pstmt7.executeUpdate();
                PrintWriter out = resp.getWriter();
                out.println("<button onclick=\"window.location.href=('buyorder?')\" >购买成功</button>");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
