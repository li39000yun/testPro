

/**
 * @(#)FetchSearch.java     2014-6-26
 *
 * Copyright 2014 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.business.vo.fetch;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 数据抓取查询条件对象
 * 
 * @author wangchao
 * 
 * @version 2014-6-26
 * 
 * @since JDK 1.6
 * 
 */
public class FetchSearch implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String dock = StringManage.FS_EMPTY;// 码头
	protected int regionId = 0;// 地区编号
	protected String containerNo = StringManage.FS_EMPTY;// 箱号
	protected String bookingNo = StringManage.FS_EMPTY;// 提单号
	
	public String toString2() {
		String regionStr = "";
		switch(regionId){
			case 1:regionStr="sz";break;
			case 2:regionStr="sh";break;
			case 3:regionStr="qd";break;
			case 4:regionStr="xm";break;
			case 5:regionStr="gz";break;
			case 6:regionStr="tj";break;
			case 7:regionStr="nb";break;
		}
		return "fetch-center FetchSearch [ regionId=" + regionStr +" , bookingNo=" + bookingNo + " , containerNo=" + containerNo + " , dock=" + dock + "]";
	}
	
	public FetchSearch() {
	}



	public String getDock() {
		return dock;
	}

	public void setDock(String dock) {
		this.dock = dock.trim();
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo.trim();
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo.trim();
	}

}
