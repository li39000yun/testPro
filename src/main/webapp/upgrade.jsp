<%@ page pageEncoding="UTF-8" %>
<!-- 升级日志 -->
<div id="p" class="easyui-panel" title="升级日志"
     style="background:#fafafa;"
     data-options="closable:true,collapsible:true,fit:true">
    <table class="easyui-datagrid" data-options="fitColumns:true,singleSelect:true,fit:true">
        <thead>
        <tr>
            <th data-options="field:'count',width:60">#</th>
            <th data-options="field:'time',width:100">时间</th>
            <th data-options="field:'content',width:250">内容</th>
            <th data-options="field:'operate',width:80">操作</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>1</td>
            <td>2016-11-01</td>
            <td>添加发送邮件工具</td>
            <td><a href="javascript:void(0)" onclick="addTab('发送邮件','test/tool/mail.jsp','mail')"
                   class="easyui-linkbutton" data-options="iconCls:'icon-add'">测试</a></td>
        </tr>
        <tr>
            <td>2</td>
            <td>2016-11-03</td>
            <td>添加freemarker模板测试功能</td>
            <td><a href="javascript:void(0)" onclick="addTab('freemarkerDemo','hello','freemarkerDemo')"
                   class="easyui-linkbutton" data-options="iconCls:'icon-add'">测试</a></td>
        </tr>
        <tr>
            <td>3</td>
            <td>2016-11-05</td>
            <td>添加ip查询工具</td>
            <td><a href="javascript:void(0)" onclick="addTab('ip查询','servlet/iptool','iptool')"
                   class="easyui-linkbutton" data-options="iconCls:'icon-add'">测试</a></td>
        </tr>
        </tbody>
    </table>
</div>
