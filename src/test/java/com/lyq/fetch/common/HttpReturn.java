/**
 * @(#)HttpReturn.java     2014-11-27
 *
 * Copyright 2014 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.lyq.fetch.common;

import java.io.Serializable;

import org.apache.http.Header;

import com.kingsoft.control.util.StringManage;

/**
 * httpResponse 请求返回对像
 * 
 * @author kangweishui
 * 
 * @version 2015年7月10日
 * 
 * @since JDK 1.6
 * 
 */
public class HttpReturn implements Serializable {
	private static final long serialVersionUID = 1L;
	private Header[] head = new Header[0];// 获取响应的header部门
	private String returnHtml = StringManage.FS_EMPTY;// 返回的http页面数据

	public Header[] getHead() {
		return head;
	}

	public void setHead(Header[] head) {
		this.head = head;
	}

	public String getReturnHtml() {
		return returnHtml;
	}

	public void setReturnHtml(String returnHtml) {
		this.returnHtml = returnHtml;
	}

}
