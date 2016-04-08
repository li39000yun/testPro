<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>datagrid</title>
</head>
<%@ include file="/inc.jsp"%>
<script type="text/javascript">
	var grid;
	$(function() {
		grid = $('#dg').datagrid({
			url : 'datagrid_data.json',
			pagination : true,
			columns : [ [ {
				field : 'code',
				title : 'Code',
				width : 100
			}, {
				field : 'name',
				title : 'Name',
				width : 100
			}, {
				field : 'addr',
				title : 'addr',
				width : 100,
				align : 'right'
			} ] ],
			fit : true,
			toolbar : [ {
				iconCls : 'icon-add',
				text : '添加',
				handler : addFun
			}, '-', {
				iconCls : 'icon-edit',
				text : '修改',
				handler : function() {
					var selects = grid.datagrid('getSelections');
					console.info(selects);
					var len = selects.length;
					if (len != 1) {
						parent.$.messager.alert('提示', '请选择一条记录!');
					} else {
						editFun(selects[0].code);
					}
				}
			}, '-', {
				iconCls : 'icon-remove',
				text : '删除',
				handler : removeFun
			}, '-', {
				iconCls : 'icon-help',
				handler : function() {
					alert('help')
				}
			} ],
			onDblClickRow : function(rowIndex, rowData) {
				editFun(rowData.code);
			}
		});
	});

	// 添加记录
	addFun = function() {
		var dialog = parent.lyq.modalDialog({
			title : '添加数据信息',
			url : lyq.contextPath + '/test/datagridForm.jsp',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	}

	// 修改记录
	editFun = function(id) {
		var dialog = parent.lyq.modalDialog({
			title : '编辑数据信息',
			url : lyq.contextPath + '/test/datagridForm.jsp?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	}

	// 删除记录
	function removeFun() {
		var selects = grid.datagrid('getSelections');
		var len = selects.length;
		if (len == 0) {
			parent.$.messager.alert('提示', '请先选择记录!');
		} else {
			parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
				if (r) {
					var index;
					for ( var i = 0; i < len; i++) {
						index = grid.datagrid('getRowIndex', selects[i]);
						grid.datagrid('deleteRow', index);
					}
					grid.datagrid('unselectAll');// 取消选择所有页面的行。
					$.messager.show({
						title : '提示',
						msg : '操作成功!',
						timeout : 5000,
						showType : 'slide'
					});
				}
			});
		}
	}
</script>
<body>
	<div id="cc" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',title:'搜索条件',split:true" style="height:100px;">
			<form id="searchForm"></form>
			<table class="table">
				<tr>
					<th>Email:</th>
					<td><input id="email" class="easyui-validatebox" data-options="validType:'email',missingMessage:'请输入Email'" /></td>
					<th>开始时间</th>
					<td><input id="beginTime" type="text" class="easyui-datebox" data-options="required:true" /></td>
					<td><a id="btnSearch" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="if ($('#searchForm').form('validate')) {grid.datagrid('load',lyq.serializeObject($('#searchForm')));}">查询</a></td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',title:'数据表格'" style="background:#eee;">
			<table id="dg"></table>
		</div>
	</div>
</body>
</html>
