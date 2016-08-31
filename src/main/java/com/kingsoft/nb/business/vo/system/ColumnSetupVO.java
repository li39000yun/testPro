/**
 * @(#)ColumnSetupVO.java     2013-7-11
 *  
 * Copyright 2013 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.nb.business.vo.system;

import com.kingsoft.control.util.StringManage;
import com.kingsoft.nb.dao.entity.system.ColumnSetup;

/**
 * 自定义界面实体扩展对象
 *  
 * @author caikun
 * 
 * @version 2013-7-11
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */
public class ColumnSetupVO extends ColumnSetup {
	private static final long serialVersionUID = 1L;
	protected int sysColumnId = 0;// 字段编号
	protected String sysColumnName = StringManage.FS_EMPTY;// 字段名称
	protected int sysOrderby = 0;// 排序
	protected int sysColspan = 0;// 合并列数
	protected int sysIsNeed = 0;// 是否必须

	public int getSysColumnId() {
		return sysColumnId;
	}

	public void setSysColumnId(int sysColumnId) {
		this.sysColumnId = sysColumnId;
	}

	public String getSysColumnName() {
		return sysColumnName;
	}

	public void setSysColumnName(String sysColumnName) {
		if (sysColumnName != null) {
			this.sysColumnName = sysColumnName;
		}
	}

	public int getSysOrderby() {
		return sysOrderby;
	}

	public void setSysOrderby(int sysOrderby) {
		this.sysOrderby = sysOrderby;
	}

	public int getSysColspan() {
		return sysColspan;
	}

	public void setSysColspan(int sysColspan) {
		this.sysColspan = sysColspan;
	}

	public int getSysIsNeed() {
		return sysIsNeed;
	}

	public void setSysIsNeed(int sysIsNeed) {
		this.sysIsNeed = sysIsNeed;
	}

}
