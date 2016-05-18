package com.lyq.tcb.vo;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 司机费用信息
 * 
 * @author liyunqiang
 * 
 * @version 2015-11-23
 */
public class TcbPayoutFee implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int feeid = 0;// 费用Id
	protected double amount = 0.00;// 费用金额
	protected int ispicture = 0;// app是否有照片
	protected String synctime = StringManage.FS_EMPTY;// 同步时间

	public int getFeeid() {
		return feeid;
	}

	public void setFeeid(int feeid) {
		this.feeid = feeid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getIspicture() {
		return ispicture;
	}

	public void setIspicture(int ispicture) {
		this.ispicture = ispicture;
	}

	public String getSynctime() {
		return synctime;
	}

	public void setSynctime(String synctime) {
		this.synctime = synctime;
	}

}
