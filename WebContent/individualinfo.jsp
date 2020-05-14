<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,com.imooc.bearlive.*"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
   	
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>超级会互动/个人中心</title>

 <!--   <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en"> --> 
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link Type="text/css" href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.cyan-light_blue.min.css">
    <link rel="stylesheet" href="assets/styles.css">
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
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
    <div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
      <header class="demo-header mdl-layout__header mdl-color--grey-100 mdl-color-text--grey-600">
        <div class="mdl-layout__header-row">
          <span class="mdl-layout-title">主页</span>
          <div class="mdl-layout-spacer"></div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
            <label class="mdl-button mdl-js-button mdl-button--icon" for="search">
              <i class="material-icons">search</i>
            </label>
            <div class="mdl-textfield__expandable-holder">
              <input class="mdl-textfield__input" type="text" id="search">
              <label class="mdl-textfield__label" for="search">Enter your query...</label>
            </div>
          </div>
          <button class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon" id="hdrbtn">
            <i class="material-icons">more_vert</i>
          </button>
          <ul class="mdl-menu mdl-js-menu mdl-js-ripple-effect mdl-menu--bottom-right" for="hdrbtn">
            <li class="mdl-menu__item">关于</li>
            <li class="mdl-menu__item">联系我们</li>
            <li class="mdl-menu__item">版权信息</li>
          </ul>
        </div>
      </header>
      
      <%
      FullUserInfo user=(FullUserInfo)session.getAttribute("user");
      %>
      <div class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50">
        <header class="demo-drawer-header">
          <img src="image/ava.jpg" class="demo-avatar">
          <div class="demo-avatar-dropdown">
            <span><%=user.getUserId()%></span>
          </div>
        </header>
        <nav class="demo-navigation mdl-navigation mdl-color--blue-grey-800">
          <a class="mdl-navigation__link" href="#individual">个人中心</a>
          <a class="mdl-navigation__link" data-toggle="tab" href="#meetinglist">会议列表</a>
          <a class="mdl-navigation__link" data-toggle="tab" href="#userrecord">会议记录</a>
          <a class="mdl-navigation__link" data-toggle="tab" href="#room">当前会议管理</a>
          <a class="mdl-navigation__link" href="question.html" target="_blank">帮助</a>
          <a class="mdl-navigation__link" onclick="requestFullScreen('#content')">全屏</a>
          <a class="mdl-navigation__link" href="index.html">退出</a>   
        </nav>
      </div>
   	<div class="mdl-layout__content" id="content">
        <div class="mdl-grid demo-content" >
        	<div id="myTabContent" class="tab-content">
        		<div class="tab-pane fade in active" id="meetinglist">
        			<jsp:include page="meettingList.jsp"></jsp:include>
				</div>
			
				<div class="tab-pane fade" id="userrecord">
        		<jsp:include page="userRecord.jsp">
        		<jsp:param value="<%=user.getUserId() %>" name="userid"/>
        		</jsp:include>
        		</div>
        		
        		<div class="tab-pane fade" id="room">
        		<jsp:include page="room.jsp">
        		<jsp:param value="<%=user.getUserId() %>" name="userid"/>
        		</jsp:include>
        		</div>
        </div>
     </div>
 </div>
  
  
  <!--     <section class="mdl-layout__content"  id="userrecord" style="height: 100%;background-color:#ffffff">
        <div class="mdl-grid demo-content" >
        <div>
        <jsp:include page="userRecord.jsp">
        <jsp:param value="" name="userid"/>
        </jsp:include>
        </div>
        </div>
     </section>
     -->
    

    
    
    
      </div>
      

  </body>
</html>
