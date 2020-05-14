package com.imooc.bearlive;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bearlive.action.GetGiftAction;
import com.imooc.bearlive.action.IAction;
import com.imooc.bearlive.action.SendGiftAction;

public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String Param_Action = "action";
	private static final String Param_Action_SendGift = "sendGift";
	private static final String Param_Action_GetGift = "getGift";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter(Param_Action);
		if (action == null || action.equals("")) {
			// 告诉调用者，没有填写action参数
			ResponseObj responseObj = ResponseObj.getFail(
					Error.Error_Code_NoParam,
					Error.getErrorMsgNoParam(Param_Action));
			ResponseObj.send(resp, responseObj);
			return;
		}

		// 判断action，然后创建对应的action对象去处理。
		if (Param_Action_SendGift.equals(action)) {
			IAction sendGiftAction = new SendGiftAction();
			sendGiftAction.doGet(req, resp);
		} else if (Param_Action_GetGift.equals(action)) {
			IAction getGiftAction = new GetGiftAction();
			getGiftAction.doGet(req, resp);
		}

	}
}
