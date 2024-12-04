package com.example.demo1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/offcart")
public class OffCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        String username=req.getParameter("username");
        String goodsid=req.getParameter("goodsid");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false", "root", "123456");
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM cart WHERE name = ? AND goodsid = ?");
            pstmt.setString(1,username);
            pstmt.setString(2,goodsid);
            int rs = pstmt.executeUpdate();
            if (rs > 0) {
                PrintWriter out = resp.getWriter();
                out.println("<button onclick=\"window.location.href=('indexcart?user="+username+"')\" >成功从购物车移除</button>");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
