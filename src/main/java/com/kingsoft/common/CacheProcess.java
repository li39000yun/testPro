
/**
 * @(#)CacheProcess.java     2014-6-30
 *
 * Copyright 2014 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingsoft.business.vo.fetch.Cache;
import com.kingsoft.control.Console;
import com.kingsoft.control.tasks.AbstractProcess;

/**
 * 数据缓存线程类
 * 
 * @author wangchao
 * 
 * @version 2014-6-30
 * 
 * @since JDK 1.6
 * 
 */
public class CacheProcess extends AbstractProcess {
	private static Logger S_Logger = Logger.getLogger(CacheProcess.class);
	public static Map<String, Cache> S_Caches = new HashMap<String, Cache>();// 数据缓存

	public CacheProcess() {
		setTaskName("Fetch-Center-Cache-Process");// 线程名称：工程名称-功能描述
		setDelay(15*60*1000);// 延迟执行
		setSleepTime(15*60*1000);// 设置线程休眠时间为15分
	}

	@Override
	public void execute() {
		Set<String> keys = S_Caches.keySet();
		Iterator<String> ite = keys.iterator();
		String now = Console.FS_TIME.getNow();
		String key = "";
		try {
			while (ite.hasNext()) {
				key = ite.next();
				Cache cache = CacheProcess.get(key);
				if (Console.FS_TIME.compareMinute(now, cache.getCacheTime()) > 15) {
					CacheProcess.remove(key);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(S_Logger.isDebugEnabled())
				S_Logger.debug("fetch-center - [CacheProcess] key length = "+ keys.size()+" keys "+ keys +"  key="+ key +" Process Lose Cache error ");
			
		}
	}

	public static synchronized Cache get(String key) {
		return S_Caches.get(key);
	}

	public static synchronized void remove(String key) {
		S_Caches.remove(key);
	}

	public static synchronized void put(String key, Cache cache) {
		S_Caches.put(key, cache);
	}

	public void clear() {
		S_Caches.clear();
		S_Caches = null;
	}

}
