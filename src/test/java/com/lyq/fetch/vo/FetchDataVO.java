/**
 * @(#)FetchDataVO.java     2014-7-3
 *
 * Copyright 2014 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.lyq.fetch.vo;

import com.kingsoft.control.util.StringManage;

/**
 * 抓数据对象扩展类
 * @author LXM
 * @version 2014-7-3
 * @since JDK 1.6
 */
public class FetchDataVO extends FetchData {
	private static final long serialVersionUID = 1L;
	private String containerNo = StringManage.FS_EMPTY;//箱号
	private String bookingNo = StringManage.FS_EMPTY;//提单号
	private String port = StringManage.FS_EMPTY;//港区
	/*放行信息*/
	private String customsNo = StringManage.FS_EMPTY;//报关单号
	private String customsPass = StringManage.FS_EMPTY;//海关放行
	private String receiveTime = StringManage.FS_EMPTY;//EDI接收时间
	private String receiptState = StringManage.FS_EMPTY;//回执状态
	private String enterTime = StringManage.FS_EMPTY;//进港时间
	
	private String fecthBeginTime = StringManage.FS_EMPTY;//抓取开始时间
	private String fecthEndTime = StringManage.FS_EMPTY;//抓取结束时间

	protected FetchData[] goods = new FetchData[0];
	protected FetchData[] packings = new FetchData[0];	
	

	public FetchData[] getGoods() {
		return goods;
	}

	public void setGoods(FetchData[] goods) {
		this.goods = goods;
	}

	public FetchData[] getPackings() {
		return packings;
	}

	public void setPackings(FetchData[] packings) {
		this.packings = packings;
	}

	public String getFecthBeginTime() {
		return fecthBeginTime;
	}

	public void setFecthBeginTime(String fecthBeginTime) {
		this.fecthBeginTime = fecthBeginTime;
	}

	public String getFecthEndTime() {
		return fecthEndTime;
	}

	public void setFecthEndTime(String fecthEndTime) {
		this.fecthEndTime = fecthEndTime;
	}

	public String getCustomsNo() {
		return customsNo;
	}

	public void setCustomsNo(String customsNo) {
		this.customsNo = customsNo;
	}

	public String getCustomsPass() {
		return customsPass;
	}

	public void setCustomsPass(String customsPass) {
		this.customsPass = customsPass;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getReceiptState() {
		return receiptState;
	}

	public void setReceiptState(String receiptState) {
		this.receiptState = receiptState;
	}

	public String getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public String getPort() {
		return port;
	}

	/**
	 * @param plainTextString
	 */
	public void setPort(String plainTextString) {
	}

}
