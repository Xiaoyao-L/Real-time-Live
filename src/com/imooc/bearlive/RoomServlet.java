package com.imooc.bearlive;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bearlive.action.CreateRoomAction;
import com.imooc.bearlive.action.GetRoomListAction;
import com.imooc.bearlive.action.GetWatchersAction;
import com.imooc.bearlive.action.HeartBeatAction;
import com.imooc.bearlive.action.IAction;
import com.imooc.bearlive.action.JoinRoomAction;
import com.imooc.bearlive.action.QuitRoomAction;

public class RoomServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String Param_Action = "action"; 
	private static final String Param_Action_CreateRoom = "createRoom"; 
	private static final String Param_Action_GetRoomList = "getRoomList"; 
	private static final String Param_Action_JoinRoom = "joinRoom"; 
	private static final String Param_Action_QuitRoom = "quitRoom"; 
	private static final String Param_Action_HeartBeat = "heartBeat"; 
	private static final String Param_Action_GetWatchers = "getWatchers"; 

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String action = req.getParameter(Param_Action);
		if(action == null || action.equals("")){
			//告诉调用者，没有填写action参数
			ResponseObj responseObj = 
					ResponseObj.getFail(Error.Error_Code_NoParam, Error.getErrorMsgNoParam(Param_Action));
			ResponseObj.send(resp, responseObj);
			return;
		}
		
		//判断action，然后创建对应的action对象去处理。
		if(Param_Action_CreateRoom.equals(action)){
			IAction createRoomAction = new CreateRoomAction();
			createRoomAction.doGet(req, resp);
		}else if(Param_Action_GetRoomList.equals(action)){
			IAction getRoomListAction = new GetRoomListAction();
			getRoomListAction.doGet(req, resp);
		} else if(Param_Action_JoinRoom.equals(action)){
			IAction joinRoomAction = new JoinRoomAction();
			joinRoomAction.doGet(req, resp);
		}  else if(Param_Action_QuitRoom.equals(action)){
			IAction quitRoomAction = new QuitRoomAction();
			quitRoomAction.doGet(req, resp);
		} else if(Param_Action_HeartBeat.equals(action)){
			IAction heartBeatAcion  = new HeartBeatAction();
			heartBeatAcion.doGet(req, resp);
		} else if(Param_Action_GetWatchers.equals(action)){
			IAction getWatchersAcion  = new GetWatchersAction();
			getWatchersAcion.doGet(req, resp);
		}
	}
}
