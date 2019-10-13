package com.phenix.bigdata.charging.collector.persistent;

import java.util.List;

import com.phenix.bigdata.charging.collector.schema.BusinessSpaceConsumedDetail;
import com.phenix.bigdata.charging.collector.schema.BusinessSpaceConsumedSummary;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class HDFSSpaceConsumedPersistent {
	private static final Logger logger = LoggerFactory.getLogger(HDFSSpaceConsumedPersistent.class);
	private Configuration config;
	private String summaryFileName;
	private String detailFileName;

	public HDFSSpaceConsumedPersistent(Configuration config, String summaryFileName, String detailFileName) {
		this.config = config;
		this.summaryFileName = summaryFileName;
		this.detailFileName = detailFileName;
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
		
		try {
			writeToHDFS(this.summaryFileName, builder);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
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
		
		try {
			writeToHDFS(this.detailFileName, builder);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}
	
	private void writeToHDFS(String fileName, StringBuilder builder) throws Exception {
		System.out.println(fileName);
		FSDataOutputStream out = null;
		try {
			FileSystem fs = FileSystem.get(config);
			
			//out = fs.create(new Path(fileName));
			out = FileSystem.create(fs, new Path(fileName), new FsPermission("777"));
			out.write(builder.toString().getBytes());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			logger.error(ex.getMessage());
			throw ex;
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
}