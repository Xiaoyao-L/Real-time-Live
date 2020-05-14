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
    	//���ݿ������
    	
        ArrayList<Item> list = new ArrayList<Item>();
        
        //����ѡ�������ơ��͡���������Ϊ�������Է�װ��һ��Product���ÿ��Product��Ķ��󶼿��Կ�����һ�����X������ֵ����������Y������ֵ���ļ���
        list.add(new Item("�ϱ��������", 12));
        list.add(new Item("ʱ������", 22));
        list.add(new Item("���������Ļ�", 15));
        
   
        
        String json = gson.toJson(list);    //��list�еĶ���ת��ΪJson��ʽ������
        
       // System.out.println(json);
        
        //��json���ݷ��ظ��ͻ���
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(json);    
    }

}
