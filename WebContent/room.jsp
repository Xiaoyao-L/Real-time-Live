<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf8"%>
 <%@ page import="java.io.*,java.util.*,com.imooc.bearlive.*,java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>房间信息</title>
 <link href="css/bootstrap.min.css" rel="stylesheet">
   
 <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>

 <script src="js/bootstrap.min.js"></script>
 <script>
        function requestFullScreen(element) {
    var de = document.querySelector(element) || document.documentElement;
    if (de.requestFullscreen) {
        de.requestFullscreen();
    } else if (de.mozRequestFullScreen) {
        de.mozRequestFullScreen();
    } else if (de.webkitRequestFullScreen) {
        de.webkitRequestFullScreen();
    }
}
//退出全屏
function exitFullscreen(element) {
    var de = document.querySelector(element) || document.documentElement;
    if (de.exitFullscreen) {
        de.exitFullscreen();
    } else if (de.mozCancelFullScreen) {
        de.mozCancelFullScreen();
    } else if (de.webkitCancelFullScreen) {
        de.webkitCancelFullScreen();
    }
}
        </script>
	
</head>
<body>
<%
Connection conn = null;
Statement stm = null;


	
conn = SqlManager.getConnection();
RoomInfo roomInfo = new RoomInfo();
try{stm = conn.createStatement();
String sqlstr="SELECT * FROM `roominfo` where `user_id`='"+request.getParameter("userid")+"'";
stm.execute(sqlstr);
ResultSet result = stm.getResultSet();

if (result != null && !result.wasNull()) {

	while (result.next()) {

		

		roomInfo.roomId = result.getInt("room_id");
		roomInfo.userId = result.getString("user_id");
		roomInfo.userName = result.getString("user_name");
		roomInfo.userAvatar = result.getString("user_avatar");
		roomInfo.liveCover = result.getString("live_cover");
		roomInfo.liveTitle = result.getString("live_title");
		roomInfo.watchNums = result.getInt("watcher_nums");
		
	}
	}
	if(roomInfo.roomId != 0)
	{%>
	<div class="container">
	<div class="row">
		<div class="col-md-8 col-sm-8 col-xs-6">
			<jsp:include page="danmu.jsp">
				<jsp:param value="<%=roomInfo.roomId %>" name="roomid"/>
			</jsp:include>
		</div>
		<div class="col-md-4 col-sm-4 col-xs-4">
			<jsp:include page="fullInfo.jsp">
				<jsp:param value="<%=roomInfo.roomId %>" name="roomid"/>
			</jsp:include>
			<br>
			<br>
			<div>
			<p>扫描二维码签到</p>
		<img  style="height:100px;width:100px" src="image/sign.png" />
		</div>
		<br>
		<br>
		<a href="vote.jsp" target="_blank">查看投票结果</a>
		<br>
		<br>
		<a href="redPacket.jsp" target="_blank">查看获奖情况</a>
		
		
		</div>
	</div>
	
</div>


<% }

else if(roomInfo.roomId == 0){
%>
<p>您当前没有正在直播的会议，请使用移动端创建会议后刷新。<P>


<%
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
</body>

</html>