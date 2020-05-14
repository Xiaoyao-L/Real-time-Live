package com.imooc.bearlive.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bearlive.SqlManager;

public class UserVoteAction implements IAction{
	
	private static final String Param_Room_id = "roomID";
	private static final String Param_Vote_title="voteTitle";
	private static final String Param_Choice_name = "choiceName";
	
	public void update(int num, Connection conn,int flag,String roomid, String title) throws SQLException {
		Statement stm = conn.createStatement();
		String sql = null;
		if(flag == 1)
		{
			sql ="UPDATE `vote_info` SET `choice1_result`="+num+" WHERE `room_id`='"+roomid+"' and `vote_title`='"+title+"'";
		}
		if(flag == 2)
		{
			sql ="UPDATE `vote_info` SET `choice2_result`="+num+" WHERE `room_id`='"+roomid+"' and `vote_title`='"+title+"'";
		}
		if(flag == 3)
		{
			sql ="UPDATE `vote_info` SET `choice3_result`="+num+" WHERE `room_id`='"+roomid+"' and `vote_title`='"+title+"'";
		}
		if(flag == 4) 
		{
			sql ="UPDATE `vote_info` SET `choice4_result`="+num+" WHERE `room_id`='"+roomid+"' and `vote_title`='"+title+"'";
		}
		stm.execute(sql);
		if(stm!=null)
		{
			stm.close();
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		String roomid = req.getParameter(Param_Room_id);
		String votetitle = req.getParameter(Param_Vote_title);
		String choicechosen = req.getParameter(Param_Choice_name);
		String c1 = null;	int r1 = 0;
		String c2 = null;	int r2 = 0;
		String c3 = null;	int r3 = 0;
		String c4 = null;	int r4 = 0;
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = SqlManager.getConnection();
			stm = conn.createStatement();
			String sql = "SELECT * FROM `vote_info` WHERE `room_id`='"+roomid+"'";
			stm.execute(sql);
			rs =stm.getResultSet();
			if(rs != null&& !rs.wasNull())
			{
				while(rs.next())
				{
					c1 = rs.getString("choice1_name");
					r1 = rs.getInt("choice1_result");
					c2 = rs.getString("choice2_name");
					r2 = rs.getInt("choice2_result");
					c3 = rs.getString("choice3_name");
					r3 = rs.getInt("choice3_result");
					c4 = rs.getString("choice4_name");
					r4 = rs.getInt("choice4_result");
				}
				
			}
			if(choicechosen.equals(c1))
			{
				r1++;
				update(r1,conn,1,roomid,votetitle);
			}
			else if(choicechosen.equals(c2))
			{
				r2++;
				update(r2,conn,2,roomid,votetitle);
			}
			else if(choicechosen.equals(c3))
			{
				r3++;
				update(r3,conn,3,roomid,votetitle);
			}
			else if(choicechosen.equals(c4))
			{
				r4++;
				update(r4,conn,4,roomid,votetitle);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally
		{
			try {
				if(rs!=null)
				{
					rs.close();
				}
				if(stm!=null)
				{
					stm.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

}
