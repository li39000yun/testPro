/**
 * @(#)Mail.java     2014-10-23
 *
 * Copyright 2011 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.lyq.test.mail;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 邮件信息对象
 * 
 * @author wangchao
 * 
 * @version 2014-10-23
 * 
 * @since JDK 1.6
 * 
 */
public class Mail implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	protected String subject = StringManage.FS_EMPTY;// 设置邮件主题
	protected String body = StringManage.FS_EMPTY;// 设置邮件正文
	protected String mailTo = StringManage.FS_EMPTY;// 设置邮件接收方的地址
	protected String filePath = StringManage.FS_EMPTY;// 文件地址
	protected String host = "smtp.163.com";// 邮箱服务器
	protected String user = StringManage.FS_EMPTY;// 邮箱用户
	protected String pass = StringManage.FS_EMPTY;// 邮箱密码
	protected String mailFrom = StringManage.FS_EMPTY;// 邮件发送方的地址
	protected String signature = StringManage.FS_EMPTY;// 签名信息

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		if (subject != null)
			this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		if (body != null)
			this.body = body;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		if (mailTo != null)
			this.mailTo = mailTo;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		if (filePath != null) {
			this.filePath = filePath;
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
		this.mailFrom = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}

