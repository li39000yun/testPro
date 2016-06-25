package com.lyq.tcb.vo;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 拖车宝基础信息
 * 
 * @author liyunqiang
 * 
 * @version 2015-12-2
 */
public class TcbBase implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String appkey = StringManage.FS_EMPTY;// 密钥
	protected String accreditid = StringManage.FS_EMPTY;// 授权编号
	protected String cmd = StringManage.FS_EMPTY;// 方法名

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		if (appkey != null) {
			this.appkey = appkey;
		}
	}

	public String getAccreditid() {
		return accreditid;
	}

	public void setAccreditid(String accreditid) {
		if (accreditid != null) {
			this.accreditid = accreditid;
		}
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		if (cmd != null) {
			this.cmd = cmd;
		}
	}

}
