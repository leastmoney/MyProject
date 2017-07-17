<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
<script type="text/javascript" src="${ctx }/js/jquery-1.8.3.js"></script>
<link rel="stylesheet" href="${ctx }/components/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="${ctx }/components/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx }/components/zTree/js/jquery.ztree.excheck-3.5.min.js"></script>
	 <script type="text/javascript">
			var zTreeObj;
			var setting = {
				check : {
					enable : true
				},
				data : {
					simpleData : {
						enable : true
					}
				}
			};
			$(document).ready(function(){
				$.ajax({
				url:"${ctx}/sysadmin/roleAction_roleModuleJsonStr?id=${id}",
				type:"get",
				dataType:"text",
				success:initZtree
				});
			});
			
			function initZtree(data){
				//zTreeObj=$("#jxTree");
				var zNodes=eval("("+data+")");
				zTreeObj=$.fn.zTree.init($("#jxTree"),setting,zNodes);
				zTreeObj.expandAll(true);
			}
			
			function submitCheckedNodes(){
				var nodes=zTreeObj.getCheckedNodes(true);
				var str="";
				for (var i = 0; i < nodes.length; i++) {
					str+=nodes[i].id+",";
				}
				str=str.substring(0,str.length-1);
				$("#moduleIds").val(str);
			}
	</script>
</head>

<body>
<form name="icform" method="post">
	<input type="hidden" name="id" value="${id}"/>
	<input type="hidden" id="moduleIds" name="moduleIds" value="" />
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="submitCheckedNodes();formSubmit('roleAction_module','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="formSubmit('roleAction_list','_self');this.blur();">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
    配置 [${name}] 角色的模块  
<div>  
	<ul id="jxTree" class="ztree"></ul>  
</div>

 
 
</form>
</body>
</html>

