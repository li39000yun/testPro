/**
 * @(#)ColumnSetup.java     2013-07-11
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.nb.dao.entity.system;

import com.kingsoft.control.util.StringManage;
import com.kingsoft.control.exception.DataBaseException;
import com.kingsoft.control.database.MappingTableModel;

/**
 * 客户自定义界面
 *
 * @author kingsoft
 *
 * @version 2013-07-11
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */

public class ColumnSetup implements MappingTableModel{

	private static final long serialVersionUID = 1L;

	private static final String $MAPPING_TABLE_NAME = "base_column_setup";// 数据库表名称

	protected int count = 0;// 分页列表中的序号
	protected String accreditId = StringManage.FS_EMPTY;// 授权码
	protected int columnId = Integer.MIN_VALUE;// 字段编号
	protected String columnName = StringManage.FS_EMPTY;// 字段名
	protected int orderby = 1;// 排序
	protected int colspan = 0;// 合并列数
	
	protected String style = StringManage.FS_EMPTY;// 样式

	public ColumnSetup() {

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getAccreditId() {
		return accreditId;
	}

	public void setAccreditId(String accreditId) {
		if (accreditId != null) {
			this.accreditId = accreditId;
		}
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		if (columnName != null) {
			this.columnName = columnName;
		}
	}

	public int getOrderby() {
		return orderby;
	}

	public void setOrderby(int orderby) {
		this.orderby = orderby;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}
	
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public final String mappingTableName() {
		return $MAPPING_TABLE_NAME;
	}

	public void clone(MappingTableModel model) {
		if (!(model instanceof ColumnSetup)) {
			return;
		}
		ColumnSetup obj = (ColumnSetup) model;
		if (obj != null) {
			obj.count = count;
			obj.accreditId = accreditId;
			obj.columnId = columnId;
			obj.columnName = columnName;
			obj.orderby = orderby;
			obj.colspan = colspan;
		}
	}

	public void validate() throws DataBaseException {
		if (StringManage.isEmpty(accreditId)) {
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,
					"授权码 不能为空.");
		}
		if (StringManage.isEmpty(columnName)) {
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,
					"字段名 不能为空.");
		}
	}
}