package com.lyq.jkxtest.serviceCenter;

import com.kingsoft.control.util.StringManage;

import java.io.Serializable;

/**
 * 柜校验信息
 * 
 * @author xiehui
 * 
 * @version 2015年5月21日
 * 
 * @since JDK 1.6
 * 
 */
public class CheckContainer implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String accreditId = StringManage.FS_EMPTY;// 授权代码,引用(corp_accredit.accredit_id)
	protected String busiId = StringManage.FS_EMPTY;// 承运单号,引用(busi_base.busi_id)
	protected int sequence = 1;// 柜序号
	protected int isDoubleCon = 1;// 第几个柜
	protected String containerNo = StringManage.FS_EMPTY;// 箱号
	protected String bookingNo = StringManage.FS_EMPTY;// 提单号
	protected String ship = StringManage.FS_EMPTY;// 英文船名
	protected String voyage = StringManage.FS_EMPTY;// 航次
	protected String returnConPile = StringManage.FS_EMPTY;// 码头(dock)就是界面上的还柜堆场
	protected String containerType = StringManage.FS_EMPTY;// 尺寸类型
	protected String receiveTime = StringManage.FS_EMPTY;// 信息收到时间,格式:YYYY-MM-DD hh:mm
	protected String enterTime = StringManage.FS_EMPTY;// 进门时间,格式:YYYY-MM-DD hh:mm
	protected String sealNo = StringManage.FS_EMPTY; // 铅封号
	protected String weight = StringManage.FS_EMPTY;// 箱毛重
	protected String loadAnchor = StringManage.FS_EMPTY;// 卸货港
	protected String fecthTime = StringManage.FS_EMPTY;// 信息收到时间,格式:YYYY-MM-DD hh:mm
	protected String containerState = StringManage.FS_EMPTY; // 箱状态
	protected String containerOwner = StringManage.FS_EMPTY;// 箱主
	protected int piece = 0;// 件数
	protected double goodsWeight = 0; // 货物重量
	protected double cubage = 0; // 体积
	protected String goodsName = StringManage.FS_EMPTY; // 货名

	protected int isChecked = 0;// 是否验证 0未验证 1未查到记录就是未进港 2验证不通过 3验证通过

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

	public int getIsDoubleCon() {
		return isDoubleCon;
	}

	public void setIsDoubleCon(int isDoubleCon) {
		this.isDoubleCon = isDoubleCon;
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

	public String getReturnConPile() {
		return returnConPile;
	}

	public void setReturnConPile(String returnConPile) {
		if (returnConPile != null) {
			this.returnConPile = returnConPile;
		}
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

}