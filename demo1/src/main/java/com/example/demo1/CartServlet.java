package com.example.demo1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String goodsid=req.getParameter("goodsid");
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false", "root", "123456");
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM cart WHERE name = ? AND goodsid = ?");
            pstmt.setString(1,username);
            pstmt.setString(2,goodsid);
            ResultSet rs= pstmt.executeQuery();
            if (rs.next()) {
                resp.setContentType("text/html;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.println("<button onclick=\"window.location.href=('index?user="+username+"')\" >购物车中已存在，请勿重复添加</button>");

            }
            else {
                pstmt = conn.prepareStatement("INSERT INTO cart (name, goodsid) VALUES (?, ?)");
                pstmt.setString(1, username);
                pstmt.setString(2, goodsid);
                int result = pstmt.executeUpdate();
                if (result > 0) {
                    resp.setContentType("text/html;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.println("<button onclick=\"window.location.href=('index?user="+username+"')\" >成功加入购物车</button>");
                } else {
                    PrintWriter out = resp.getWriter();
                    out.println("<button onclick=\"window.location.href=('index?user="+username+"')\" >添加失败</button>");

                }
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
