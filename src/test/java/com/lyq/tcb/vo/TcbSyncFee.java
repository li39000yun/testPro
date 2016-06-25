package com.lyq.tcb.vo;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 同步司机费用信息对象
 * 
 * @author wmy
 * 
 * @version 2015-9-1
 * 
 * @since JDK 1.6
 * 
 */
public class TcbSyncFee implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String appkey = StringManage.FS_EMPTY;// 密钥
	protected String accreditid = StringManage.FS_EMPTY;// 授权编号
	protected String businessid = new String();// 业务编号
	protected TcbPayoutFee[] data = new TcbPayoutFee[0];// 司机费用信息数组
	protected String cmd = StringManage.FS_EMPTY;// 方法

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

	public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		if (businessid != null) {
			this.businessid = businessid;
		}
	}

	public TcbPayoutFee[] getData() {
		return data;
	}

	public void setData(TcbPayoutFee[] data) {
		this.data = data;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

}
