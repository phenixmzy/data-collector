package com.phenix.bigdata.charging.collector.persistent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.phenix.bigdata.charging.collector.schema.BusinessSpaceConsumedSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.phenix.bigdata.charging.collector.schema.BusinessSpaceConsumedDetail;

public class FileHDFSSpaceConsumedPersistent {
	private static final Logger logger = LoggerFactory.getLogger(FileHDFSSpaceConsumedPersistent.class);
	private Properties propertys;
	private String summaryOutput;
	private String detailOutput;
	
	public FileHDFSSpaceConsumedPersistent(Properties propertys) {
		this.propertys = propertys;
	}
	
	public FileHDFSSpaceConsumedPersistent(String summaryOutput, String detailOutput) {
		this.summaryOutput = summaryOutput;
		this.detailOutput = detailOutput;
	}

	public void addSummaryList(List<BusinessSpaceConsumedSummary> resultList) {
		StringBuilder builder = new StringBuilder();
		JSONObject json = new JSONObject();
		for (BusinessSpaceConsumedSummary bsc : resultList) {
			json.put("consumed_date", bsc.getDate());
			json.put("business_name", bsc.getBusinessName());
			json.put("business_sub_name", bsc.getSubBusinessName());
			json.put("space_consumed", bsc.getSpaceConsumed());
			builder.append(json.toJSONString()).append("\n");
		}
		writeToFile(builder,summaryOutput);
	}
	
	public void addDetailList(List<BusinessSpaceConsumedDetail> resultList) {
		StringBuilder builder = new StringBuilder();
		JSONObject json = new JSONObject();
		for (BusinessSpaceConsumedDetail bscd : resultList) {
			json.put("consumed_date", bscd.getDate());
			json.put("business_name", bscd.getBusinessName());
			json.put("business_sub_name", bscd.getSubBusinessName());
			json.put("space_consumed", bscd.getSpaceConsumed());
			json.put("dir", bscd.getDir());
			builder.append(json.toJSONString()).append("\n");
		}
		writeToFile(builder, detailOutput);
	}
	
	private void writeToFile(StringBuilder builder, String distDir) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(distDir));
			bw.write(builder.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.flush();
					bw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}
	}
}