package com.imooc.bearlive.action;

import java.io.IOException;
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

public class QuitRoomAction implements IAction {
	private static final String Param_User_id = "userId";
	private static final String Param_Room_id = "roomId";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		// 如果是主播：删除数据库中的roomId，RoomManager中的roomId，WatcherManger中的roomId.
		// 如果是观众：WatcherManger中的userId。

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

			String queryRoomIdSql = "SELECT `user_id`,`watcher_nums` FROM `RoomInfo` WHERE `room_id`=\""
					+ roomIdParam + "\"";
			stmt.execute(queryRoomIdSql);
			ResultSet resultSet = stmt.getResultSet();
			if (resultSet != null && !resultSet.wasNull()) {
				while (resultSet.next()) {
					int watchNums = resultSet.getInt("watcher_nums");
					String userIdSql = resultSet.getString("user_id");
					if (userIdSql != null && userIdSql.equals(userId)) {
						// 主播退出
						createrQuit(userId, resp);
						RoomManager.getInstance().removeRoom(roomIdParam);
						WatcherManager.getInstance().removeRoom(roomIdParam);
					} else {
						// 观众退出
						watcherQuit(roomId, resp, watchNums);
						WatcherManager.getInstance().removeWatcher(roomIdParam,
								userId);
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

	private void watcherQuit(int roomIdParam, HttpServletResponse response,
			int watchNums) throws SQLException, IOException {

		int fianlWatchNums = (watchNums - 1);
		if (fianlWatchNums < 0) {
			fianlWatchNums = 0;
		}
		Connection dbConnection = null;
		Statement stmt = null;
		dbConnection = SqlManager.getConnection();
		stmt = dbConnection.createStatement();
		String updateWatcherNumsSql = "UPDATE `RoomInfo` SET `watcher_nums`=\""
				+ fianlWatchNums + "\" WHERE `room_id`=\"" + roomIdParam + "\"";
		stmt.execute(updateWatcherNumsSql);
		int updateCount = stmt.getUpdateCount();//
	}

	public static void createrQuit(String userId, HttpServletResponse response)
			throws SQLException, IOException {
		Connection dbConnection = null;
		Statement stmt = null;
		dbConnection = SqlManager.getConnection();
		stmt = dbConnection.createStatement();
		String deleteRoomIdSql = "DELETE FROM `RoomInfo` WHERE `user_id`=\""
				+ userId + "\"";
		stmt.execute(deleteRoomIdSql);
		int updateCount = stmt.getUpdateCount();//
	}

}
