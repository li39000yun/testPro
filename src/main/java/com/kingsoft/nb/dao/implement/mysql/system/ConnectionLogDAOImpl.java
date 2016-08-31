/**
 * @(#)ConnectionLogDAO.java     2016-08-25
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.nb.dao.implement.mysql.system;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kingsoft.control.dao.AbstractDAO;
import com.kingsoft.control.database.PagerController;
import com.kingsoft.control.database.PreparedStatement;
import com.kingsoft.control.database.ResultSet;
import com.kingsoft.control.database.Statement;
import com.kingsoft.nb.dao.entity.system.ConnectionLog;
import com.kingsoft.nb.dao.interfaces.system.ConnectionLogDAO;

/**
 * 接口操作日志持久层服务实现类
 *
 * @author kingsoft
 *
 * @version 2016-08-25
 * 
 * @since JDK 1.6.0_43
 * 
 */

public class ConnectionLogDAOImpl extends AbstractDAO implements ConnectionLogDAO {

	/** 查询所有记录. */
	private static final String FS_SQL_SELECT = "SELECT * FROM sys_connection_log";

	/** 插入一条记录. */
	private static final String FS_SQL_INSERT = "INSERT INTO sys_connection_log(accredit_id,operation_man,operation_time,service,parameter,result,remark)VALUES(?,?,?,?,?,?,?)";

	/** 根据主键修改某条记录. */
	private static final String FS_SQL_UPDATE = "UPDATE sys_connection_log SET accredit_id=?,operation_man=?,operation_time=?,service=?,parameter=?,result=?,remark=? WHERE id=?";

	/** 根据主键查询某条记录. */
	private static final String FS_SQL_SELECT_BYKEY = "SELECT * FROM sys_connection_log WHERE id=?";

	/** 删除某时间前的记录. */
	private static final String FS_SQL_DELETE = "DELETE FROM sys_connection_log WHERE sys_connection_log.operation_time <=?";

	public ConnectionLogDAOImpl() {

	}

	public void insert(ConnectionLog connectionLog) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(FS_SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, connectionLog.getAccreditId());
		pstmt.setString(2, connectionLog.getOperationMan());
		pstmt.setString(3, connectionLog.getOperationTime());
		pstmt.setString(4, connectionLog.getService());
		pstmt.setString(5, connectionLog.getParameter());
		pstmt.setString(6, connectionLog.getResult());
		pstmt.setString(7, connectionLog.getRemark());
		pstmt.execute();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			connectionLog.setId(rs.getInt(1));
		}
		rs.close();
		pstmt.close();
	}

	public ConnectionLog selectByPrimaryKeys(int id) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(FS_SQL_SELECT_BYKEY);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		ConnectionLog[] rvalue = putEntity(rs);
		rs.close();
		pstmt.close();
		if (rvalue.length > 0) {
			return rvalue[0];
		}
		return null;

	}

	public void updateByPrimaryKeys(ConnectionLog connectionLog) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(FS_SQL_UPDATE);
		pstmt.setString(1, connectionLog.getAccreditId());
		pstmt.setString(2, connectionLog.getOperationMan());
		pstmt.setString(3, connectionLog.getOperationTime());
		pstmt.setString(4, connectionLog.getService());
		pstmt.setString(5, connectionLog.getParameter());
		pstmt.setString(6, connectionLog.getResult());
		pstmt.setString(7, connectionLog.getRemark());
		pstmt.setInt(8, connectionLog.getId());
		pstmt.execute();
		pstmt.close();
	}

	public ConnectionLog[] select() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(FS_SQL_SELECT);
		ConnectionLog[] rvalue = putEntity(rs);
		rs.close();
		stmt.close();
		return rvalue;
	}

	public ConnectionLog[] select(ConnectionLog search, PagerController pager) throws SQLException {
		StringBuffer sql = new StringBuffer(FS_SQL_SELECT);
		if (search != null) {
			// 添加查询条件
			sql.append(" WHERE 1=1");
		}
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executePagination(sql.toString(), pager);
		ConnectionLog[] rvalue = putEntity(rs);
		rs.close();
		stmt.close();
		return rvalue;
	}

	private static final ConnectionLog[] putEntity(ResultSet rs) throws SQLException {
		ConnectionLog[] rvalue = new ConnectionLog[0];
		ArrayList<ConnectionLog> list = null;
		ConnectionLog connectionLog;
		while (rs.next()) {
			connectionLog = new ConnectionLog();
			connectionLog.setCount(rs.getCount());
			connectionLog.setId(rs.getInt("id"));
			connectionLog.setAccreditId(rs.getString("accredit_id"));
			connectionLog.setOperationMan(rs.getString("operation_man"));
			connectionLog.setOperationTime(rs.getString("operation_time"));
			connectionLog.setService(rs.getString("service"));
			connectionLog.setParameter(rs.getString("parameter"));
			connectionLog.setResult(rs.getString("result"));
			connectionLog.setRemark(rs.getString("remark"));
			if (list == null) {
				list = new ArrayList<ConnectionLog>();
			}
			list.add(connectionLog);
		}
		if (list != null) {
			rvalue = (ConnectionLog[]) list.toArray(rvalue);
		}
		return rvalue;
	}

	@Override
	public void deleteBefore(String date) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(FS_SQL_DELETE);
		pstmt.setString(1, date);
		pstmt.execute();
		pstmt.close();
	}

}