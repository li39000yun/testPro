package com.lyq.jkxtest.gznm;

public class Container {
	private String accreditId = "";
	private String busiId = "";
	private String sequence = "";
	private String otherReportId = "";
	private String otherPayoutUnitId = "";

	public Container() {
	}

	public Container(String accreditId, String busiId, String sequence, String otherReportId, String otherPayoutUnitId) {
		this.accreditId = accreditId;
		this.busiId = busiId;
		this.sequence = sequence;
		this.otherReportId = otherReportId;
		this.otherPayoutUnitId = otherPayoutUnitId;
	}

	public String toString2() {
		return "Container [accreditId=" + accreditId + ", busiId=" + busiId + ", sequence=" + sequence + ", otherReportId=" + otherReportId + ", otherPayoutUnitId=" + otherPayoutUnitId + "]";
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
		this.busiId = busiId;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getOtherReportId() {
		return otherReportId;
	}

	public void setOtherReportId(String otherReportId) {
		this.otherReportId = otherReportId;
	}

	public String getOtherPayoutUnitId() {
		return otherPayoutUnitId;
	}

	public void setOtherPayoutUnitId(String otherPayoutUnitId) {
		this.otherPayoutUnitId = otherPayoutUnitId;
	}
}




