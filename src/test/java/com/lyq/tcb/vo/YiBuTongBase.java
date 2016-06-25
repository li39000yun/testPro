package com.lyq.tcb.vo;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

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
public class YiBuTongBase implements Serializable {
	protected String accreditId = StringManage.FS_EMPTY;
	protected String customerUnit = StringManage.FS_EMPTY;
	protected String customerContact= StringManage.FS_EMPTY;
	protected String startCustoms= StringManage.FS_EMPTY;
	protected String bookingNo= StringManage.FS_EMPTY;
	protected String ship= StringManage.FS_EMPTY;
	protected String voyage= StringManage.FS_EMPTY;
	protected String cutOffTime= StringManage.FS_EMPTY;
	protected String followRemark= StringManage.FS_EMPTY;
	protected String appendix= StringManage.FS_EMPTY;
	protected String sectionDataTime= StringManage.FS_EMPTY;
	protected String customerTelephone= StringManage.FS_EMPTY;
	protected String takeConUnit= StringManage.FS_EMPTY;
	protected String takeConContact= StringManage.FS_EMPTY;
	protected String takeConTelephone= StringManage.FS_EMPTY;
	protected String takeConAddress = StringManage.FS_EMPTY;
	protected String invoiceAddress = StringManage.FS_EMPTY;	
	protected int isInvoice =0;
	public String getAccreditId() {
		return accreditId;
	}
	public void setAccreditId(String accreditId) {
		this.accreditId = accreditId;
	}
	public String getCustomerUnit() {
		return customerUnit;
	}
	public void setCustomerUnit(String customerUnit) {
		this.customerUnit = customerUnit;
	}
	public String getCustomerContact() {
		return customerContact;
	}
	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}
	public String getStartCustoms() {
		return startCustoms;
	}
	public void setStartCustoms(String startCustoms) {
		this.startCustoms = startCustoms;
	}
	public String getBookingNo() {
		return bookingNo;
	}
	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}
	public String getShip() {
		return ship;
	}
	public void setShip(String ship) {
		this.ship = ship;
	}
	public String getVoyage() {
		return voyage;
	}
	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}
	public String getCutOffTime() {
		return cutOffTime;
	}
	public void setCutOffTime(String cutOffTime) {
		this.cutOffTime = cutOffTime;
	}
	public String getFollowRemark() {
		return followRemark;
	}
	public void setFollowRemark(String followRemark) {
		this.followRemark = followRemark;
	}
	public String getAppendix() {
		return appendix;
	}
	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}
	public String getSectionDataTime() {
		return sectionDataTime;
	}
	public void setSectionDataTime(String sectionDataTime) {
		this.sectionDataTime = sectionDataTime;
	}
	public String getCustomerTelephone() {
		return customerTelephone;
	}
	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}
	public String getTakeConUnit() {
		return takeConUnit;
	}
	public void setTakeConUnit(String takeConUnit) {
		this.takeConUnit = takeConUnit;
	}
	public String getTakeConContact() {
		return takeConContact;
	}
	public void setTakeConContact(String takeConContact) {
		this.takeConContact = takeConContact;
	}
	public String getTakeConTelephone() {
		return takeConTelephone;
	}
	public void setTakeConTelephone(String takeConTelephone) {
		this.takeConTelephone = takeConTelephone;
	}
	public String getTakeConAddress() {
		return takeConAddress;
	}
	public void setTakeConAddress(String takeConAddress) {
		this.takeConAddress = takeConAddress;
	}
	public String getInvoiceAddress() {
		return invoiceAddress;
	}
	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}
	public int getIsInvoice() {
		return isInvoice;
	}
	public void setIsInvoice(int isInvoice) {
		this.isInvoice = isInvoice;
	}
	
	
}
