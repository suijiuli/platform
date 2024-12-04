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

@WebServlet("/changeprice")
public class ChangePriceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        String username = req.getParameter("user");
        String goodsid = req.getParameter("goodsID");
        String price = req.getParameter("price");
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false","root","123456");
            pstmt = conn.prepareStatement("UPDATE goods SET price = ? WHERE goodsid = ?");
            pstmt.setString(1, price);
            pstmt.setString(2, goodsid);
            int rs1 = pstmt.executeUpdate();
            if (rs1 > 0) {
                PrintWriter out = resp.getWriter();
                out.println("<button onclick=\"window.location.href=('loginservlet?username="+username+"')\" >成功更新价格</button>");

            } else {
                PrintWriter out = resp.getWriter();
                out.println("<button onclick=\"window.location.href=('loginservlet?username="+username+"')\" >未知原因修改失败</button>");
            }
    }catch (ClassNotFoundException|SQLException e) {
        throw new RuntimeException(e);
    } }

        @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
