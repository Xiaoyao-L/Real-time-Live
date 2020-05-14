package com.imooc.bearlive.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bearlive.Error;
import com.imooc.bearlive.ResponseObj;
import com.imooc.bearlive.SqlManager;
import com.imooc.bearlive.UserInfo;

public class SendGiftAction implements IAction {

	private static final String Param_User_id = "userId";
	private static final String Param_Gift_exp = "exp";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {

		//
		String userId = req.getParameter(Param_User_id);
		if (userId == null || userId.isEmpty()) {
			// ��û�в����Ĵ���
			ResponseObj failObj = ResponseObj.getFail(Error.Error_Code_NoParam,
					Error.getErrorMsgNoParam(Param_User_id));
			ResponseObj.send(resp, failObj);
			return;
		}

		int exp = -1;
		try {
			exp = Integer.valueOf(req.getParameter(Param_Gift_exp));
		} catch (Exception e) {

		}

		if (exp < 0) {
			// ��û�в����Ĵ���
			ResponseObj failObj = ResponseObj.getFail(Error.Error_Code_NoParam,
					Error.getErrorMsgNoParam(Param_Gift_exp));
			ResponseObj.send(resp, failObj);
			return ;
		}

		// �������ݿ�
		Connection dbcon = null;
		Statement stm = null;
		try {
			dbcon = SqlManager.getConnection();
			stm = dbcon.createStatement();

			String queryUserSql = "SELECT * FROM `userinfo` WHERE `user_id` = \""
					+ userId + "\"";
			stm.execute(queryUserSql);
			ResultSet userResult = stm.getResultSet();
			UserInfo userInfo = null;
			
			if (userResult == null || !userResult.next()) {
				// ˵��û���û���Ϣ�������µ��û���Ϣ
				boolean insertSuccess = insertNewUser(dbcon, userId);
				if (insertSuccess) {
					userInfo = updateUserInfo(dbcon, userId, exp);
				}
			} else {
				// ���û���Ϣ�������û���Ϣ
				userInfo = updateUserInfo(dbcon, userId, exp);
			}
			//���û���Ϣ���أ���APP����������
			ResponseObj successObj = ResponseObj.getSuccess(userInfo);
			ResponseObj.send(resp, successObj);
	
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

	private UserInfo updateUserInfo(Connection dbcon, String userId, int exp) {

		Statement stm = null;

		UserInfo userInfo = null;
		try {
			stm = dbcon.createStatement();
			String queryUserSql = "SELECT * FROM `userinfo` WHERE `user_id` = \""
					+ userId + "\"";
			stm.execute(queryUserSql);
			ResultSet userResult = stm.getResultSet();
			if (userResult != null && userResult.next()) {
				// ��ȡ�û���Ϣ
				int dbGetNum = 0;
				int dbSendNum = 0;
				int dbExp = 0;
				int dbLevel = 0;
				dbGetNum = userResult.getInt("get_nums");
				dbSendNum = userResult.getInt("send_nums");
				dbExp = userResult.getInt("exp");
				dbLevel = userResult.getInt("user_level");

				// �����µĸ����;���
				dbSendNum++;
				dbExp += exp;
				dbLevel = dbExp / 200 + 1;

				//�������ݿ�
				String updateUserSql = "UPDATE `userinfo` SET `user_level`="
						+ dbLevel + ",`send_nums`=" + dbSendNum + ",`exp`=" + dbExp
						+ " WHERE `user_id` = \"" + userId + "\"";

				stm.execute(updateUserSql);
				int updateCount = stm.getUpdateCount();
				if (updateCount > 0) {//���³ɹ�
					userInfo = new UserInfo();
					userInfo.userId = userId;
					userInfo.getNums = dbGetNum;
					userInfo.sendNums = dbSendNum;
					userInfo.level = dbLevel;
				}
			}

			return userInfo;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		} finally {
			try {
				if (stm != null) {
					stm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean insertNewUser(Connection dbcon, String userId) {
		Statement stm = null;
		try {
			stm = dbcon.createStatement();
			String insertUserSql = "INSERT INTO `userinfo`(`user_id`, `user_level`, `send_nums`, `get_nums`, `exp`) VALUES ("
					+ "\"" + userId + "\" , " + "1 , 0 , 0 , 0" + ")";
			stm.execute(insertUserSql);
			int updateCount = stm.getUpdateCount();
			return (updateCount > 0);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		} finally {
			try {
				if (stm != null) {
					stm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
