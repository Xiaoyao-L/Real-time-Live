<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>ECharts</title>
    
    <!-- 引入 echarts.js -->
    <script type="text/javascript" src="js/echarts.js"></script>
    <!-- 引入jquery.js -->
    <script type="text/javascript" src="js/jquery-3.3.1.js"></script>
</head>
<%String roomid = request.getParameter("roomid"); %>
<body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div>

    </div>
    <div id="main" style="width: 500px;height:300px;"></div>
    
    <script type="text/javascript">
        
        var myChart = echarts.init(document.getElementById('main'));
         // 显示标题，图例和空的坐标轴
         myChart.setOption({
             title: {
                 text: '投票结果'
             },
             tooltip: {
            	 trigger: 'item',
                 formatter: "{a} <br/>{b} : {c} ({d}%)"
             },
             visualMap: {
                 show: false,
                 min: 80,
                 max: 600,
                 inRange: {
                     colorLightness: [0, 1]
                 }
             },
             legend: {
                 data:['人数']
             },
             
             series: [{
                 name: '人数',
                 type: 'pie',
                 data: []
             }]
         });
         
        // myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
         
         var names=[];    //类别数组（实际用来盛放X轴坐标值）
         var nums=[];    //销量数组（实际用来盛放Y坐标值）
         
         $.ajax({
         type : "post",
         async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
         url : "voteServlet",    //请求发送到TestServlet处
         data : {roomid:$("roomid").text()},
         dataType : "json",        //返回数据形式为json
         success : function(result) {
             //请求成功时执行该函数内容，result即为服务器返回的json对象
             if (result) {
                    for(var i=0;i<result.length;i++){       
                       names.push(result[i].item);    //挨个取出类别并填入类别数组
                     }
                    for(var i=0;i<result.length;i++){       
                        nums.push(result[i].num);    //挨个取出销量并填入销量数组
                      }
                    for(var i=0;i<result.length;i++){       
                        da.push(result[i]);    //挨个取出销量并填入销量数组
                      }
                    myChart.hideLoading();    //隐藏加载动画
                    myChart.setOption({        //加载数据图表
                        series: [{
                            // 根据名字对应到相应的系列
                            name: '选项',
                            type:'pie',
                            radius : '55%',
                            center: ['50%', '50%'],
                            data: [ {value:335,name:'直接访问'},
                                {value:310, name:'邮件营销'},
                                {value:274, name:'联盟广告'},
                                {value:235, name:'视频广告'},
                                {value:400, name:'搜索引擎'} ].sort(function (a, b) { return a.value - b.value; }),
                            roseType: 'radius',
                            label: {
                                normal: {
                                    textStyle: {
                                        color: '#2c343c'
                                    }
                                }
                            },
                            labelLine: {
                                normal: {
                                    lineStyle: {
                                        color: 'rgba(#2c343c)'
                                    },
                                    smooth: 0.2,
                                    length: 10,
                                    length2: 20
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: '#c23531',
                                    shadowBlur: 200,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            },
                            
                            animationType: 'scale',
                            animationEasing: 'elasticOut',
                            animationDelay: function (idx) {
                                return Math.random() * 200;
                            }
                        }]
                    });
                    
             }
         
        },
         error : function(errorMsg) {
             //请求失败时执行该函数
         alert("图表请求数据失败!");
       //  myChart.hideLoading();
         }
    })

         
    </script>
    
</body>
</html> 