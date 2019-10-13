package com.phenix.bigdata.charging.collector.schema;

public class BusinessSpaceConsumedSummary {
	private String date;
	private String businessName;
	private String subBusinessName;
	private long spaceConsumed;
	
	public BusinessSpaceConsumedSummary() {}
	
	public BusinessSpaceConsumedSummary(String date, String businessName, String subBusinessName) {
		this.date = date;
		this.businessName = businessName;
		this.subBusinessName = subBusinessName;
	}
	
	public BusinessSpaceConsumedSummary(String date, String businessName, String subBusinessName, long spaceConsumed) {
		this.date = date;
		this.businessName = businessName;
		this.subBusinessName = subBusinessName;
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
	
	public long getSpaceConsumed() {
		return spaceConsumed;
	}	
	
	public void setSpaceConsumed(long spaceConsumed) {
		this.spaceConsumed = spaceConsumed;
	}
}