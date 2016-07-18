/**
 * @(#)Cache.java     2014-6-27
 *
 * Copyright 2014 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.lyq.fetch.common;

import java.io.Serializable;

import com.kingsoft.control.Console;
import com.kingsoft.control.util.StringManage;

/**
 * 数据抓取缓存对象
 * 
 * @author wangchao
 * 
 * @version 2014-6-27
 * 
 * @since JDK 1.6
 * 
 */
public class Cache implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String cacheTime = StringManage.FS_EMPTY;// 缓存时间
	protected String datas = StringManage.FS_EMPTY;// 缓存数据

	public String getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(String cacheTime) {
		this.cacheTime = cacheTime;
	}

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}

	public boolean compareHour(int hour) {
		return Console.FS_TIME.compareHour(Console.FS_TIME.getNow(), cacheTime) < hour;
	}
	
	public boolean compareMinute(int minite) {
		return Console.FS_TIME.compareMinute(Console.FS_TIME.getNow(), cacheTime) < minite;
	}

}
