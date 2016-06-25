package com.lyq.tcb.vo;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 调度确认司机费用业务编号信息
 * 
 * @author wmy
 * 
 * @version 2015-9-6
 * 
 * @since JDK 1.6
 * 
 */
public class TcbComfirmDriverFee implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String businessid = StringManage.FS_EMPTY;// 业务编号

	public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}

}
