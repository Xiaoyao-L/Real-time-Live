package com.imooc.bearlive.action;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bearlive.Error;
import com.imooc.bearlive.ResponseObj;
import com.imooc.bearlive.WatcherManager;

public class GetWatchersAction implements IAction {

	private static final String Param_Room_id = "roomId";
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		
		String roomIdParam = req.getParameter(Param_Room_id);
		int roomId = -1;
		try {
			roomId = Integer.valueOf(roomIdParam);
		} catch (Exception e) {

		}
		if (roomId < 0) {
			ResponseObj responseObject = ResponseObj.getFail(
					Error.Error_Code_ErrorParam,
					Error.getErrorMsgErrorParam(Param_Room_id));
			ResponseObj.send(resp, responseObject);
			return;
		}
		
		
		Set<String> watchers = WatcherManager.getInstance().getWatchers(roomId+"");
		
		ResponseObj successObj = ResponseObj.getSuccess(watchers);
		ResponseObj.send(resp, successObj);
	}

}
