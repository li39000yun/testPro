/**
 * @(#)ConnectionLogDAO.java     2016-08-25
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.nb.dao.interfaces.system;

import java.sql.SQLException;

import com.kingsoft.control.dao.DAO;
import com.kingsoft.control.database.PagerController;
import com.kingsoft.nb.dao.entity.system.ConnectionLog;

/**
 * 接口操作日志持久层服务接口类
 * 
 * 提供对该表的增、删、改、查功能实现方法.
 * 
 * @author kingsoft
 *
 * @version 2016-08-25
 * 
 * @since JDK 1.6.0_43
 * 
 */

public interface ConnectionLogDAO extends DAO {

	/**
	 * 将给定的对象保存到数据库中
	 * 
	 * @param connectionLog
	 *            实体对象
	 * @throws SQLException
	 */
	public void insert(ConnectionLog connectionLog) throws SQLException;

	/**
	 * 根据主键获取对象
	 * 
	 * @param id
	 *            接口日志编号
	 * @return ConnectionLog 返回实体对象
	 * @throws SQLException
	 */
	public ConnectionLog selectByPrimaryKeys(int id) throws SQLException;

	/**
	 * 根据主键更新对象
	 *
	 * @param connectionLog
	 *            实体对象
	 * @throws SQLException
	 */
	public void updateByPrimaryKeys(ConnectionLog connectionLog) throws SQLException;

	/**
	 * 查询所有记录
	 * 
	 * @return ConnectionLog[] 实体对象数组
	 * @throws SQLException
	 */
	public ConnectionLog[] select() throws SQLException;

	/**
	 * 根据给定的查询参数和分页参数做分页查询
	 *
	 * @param search
	 *            查询条件对象
	 * @param pager
	 *            分页信息
	 * @return ConnectionLog[] 实体对象数组
	 * @throws SQLException
	 */
	public ConnectionLog[] select(ConnectionLog search, PagerController pager) throws SQLException;

	/**
	 * 删除某时间之前的记录
	 * 
	 * @param date
	 *            时间
	 * @throws SQLException
	 */
	public void deleteBefore(String date) throws SQLException;

}