package com.imooc.bearlive;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FullRoomInfo {

	
	public String roomid;
	public String userid;
	public String username;
	public String useravatar;
	public String livetitle;
	public String livecover;
	public String roomtag;
	public String roomintro;
	public String starttime;
	public String endtime;
	public int numqiandao;

	public  FullRoomInfo roominfo(String roomid) {
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		FullRoomInfo roominfo = new FullRoomInfo();
		try {
			conn = SqlManager.getConnection();
			stm = conn.createStatement();
			String sql = "SELECT * FROM `room_info` WHERE `room_id`='"+roomid+"'";
			stm.execute(sql);
			rs = stm.getResultSet();
			if(rs != null && !rs.wasNull()) {
				while(rs.next())
				{
					roominfo.roomid = rs.getString("room_id");
					roominfo.userid = rs.getString("user_id");
					roominfo.username = rs.getString("user_name");
					roominfo.useravatar = rs.getString("user_avatar");
					roominfo.livetitle = rs.getString("live_title");
					roominfo.livecover = rs.getString("user_cove");
					roominfo.roomtag = rs.getString("room_tag");
					roominfo.roomintro = rs.getString("room_intro"); 
					roominfo.starttime = rs.getString("start_time");
					roominfo.endtime = rs.getString("end_time");
					roominfo.numqiandao = rs.getInt("num_qiandao");
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conn != null) {
					conn.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			
		}
		return roominfo;
	}
	
	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUseravatar() {
		return useravatar;
	}

	public void setUseravatar(String useravatar) {
		this.useravatar = useravatar;
	}

	public String getLivetitle() {
		return livetitle;
	}

	public void setLivetitle(String livetitle) {
		this.livetitle = livetitle;
	}

	public String getLivecover() {
		return livecover;
	}

	public void setLivecover(String livecover) {
		this.livecover = livecover;
	}

	public String getRoomtag() {
		return roomtag;
	}

	public void setRoomtag(String roomtag) {
		this.roomtag = roomtag;
	}

	public String getRoomintro() {
		return roomintro;
	}

	public void setRoomintro(String roomintro) {
		this.roomintro = roomintro;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public int getNumqiandao() {
		return numqiandao;
	}

	public void setNumqiandao(int numqiandao) {
		this.numqiandao = numqiandao;
	}

}

