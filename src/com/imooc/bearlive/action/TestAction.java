package com.imooc.bearlive.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TestAction implements IAction{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		//GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-20eac56af04e4469b5b11215251f1e21");
		//		goEasy.publish("5", "Hello!");
		try {
			req.getRequestDispatcher("test.jsp").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
