/**
 * @(#)SmsSetupDAO.java     2011-01-22
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.dao.interfaces.sms;

import java.sql.SQLException;

import com.kingsoft.control.dao.DAO;
import com.kingsoft.dao.entity.sms.SmsSetup;

/**
 * 短信服务基础设置持久层服务接口类
 * 
 * 提供对该表的增、删、改、查功能实现方法.
 * 
 * @author kingsoft
 * 
 * @version 2011-01-22
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */

public interface SmsSetupDAO extends DAO {

	/**
	 * 将给定的对象保存到数据库中
	 * 
	 * @param smsSetup
	 *            实体对象
	 * @throws SQLException
	 */
	public void insert(SmsSetup smsSetup) throws SQLException;

	/**
	 * 根据主键获取对象
	 * 
	 * @param id
	 *            流水号
	 * @return SmsSetup 返回实体对象
	 * @throws SQLException
	 */
	public SmsSetup selectByPrimaryKeys(int id) throws SQLException;

	/**
	 * 根据主键更新对象
	 * 
	 * @param smsSetup
	 *            实体对象
	 * @throws SQLException
	 */
	public void updateByPrimaryKeys(SmsSetup smsSetup) throws SQLException;

}