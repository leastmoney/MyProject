<%--
  Created by IntelliJ IDEA.
  User: leastmoney
  Date: 2017/7/14
  Time: 21:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
            'echarts/chart/pie' // 使用折线图就加载line模块，按需加载
        ],
        function (ec) {
            //获取值栈数据
            var data = ${chartData}
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('main'));
            option = {
                title : {
                    text: '工厂销售总额',
                    subtext: '真实可靠',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient : 'vertical',
                    x : 'left',
                    data:['光华','宏艺','精艺','天顺','文水志远','民鑫','南皮开发','华艺','会龙','平遥鸿艺','汇越','晶晨','平遥远江','升华']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {
                            show: true,
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                    max: 1548
                                }
                            }
                        },
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                series : data
            };

            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );
</script>
</body>
</html>

