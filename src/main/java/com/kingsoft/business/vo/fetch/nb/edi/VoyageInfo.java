/**
 * @(#)VoyageInfo.java     2012-4-5
 *
 * Copyright 2012 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.business.vo.fetch.nb.edi;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;


/**
 * 船期信息
 * @author LXM
 * @date 2015-4-6
 * @since JDK 1.6
 */
public class VoyageInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String dock = StringManage.FS_EMPTY;// 码头    就是界面上的 还柜堆场
	protected String en_ship = StringManage.FS_EMPTY;// 英文船名
	protected String cn_ship = StringManage.FS_EMPTY;// 中文船名
	protected String voyage = StringManage.FS_EMPTY;// 航次
	protected String inStartTime = StringManage.FS_EMPTY;// 进箱开始时间
	protected String inCutOffTime = StringManage.FS_EMPTY;// 进箱截止时间
	protected String unCode = StringManage.FS_EMPTY;// 船舶UN代码
	protected String type = StringManage.FS_EMPTY;// 进出口类型(I:进口;E:出口;)
	
	
	public String getEn_ship() {
		return en_ship;
	}

	public void setEn_ship(String enShip) {
		if (enShip != null)
			en_ship = enShip;
	}

	public String getCn_ship() {
		return cn_ship;
	}

	public void setCn_ship(String cnShip) {
		if (cnShip != null)
			cn_ship = cnShip;
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		if (voyage != null)
			this.voyage = voyage;
	}

	public String getDock() {
		return dock;
	}

	public void setDock(String dock) {
		if (dock != null)
			this.dock = dock;
	}

	public String getInStartTime() {
		return inStartTime;
	}

	public void setInStartTime(String inStartTime) {
		if (inStartTime != null)
			this.inStartTime = inStartTime;
	}

	public String getInCutOffTime() {
		return inCutOffTime;
	}

	public void setInCutOffTime(String inCutOffTime) {
		if (inCutOffTime != null)
			this.inCutOffTime = inCutOffTime;
	}

	public String getUnCode() {
		return unCode;
	}

	public void setUnCode(String unCode) {
		if (unCode != null)
			this.unCode = unCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (type != null)
			this.type = type;
	}
	
}