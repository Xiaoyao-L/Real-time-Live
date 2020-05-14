package com.imooc.bearlive.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bearlive.Error;
import com.imooc.bearlive.ResponseObj;
import com.imooc.bearlive.RoomManager;
import com.imooc.bearlive.SqlManager;
import com.imooc.bearlive.WatcherManager;

public class HeartBeatAction implements IAction {
	private static final String Param_User_id = "userId";
	private static final String Param_Room_id = "roomId";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String userId = req.getParameter(Param_User_id);
		if (userId == null || userId.isEmpty()) {
			// 报没有参数的错误
			ResponseObj failObj = ResponseObj.getFail(Error.Error_Code_NoParam,
					Error.getErrorMsgNoParam(Param_User_id));
			ResponseObj.send(resp, failObj);
			return;
		}

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

		Connection dbConnection = null;
		Statement stmt = null;
		try {

			dbConnection = SqlManager.getConnection();
			stmt = dbConnection.createStatement();

			String queryRoomIdSql = "SELECT `user_id` FROM `RoomInfo` WHERE `room_id`=\""
					+ roomIdParam + "\"";
			stmt.execute(queryRoomIdSql);
			ResultSet resultSet = stmt.getResultSet();
			if (resultSet != null && !resultSet.wasNull()) {
				while (resultSet.next()) {
					String userIdSql = resultSet.getString("user_id");
					if (userIdSql != null && userIdSql.equals(userId)) {
						// 主播
						RoomManager.getInstance().updateRoom(roomIdParam);
					} else {
						// 观众
						WatcherManager.getInstance().updateRoomUser(
								roomIdParam, userId);
					}
				}
			}

			ResponseObj responseObject = ResponseObj.getSuccess(null);
			ResponseObj.send(resp, responseObject);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (dbConnection != null) {
					dbConnection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

}
