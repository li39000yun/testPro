<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	if (id == null) {
		id = "";
	}
%>
<!DOCTYPE HTML>
<html>
<head>
<title>数据修改界面</title>
</head>
<%@ include file="/inc.jsp"%>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url;
			if ($(':input[name="data.id"]').val().length > 0) {
				url = lyq.contextPath + '/test/result.json';
			} else {
				url = lyq.contextPath + '/test/result.json';
			}
			$.post(url, lyq.serializeObject($('form')), function(result) {
				if (result.success) {
					$pjq.messager.alert('提示', result.msg, 'info');
					$grid.datagrid('load');
					$dialog.dialog('destroy');
				} else {
					$pjq.messager.alert('提示', result.msg, 'error');
				}
			}, 'json');
		}
	};
	$(function() {
		if ($(':input[name="data.id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(lyq.contextPath + '/test/datagrid_form_data.json', {
				id : $(':input[name="data.id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.name' : result.name,
					});
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
	});
</script>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>数据信息</legend>
			<table class="table" style="width: 100%">
				<tr>
					<th>编号</th>
					<td><input name="data.id" value="<%=id%>" readonly="readonly" /></td>
					<th>名称</th>
					<td><input name="data.name" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>