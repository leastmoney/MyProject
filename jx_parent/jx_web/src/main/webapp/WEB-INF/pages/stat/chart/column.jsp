<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>amCharts examples</title>
        <link rel="stylesheet" href="${ctx}/js/amchart/style.css" type="text/css">
        <script src="${ctx}/js/amchart/amcharts/amcharts.js" type="text/javascript"></script>
        <script src="${ctx}/js/amchart/amcharts/serial.js" type="text/javascript"></script>

        <script>
            // note, each data item has "bullet" field.
            var columnChartData = ${chartData};


            AmCharts.ready(function () {
                // SERIAL CHART
                var chart = new AmCharts.AmSerialChart();
                chart.dataProvider = columnChartData;
                chart.categoryField = "name";
                chart.startDuration = 1;
                // sometimes we need to set margins manually
                // autoMargins should be set to false in order chart to use custom margin values
                chart.autoMargins = false;
                chart.marginRight = 0;
                chart.marginLeft = 0;
                chart.marginBottom = 0;
                chart.marginTop = 0;

                // AXES
                // category
                var categoryAxis = chart.categoryAxis;
                categoryAxis.inside = true;
                categoryAxis.axisAlpha = 0;
                categoryAxis.gridAlpha = 0;
                categoryAxis.tickLength = 0;

                // value
                var valueAxis = new AmCharts.ValueAxis();
                valueAxis.minimum = 0;
                valueAxis.axisAlpha = 0;
                valueAxis.maximum = 8000;
                valueAxis.dashLength = 4;
                chart.addValueAxis(valueAxis);

                // GRAPH
                var graph = new AmCharts.AmGraph();
                graph.valueField = "points";
                graph.customBulletField = "bullet"; // field of the bullet in data provider
                graph.bulletOffset = 16; // distance from the top of the column to the bullet
                graph.colorField = "color";
                graph.bulletSize = 34; // bullet image should be rectangle (width = height)
                graph.type = "column";
                graph.fillAlphas = 0.8;
                graph.cornerRadiusTop = 8;
                graph.lineAlpha = 0;
                graph.balloonText = "<span style='font-size:13px;'>[[category]]: <b>[[value]]</b></span>";
                chart.addGraph(graph);

                // WRITE
                chart.write("chartdiv");
            });
        </script>
    </head>

    <body>
        <div id="chartdiv" style="width: 520px; height: 400px;"></div>
    </body>

</html>