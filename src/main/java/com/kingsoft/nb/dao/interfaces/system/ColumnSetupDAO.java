/**
 * @(#)ColumnSetupDAO.java     2013-07-11
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.nb.dao.interfaces.system;

import java.sql.SQLException;
import java.util.List;

import com.kingsoft.nb.business.vo.system.ColumnSetupVO;
import com.kingsoft.control.dao.DAO;
import com.kingsoft.nb.dao.entity.system.ColumnSetup;

/**
 * 客户自定义界面持久层服务接口类 

 * 提供对该表的增、删、改、查功能实现方法. 

 * @author kingsoft
 *
 * @version 2013-07-11
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */

public interface ColumnSetupDAO extends DAO{

	/**
	 * 将给定的对象保存到数据库中
	 * 
	 * @param columnSetup
	 *            实体对象
	 * @throws SQLException
	 */
	public void insert(ColumnSetup columnSetup) throws SQLException;

	/**
	 * 删除某公司的自定义列
	 * 
	 * @param accreditId
	 *            授权码 *
	 * @throws SQLException
	 */
	public void delete(String accreditId) throws SQLException;

	/**
	 * 获取某授权公司的自定义界面设置信息
	 * 
	 * @throws SQLException
	 */
	public List<ColumnSetupVO> selectByUnion(String accreditId)
			throws SQLException;

	/**
	 * 获取某授权公司的自定义界面设置信息
	 * 
	 * @throws SQLException
	 */
	public List<ColumnSetup> selectByAccreditId(String accreditId)
			throws SQLException;

	/**
	 * 获取用户自定义界面初始化信息
	 * 
	 * @throws SQLException
	 */
	public void initColumnSetup() throws SQLException;
}