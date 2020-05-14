package com.imooc.bearlive.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bearlive.SqlManager;

import java.sql.*;

public class GetSigninAction implements IAction {

	private static final String Param_Room_Id = "roomId";
	private static final String Param_User_Id = "userId";
	private static final String Param_Room_Time = "timeSignin";
	private static final String Param_User_Name = "userName";
	private static final String Param_Room_Name = "roomName";

	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		String roomid = req.getParameter(Param_Room_Id);
		String userid = req.getParameter(Param_User_Id);
		String signintime = req.getParameter(Param_Room_Time);
		String username = req.getParameter(Param_User_Name);
		String roomname = req.getParameter(Param_Room_Name);
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = SqlManager.getConnection();
			stm = conn.createStatement();

			String sql = "INSERT INTO `user_signin`(`user_id`, `user_name`, `room_id`, `room_name`, `time_signin`) VALUES "
					+ "('"+userid+"','"+username+"','"+roomid+"','"+roomname+"','"+signintime+"')";
			stm.execute(sql);
			int updatecount = stm.getUpdateCount();
			if(updatecount > 0) {


				String countsql = "SELECT COUNT(*) as count FROM `user_signin` WHERE `room_id`='"+roomid+"' and `room_name`='"+roomname+"'";
				rs = stm.executeQuery(countsql);
				rs.next();
				int count = rs.getInt("count");

				String sql1 = "UPDATE `room_info` SET `num_qiandao`= "+count+" WHERE `room_id`='"+roomid+"' and `live_title`='"+roomname+"'";
				stm.execute(sql1);
				
				String sql2 = "UPDATE `user_record` SET `if_qiandao`='ÒÑÇ©µ½' WHERE `user_id`='"+userid+"' and `room_id`='"+roomid+"'";
				stm.execute(sql2);
			}
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stm != null) {
					stm.close();
				}

				if (conn != null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
