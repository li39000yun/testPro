/**
 * @(#)VoyageInfo.java     2012-4-5
 *
 * Copyright 2012 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.nb.outer;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 船期信息
 * 
 * @author zhangxulong
 * 
 * @version 2012-4-5
 * 
 * @since JDK 1.6
 * 
 */
public class VoyageInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String dock = StringManage.FS_EMPTY;// 码头    就是界面上的 还柜堆场
	protected String en_ship = StringManage.FS_EMPTY;// 英文船名
	protected String cn_ship = StringManage.FS_EMPTY;// 中文船名
	protected String voyage = StringManage.FS_EMPTY;// 航次
	protected String inStartTime = StringManage.FS_EMPTY;// 进箱开始时间
	protected String inCutOffTime = StringManage.FS_EMPTY;// 进箱截止时间
	protected String unCode = StringManage.FS_EMPTY;// 船舶UN代码
	protected String type = StringManage.FS_EMPTY;// 进出口类型(I:进口;E:出口;)
	
	
	protected String accreditId=StringManage.FS_EMPTY;//授权代码,引用(corp_accredit.accredit_id)
	protected String busiId=StringManage.FS_EMPTY;//承运单号,引用(busi_base.busi_id)
	protected int sequence=1;//柜序号
	protected int ifdoubleCom=1;//第几个柜  
	protected String containerNo=StringManage.FS_EMPTY;//箱号
	protected String bookingNo=StringManage.FS_EMPTY;//提单号
	protected String containerType=StringManage.FS_EMPTY;//尺寸类型
	protected String receiveTime=StringManage.FS_EMPTY;//信息收到时间,格式:YYYY-MM-DD hh:mm
	protected String enterTime=StringManage.FS_EMPTY;//进门时间,格式:YYYY-MM-DD hh:mm
	protected String sealNo=StringManage.FS_EMPTY; //铅封号
	protected String weight=StringManage.FS_EMPTY;//箱毛重
	protected String loadAnchor=StringManage.FS_EMPTY;//卸货港
	protected String fecthTime=StringManage.FS_EMPTY;//信息收到时间,格式:YYYY-MM-DD hh:mm
	protected String containerState=StringManage.FS_EMPTY; //箱状态
	protected String containerOwner=StringManage.FS_EMPTY;//箱主
	protected int piece=0;//件数
	protected double goodsWeight=0; //货物重量
	protected double cubage=0; //体积
	protected String goodsName=StringManage.FS_EMPTY; //货名
	
	protected int isChecked=0;//是否验证   0未验证   1未查到记录就是未进港    2验证不通过   3验证通过
	
	/* 新版npedi Json对象 */
	protected String blNo = StringManage.FS_EMPTY;// 提单号
	protected String ctnNo = StringManage.FS_EMPTY;// 集装箱号
	protected String ctnOperatorCode = StringManage.FS_EMPTY;// 箱主
	protected String ctnSizeType = StringManage.FS_EMPTY;// 尺寸类型
	protected String ctnStatus = StringManage.FS_EMPTY;// 箱状态 F：整箱；L: 拼箱；E：空箱
	protected String direct = StringManage.FS_EMPTY;// 进出口
	protected String dischargePortCode = StringManage.FS_EMPTY;// 卸货港
	protected String grossWeight = StringManage.FS_EMPTY;// 卸货港
	protected String id = StringManage.FS_EMPTY;//
	protected String loadPortCode = StringManage.FS_EMPTY;// 装货港
	protected String realTimeLoadDisch = StringManage.FS_EMPTY;// 卸船时间
	protected String senderCode = StringManage.FS_EMPTY;// 码头代码
	protected String stowageLoction = StringManage.FS_EMPTY;// 船舶贝位
	protected String vessel = StringManage.FS_EMPTY;// 船名
	protected String ctnGrossWeight = StringManage.FS_EMPTY;// 箱毛重
	protected String dlPortCode = StringManage.FS_EMPTY;// 卸货港
	protected String inGateTime = StringManage.FS_EMPTY;// 进门时间
	protected String msgReceiveTime = StringManage.FS_EMPTY;// 信息收到

	/* 点击提单号显示详情 */
	protected String scoarriId = StringManage.FS_EMPTY;
	protected String blRemark = StringManage.FS_EMPTY;
	protected String scodecoId = StringManage.FS_EMPTY;
	protected String cargoDescription = StringManage.FS_EMPTY;
	protected String cargoGrossWeight = StringManage.FS_EMPTY;// 货毛重
	protected String cargoMeasurement = StringManage.FS_EMPTY;// 货尺寸
	protected String cargoTypeCode = StringManage.FS_EMPTY;// 货名
	protected String numbersOfPackages = StringManage.FS_EMPTY;// 件数
	
	public String getEn_ship() {
		return en_ship;
	}

	public void setEn_ship(String enShip) {
		if (enShip != null)
			en_ship = enShip;
	}

	public String getCn_ship() {
		return cn_ship;
	}

	public void setCn_ship(String cnShip) {
		if (cnShip != null)
			cn_ship = cnShip;
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		if (voyage != null)
			this.voyage = voyage;
	}

	public String getDock() {
		return dock;
	}

	public void setDock(String dock) {
		if (dock != null)
			this.dock = dock;
	}

	public String getInStartTime() {
		return inStartTime;
	}

	public void setInStartTime(String inStartTime) {
		if (inStartTime != null)
			this.inStartTime = inStartTime;
	}

	public String getInCutOffTime() {
		return inCutOffTime;
	}

	public void setInCutOffTime(String inCutOffTime) {
		if (inCutOffTime != null)
			this.inCutOffTime = inCutOffTime;
	}

	public String getUnCode() {
		return unCode;
	}

	public void setUnCode(String unCode) {
		if (unCode != null)
			this.unCode = unCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (type != null)
			this.type = type;
	}

	public String getAccreditId() {
		return accreditId;
	}

	public void setAccreditId(String accreditId) {
		this.accreditId = accreditId;
	}

	public String getBusiId() {
		return busiId;
	}

	public void setBusiId(String busiId) {
		if (busiId != null)
			this.busiId = busiId;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getIfdoubleCom() {
		return ifdoubleCom;
	}

	public void setIfdoubleCom(int ifdoubleCom) {
		this.ifdoubleCom = ifdoubleCom;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		if (containerNo != null)
			this.containerNo = containerNo;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		if (bookingNo != null)
			this.bookingNo = bookingNo;
	}

	public int getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		if (containerType != null)
			this.containerType = containerType;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		if (receiveTime != null)
			this.receiveTime = receiveTime;
	}

	public String getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(String enterTime) {
		if (enterTime != null)
			this.enterTime = enterTime;
	}

	public String getSealNo() {
		return sealNo;
	}

	public void setSealNo(String sealNo) {
		if (sealNo != null)
			this.sealNo = sealNo;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getLoadAnchor() {
		return loadAnchor;
	}

	public void setLoadAnchor(String loadAnchor) {
		if (loadAnchor != null)
			this.loadAnchor = loadAnchor;
	}

	public String getFecthTime() {
		return fecthTime;
	}

	public void setFecthTime(String fecthTime) {
		if (fecthTime != null)
			this.fecthTime = fecthTime;
	}

	public String getContainerState() {
		return containerState;
	}

	public void setContainerState(String containerState) {
		if (containerState != null)
			this.containerState = containerState;
	}

	public String getContainerOwner() {
		return containerOwner;
	}

	public void setContainerOwner(String containerOwner) {
		if (containerOwner != null)
			this.containerOwner = containerOwner;
	}

	public int getPiece() {
		return piece;
	}

	public void setPiece(int piece) {
		this.piece = piece;
	}

	public double getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(double goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public double getCubage() {
		return cubage;
	}

	public void setCubage(double cubage) {
		this.cubage = cubage;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		if (goodsName != null)
			this.goodsName = goodsName;
	}

	public String getBlNo() {
		return blNo;
	}

	public void setBlNo(String blNo) {
		this.blNo = blNo;
	}

	public String getCtnNo() {
		return ctnNo;
	}

	public void setCtnNo(String ctnNo) {
		this.ctnNo = ctnNo;
	}

	public String getCtnOperatorCode() {
		return ctnOperatorCode;
	}

	public void setCtnOperatorCode(String ctnOperatorCode) {
		this.ctnOperatorCode = ctnOperatorCode;
	}

	public String getCtnSizeType() {
		return ctnSizeType;
	}

	public void setCtnSizeType(String ctnSizeType) {
		this.ctnSizeType = ctnSizeType;
	}

	public String getCtnStatus() {
		return ctnStatus;
	}

	public void setCtnStatus(String ctnStatus) {
		this.ctnStatus = ctnStatus;
	}

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

	public String getDischargePortCode() {
		return dischargePortCode;
	}

	public void setDischargePortCode(String dischargePortCode) {
		this.dischargePortCode = dischargePortCode;
	}

	public String getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoadPortCode() {
		return loadPortCode;
	}

	public void setLoadPortCode(String loadPortCode) {
		this.loadPortCode = loadPortCode;
	}

	public String getRealTimeLoadDisch() {
		return realTimeLoadDisch;
	}

	public void setRealTimeLoadDisch(String realTimeLoadDisch) {
		this.realTimeLoadDisch = toString(realTimeLoadDisch);
	}

	public String getSenderCode() {
		return senderCode;
	}

	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}

	public String getStowageLoction() {
		return stowageLoction;
	}

	public void setStowageLoction(String stowageLoction) {
		this.stowageLoction = stowageLoction;
	}

	public String getVessel() {
		return vessel;
	}

	public void setVessel(String vessel) {
		this.vessel = vessel;
	}

	public String getCtnGrossWeight() {
		return ctnGrossWeight;
	}

	public void setCtnGrossWeight(String ctnGrossWeight) {
		this.ctnGrossWeight = ctnGrossWeight;
	}

	public String getDlPortCode() {
		return dlPortCode;
	}

	public void setDlPortCode(String dlPortCode) {
		this.dlPortCode = dlPortCode;
	}

	public String getInGateTime() {
		return inGateTime;
	}

	public void setInGateTime(String inGateTime) {
		this.inGateTime = toString(inGateTime);
	}

	public String getMsgReceiveTime() {
		return msgReceiveTime;
	}

	public void setMsgReceiveTime(String msgReceiveTime) {
		this.msgReceiveTime = toString(msgReceiveTime);
	}

	public String getScoarriId() {
		return scoarriId;
	}

	public void setScoarriId(String scoarriId) {
		this.scoarriId = scoarriId;
	}

	public String getBlRemark() {
		return blRemark;
	}

	public void setBlRemark(String blRemark) {
		this.blRemark = blRemark;
	}

	public String getScodecoId() {
		return scodecoId;
	}

	public void setScodecoId(String scodecoId) {
		this.scodecoId = scodecoId;
	}

	public String getCargoDescription() {
		return cargoDescription;
	}

	public void setCargoDescription(String cargoDescription) {
		this.cargoDescription = cargoDescription;
	}

	public String getCargoGrossWeight() {
		return cargoGrossWeight;
	}

	public void setCargoGrossWeight(String cargoGrossWeight) {
		this.cargoGrossWeight = cargoGrossWeight;
	}

	public String getCargoMeasurement() {
		return cargoMeasurement;
	}

	public void setCargoMeasurement(String cargoMeasurement) {
		this.cargoMeasurement = cargoMeasurement;
	}

	public String getCargoTypeCode() {
		return cargoTypeCode;
	}

	public void setCargoTypeCode(String cargoTypeCode) {
		this.cargoTypeCode = cargoTypeCode;
	}

	public String getNumbersOfPackages() {
		return numbersOfPackages;
	}

	public void setNumbersOfPackages(String numbersOfPackages) {
		this.numbersOfPackages = numbersOfPackages;
	}
	
	// 格式化时间格式
	private String toString(String str) {
		if (!StringManage.isEmpty(str)) {
			if (str.length() == 12) {
				str = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8) + " " + str.substring(8, 10) + ":" +str.substring(10, 12);
			}else if (str.length() == 14) {
				str = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8) + " " + str.substring(8, 10) + ":" +str.substring(10, 12) + ":" +str.substring(12, 14);
			} 
		}
		return str;
	}
}
