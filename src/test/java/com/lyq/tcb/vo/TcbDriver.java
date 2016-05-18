package com.lyq.tcb.vo;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 司机信息数据传输对象
 * 
 * @author wangchao
 * 
 * @version 2015年11月10日
 * 
 * @since JDK 1.6
 * 
 */
public class TcbDriver implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String driver = StringManage.FS_EMPTY;// 司机名称
	protected String code = StringManage.FS_EMPTY;// 司机编号
	protected String accreditid = StringManage.FS_EMPTY;// 所属公司编号
	protected String teamcode = StringManage.FS_EMPTY;// 所属车队编号
	protected String sex = StringManage.FS_EMPTY;// 性别
	protected String idcard = StringManage.FS_EMPTY;// 身份证号
	protected String nation = StringManage.FS_EMPTY;// 民族
	protected String birthplace = StringManage.FS_EMPTY;// 贯籍
	protected String address = StringManage.FS_EMPTY;// 住址
	protected String mobilephone = StringManage.FS_EMPTY;// 手机号
	protected String telephone = StringManage.FS_EMPTY;// 联系电话
	protected String fax = StringManage.FS_EMPTY;// 传真
	protected String email = StringManage.FS_EMPTY;// 邮箱地址
	protected String urgentcontactphone = StringManage.FS_EMPTY;// 紧急联系人电话
	protected String dirverlicense = StringManage.FS_EMPTY;// 驾驶证号
	protected String qq = StringManage.FS_EMPTY;// qq
	protected String remark = StringManage.FS_EMPTY;// 备注

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccreditid() {
		return accreditid;
	}

	public void setAccreditid(String accreditid) {
		this.accreditid = accreditid;
	}

	public String getTeamcode() {
		return teamcode;
	}

	public void setTeamcode(String teamcode) {
		this.teamcode = teamcode;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrgentcontactphone() {
		return urgentcontactphone;
	}

	public void setUrgentcontactphone(String urgentcontactphone) {
		this.urgentcontactphone = urgentcontactphone;
	}

	public String getDirverlicense() {
		return dirverlicense;
	}

	public void setDirverlicense(String dirverlicense) {
		this.dirverlicense = dirverlicense;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

//	public TcbDriver clone(Driver obj) {
//		this.driver = obj.getName();
//		this.code = Integer.toString(obj.getId());
//		this.accreditid = obj.getAccreditId();
//		this.idcard = obj.getIdentityCard();
//		this.address = obj.getAddress();
//		this.mobilephone = obj.getMobilePhone();
//		this.telephone = obj.getTelephone();
//		this.fax = obj.getFax();
//		this.email = obj.getEmail();
//		this.dirverlicense = obj.getDirverLicense();
//		this.remark = obj.getRemark();
//		return this;
//	}
}
