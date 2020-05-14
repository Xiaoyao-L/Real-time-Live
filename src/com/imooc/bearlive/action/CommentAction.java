package com.imooc.bearlive.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bearlive.SqlManager;
import com.imooc.bearlive.UserComment;

public class CommentAction implements IAction {

	private static final String Param_User_id = "userId";
	private static final String Param_User_name = "userName";
	private static final String Param_Room_id = "roomId";
	private static final String Param_Room_name = "roomName";
	private static final String Param_Comment_word = "cmtWords";
	private static final String Param_Comment_time = "cmtTime";
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		UserComment cmt = new UserComment();
		cmt.roomid = req.getParameter(Param_Room_id);
		cmt.roomname = req.getParameter(Param_Room_name);
		cmt.userid = req.getParameter(Param_User_id);
		cmt.username = req.getParameter(Param_User_name);
		cmt.time = req.getParameter(Param_Comment_time);
		cmt.words = req.getParameter(Param_Comment_word);

		Connection conn = null;
		Statement stm = null;
		try {
			conn=SqlManager.getConnection();
			stm = conn.createStatement();
			String sql = "INSERT INTO `room_comment`(`room_id`, `room_name`, `user_id`, "
					+ "`user_name`, `cmt_words`, `cmt_time`) VALUES "
					+ "('"+cmt.roomid+"','"+cmt.roomname+"','"+cmt.userid+"',"
					+ "'"+cmt.username+"','"+cmt.words+"','"+cmt.time+"')";
			stm.execute(sql);


		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			try {
				if(stm!=null)
				{
					stm.close();
				} 
				if(conn!=null)
				{
					conn.close();
				}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
