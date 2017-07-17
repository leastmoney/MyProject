<%--
  Created by IntelliJ IDEA.
  User: leastmoney
  Date: 2017/7/14
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../base.jsp" %>
<html>
<head>
    <title>Insert title here</title>
</head>
<body>
<div id="main" style="height:400px"></div>
<script src="${ctx}/js/echarts/echarts.js"></script>
<script type="text/javascript">
    // 路径配置
    require.config({
        paths: {
            echarts: '${ctx}/js/echarts'
        }
    });
    // 使用
    require(
        [
            'echarts',
            'echarts/chart/line' // 使用折线图就加载line模块，按需加载
        ],
        function (ec) {
            //获取值栈数据
            var data = ${chartData}
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('main'));
            var option = {
                title : {
                    text: '在线人数统计',
                    subtext: 'Source:www.itcast.cn'
                },
                tooltip : {
                    trigger: 'axis'
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : ['00', '01', '02', '03', '04', '05',
                            '06', '07', '08', '09', '10', '11',
                            '12', '13', '14', '15', '16', '17',
                            '18', '19', '20', '21', '22', '23']
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                    }
                ],
                series : data
            };
            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );
</script>
</body>
</html>

</body>
</html>
