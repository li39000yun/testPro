package com.lyq.tcb.vo;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 查询挂靠费管理信息对象
 * 
 * @author wmy
 * 
 * @version 2015-10-28
 * 
 * @since JDK 1.6
 * 
 */
public class SearchManageFee implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String appkey = StringManage.FS_EMPTY;// 密钥
	protected String accreditid = StringManage.FS_EMPTY;// 授权编号
	protected int userid = 0;// 用户id
	protected String[] trucks = new String[0];// 车辆信息数组
	protected String beginDate = StringManage.FS_EMPTY;//查询开始月份,格式：YYYY-MM
	protected String endDate = StringManage.FS_EMPTY;//查询结束月份,格式：YYYY-MM
	
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

	public String[] getTrucks() {
		return trucks;
	}

	public void setTrucks(String[] trucks) {
		this.trucks = trucks;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String toString() {
		// 操作内容格式
		StringBuffer sb = new StringBuffer();
		sb.append("授权编号:").append(accreditid).append(";");
		sb.append("用户id：").append(userid).append(";");
		sb.append("车牌号：");
		for (String truck : getTrucks()){
			sb.append(truck).append(";");
		}
		return sb.toString();
	}
	
}
