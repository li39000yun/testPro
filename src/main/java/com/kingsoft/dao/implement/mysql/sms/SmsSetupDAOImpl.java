/**
 * @(#)SmsSetupDAO.java     2011-01-22
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.dao.implement.mysql.sms;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kingsoft.control.dao.AbstractDAO;
import com.kingsoft.control.database.PreparedStatement;
import com.kingsoft.control.database.ResultSet;
import com.kingsoft.dao.entity.sms.SmsSetup;
import com.kingsoft.dao.interfaces.sms.SmsSetupDAO;

/**
 * 短信服务基础设置持久层服务实现类
 * 
 * @author kingsoft
 * 
 * @version 2011-01-22
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */

public class SmsSetupDAOImpl extends AbstractDAO implements SmsSetupDAO {

	/** 插入一条记录. */
	private static final String FS_SQL_INSERT = "INSERT INTO sms_setup(telephone,db_name,db_user,db_pass,user_id,user_pass,service_id,fee_type,fee_code,url,actflag)VALUES(?,?,?,?,?,?,?,?,?,?,?)";

	/** 根据主键修改某条记录. */
	private static final String FS_SQL_UPDATE = "UPDATE sms_setup SET telephone=?,db_name=?,db_user=?,db_pass=?,user_id=?,user_pass=?,service_id=?,fee_type=?,fee_code=?,url=?,actflag=? WHERE id=?";

	/** 根据主键查询某条记录. */
	private static final String FS_SQL_SELECT_BYKEY = "SELECT * FROM sms_setup WHERE id=?";

	public SmsSetupDAOImpl() {

	}

	public void insert(SmsSetup smsSetup) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(FS_SQL_INSERT,
				PreparedStatement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, smsSetup.getTelephone());
		pstmt.setString(2, smsSetup.getDbName());
		pstmt.setString(3, smsSetup.getDbUser());
		pstmt.setString(4, smsSetup.getDbPass());
		pstmt.setString(5, smsSetup.getUserId());
		pstmt.setString(6, smsSetup.getUserPass());
		pstmt.setString(7, smsSetup.getServiceId());
		pstmt.setString(8, smsSetup.getFeeType());
		pstmt.setString(9, smsSetup.getFeeCode());
		pstmt.setString(10, smsSetup.getUrl());
		pstmt.setInt(11, smsSetup.getActflag());
		pstmt.execute();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			smsSetup.setId(rs.getInt(1));
		}
		rs.close();
		pstmt.close();
	}

	public SmsSetup selectByPrimaryKeys(int id) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(FS_SQL_SELECT_BYKEY);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		SmsSetup[] rvalue = putEntity(rs);
		rs.close();
		pstmt.close();
		if (rvalue.length > 0) {
			return rvalue[0];
		}
		return null;

	}

	public void updateByPrimaryKeys(SmsSetup smsSetup) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(FS_SQL_UPDATE);
		pstmt.setString(1, smsSetup.getTelephone());
		pstmt.setString(2, smsSetup.getDbName());
		pstmt.setString(3, smsSetup.getDbUser());
		pstmt.setString(4, smsSetup.getDbPass());
		pstmt.setString(5, smsSetup.getUserId());
		pstmt.setString(6, smsSetup.getUserPass());
		pstmt.setString(7, smsSetup.getServiceId());
		pstmt.setString(8, smsSetup.getFeeType());
		pstmt.setString(9, smsSetup.getFeeCode());
		pstmt.setString(10, smsSetup.getUrl());
		pstmt.setInt(11, smsSetup.getActflag());
		pstmt.setInt(12, smsSetup.getId());
		pstmt.execute();
		pstmt.close();
	}

	private static final SmsSetup[] putEntity(ResultSet rs) throws SQLException {
		SmsSetup[] rvalue = new SmsSetup[0];
		ArrayList<SmsSetup> list = null;
		SmsSetup smsSetup;
		while (rs.next()) {
			smsSetup = new SmsSetup();
			smsSetup.setCount(rs.getCount());
			smsSetup.setId(rs.getInt("id"));
			smsSetup.setTelephone(rs.getString("telephone"));
			smsSetup.setDbName(rs.getString("db_name"));
			smsSetup.setDbUser(rs.getString("db_user"));
			smsSetup.setDbPass(rs.getString("db_pass"));
			smsSetup.setUserId(rs.getString("user_id"));
			smsSetup.setUserPass(rs.getString("user_pass"));
			smsSetup.setServiceId(rs.getString("service_id"));
			smsSetup.setFeeType(rs.getString("fee_type"));
			smsSetup.setFeeCode(rs.getString("fee_code"));
			smsSetup.setUrl(rs.getString("url"));
			smsSetup.setActflag(rs.getInt("actflag"));
			if (list == null) {
				list = new ArrayList<SmsSetup>();
			}
			list.add(smsSetup);
		}
		if (list != null) {
			rvalue = (SmsSetup[]) list.toArray(rvalue);
		}
		return rvalue;
	}

}