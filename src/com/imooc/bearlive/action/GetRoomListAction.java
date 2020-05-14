package com.imooc.bearlive.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bearlive.Error;
import com.imooc.bearlive.ResponseObj;
import com.imooc.bearlive.RoomInfo;
import com.imooc.bearlive.SqlManager;

public class GetRoomListAction implements IAction {

	private static final String Param_page_index = "pageIndex";

	private static final int pageSize = 20;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {

		String pageIndex = req.getParameter(Param_page_index);
		int page = -1;
		try {
			page = Integer.valueOf(pageIndex);
		} catch (Exception e) {

		}

		if (page < 0) {
			ResponseObj responseObj = ResponseObj.getFail(
					Error.Error_Code_ErrorParam,
					Error.getErrorMsgErrorParam(Param_page_index));
			ResponseObj.send(resp, responseObj);

			return;
		}

		Statement stm = null;
		Connection dbcon = null;
		try {
			dbcon = SqlManager.getConnection();
			stm = dbcon.createStatement();

			String sqlStr = "SELECT * FROM `roominfo` LIMIT " + page + " , "
					+ pageSize;
			stm.execute(sqlStr);
			ResultSet result = stm.getResultSet();
			ArrayList<RoomInfo> roomInfos = new ArrayList<RoomInfo>();
			if (result != null && !result.wasNull()) {

				while (result.next()) {

					RoomInfo roomInfo = new RoomInfo();

					roomInfo.roomId = result.getInt("room_id");
					roomInfo.userId = result.getString("user_id");
					roomInfo.userName = result.getString("user_name");
					roomInfo.userAvatar = result.getString("user_avatar");
					roomInfo.liveCover = result.getString("live_cover");
					roomInfo.liveTitle = result.getString("live_title");
					roomInfo.watchNums = result.getInt("watcher_nums");

					roomInfos.add(roomInfo);
				}

			}
			// 获取到roomList之后，发送结果
			ResponseObj succObj = ResponseObj.getSuccess(roomInfos);
			ResponseObj.send(resp, succObj);

		} catch (SQLException e) {
			ResponseObj failObj = ResponseObj.getFail(
					Error.Error_Code_Exception,
					Error.getErrorMsgException(e.getMessage()));
			ResponseObj.send(resp, failObj);

		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (dbcon != null) {
					dbcon.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
