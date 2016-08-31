/**
 * @(#)ServiceCenterDAO.java     2011-02-25
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.nb.dao.implement.mysql.manager;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kingsoft.control.dao.AbstractDAO;
import com.kingsoft.control.database.PagerController;
import com.kingsoft.control.database.PreparedStatement;
import com.kingsoft.control.database.ResultSet;
import com.kingsoft.control.database.Statement;
import com.kingsoft.nb.dao.entity.manager.ServiceCenter;
import com.kingsoft.nb.dao.interfaces.manager.ServiceCenterDAO;

/**
 * 服务中心资料持久层服务实现类
 * 
 * @author kingsoft
 * 
 * @version 2011-02-25
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */

public class ServiceCenterDAOImpl extends AbstractDAO implements
		ServiceCenterDAO {

	/** 查询所有记录. */
	private static final String FS_SQL_SELECT = "SELECT * FROM global_service_center";

	/** 插入一条记录. */
	private static final String FS_SQL_INSERT = "INSERT INTO global_service_center(accredit_id,user_id,password,url)VALUES(?,?,?,?)";

	/** 根据主键修改某条记录. */
	private static final String FS_SQL_UPDATE = "UPDATE global_service_center SET accredit_id=?,user_id=?,password=?,url=? WHERE id=?";

	/** 根据主键查询某条记录. */
	private static final String FS_SQL_SELECT_BYKEY = "SELECT * FROM global_service_center WHERE id=?";

	public ServiceCenterDAOImpl() {

	}

	public void insert(ServiceCenter serviceCenter) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(FS_SQL_INSERT,
				PreparedStatement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, serviceCenter.getAccreditId());
		pstmt.setString(2, serviceCenter.getUserId());
		pstmt.setString(3, serviceCenter.getPassword());
		pstmt.setString(4, serviceCenter.getUrl());
		pstmt.execute();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			serviceCenter.setId(rs.getInt(1));
		}
		rs.close();
		pstmt.close();
	}

	public ServiceCenter selectByPrimaryKeys(int id) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(FS_SQL_SELECT_BYKEY);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		ServiceCenter[] rvalue = putEntity(rs);
		rs.close();
		pstmt.close();
		if (rvalue.length > 0) {
			return rvalue[0];
		}
		return null;

	}

	public void updateByPrimaryKeys(ServiceCenter serviceCenter)
			throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(FS_SQL_UPDATE);
		pstmt.setString(1, serviceCenter.getAccreditId());
		pstmt.setString(2, serviceCenter.getUserId());
		pstmt.setString(3, serviceCenter.getPassword());
		pstmt.setString(4, serviceCenter.getUrl());
		pstmt.setInt(5, serviceCenter.getId());
		pstmt.execute();
		pstmt.close();
	}

	public ServiceCenter[] select(ServiceCenter search) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(FS_SQL_SELECT);
		ServiceCenter[] rvalue = putEntity(rs);
		rs.close();
		stmt.close();
		return rvalue;
	}

	public ServiceCenter[] select(ServiceCenter search, PagerController pager)
			throws SQLException {
		StringBuffer sql = new StringBuffer(FS_SQL_SELECT);
		if (search != null) {
			// 添加查询条件
			sql.append(" WHERE 1=1");
		}
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executePagination(sql.toString(), pager);
		ServiceCenter[] rvalue = putEntity(rs);
		rs.close();
		stmt.close();
		return rvalue;
	}

	private static final ServiceCenter[] putEntity(ResultSet rs)
			throws SQLException {
		ServiceCenter[] rvalue = new ServiceCenter[0];
		ArrayList<ServiceCenter> list = null;
		ServiceCenter serviceCenter;
		while (rs.next()) {
			serviceCenter = new ServiceCenter();
			serviceCenter.setCount(rs.getCount());
			serviceCenter.setId(rs.getInt("id"));
			serviceCenter.setAccreditId(rs.getString("accredit_id"));
			serviceCenter.setUserId(rs.getString("user_id"));
			serviceCenter.setPassword(rs.getString("password"));
			serviceCenter.setUrl(rs.getString("url"));
			if (list == null) {
				list = new ArrayList<ServiceCenter>();
			}
			list.add(serviceCenter);
		}
		if (list != null) {
			rvalue = (ServiceCenter[]) list.toArray(rvalue);
		}
		return rvalue;
	}

}