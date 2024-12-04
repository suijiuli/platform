package com.example.demo1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/logs")
public class LogsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("user");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false", "root", "123456");
            PreparedStatement pstmt1 = conn.prepareStatement("SELECT * FROM logs WHERE merchant = ? ");
            pstmt1.setString(1,username);
            ResultSet rs =pstmt1.executeQuery();

            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html>");
            out.println("<head><title>Logs</title></head>");
            out.println("<body>");
            out.println("<button onclick=\"window.location.href=('loginservlet')\" >返回主页</button>");
            out.println("<table border='1'>");
            out.println("<tr><th>Consumer</th><th>Status</th><th>Goods ID</th><th>Time</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("consumer") + "</td>");
                out.println("<td>" + rs.getString("status") + "</td>");
                out.println("<td>" + rs.getString("goodsid") + "</td>");
                out.println("<td>" + rs.getString("time") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
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
