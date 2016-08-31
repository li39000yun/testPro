/**
 * @(#)GlobalVariablesDAO.java     2010-05-21
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.nb.dao.interfaces.system;

import java.sql.SQLException; 

import com.kingsoft.control.dao.DAO;
import com.kingsoft.control.database.PagerController;
import com.kingsoft.nb.dao.entity.system.GlobalVariables;

/**
 * 系统全局变量持久层服务接口类
 * 
 * 提供对该表的增、删、改、查功能实现方法.
 * 
 * @author kingsoft
 * 
 * @version 2010-05-21
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */

public interface GlobalVariablesDAO extends DAO {

	/**
	 * 根据主键获取对象
	 * 
	 * @param id
	 *            变量标识
	 * @return GlobalVariables 返回实体对象
	 * @throws SQLException
	 */
	public GlobalVariables selectByPrimaryKeys(String id) throws SQLException;

	/**
	 * 根据主键更新对象
	 * 
	 * @param globalVariables
	 *            实体对象
	 * @throws SQLException
	 */
	public void updateByPrimaryKeys(GlobalVariables globalVariables)
			throws SQLException;

	/**
	 * 根据给定的查询参数和分页参数做分页查询
	 * 
	 * @param search
	 *            查询参数对象
	 * @param pager
	 *            分页信息
	 * @return GlobalVariables[] 实体对象数组
	 * @throws SQLException
	 */
	public GlobalVariables[] select(GlobalVariables search,
			PagerController pager) throws SQLException;

	/**
	 * 根据类型获取全局变量属性
	 * 
	 * @param type
	 *            属性类型
	 * @return GlobalVariables[] 变量对象数组
	 * @throws SQLException
	 */
	public GlobalVariables[] selectByType(String type) throws SQLException;

	/**
	 * 更改全局变量状态
	 * 
	 * @param id
	 *            变量标识
	 * @param actflag
	 *            状态
	 * @throws SQLException
	 */
	public void updateActflag(String id, int actflag) throws SQLException;
}