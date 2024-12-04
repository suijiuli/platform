package com.example.demo1;

import com.example.demo1.model.Goods;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.*;
import java.sql.* ;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/loginservlet")
public class LoginSevlet extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false","root","123456");

            String user = (String) request.getSession().getAttribute("user");
            if (user == null) {
                pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    HttpSession session = request.getSession(); // 创建会话
                    session.setAttribute("user", username);
                    request.setAttribute("user", username);
                    request.setAttribute("balance",rs.getString("balance"));
                    pstmt=conn.prepareStatement("SELECT * FROM goods WHERE name = ?");
                    pstmt.setString(1,username);
                    rs=pstmt.executeQuery();

                    List <Goods> GoodsList= new ArrayList<>();

                    while(rs.next()){
                        Goods goods = new Goods();
                        goods.goodsId=rs.getString("goodsid");
                        goods.user=rs.getString("name");
                        goods.goodsName=rs.getString("goods");
                        goods.quantity=rs.getInt("quantity");
                        goods.price=rs.getBigDecimal("price");
                        GoodsList.add(goods);
                    }
                    Gson gson=new Gson();
                    String json=gson.toJson(GoodsList);
                    request.setAttribute("json", json);
                    request.getRequestDispatcher("home.jsp").forward(request,response);
                }
                else {
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
            else {
                pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
                pstmt.setString(1, user);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    request.setAttribute("balance",rs.getString("balance"));
                    request.setAttribute("user", user);
                    pstmt=conn.prepareStatement("SELECT * FROM goods WHERE name = ?");
                    pstmt.setString(1,user);
                    rs=pstmt.executeQuery();

                    List <Goods> GoodsList= new ArrayList<>();

                    while(rs.next()){
                        Goods goods = new Goods();
                        goods.goodsId=rs.getString("goodsid");
                        goods.user=rs.getString("name");
                        goods.goodsName=rs.getString("goods");
                        goods.quantity=rs.getInt("quantity");
                        goods.price=rs.getBigDecimal("price");
                        GoodsList.add(goods);
                    }
                    Gson gson=new Gson();
                    String json=gson.toJson(GoodsList);
                    request.setAttribute("json", json);
                    request.getRequestDispatcher("home.jsp").forward(request,response);
                }
                else {
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }

        }
        catch (ClassNotFoundException | SQLException |ServletException e) {
            throw new RuntimeException(e);
        }
        finally {
            // 关闭资源
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doGet(req,resp);
    }
}
