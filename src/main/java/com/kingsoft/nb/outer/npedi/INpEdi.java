/**
 * @(#)INpEdi.java     2012-4-5
 *
 * Copyright 2012 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.nb.outer.npedi;

import com.kingsoft.nb.outer.OuterFetch;
import com.kingsoft.nb.outer.VoyageInfo;

/**
 * NpEdi 宁波港数据抓取接口
 * 
 * @author zhangxulong
 * 
 * @version 2012-4-5
 * 
 * @since JDK 1.6
 * 
 */
public interface INpEdi extends OuterFetch {
	/**
	 * 获取网站上面的船期信息
	 * 
	 * @return VoyageInfo[] 船期对象数组
	 * @throws Exception
	 */
	public VoyageInfo[] fetch() throws Exception;
	
	/**
	 *  获取网站上面的信息
	 * @param param  需要传的参数
	 * @return
	 * @throws Exception
	 */
	public VoyageInfo[] fetch(String formData) throws Exception;
}
