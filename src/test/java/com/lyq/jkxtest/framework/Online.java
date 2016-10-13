package com.lyq.jkxtest.framework;

import java.io.Serializable;

public class Online
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected int count = 1;
  protected String busiId = "";
  protected int sequence = 1;
  protected String stateName = "";
  protected String appointDate = "";
  protected String customerName = "";
  protected String containerType = "";
  protected String bookingNo = "";
  protected String otherBookingNo = "";
  protected String factoryName = "";
  protected String factoryShortName = "";
  protected String contact = "";
  protected String telephone = "";
  protected String loadPlace = "";
  protected String address = "";
  protected String lineName = "";
  protected String customsMode = "";
  protected int isDoubleCon = 1;
  protected String containerNo = "";
  protected String otherContainerNo = "";
  protected String sealNo = "";
  protected String otherSealNo = "";
  protected String getConPile = "";
  protected String getConPlace = "";
  protected String returnConPile = "";
  protected String returnConPlace = "";
  protected String truck = "";
  protected String transportTeam = "";
  protected String driver = "";
  protected String driverPhone = "";
  protected String startHarborTime = "";
  protected String endHarborTime = "";
  protected double cubage = 0.0D;
  protected double weight = 0.0D;
  protected int piece = 0;
  protected String goodsName = "";
  protected String customsNo = "";
  protected double containerWeight = 0.0D;
  protected double containerWeight2 = 0.0D;
  protected String containerWeightUnit = "";
  protected String containerWeight2Unit = "";
  protected String whiteCardNo = "";
  protected BusiContainerAddition containerAddition = new BusiContainerAddition();

  public double getContainerWeight2() {
    return this.containerWeight2;
  }

  public void setContainerWeight2(double containerWeight2) {
    this.containerWeight2 = containerWeight2;
  }

  public String getContainerWeightUnit() {
    return this.containerWeightUnit;
  }

  public void setContainerWeightUnit(String containerWeightUnit) {
    this.containerWeightUnit = containerWeightUnit;
  }

  public String getContainerWeight2Unit() {
    return this.containerWeight2Unit;
  }

  public void setContainerWeight2Unit(String containerWeight2Unit) {
    this.containerWeight2Unit = containerWeight2Unit;
  }

  public String getWhiteCardNo() {
    return this.whiteCardNo;
  }

  public void setWhiteCardNo(String whiteCardNo) {
    this.whiteCardNo = whiteCardNo;
  }

  public double getContainerWeight() {
    return this.containerWeight;
  }

  public void setContainerWeight(double containerWeight) {
    this.containerWeight = containerWeight;
  }
  public int getCount() {
    return this.count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getBusiId() {
    return this.busiId;
  }

  public void setBusiId(String busiId) {
    this.busiId = busiId;
  }

  public int getSequence() {
    return this.sequence;
  }

  public void setSequence(int sequence) {
    this.sequence = sequence;
  }

  public String getStateName() {
    return this.stateName;
  }

  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

  public String getAppointDate() {
    return this.appointDate;
  }

  public void setAppointDate(String appointDate) {
    this.appointDate = appointDate;
  }

  public String getCustomerName() {
    return this.customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getContainerType() {
    return this.containerType;
  }

  public void setContainerType(String containerType) {
    this.containerType = containerType;
  }

  public String getBookingNo() {
    return this.bookingNo;
  }

  public void setBookingNo(String bookingNo) {
    this.bookingNo = bookingNo;
  }

  public String getOtherBookingNo() {
    return this.otherBookingNo;
  }

  public void setOtherBookingNo(String otherBookingNo) {
    this.otherBookingNo = otherBookingNo;
  }

  public String getFactoryName() {
    return this.factoryName;
  }

  public void setFactoryName(String factoryName) {
    this.factoryName = factoryName;
  }

  public String getFactoryShortName() {
    return this.factoryShortName;
  }

  public void setFactoryShortName(String factoryShortName) {
    this.factoryShortName = factoryShortName;
  }

  public String getLoadPlace() {
    return this.loadPlace;
  }

  public void setLoadPlace(String loadPlace) {
    this.loadPlace = loadPlace;
  }

  public String getContact() {
    return this.contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getTelephone() {
    return this.telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getLineName() {
    return this.lineName;
  }

  public void setLineName(String lineName) {
    this.lineName = lineName;
  }

  public String getCustomsMode() {
    return this.customsMode;
  }

  public void setCustomsMode(String customsMode) {
    this.customsMode = customsMode;
  }

  public int getIsDoubleCon() {
    return this.isDoubleCon;
  }

  public void setIsDoubleCon(int isDoubleCon) {
    this.isDoubleCon = isDoubleCon;
  }

  public String getContainerNo() {
    return this.containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public String getOtherContainerNo() {
    return this.otherContainerNo;
  }

  public void setOtherContainerNo(String otherContainerNo) {
    this.otherContainerNo = otherContainerNo;
  }

  public String getSealNo() {
    return this.sealNo;
  }

  public void setSealNo(String sealNo) {
    this.sealNo = sealNo;
  }

  public String getOtherSealNo() {
    return this.otherSealNo;
  }

  public void setOtherSealNo(String otherSealNo) {
    this.otherSealNo = otherSealNo;
  }

  public String getGetConPile() {
    return this.getConPile;
  }

  public void setGetConPile(String getConPile) {
    this.getConPile = getConPile;
  }

  public String getGetConPlace() {
    return this.getConPlace;
  }

  public void setGetConPlace(String getConPlace) {
    this.getConPlace = getConPlace;
  }

  public String getReturnConPile() {
    return this.returnConPile;
  }

  public void setReturnConPile(String returnConPile) {
    this.returnConPile = returnConPile;
  }

  public String getReturnConPlace() {
    return this.returnConPlace;
  }

  public void setReturnConPlace(String returnConPlace) {
    this.returnConPlace = returnConPlace;
  }

  public String getTruck() {
    return this.truck;
  }

  public void setTruck(String truck) {
    this.truck = truck;
  }

  public String getTransportTeam() {
    return this.transportTeam;
  }

  public void setTransportTeam(String transportTeam) {
    this.transportTeam = transportTeam;
  }

  public String getDriver() {
    return this.driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public String getDriverPhone() {
    return this.driverPhone;
  }

  public void setDriverPhone(String driverPhone) {
    this.driverPhone = driverPhone;
  }

  public String getStartHarborTime() {
    return this.startHarborTime;
  }

  public void setStartHarborTime(String startHarborTime) {
    this.startHarborTime = startHarborTime;
  }

  public String getEndHarborTime() {
    return this.endHarborTime;
  }

  public void setEndHarborTime(String endHarborTime) {
    this.endHarborTime = endHarborTime;
  }

  public double getCubage() {
    return this.cubage;
  }

  public void setCubage(double cubage) {
    this.cubage = cubage;
  }

  public double getWeight() {
    return this.weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public int getPiece() {
    return this.piece;
  }

  public void setPiece(int piece) {
    this.piece = piece;
  }

  public String getGoodsName() {
    return this.goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public String getCustomsNo() {
    return this.customsNo;
  }

  public void setCustomsNo(String customsNo) {
    this.customsNo = customsNo;
  }

  public BusiContainerAddition getContainerAddition() {
    return this.containerAddition;
  }

  public void setContainerAddition(BusiContainerAddition containerAddition) {
    this.containerAddition = containerAddition;
  }
}