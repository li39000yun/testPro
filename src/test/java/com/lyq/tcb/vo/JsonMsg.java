package com.lyq.tcb.vo;

import java.io.Serializable;

public class JsonMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int ret = 0;
	protected String msg = "";
	protected String data = "";

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
