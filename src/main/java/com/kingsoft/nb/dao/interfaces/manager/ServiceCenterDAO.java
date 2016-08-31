/**
 * @(#)ServiceCenterDAO.java     2011-02-25
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.nb.dao.interfaces.manager;

import java.sql.SQLException;

import com.kingsoft.control.dao.DAO;
import com.kingsoft.control.database.PagerController;
import com.kingsoft.nb.dao.entity.manager.ServiceCenter;

/**
 * 服务中心资料持久层服务接口类
 * 
 * 提供对该表的增、删、改、查功能实现方法.
 * 
 * @author kingsoft
 * 
 * @version 2011-02-25
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */

public interface ServiceCenterDAO extends DAO {

	/**
	 * 将给定的对象保存到数据库中
	 * 
	 * @param serviceCenter
	 *            实体对象
	 * @throws SQLException
	 */
	public void insert(ServiceCenter serviceCenter) throws SQLException;

	/**
	 * 根据主键获取对象
	 * 
	 * @param id
	 *            流水号
	 * @return ServiceCenter 返回实体对象
	 * @throws SQLException
	 */
	public ServiceCenter selectByPrimaryKeys(int id) throws SQLException;

	/**
	 * 根据主键更新对象
	 * 
	 * @param serviceCenter
	 *            实体对象
	 * @throws SQLException
	 */
	public void updateByPrimaryKeys(ServiceCenter serviceCenter)
			throws SQLException;

	/**
	 * 查询所有记录
	 * 
	 * @return ServiceCenter[] 实体对象数组
	 * @throws SQLException
	 */
	public ServiceCenter[] select(ServiceCenter search) throws SQLException;

	/**
	 * 根据给定的查询参数和分页参数做分页查询
	 * 
	 * @param search
	 *            查询条件对象
	 * @param pager
	 *            分页信息
	 * @return ServiceCenter[] 实体对象数组
	 * @throws SQLException
	 */
	public ServiceCenter[] select(ServiceCenter search, PagerController pager)
			throws SQLException;

}