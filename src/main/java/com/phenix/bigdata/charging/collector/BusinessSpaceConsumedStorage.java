package com.phenix.bigdata.charging.collector;

import java.util.LinkedList;
import java.util.List;

import com.phenix.bigdata.charging.collector.schema.BusinessSpaceConsumedDetail;
import com.phenix.bigdata.charging.collector.schema.BusinessSpaceConsumedSummary;

public class BusinessSpaceConsumedStorage {
	private List<BusinessSpaceConsumedDetail> details = new LinkedList<BusinessSpaceConsumedDetail>();
	
	private List<BusinessSpaceConsumedSummary> summarys = new LinkedList<BusinessSpaceConsumedSummary>();
	
	public void addDetail(BusinessSpaceConsumedDetail detail) {
		this.details.add(detail);
	}
	
	public void addSummary(BusinessSpaceConsumedSummary summary) {
		this.summarys.add(summary);
	}

	public List<BusinessSpaceConsumedDetail> getDetails() {
		return details;
	}

	public List<BusinessSpaceConsumedSummary> getSummarys() {
		return summarys;
	}	
}
