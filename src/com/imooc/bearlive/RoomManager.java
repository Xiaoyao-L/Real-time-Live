package com.imooc.bearlive;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RoomManager {

	private static RoomManager instance;

	private ScheduledExecutorService service = Executors
			.newSingleThreadScheduledExecutor();

	private Runnable command = new Runnable() {

		@Override
		public void run() {
			// 检测房间列表的最后跟新时间，如果超过20秒，就认为无效，需要从数据库删掉
			for (String roomId : roomIdMap.keySet()) {
				Long time = roomIdMap.get(roomId);

				if (time + 20 * 1000 < System.currentTimeMillis()) {
					// 已经超过20秒没有更新。

					System.err.println("已经超过20秒没有更新 的roomId = " + roomId);
					WatcherManager.getInstance().removeRoom(roomId);
					deleteRoom(roomId);
					roomIdMap.remove(roomId);
				}
			}

		}
	};

	public static RoomManager getInstance() {
		if (instance == null) {
			synchronized (RoomManager.class) {
				if (instance == null) {
					instance = new RoomManager();
				}
			}
		}

		return instance;
	}

	protected void deleteRoom(String roomId) {
		// 删除数据库中的roomId。
		Connection dbConnection = null;
		Statement stmt = null;

		try {
			dbConnection = SqlManager.getConnection();
			stmt = dbConnection.createStatement();

			String deleteRoomIdSql = "DELETE FROM `RoomInfo` WHERE `room_id`=\""
					+ roomId + "\"";
			stmt.execute(deleteRoomIdSql);
			int updateCount = stmt.getUpdateCount();
		} catch (SQLException e) {
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

	private RoomManager() {
		addExistRooms();
		startService();
	}

	private void addExistRooms() {
		// 从数据库中查询出已经存在的roomId，放到roomIdList里面去
		Connection dbConnection = null;
		Statement stmt = null;

		try {
			dbConnection = SqlManager.getConnection();
			stmt = dbConnection.createStatement();

			String getRoomIdSql = "SELECT `room_id` FROM `RoomInfo`";
			stmt.execute(getRoomIdSql);
			ResultSet resultSet = stmt.getResultSet();
			if (resultSet != null && !resultSet.wasNull()) {
				while (resultSet.next()) {
					String roomId = resultSet.getInt("room_id") + "";
					System.err.println("已有的roomId = " + roomId);
					updateRoom(roomId);
				}
			} else {

			}
		} catch (SQLException e) {
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

	private void startService() {
		// 每20秒启动一次command，检查时间是否过期
		service.scheduleWithFixedDelay(command, 0, 20, TimeUnit.SECONDS);

	}

	private Map<String, Long> roomIdMap = new HashMap<String, Long>();

	public void updateRoom(String roomId) {
		if (roomIdMap.keySet().contains(roomId)) {
			// 更新已有的房间最后使用的时间
			roomIdMap.put(roomId, System.currentTimeMillis());
		} else {
			// 没有的话，直接添加新的。
			roomIdMap.put(roomId, System.currentTimeMillis());
		}
	}

	public void removeRoom(String roomId) {
		roomIdMap.remove(roomId);
	}
}
