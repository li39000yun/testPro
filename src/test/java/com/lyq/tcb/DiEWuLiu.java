/**
 * 
 */
package com.lyq.tcb;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * E物流数据对象
 * 
 * @author xiehui
 * 
 * @version 2016-1-18
 * 
 * @since JDK 1.6
 * 
 */
public class DiEWuLiu implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int count = 0;// 分页列表中的序号
	protected String busiId = StringManage.FS_EMPTY; // 承运单号
	protected int sequence = 1;// 柜序号
	protected String appointDate = StringManage.FS_EMPTY;// 做箱时间
	protected String customsMode = StringManage.FS_EMPTY;// 报关方式(业务类型)
	protected String port = StringManage.FS_EMPTY;// 港区
	protected String truck = StringManage.FS_EMPTY;// 车牌号
	protected String containerNo = StringManage.FS_EMPTY;// 柜号
	protected String sealNo = StringManage.FS_EMPTY;// 封条号
	protected String containerType = StringManage.FS_EMPTY;// 柜型
	protected String bookingNo = StringManage.FS_EMPTY;// 订舱号
	protected String ship = StringManage.FS_EMPTY;// 船名
	protected String voyage = StringManage.FS_EMPTY;// 航次
	protected String sailingDate = StringManage.FS_EMPTY;// 船期

	protected double incomeFee = 0;// 应收费用
	protected double payoutFee = 0;// 应付费用
	protected double truckFee = 0;// 车辆成本费用
	protected double companyFee = 0;// 公司成本费用
	protected double profit = 0;// 利润

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getBusiId() {
		return busiId;
	}

	public void setBusiId(String busiId) {
		if (busiId != null) {
			this.busiId = busiId;
		}
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getAppointDate() {
		return appointDate;
	}

	public void setAppointDate(String appointDate) {
		if (appointDate != null) {
			this.appointDate = appointDate;
		}
	}

	public String getCustomsMode() {
		return customsMode;
	}

	public void setCustomsMode(String customsMode) {
		if (customsMode != null) {
			this.customsMode = customsMode;
		}
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		if (port != null) {
			this.port = port;
		}
	}

	public String getTruck() {
		return truck;
	}

	public void setTruck(String truck) {
		if (truck != null) {
			this.truck = truck;
		}
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		if (containerNo != null) {
			this.containerNo = containerNo;
		}
	}

	public String getSealNo() {
		return sealNo;
	}

	public void setSealNo(String sealNo) {
		if (sealNo != null) {
			this.sealNo = sealNo;
		}
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		if (containerType != null) {
			this.containerType = containerType;
		}
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		if (bookingNo != null) {
			this.bookingNo = bookingNo;
		}
	}

	public String getShip() {
		return ship;
	}

	public void setShip(String ship) {
		if (ship != null) {
			this.ship = ship;
		}
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		if (voyage != null) {
			this.voyage = voyage;
		}
	}

	public String getSailingDate() {
		return sailingDate;
	}

	public void setSailingDate(String sailingDate) {
		if (sailingDate != null) {
			this.sailingDate = sailingDate;
		}
	}

	public double getIncomeFee() {
		return incomeFee;
	}

	public void setIncomeFee(double incomeFee) {
		this.incomeFee = incomeFee;
	}

	public double getPayoutFee() {
		return payoutFee;
	}

	public void setPayoutFee(double payoutFee) {
		this.payoutFee = payoutFee;
	}

	public double getTruckFee() {
		return truckFee;
	}

	public void setTruckFee(double truckFee) {
		this.truckFee = truckFee;
	}

	public double getCompanyFee() {
		return companyFee;
	}

	public void setCompanyFee(double companyFee) {
		this.companyFee = companyFee;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}
}
