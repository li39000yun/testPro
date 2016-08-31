/**
 * @(#)GlobalVariables.java     2010-05-21
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
 * 系统全局变量
 * 
 * @author kingsoft
 * 
 * @version 2010-05-21
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */

public class GlobalVariables implements MappingTableModel {

	private static final long serialVersionUID = 1L;

	private static final String $MAPPING_TABLE_NAME = "global_variables";// 数据库表名称

	protected int count = 0;// 页面列表显示序号
	protected String id = StringManage.FS_EMPTY;// 变量标识
	protected String value = StringManage.FS_EMPTY;// 变量值
	protected String defaultValue = StringManage.FS_EMPTY;// 变量默认值
	protected String name = StringManage.FS_EMPTY;// 变量名称
	protected String type = StringManage.FS_EMPTY;// 量变类型
	protected int edit = 0;// 可否修改(0:可以;1:不可以;)
	protected String editCode = StringManage.FS_EMPTY;// 是否修改查询条件
	protected int idx = 0;// 排序，同类型变量显示顺序
	protected int actflag = 1;// 当前状态(0:停止使用;1:正常使用)

	public GlobalVariables() {

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null) {
			this.id = id;
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if (value != null) {
			this.value = value;
		}
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		if (defaultValue != null) {
			this.defaultValue = defaultValue;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null) {
			this.name = name;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getEdit() {
		return edit;
	}

	public void setEdit(int edit) {
		this.edit = edit;
	}

	public String getEditCode() {
		return editCode;
	}

	public void setEditCode(String editCode) {
		this.editCode = editCode;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getActflag() {
		return actflag;
	}

	public void setActflag(int actflag) {
		this.actflag = actflag;
	}

	public final String mappingTableName() {
		return $MAPPING_TABLE_NAME;
	}

	public void clone(MappingTableModel model) {
		if (!(model instanceof GlobalVariables)) {
			return;
		}
		GlobalVariables obj = (GlobalVariables) model;
		if (obj != null) {
			obj.count = count;
			obj.id = id;
			obj.value = value;
			obj.defaultValue = defaultValue;
			obj.name = name;
			obj.type = type;
			obj.edit = edit;
			obj.editCode = editCode;
			obj.idx = idx;
			obj.actflag = actflag;
		}
	}

	public void validate() throws DataBaseException {
		if (StringManage.isEmpty(id)) {
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,
					"变量标识 不能为空.");
		}
		if (StringManage.isEmpty(value)) {
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,
					"变量值 不能为空.");
		}
		if (StringManage.isEmpty(name)) {
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,
					"变量名称 不能为空.");
		}

	}
}