package com.imooc.bearlive;




import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.code.QRCodeUtils;

public class CodeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		String roomid = req.getParameter("roomId");
		String content = "http://meetinglive.butterfly.mopaasapp.com/userServlet?action=signIn&roomId="+roomid;
		QRCodeUtils encoder = new QRCodeUtils();
		encoder.encoderQRCoder(content, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(req,response);
	}
}

