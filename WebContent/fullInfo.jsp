<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page import="java.io.*,java.util.*,com.imooc.bearlive.*,java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>info</title>
<link Type="text/css" href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/bootstrap.min.js"></script>
<%
FullRoomInfo room_full = new FullRoomInfo();
Connection conn = null;
Statement stm = null;

conn = SqlManager.getConnection();

try{stm = conn.createStatement();
String sqlstr="SELECT * FROM `room_info` WHERE `room_id`='"+request.getParameter("roomid")+"'";
stm.execute(sqlstr);
ResultSet rs = stm.getResultSet();

if (rs != null && !rs.wasNull()) {

	while (rs.next()) {

		
		room_full.roomid = rs.getString("room_id");
		room_full.userid = rs.getString("user_id");
		room_full.username = rs.getString("user_name");
		room_full.useravatar = rs.getString("user_avatar");
		room_full.livetitle = rs.getString("live_title");
		room_full.livecover = rs.getString("live_cover");
		room_full.roomtag = rs.getString("room_tag");
		room_full.roomintro = rs.getString("room_intro"); 
		room_full.starttime = rs.getString("start_time");
		room_full.endtime = rs.getString("end_time");
		room_full.numqiandao = rs.getInt("num_qiandao");
			
		
		
		
	}

}
}
catch (SQLException e) {
	e.printStackTrace();

} finally {
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
%>
</head>

<body>
<table class = "table table-hover table-responsive">
	<caption>
	<pre><big><strong>详细信息</strong></big></pre>
	</caption>
	<tbody>
		<tr>
			<td>房间编号</td>
			<td><%=request.getParameter("roomid") %></td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td>会议名称</td>
			<td><%=room_full.livetitle %></td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td>主持人</td>
			<td><%=room_full.username %></td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td>会议标签</td>
			<td><%=room_full.roomtag %></td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td>会议简介</td>
			<td><%=room_full.roomintro %></td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td>开始时间</td>
			<td><%=room_full.starttime %></td>
		</tr>
	</tbody>
</table>
</body>
</html>