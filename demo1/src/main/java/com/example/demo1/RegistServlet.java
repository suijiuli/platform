package com.example.demo1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.* ;
import java.io.IOException;
@WebServlet("/registservlet")
public class RegistServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        resp.setContentType("application/json");
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false", "root", "123456");
            pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                resp.setContentType("text/html;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.println("<button onclick=\"window.location.href=('regist.jsp')\" >用户名重复，请重新注册</button>");

            }
            else {
                pstmt = conn.prepareStatement("INSERT INTO users (username, password, balance) VALUES (?, ? , ?)");
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3,"1000");
                int result = pstmt.executeUpdate();
                if (result > 0) {
                    RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
                    dispatcher.forward(req, resp);
                } else {
                    PrintWriter out = resp.getWriter();
                    out.print("{\"status\":\"error\"}");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
