/**
 * @(#)UserFetch.java     2016-01-21
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.dao.entity.data.userFetch;

import com.kingsoft.control.util.StringManage;
import com.kingsoft.control.exception.DataBaseException;
import com.kingsoft.control.database.MappingTableModel;

/**
 * 彭腾用户表
 *
 * @author kingsoft
 *
 * @version 2016-01-21
 * 
 * @since JDK 1.8.0_65
 * 
 */

public class UserFetch implements MappingTableModel{

	private static final long serialVersionUID = 1L;	

	private static final String $MAPPING_TABLE_NAME="sys_user_fetch";//数据库表名称

	protected int count=0;//分页列表中的序号
	protected int id=Integer.MIN_VALUE;//主键递增
	protected int userId=0;//用户Id
	protected String loginId=StringManage.FS_EMPTY;//登陆Id
	protected String password=StringManage.FS_EMPTY;//密码
	protected String email=StringManage.FS_EMPTY;//邮箱
	protected String accreditId=StringManage.FS_EMPTY;//云编号
	
	
	public UserFetch(){
	
	}

	

	public String getAccreditId() {
		return accreditId;
	}

	public void setAccreditId(String accreditId) {
		this.accreditId = accreditId;
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

	public int getUserId(){
		return userId;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public String getLoginId(){
		return loginId;
	}

	public void setLoginId(String loginId){
		if(loginId != null){
			this.loginId = loginId;
		}
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		if(password != null){
			this.password = password;
		}
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		if(email != null){
			this.email = email;
		}
	}

	public final String mappingTableName(){
		return $MAPPING_TABLE_NAME;
	}

	public void clone(MappingTableModel model) {
		if(!(model instanceof UserFetch)){return;}
		UserFetch obj = (UserFetch)model;
		if(obj != null){
			obj.count=count;
			obj.id = id;
			obj.userId = userId;
			obj.loginId = loginId;
			obj.password = password;
			obj.email = email;
		}
	}

	public void validate() throws DataBaseException{
		if(StringManage.isEmpty(loginId)){
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,"登陆Id 不能为空.");
		}
		if(StringManage.isEmpty(password)){
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,"密码 不能为空.");
		}
		if(StringManage.isEmpty(email)){
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,"邮箱 不能为空.");
		}

	}
}

