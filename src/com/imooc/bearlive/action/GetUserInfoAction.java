package com.imooc.bearlive.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

import com.imooc.bearlive.*;
import com.imooc.bearlive.Error;
public class GetUserInfoAction implements IAction {

	private static final String Param_User_id = "userId1";
	private static final String Param_User_password = "userPassword";
	@SuppressWarnings("unused")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		String userId=req.getParameter(Param_User_id);
		String password=req.getParameter(Param_User_password);	
		Connection dbcon = null;
		Statement stm = null;
		
		try {
			dbcon = SqlManager.getConnection();
			boolean flag= insertUserInfo(dbcon,userId,password);


		} catch (SQLException e) {
			ResponseObj failObj = ResponseObj.getFail(
					Error.Error_Code_Exception,
					Error.getErrorMsgException(e.getMessage()));
			ResponseObj.send(resp, failObj);
		}
		finally {
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
	
	public boolean insertUserInfo(Connection dbcon, String userid, String password) {
		Statement stm=null;
		try {
			stm=dbcon.createStatement();
			String insertSQL ="INSERT INTO `user_info`(`user_id`, `password`) VALUES ("+"\""+userid+"\","+"\""+password+"\")";
			stm.execute(insertSQL);
			int updateCount=stm.getUpdateCount();
			return (updateCount > 0);
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
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
