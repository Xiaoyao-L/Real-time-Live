package com.imooc.bearlive.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bearlive.Error;
import com.imooc.bearlive.FullUserInfo;
import com.imooc.bearlive.ResponseObj;
import com.imooc.bearlive.SqlManager;

public class GetFullUserInfo implements IAction {

	private static final String Param_User_id = "userId";
	private static final String Param_User_name = "userName";
	private static final String Param_User_gender = "userGender";
	private static final String Param_User_sign = "userSign";
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		String userid=req.getParameter(Param_User_id);
		String username=req.getParameter(Param_User_name);
		String usergender=req.getParameter(Param_User_gender);
		String usersign=req.getParameter(Param_User_sign);
		Connection dbcon = null;
		
		try {
			dbcon= SqlManager.getConnection();
			
			FullUserInfo user=null;
			user= update(dbcon, userid, usersign, username, usergender);
			//用户信息返回
			ResponseObj successObj = ResponseObj.getSuccess(user);
			ResponseObj.send(resp, successObj);
		}catch (SQLException e) {
			ResponseObj failObj = ResponseObj.getFail(
					Error.Error_Code_Exception,
					Error.getErrorMsgException(e.getMessage()));
			ResponseObj.send(resp, failObj);

	}finally {
		try {
			

			if (dbcon != null) {
				dbcon.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

	private FullUserInfo update(Connection conn, String userid, String usersign, String username, String usergender) {
		FullUserInfo user=null;
		Statement stm=null;
		try {
			stm = conn.createStatement();
			String updateUserSql = "UPDATE `user_info` SET `user_name`="+"\""
			+username+"\",`user_gender`="+"\""+usergender+"\",`user_sign`="+"\""+usersign+"\" WHERE `user_id` = \"" + userid + "\"";
			stm.execute(updateUserSql);
			int updateCount = stm.getUpdateCount();
			if(updateCount>0)
			{
				user=new FullUserInfo();
				user.userId = userid;
				user.username = username;
				user.usergender = usergender;
				user.usersign = usersign;
			}
			return user;
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}finally {
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
