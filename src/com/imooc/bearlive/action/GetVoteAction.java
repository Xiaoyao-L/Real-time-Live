package com.imooc.bearlive.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bearlive.ResponseObj;
import com.imooc.bearlive.SqlManager;
import com.imooc.bearlive.VoteInfo;

public class GetVoteAction implements IAction {

	private static final String Param_Room_id = "roomId";
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		VoteInfo vote = new VoteInfo();
		vote.roomId = req.getParameter(Param_Room_id);
		Connection conn = null;
		Statement smt = null;
		ResultSet rs = null;
		try {
			conn = SqlManager.getConnection();
			smt = conn.createStatement();
			String sql="SELECT * FROM `vote_info` WHERE `room_id`='"+vote.roomId+"'";
			smt.execute(sql);
			rs = smt.getResultSet();
			if(rs != null&& !rs.wasNull())
			{
				while(rs.next())
				{
					vote.voteTitle=rs.getString("vote_title");
					vote.choiceName_1 = rs.getString("choice1_name");
					vote.choiceResult_1 = rs.getInt("choice1_result");
					vote.choiceName_2 = rs.getString("choice2_name");
					vote.choiceResult_2 = rs.getInt("choice2_result");
					vote.choiceName_3 = rs.getString("choice3_name");
					vote.choiceResult_3 = rs.getInt("choice3_result");
					vote.choiceName_4 = rs.getString("choice4_name");
					vote.choiceResult_4 = rs.getInt("choice4_result");
				}
				//·¢ËÍ½á¹û
				ResponseObj success = ResponseObj.getSuccess(vote);
				ResponseObj.send(resp, success);
			}


		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			try {
				if(rs!=null)
				{
					rs.close();
				}
				if(smt!=null)
				{
					smt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}catch(SQLException e)
			{
				e.printStackTrace();
			}

		}
	}


}
