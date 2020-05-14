package com.imooc.bearlive.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bearlive.Error;
import com.imooc.bearlive.ResponseObj;
import com.imooc.bearlive.RoomInfo;
import com.imooc.bearlive.SqlManager;
import com.imooc.bearlive.WatcherManager;

/**
 * 创建房间接口
 * @author Administrator
 *
 */
public class CreateRoomAction implements IAction{


	private static final String Param_User_id = "userId";
	private static final String Param_User_avatar = "userAvatar";
	private static final String Param_User_name = "userName";
	private static final String Param_Live_cover = "liveCover";
	private static final String Param_Live_title = "liveTitle";
	
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

		String userAvatar = req.getParameter(Param_User_avatar);
		if (userAvatar == null) {
			userAvatar = "";
		}

		String userName = req.getParameter(Param_User_name);
		if (userName == null) {
			userName = "";
		}

		String liveCover = req.getParameter(Param_Live_cover);
		if (liveCover == null) {
			liveCover = "";
		}

		String liveTitle = req.getParameter(Param_Live_title);
		if (liveTitle == null) {
			liveTitle = "";
		}

	
		Connection dbcon = null;
		Statement stm = null;
		try {
			dbcon = SqlManager.getConnection();
			stm = dbcon.createStatement();

			String sqlStr = "INSERT INTO `roominfo`(`room_id`, `user_id`, `user_avatar`, `live_cover`, `live_title`, `watcher_nums`, `user_name`) "
					+ " VALUES ("
					+ 0
					+ ","
					+ "\""
					+ userId
					+ "\""
					+ ","
					+ "\""
					+ userAvatar
					+ "\""
					+ ","
					+ "\""
					+ liveCover
					+ "\""
					+ ","
					+ "\""
					+ liveTitle
					+ "\""
					+ ","
					+ 0
					+ ","
					+ "\""
					+ userName
					+ "\""
					+ ")";
			stm.execute(sqlStr);

			int updateCount = stm.getUpdateCount();
			if (updateCount > 0) {
				
				// 执行成功
				String queryRoomSql = "SELECT * FROM `roominfo` WHERE `user_id` = "
						+ "\"" + userId + "\"";
				stm.execute(queryRoomSql);
				ResultSet result = stm.getResultSet();
				if (result != null && !result.wasNull()) {
					RoomInfo roomInfo = new RoomInfo();
					while (result.next()) {
						roomInfo.roomId = result.getInt("room_id");
						roomInfo.userId = result.getString("user_id");
						roomInfo.userName = result.getString("user_name");
						roomInfo.userAvatar = result.getString("user_avatar");
						roomInfo.liveCover = result.getString("live_cover");
						roomInfo.liveTitle = result.getString("live_title");
						roomInfo.watchNums = result.getInt("watcher_nums");
					}
					
					WatcherManager.getInstance().addRoom(roomInfo.roomId  + "");

					// 获取到roomId之后，发送结果
					ResponseObj succObj = ResponseObj.getSuccess(roomInfo);
					ResponseObj.send(resp, succObj);
				}
			}

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
