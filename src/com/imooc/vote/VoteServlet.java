package com.imooc.vote;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.Gson;

public class VoteServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Gson gson = new Gson();
    	String roomid = request.getParameter("roomid");
    	//TODO 
    	//数据库的连接
    	
        ArrayList<Item> list = new ArrayList<Item>();
        
        //这里把“类别名称”和“销量”作为两个属性封装在一个Product类里，每个Product类的对象都可以看作是一个类别（X轴坐标值）与销量（Y轴坐标值）的集合
        list.add(new Item("南北地域差异", 12));
        list.add(new Item("时代背景", 22));
        list.add(new Item("佛教与道教文化", 15));
        
   
        
        String json = gson.toJson(list);    //将list中的对象转换为Json格式的数组
        
       // System.out.println(json);
        
        //将json数据返回给客户端
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(json);    
    }

}
