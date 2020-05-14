package com.imooc.bearlive;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bean.LoginBean;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LoginBean login=new LoginBean();
		FullUserInfo user=new FullUserInfo();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		try {
			user=login.login(username, password);
			if(user!=null) {
				
				request.getSession().setAttribute("user", user);
				request.getRequestDispatcher("individualinfo.jsp").forward(request, response);
			}
			else
			{
				request.getRequestDispatcher("Login.html").forward(request, response);
			}
		}catch(SQLException e)
		{
			System.out.println("µÇÂ¼·ÃÎÊÊý¾Ý¿âÊ§°Ü");
			e.printStackTrace();
		}
	}

}
