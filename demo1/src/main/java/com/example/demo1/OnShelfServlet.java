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

@WebServlet("/onshelf")
public class OnShelfServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String username = req.getParameter("user");
        String goodsname = req.getParameter("goodsname");
        String quantity = req.getParameter("quantity");
        String price = req.getParameter("price");
        String detail =req.getParameter("detail");
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false","root","123456");

            pstmt = conn.prepareStatement("INSERT INTO goods (name, goods, quantity, price) VALUES (?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username);
            pstmt.setString(2, goodsname);
            pstmt.setInt(3, Integer.parseInt(quantity));
            pstmt.setBigDecimal(4, new BigDecimal(price));
            int rs =  pstmt.executeUpdate() ;
            if(rs>0){
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long generatedKey = generatedKeys.getLong(1);
                    String goodsid = Long.toString(generatedKey);
                    PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO goodsdetail (goodsid, detail, merchant) VALUES ( ?, ?, ?)");
                    pstmt1.setInt(1, Integer.parseInt(goodsid));
                    pstmt1.setString(2,detail);
                    pstmt1.setString(3,username);
                    pstmt1.executeUpdate();
                }

                PrintWriter out = resp.getWriter();
                out.println("<button onclick=\"window.location.href=('loginservlet?username="+username+"')\" >上架成功，点击返回主页</button>");
            }
            else {System.out.println("Data insertion failed");}
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }}

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
