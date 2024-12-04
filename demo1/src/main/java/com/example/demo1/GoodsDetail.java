package com.example.demo1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/detail")
public class GoodsDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String goodsid=req.getParameter("goodsid");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false","root","123456");
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM goodsdetail WHERE goodsid = ?");
            pstmt.setString(1,goodsid);
            ResultSet rs=pstmt.executeQuery();
            if (rs.next()) {
                String detail = rs.getString("detail");
                String merchant = rs.getString("merchant");
                PreparedStatement pstmt1=conn.prepareStatement("INSERT INTO logs (consumer,status,goodsid, merchant, time) VALUES( ?, ?, ?, ?, ?)");
                pstmt1.setString(1, (String) req.getSession().getAttribute("user"));
                pstmt1.setString(2,"浏览");
                pstmt1.setString(3,goodsid);
                pstmt1.setString(4,merchant);
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                pstmt1.setTimestamp(5,currentTime);
                pstmt1.executeUpdate();
                PrintWriter out = resp.getWriter();
                out.println("<html>");
                out.println("<head><title>商品详情</title></head>");
                out.println("<body>");
                out.println("<h1>商品详情</h1>");
                out.println("<p><strong>商家:</strong> " + merchant + "</p>");
                out.println("<p><strong>详细信息:</strong> " + detail + "</p>");
                out.println("<button onclick=\"window.location.href=('index?user="+(String) req.getSession().getAttribute("user")+"')\" >返回</button>");
                out.println("</body>");
                out.println("</html>");
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
