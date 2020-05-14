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
<% if(request.getParameter("userid")==null)
{
	
	response.sendRedirect("Login.jsp");
}

else{
	FullRoomInfo roominfo = new FullRoomInfo();
	
	Connection conn = null;
	Statement stm = null;
	ArrayList<UserRecord> records_h = new ArrayList<UserRecord>();
	ArrayList<UserRecord> records_w = new ArrayList<UserRecord>();
	
	conn = SqlManager.getConnection();
	
	try{stm = conn.createStatement();
	String sqlstr="SELECT * FROM `user_record` WHERE `user_id`='"+request.getParameter("userid")+"' and `user_status`='holder'";
	stm.execute(sqlstr);
	ResultSet result1 = stm.getResultSet();
	
	if (result1 != null && !result1.wasNull()) {

		while (result1.next()) {

			UserRecord record_h = new UserRecord();
			record_h.roomId = result1.getString("room_id");
			record_h.roomName = result1.getString("room_name");
			record_h.timeStart = result1.getString("time_start");
			record_h.timeEnd = result1.getString("time_end");
				
			
			records_h.add(record_h);
			
		}

	}
	
	String sql = "SELECT * FROM `user_record` WHERE `user_id`='"+request.getParameter("userid")+"' and `user_status`='watcher'";
	stm.execute(sql);
	
	ResultSet result2 = stm.getResultSet();
		
		if (result2 != null && !result2.wasNull()) {
	
			while (result2.next()) {
	
				UserRecord record_w = new UserRecord();
				record_w.roomId = result2.getString("room_id");
				record_w.roomName = result2.getString("room_name");
				record_w.timeStart = result2.getString("time_start");
				record_w.timeEnd = result2.getString("time_end");
				record_w.ifQiandao = result2.getString("if_qiandao");	
				
				records_w.add(record_w);
				
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

<table class = "table table-hover table-responsive">
<caption>主持过的会议</caption>
<thead>
	<tr>
		<th>会议编号</th>
		<th>会议名称</th>
		<th>开始时间</th>
		<th>结束时间</th>
		<th>详细信息 </th>
	</tr>	
</thead>

<tbody>
<% 
for (UserRecord r : records_h) {
%>
	<tr>
		<td><%=r.roomId %></td>
		<td><%=r.roomName %></td>
		<td><%=r.timeStart %></td>
		<td><%=r.timeEnd %></td>
		 
		<td><a href="#">查看</a> 
		</td>
<%
    	//roominfo = FullRoomInfo.roominfo(r.roomId);
}
%>
</tbody>
</table>

<table class = "table table-hover table-responsive">
<caption>参加过的会议</caption>
<thead>
	<tr>
		<th>会议编号</th>
		<th>会议名称</th>
		<th>开始时间</th>
		<th>结束时间</th>
		<th>签到状态</th>
		<th>详细信息 </th>
	</tr>	
</thead>

<tbody>
<% 
for (UserRecord r : records_w) {
%>
	<tr>
		<td><%=r.roomId %></td>
		<td><%=r.roomName %></td>
		<td><%=r.timeStart %></td>
		<td><%=r.timeEnd %></td>
		<td><%=r.ifQiandao %></td>
		<td><a href="#">查看</a></td>
	</tr>
<%
}
}
%>
</tbody>
</table>
</body>
</html>