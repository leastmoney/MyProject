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
           var chart;
           var graph;

           var chartData =${chartData};


           AmCharts.ready(function () {
               // SERIAL CHART
               chart = new AmCharts.AmSerialChart();

               chart.marginRight = 0;
               chart.marginTop = 0;
               chart.dataProvider = chartData;
               chart.categoryField = "hour";
               //chart.dataDateFormat = "YYYY";
               chart.color = "#FFFFFF";

               chart.backgroundImage = "/js/amchart/images/bgSky.jpg";

               // AXES
               // Category
               var categoryAxis = chart.categoryAxis;
               categoryAxis.parseDates = false; // as our data is date-based, we set parseDates to true
               categoryAxis.minPeriod = "YYYY"; // our data is yearly, so we set minPeriod to YYYY
               categoryAxis.gridCount = 20;
               categoryAxis.autoGridCount = false;
               categoryAxis.axisAlpha = 0.3;
               categoryAxis.axisColor = "#FFFFFF";
               categoryAxis.gridAlpha = 0.05;

               // VALUE
               var valueAxis = new AmCharts.ValueAxis();
               valueAxis.gridAlpha = 0;
               valueAxis.axisAlpha = 0.3;
               valueAxis.axisColor = "#FFFFFF";
               valueAxis.showLastLabel = false;
               chart.addValueAxis(valueAxis);

               // GRAPH
               graph = new AmCharts.AmGraph();
               graph.type = "step"; // this line makes step graph
               graph.noStepRisers = true;
               graph.valueField = "value";
               graph.lineColor = "#FFFFFF";
               graph.lineThickness = 1;
               graph.balloonText = "[[category]]<br><b><span style='font-size:14px;'>[[value]]</span></b>";
               chart.addGraph(graph);

               // CURSOR
               var chartCursor = new AmCharts.ChartCursor();
               chartCursor.cursorAlpha = 0;
               chartCursor.cursorPosition = "mouse";
              // chartCursor.categoryBalloonDateFormat = "YYYY";
               chart.addChartCursor(chartCursor);

               chart.creditsPosition = "top-right";

               // WRITE
               chart.write("chartdiv");
           });
		</script>
	</head>

    <body>
        <div id="chartdiv" style="width: 800px; height: 500px;"></div>
    </body>

</html>