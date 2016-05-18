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
public class YiBuTongContainer implements Serializable {
	protected String externalId = StringManage.FS_EMPTY;
	protected int externalSequence = 1;
	protected String factoryShortName = StringManage.FS_EMPTY;
	protected String contact = StringManage.FS_EMPTY;
	protected String telephone = StringManage.FS_EMPTY;
	protected String loadPlace = StringManage.FS_EMPTY;
	protected String address = StringManage.FS_EMPTY;
	protected String appointDate = StringManage.FS_EMPTY;
	protected String getConPile = StringManage.FS_EMPTY;
	protected double weight = 0;
	protected String containerType = StringManage.FS_EMPTY;
	protected String returnConPile = StringManage.FS_EMPTY;
	protected String timePoint = StringManage.FS_EMPTY;

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public int getExternalSequence() {
		return externalSequence;
	}

	public void setExternalSequence(int externalSequence) {
		this.externalSequence = externalSequence;
	}

	public String getFactoryShortName() {
		return factoryShortName;
	}

	public void setFactoryShortName(String factoryShortName) {
		this.factoryShortName = factoryShortName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getLoadPlace() {
		return loadPlace;
	}

	public void setLoadPlace(String loadPlace) {
		this.loadPlace = loadPlace;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAppointDate() {
		return appointDate;
	}

	public void setAppointDate(String appointDate) {
		this.appointDate = appointDate;
	}

	public String getGetConPile() {
		return getConPile;
	}

	public void setGetConPile(String getConPile) {
		this.getConPile = getConPile;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getReturnConPile() {
		return returnConPile;
	}

	public void setReturnConPile(String returnConPile) {
		this.returnConPile = returnConPile;
	}

	public String getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	
}
