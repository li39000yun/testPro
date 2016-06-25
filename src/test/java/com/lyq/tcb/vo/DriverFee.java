package com.lyq.tcb.vo;

import java.io.Serializable;

/**
 * 司机费用信息
 * 
 * @author wmy
 * 
 * @version 2015-9-1
 * 
 * @since JDK 1.6
 * 
 */
public class DriverFee implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int feeid = 0;// 费用Id
	protected double amount = 0.00;// 费用金额
	protected int ispicture = 0;// app是否有照片
	protected String synctime = "";
	
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
