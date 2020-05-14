package com.imooc.bean;
import java.sql.*;

import com.imooc.bearlive.FullUserInfo;
import com.imooc.bearlive.SqlManager;

public class LoginBean {
public FullUserInfo login(String username, String password)throws SQLException{
	//连接数据库
	Connection conn=null;
	conn = SqlManager.getConnection();
	Statement stm = conn.createStatement();
	String sql="SELECT * FROM `user_info` WHERE `user_id`='"+username+"' and `password` ='"+password+"'";
	ResultSet rs=stm.executeQuery(sql);
	FullUserInfo user=new FullUserInfo();
	
	if(rs.next()) {
		String userid=rs.getString("user_id");
		String username1 = rs.getString("user_name");
		
		
		user.setUserId(userid);
		user.setUsername(username1);
		stm.close();
		conn.close();
		return user;
	}
	else {
		stm.close();
		conn.close();
		return null;
	}
	
}
}
