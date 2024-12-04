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

@WebServlet("/offshelf")
public class OffShelfServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String username = req.getParameter("user");
        String goodsid = req.getParameter("goodsID");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载MySQL驱动（确保你的项目中包含了MySQL JDBC驱动）
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false","root","123456");
            pstmt = conn.prepareStatement("SELECT quantity FROM goods WHERE name = ? AND goodsid = ?");
            pstmt.setString(1, username);
            pstmt.setString(2,goodsid);
            rs =  pstmt.executeQuery() ;
            if (rs.next()) {
                rs.getInt("quantity");
                if(rs.getInt("quantity")>=quantity){
                    PreparedStatement pstmtUpdate = conn.prepareStatement("UPDATE goods SET quantity = ? WHERE goodsid = ?");
                    pstmtUpdate.setInt(1, rs.getInt("quantity") - quantity);
                    pstmtUpdate.setString(2, goodsid);
                    int rs1 = pstmtUpdate.executeUpdate();
                    if (rs1 > 0) {
                        PrintWriter out = resp.getWriter();
                        out.println("<button onclick=\"window.location.href=('loginservlet?username="+username+"')\" >下架成功</button>");

                    } else {
                        System.out.println("未知原因下架失败");
                    }

                }
                else {
                    PrintWriter out = resp.getWriter();
                    out.println("<button onclick=\"window.location.href=('loginservlet?username="+username+"')\" >商品数量不足，请重新操作</button>");

                }
            }
            else {
                PrintWriter out = resp.getWriter();
                out.println("<button onclick=\"window.location.href=('loginservlet?username="+username+"')\" >无操作权限或无该商品，点击返回主页</button>");
            }
        }catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }}}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}