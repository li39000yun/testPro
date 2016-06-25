package com.lyq.tcb.vo;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 司机接单
 * 
 * @author wmy
 * 
 * @version 2015-8-27
 * 
 * @since JDK 1.6
 * 
 */
public class DriverAccept implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String appkey = StringManage.FS_EMPTY;// 密钥
	protected String accreditid = StringManage.FS_EMPTY;// 授权编号
	protected int userid = 0;// 用户id
	protected String[] businessids = new String[0];// 业务编号数组
	protected String truckno = StringManage.FS_EMPTY;// 车牌
	protected String driver = StringManage.FS_EMPTY;// 司机
	protected String phone = StringManage.FS_EMPTY;// 手机号码
	protected String containerno = StringManage.FS_EMPTY;//箱号
	protected String sealno = StringManage.FS_EMPTY;//封号
	protected String businessid = StringManage.FS_EMPTY;// 业务编号
	protected String username = StringManage.FS_EMPTY;// 操作人
	protected int type = 1;
	protected String consealnoremark = StringManage.FS_EMPTY;// 箱封号备注

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

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String[] getBusinessids() {
		return businessids;
	}

	public void setBusinessids(String[] businessids) {
		this.businessids = businessids;
	}

	public String getTruckno() {
		return truckno;
	}

	public void setTruckno(String truckno) {
		if (truckno != null) {
			this.truckno = truckno;
		}
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		if (driver != null) {
			this.driver = driver;
		}
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (phone != null) {
			this.phone = phone;
		}
	}

	public String getContainerno() {
		return containerno;
	}

	public void setContainerno(String containerno) {
		if(containerno != null){
			this.containerno = containerno;
		}
	}

	public String getSealno() {
		return sealno;
	}

	public void setSealno(String sealno) {
		if(sealno != null){
			this.sealno = sealno;
		}
	}

	public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getConsealnoremark() {
		return consealnoremark;
	}

	public void setConsealnoremark(String consealnoremark) {
		this.consealnoremark = consealnoremark;
	}

}
