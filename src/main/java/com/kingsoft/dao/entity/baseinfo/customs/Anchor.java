/**
 * @(#)Anchor.java     2010-12-27
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.dao.entity.baseinfo.customs;

import com.kingsoft.control.util.StringManage;
import com.kingsoft.control.exception.DataBaseException;
import com.kingsoft.control.database.MappingTableModel;

/**
 * 停泊信息
 *
 * @author kingsoft
 *
 * @version 2010-12-27
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */

public class Anchor implements MappingTableModel{

	private static final long serialVersionUID = 1L;	

	private static final String $MAPPING_TABLE_NAME="base_anchor";//数据库表名称

	protected int count=0;//分页列表中的序号
	protected int id=Integer.MIN_VALUE;//流水号
	protected String dock=StringManage.FS_EMPTY;//码头
	protected String nameEn=StringManage.FS_EMPTY;//英文船名
	protected String nameCn=StringManage.FS_EMPTY;//中文船名
	protected String csi=StringManage.FS_EMPTY;//船代码
	protected String voyageOut=StringManage.FS_EMPTY;//出口航次
	protected String voyageIn=StringManage.FS_EMPTY;//进口航次
	protected String harborTimeStart=StringManage.FS_EMPTY;//开港时间
	protected String harborTimeEnd=StringManage.FS_EMPTY;//截港时间
	protected String pullTime=StringManage.FS_EMPTY;//靠泊时间
	protected String shoveOffTime=StringManage.FS_EMPTY;//开船时间
	protected String regionId=StringManage.FS_EMPTY;//地区

	public Anchor(){
	
	}
	
	public int getCount(){
		return count;
	}

	public void setCount(int count){
		this.count=count;
	}

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getDock(){
		return dock;
	}

	public void setDock(String dock){
		if(dock != null){
			this.dock = dock;
		}
	}

	public String getNameEn(){
		return nameEn;
	}

	public void setNameEn(String nameEn){
		if(nameEn != null){
			this.nameEn = nameEn;
		}
	}

	public String getNameCn(){
		return nameCn;
	}

	public void setNameCn(String nameCn){
		if(nameCn != null){
			this.nameCn = nameCn;
		}
	}

	public String getCsi(){
		return csi;
	}

	public void setCsi(String csi){
		if(csi != null){
			this.csi = csi;
		}
	}

	public String getVoyageOut(){
		return voyageOut;
	}

	public void setVoyageOut(String voyageOut){
		if(voyageOut != null){
			this.voyageOut = voyageOut;
		}
	}

	public String getVoyageIn(){
		return voyageIn;
	}

	public void setVoyageIn(String voyageIn){
		if(voyageIn != null){
			this.voyageIn = voyageIn;
		}
	}

	public String getHarborTimeStart(){
		return harborTimeStart;
	}

	public void setHarborTimeStart(String harborTimeStart){
		if(harborTimeStart != null){
			this.harborTimeStart = harborTimeStart;
		}
	}

	public String getHarborTimeEnd(){
		return harborTimeEnd;
	}

	public void setHarborTimeEnd(String harborTimeEnd){
		if(harborTimeEnd != null){
			this.harborTimeEnd = harborTimeEnd;
		}
	}

	public String getPullTime(){
		return pullTime;
	}

	public void setPullTime(String pullTime){
		if(pullTime != null){
			this.pullTime = pullTime;
		}
	}

	public String getShoveOffTime(){
		return shoveOffTime;
	}

	public void setShoveOffTime(String shoveOffTime){
		if(shoveOffTime != null){
			this.shoveOffTime = shoveOffTime;
		}
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		if (regionId != null) {
			this.regionId = regionId;
		}
	}

	public final String mappingTableName(){
		return $MAPPING_TABLE_NAME;
	}

	public void clone(MappingTableModel model) {
		if(!(model instanceof Anchor)){return;}
		Anchor obj = (Anchor)model;
		if(obj != null){
			obj.count=count;
			obj.id = id;
			obj.dock = dock;
			obj.nameEn = nameEn;
			obj.nameCn = nameCn;
			obj.csi = csi;
			obj.voyageOut = voyageOut;
			obj.voyageIn = voyageIn;
			obj.harborTimeStart = harborTimeStart;
			obj.harborTimeEnd = harborTimeEnd;
			obj.pullTime = pullTime;
			obj.shoveOffTime = shoveOffTime;
			obj.regionId = regionId;
		}
	}

	public void validate() throws DataBaseException{
		if(StringManage.isEmpty(dock)){
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,"码头 不能为空.");
		}
		if(StringManage.isEmpty(nameEn)){
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,"英文船名 不能为空.");
		}

	}
}