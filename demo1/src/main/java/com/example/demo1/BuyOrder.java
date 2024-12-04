package com.example.demo1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/buyorder")
public class BuyOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = (String) req.getSession().getAttribute("user");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter out = resp.getWriter();
             Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false", "root", "123456");
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orders WHERE consumer=?")) {

            pstmt.setString(1, user);
            ResultSet rs = pstmt.executeQuery();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Order Details</title>");
            out.println("<style>");
            out.println("table {");
            out.println("  width: 100%; border-collapse: collapse;");
            out.println("}");
            out.println("th, td {");
            out.println("  border: 1px solid black; padding: 8px; text-align: left;");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Consumer</th>");
            out.println("<th>Merchant</th>");
            out.println("<th>Goods Name</th>");
            out.println("<th>Goods ID</th>");
            out.println("<th>Unit Price</th>");
            out.println("<th>Quantity</th>");
            out.println("<th>Total Price</th>");
            out.println("<th>Time</th>");
            out.println("</tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("consumer") + "</td>");
                out.println("<td>" + rs.getString("merchant") + "</td>");
                out.println("<td>" + rs.getString("goodsname") + "</td>");
                out.println("<td>" + rs.getInt("goodsid") + "</td>");
                out.println("<td>" + rs.getBigDecimal("unitprice") + "</td>");
                out.println("<td>" + rs.getInt("quantity") + "</td>");
                out.println("<td>" + rs.getBigDecimal("totalprice") + "</td>");
                out.println("<td>" + rs.getTimestamp("time") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<button onclick=\"window.location.href=('loginservlet')\" >返回主页</button>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}