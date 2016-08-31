/**
 * @(#)DockService.java     2014-6-27
 *
 * Copyright 2014 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.business.implement.fetch;

import java.io.Serializable;

import com.kingsoft.business.vo.fetch.FetchSearch;

/**
 * 码头数据抓取接口类
 * 
 * @author wangchao
 * 
 * @version 2014-6-27
 * 
 * @since JDK 1.6
 * 
 */
public interface DockService extends Serializable {
	/**
	 * 根据柜号获取码头数据
	 * 
	 * @param search
	 *            查询条件
	 * @throws Exception
	 */
	public String conFetch(FetchSearch search) throws Exception;
	
}
