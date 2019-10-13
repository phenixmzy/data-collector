package com.phenix.bigdata.charging.collector.schema;

public class BusinessSpaceConsumedDetail {
	private String date;
	private String businessName;
	private String subBusinessName;
	private String dir;
	private long spaceConsumed;

	public BusinessSpaceConsumedDetail(String date, String businessName, String subBusinessName, String dir, long spaceConsumed) {
		this.date = date;
		this.businessName = businessName;
		this.subBusinessName = subBusinessName;
		this.dir = dir;
		this.spaceConsumed = spaceConsumed;
	}
	
	public String getDate() {
		return date;
	}

	public String getBusinessName() {
		return businessName;
	}

	public String getSubBusinessName() {
		return subBusinessName;
	}

	public String getDir() {
		return dir;
	}

	public long getSpaceConsumed() {
		return spaceConsumed;
	}
}
