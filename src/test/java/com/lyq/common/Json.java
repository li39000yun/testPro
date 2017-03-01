package com.lyq.common;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * Json模型
 * 
 * @author liyunqiang
 * @version 2016年2月17日
 */
public class Json implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean success = false;
	private String msg = StringManage.FS_EMPTY;
	private Object obj = null;

	public Json() {
	}

	public Json(String msg) {
		this.msg = msg;
	}

	public Json(Boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

	public Json(Boolean success, String msg, Object obj) {
		this.success = success;
		this.msg = msg;
		this.obj = obj;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
