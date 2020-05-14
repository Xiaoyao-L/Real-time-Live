package com.imooc.bearlive.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bearlive.SqlManager;
import com.imooc.bearlive.VoteInfo;

public class CreateVoteAction implements IAction {
	private static final String Param_Room_id = "roomId";
	private static final String Param_Vote_title = "voteTitle";
	private static final String Param_Choice_Name_1 = "choice1Name";
	//private static final String Param_Choice_Result_1 ="choice1Result";
	private static final String Param_Choice_Name_2 = "choice2Name";
	//private static final String Param_Choice_Result_2 ="choice2Result";
	private static final String Param_Choice_Name_3 = "choice3Name";
	//private static final String Param_Choice_Result_3 ="choice3Result";
	private static final String Param_Choice_Name_4 = "choice4Name";
	//private static final String Param_Choice_Result_4 ="choice4Result";
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		VoteInfo vote = new VoteInfo();
		vote.roomId = req.getParameter(Param_Room_id);
		vote.voteTitle = req.getParameter(Param_Vote_title);
		vote.choiceName_1 = req.getParameter(Param_Choice_Name_1);
		vote.choiceName_2 = req.getParameter(Param_Choice_Name_2);
		vote.choiceName_3 = req.getParameter(Param_Choice_Name_3);
		vote.choiceName_4 = req.getParameter(Param_Choice_Name_4);
		
		Connection conn = null;
		Statement smt = null;
		try {
			conn = SqlManager.getConnection();
			smt = conn.createStatement();
			String sql = "INSERT INTO `vote_info`(`room_id`, `vote_title`, `choice1_name`, `choice1_result`, `choice2_name`, `choice2_result`, `choice3_name`, `choice3_result`, `choice4_name`, `choice4_result`) "
					+ "VALUES ('"+vote.roomId+"','"+vote.voteTitle+"','"+vote.choiceName_1+"',0,'"+vote.choiceName_2+"',0,"
							+ "'"+vote.choiceName_3+"',0,'"+vote.choiceName_4+"',0)";
			smt.execute(sql);
			
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			try {
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
