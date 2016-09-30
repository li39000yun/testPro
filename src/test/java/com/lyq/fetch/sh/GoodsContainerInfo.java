package com.lyq.fetch.sh;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 箱货信息核对
 * 
 * @author kangweishui
 * 
 * @version 2015年8月3日
 * 
 * @since JDK 1.6
 * 
 */
public class GoodsContainerInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String bookingNo = StringManage.FS_EMPTY;// 提单号
	protected String etBookingNo = StringManage.FS_EMPTY;// 亿通提单号
	protected String etShip = StringManage.FS_EMPTY;// 亿通英文船名
	protected String etVoyage = StringManage.FS_EMPTY;// 亿通航次
	protected String etDischarge = StringManage.FS_EMPTY;// 亿通卸港代码

	protected int piece = 0;// 件数
	protected int etPiece = 0;// 亿通件数
	protected int markPiece = 0;// 件数是否与亿通相同(0:不相同,1:相同)
	protected double goodsWeight = 0; // 货物重量
	protected double etGoodsWeight = 0; // 亿通货物重量
	protected int markGoodsWeight = 0;// 货物重量是否与亿通相同(0:不相同,1:相同)

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		if (bookingNo != null)
			this.bookingNo = bookingNo;
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

	public int getMarkPiece() {
		return markPiece;
	}

	public void setMarkPiece(int markPiece) {
		this.markPiece = markPiece;
	}

	public int getMarkGoodsWeight() {
		return markGoodsWeight;
	}

	public void setMarkGoodsWeight(int markGoodsWeight) {
		this.markGoodsWeight = markGoodsWeight;
	}

	public String getEtBookingNo() {
		return etBookingNo;
	}

	public void setEtBookingNo(String etBookingNo) {
		if (etBookingNo != null) {
			this.etBookingNo = etBookingNo;
		}
	}

	public String getEtShip() {
		return etShip;
	}

	public void setEtShip(String etShip) {
		if (etShip != null) {
			this.etShip = etShip;
		}
	}

	public String getEtVoyage() {
		return etVoyage;
	}

	public void setEtVoyage(String etVoyage) {
		if (etVoyage != null) {
			this.etVoyage = etVoyage;
		}
	}

	public String getEtDischarge() {
		return etDischarge;
	}

	public void setEtDischarge(String etDischarge) {
		if (etDischarge != null) {
			this.etDischarge = etDischarge;
		}
	}

	public int getEtPiece() {
		return etPiece;
	}

	public void setEtPiece(int etPiece) {
		this.etPiece = etPiece;
	}

	public double getEtGoodsWeight() {
		return etGoodsWeight;
	}

	public void setEtGoodsWeight(double etGoodsWeight) {
		this.etGoodsWeight = etGoodsWeight;
	}

}
