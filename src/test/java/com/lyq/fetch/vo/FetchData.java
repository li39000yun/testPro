package com.lyq.fetch.vo;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

public class FetchData implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String name = StringManage.FS_EMPTY;// 名称
	protected String value = StringManage.FS_EMPTY;// 内容

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

