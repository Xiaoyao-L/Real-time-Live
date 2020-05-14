<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page language = "java" import ="java.util.*,java.sql.*,com.imooc.bearlive.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>超级会互动</title>
<link Type="text/css" href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/bootstrap.min.js"></script>
</head>
<body>
<%
//if(session.getAttribute("userid")==null)
//{
//	response.sendRedirect("individualinfo.jsp");
//}
/*else*/
//{ 	
	Connection conn = null;
	Statement stm = null;
	ArrayList<RoomInfo> roomInfos = new ArrayList<RoomInfo>();

		
	conn = SqlManager.getConnection();
	
	try{stm = conn.createStatement();
	String sqlstr="SELECT * FROM `roominfo`";
	stm.execute(sqlstr);
	ResultSet result = stm.getResultSet();
	
	if (result != null && !result.wasNull()) {

		while (result.next()) {

			RoomInfo roomInfo = new RoomInfo();

			roomInfo.roomId = result.getInt("room_id");
			roomInfo.userId = result.getString("user_id");
			roomInfo.userName = result.getString("user_name");
			roomInfo.userAvatar = result.getString("user_avatar");
			roomInfo.liveCover = result.getString("live_cover");
			roomInfo.liveTitle = result.getString("live_title");
			roomInfo.watchNums = result.getInt("watcher_nums");
			roomInfos.add(roomInfo);
			
		}

	}
	
	}catch (SQLException e) {
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

<table class = "table table-hover">
<caption>正在进行的会议</caption>
<thead>
	<tr>
		<th>会议编号</th>
		<th>会议名称</th>
		<th>主持人账号</th>
		<th>主持人名称</th>
		<th>详细信息</th>
	</tr>	
</thead>

<tbody>
<% 
for (RoomInfo room : roomInfos) {
%>
	<tr>
		<td><%=room.roomId %></td>
		<td><%=room.liveTitle %></td>
		<td><%=room.userId %></td>
		<td><%=room.userName %></td>
		<td><a href="#">查看</a></td>
	</tr>
<%
//}
}
%>
</tbody>

</table>
</body>
</html>