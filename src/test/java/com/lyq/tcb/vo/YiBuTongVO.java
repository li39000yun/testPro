package com.lyq.tcb.vo;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 司机费用信息对象
 * 
 * @author wmy
 * 
 * @version 2015-9-1
 * 
 * @since JDK 1.6
 * 
 */
public class YiBuTongVO implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String appkey = StringManage.FS_EMPTY;// 密钥
	protected String accreditid = StringManage.FS_EMPTY;
	protected YiBuTongBase base = new YiBuTongBase();
	protected YiBuTongContainer[] container = new YiBuTongContainer[0];// 司机费用信息数组
	
	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		if (appkey != null) {
			this.appkey = appkey;
		}
	}

	public YiBuTongBase getBase() {
		return base;
	}

	public void setBase(YiBuTongBase base) {
		this.base = base;
	}

	public YiBuTongContainer[] getContainer() {
		return container;
	}

	public void setContainer(YiBuTongContainer[] container) {
		this.container = container;
	}

	public String getAccreditid() {
		return accreditid;
	}

	public void setAccreditid(String accreditid) {
		this.accreditid = accreditid;
	}

	
}
