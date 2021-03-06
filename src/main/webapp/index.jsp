<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>TestPro</title>
</head>
<%@ include file="/inc.jsp"%>
<script type="text/javascript" src="test/mytime.js?version="+<%=version %>></script>
<script type="text/javascript">
	$(function() {
		showDT();// 显示时间
		$('#menu').tree({
			lines : true,
			data : [ {
				text : '例子',
				state : 'closed',
				children : [ {
					text : '时间',
					id : 'time',
					url : 'test/time.jsp'
				}, {
					text : '数据表格',
					id : 'datagrid',
					url : 'test/datagrid.jsp'
				}, {
					text : 'freemarkerDemo',
					id : 'freemarkerDemo',
					url : 'hello'
				}, {
					text : 'ueditor',
					id : 'ueditor',
					url : 'test/tool/ueditor.jsp'
				}, {
					text : 'umditor',
					id : 'umditor',
					url : 'test/tool/umditor.jsp'
				} ]
			}, {
				text : '百度',
				id : 'baidu',
				url : 'http://www.baidu.com'
			}, {
                text : 'jsonUtil',
                id : 'jsonUtil',
                url : 'test/json/jsonUtil.jsp'
            }, {
				text : '发送邮件',
				id : 'mail',
				url : 'test/tool/mail.jsp'
			}, {
                text : 'ip查询',
                id : 'iptool',
                url : 'servlet/iptool'
            } ],
			onClick : function(node) {
				if ($('#menu').tree('isLeaf', node.target)) {
					addTab(node.text, node.url, node.id);
				}
			},
			onDblClick : function(node) {
				$('#menu').tree(node.state == 'open' ? 'collapse' : 'expand', node.target);
			}
		});
	});

	function addTab(text, html, id, iconCls) {
		if ($('#tabs').tabs('exists', text)) {
			$('#tabs').tabs('select', text);
		} else {
			$('#tabs').tabs('add', {
				title : text,
				content : '<iframe frameBorder=0 width="100%" height="100%" src="' + html + '"></iframe>',
				id : id,
				closable : true,
				iconCls : iconCls,
				tools : [ {
					iconCls : 'icon-mini-refresh',
					handler : function() {
						var tab = $('#tabs').tabs('getSelected'); // get selected panel
						$('#tabs').tabs('update', {
							tab : tab,
							options : {
								text : tab.text,
							}
						});
					}
				} ]
			});
		}
	}

	var days = new Array("日", "一", "二", "三", "四", "五", "六");
	function showDT() {// 显示时间
		var currentDT = new Date();
		var y, m, date, day, hs, ms, ss, theDateStr;
		y = currentDT.getFullYear(); //四位整数表示的年份
		m = currentDT.getMonth() + 1; //月
		date = currentDT.getDate(); //日
		day = currentDT.getDay(); //星期
		hs = currentDT.getHours(); //时
		ms = currentDT.getMinutes(); //分
		ss = currentDT.getSeconds(); //秒
		theDateStr = y + "年" + m + "月" + date + "日 星期" + days[day] + " " + hs + ":" + ms + ":" + ss;
		$('#theClock').text(theDateStr);
		// setTimeout 在执行时,是在载入后延迟指定时间后,去执行一次表达式,仅执行一次
		window.setTimeout(showDT, 1000);
	}
</script>
<body class="easyui-layout">
	<div data-options="region:'north',split:true" style="height:100px;">
		<p id="theClock" align="right"></p>
		<util:timer></util:timer>
	</div>
	<div data-options="region:'south',title:'South Title',split:true,collapsed:true" style="height:100px;"></div>
	<div data-options="region:'east',iconCls:'icon-reload',title:'East',split:true,collapsed:true" style="width:100px;"></div>
	<div data-options="region:'west',title:'菜单',split:true" style="width:150px;">
		<div id="menu"></div>
	</div>
	<div data-options="region:'center'" style="background:#eee;">
		<div id="tabs" class="easyui-tabs" data-options="fit:true">
			<div title="我的桌面" data-options="iconCls:'icon-man'">
				<h3>欢迎！</h3><br />
				<table>
					<tr>
						<td><label>markDay:</label>
						</td>
						<td><p id="markDay">2015-05-22</p>
						</td>
					</tr>
				</table>
				<hr/>
				<%@ include file="/upgrade.jsp"%>
			</div>
		</div>
	</div>
</body>
</html>
