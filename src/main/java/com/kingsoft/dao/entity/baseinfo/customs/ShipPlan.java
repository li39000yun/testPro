/**
 * @(#)ShipPlan.java     2012-06-20
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.dao.entity.baseinfo.customs;

import com.kingsoft.control.util.StringManage;
import com.kingsoft.control.exception.DataBaseException;
import com.kingsoft.control.database.MappingTableModel;

/**
 * 船舶计划
 * 
 * @author kingsoft
 * 
 * @version 2012-06-20
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */

public class ShipPlan implements MappingTableModel {

	private static final long serialVersionUID = 1L;

	private static final String $MAPPING_TABLE_NAME = "base_ship_plan";// 数据库表名称

	protected int count = 0;// 分页列表中的序号
	protected int id = Integer.MIN_VALUE;// 流水号
	protected String dock = StringManage.FS_EMPTY;// 码头
	protected String nameEn = StringManage.FS_EMPTY;// 英文船名
	protected String voyage = StringManage.FS_EMPTY;// 航次
	protected String harborTimeStart = StringManage.FS_EMPTY;// 计划靠泊时间
	protected String harborTimeEnd = StringManage.FS_EMPTY;// 计划停泊时间

	public ShipPlan() {

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDock() {
		return dock;
	}

	public void setDock(String dock) {
		if (dock != null) {
			this.dock = dock;
		}
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		if (nameEn != null) {
			this.nameEn = nameEn;
		}
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		if (voyage != null) {
			this.voyage = voyage;
		}
	}

	public String getHarborTimeStart() {
		return harborTimeStart;
	}

	public void setHarborTimeStart(String harborTimeStart) {
		if (harborTimeStart != null) {
			this.harborTimeStart = harborTimeStart;
		}
	}

	public String getHarborTimeEnd() {
		return harborTimeEnd;
	}

	public void setHarborTimeEnd(String harborTimeEnd) {
		if (harborTimeEnd != null) {
			this.harborTimeEnd = harborTimeEnd;
		}
	}

	public final String mappingTableName() {
		return $MAPPING_TABLE_NAME;
	}

	public void clone(MappingTableModel model) {
		if (!(model instanceof ShipPlan)) {
			return;
		}
		ShipPlan obj = (ShipPlan) model;
		if (obj != null) {
			obj.count = count;
			obj.id = id;
			obj.dock = dock;
			obj.nameEn = nameEn;
			obj.voyage = voyage;
			obj.harborTimeStart = harborTimeStart;
			obj.harborTimeEnd = harborTimeEnd;
		}
	}

	public String getKeyCode() {
		return dock + "-" + nameEn + "-" + voyage;
	}

	public void validate() throws DataBaseException {
		if (StringManage.isEmpty(dock)) {
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,
					"码头 不能为空.");
		}
		if (StringManage.isEmpty(nameEn)) {
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,
					"英文船名 不能为空.");
		}

	}
}