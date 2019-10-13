package com.phenix.bigdata.charging.collector;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phenix.bigdata.charging.collector.persistent.FileHDFSSpaceConsumedPersistent;
import com.phenix.bigdata.charging.collector.persistent.HDFSSpaceConsumedPersistent;
import com.phenix.bigdata.charging.collector.schema.BusinessSpaceConsumedDetail;
import com.phenix.bigdata.charging.collector.schema.BusinessSpaceConsumedSummary;
import com.phenix.bigdata.hdfs.util.HDFSUtil;

public class HDFSSpaceConsumedCollectService {
	BusinessSpaceConsumedStorage storage = new BusinessSpaceConsumedStorage();
	private static final Logger logger = LoggerFactory.getLogger(HDFSSpaceConsumedCollectService.class);
	
	private Properties propertys;
	private Configuration config;
	
	public HDFSSpaceConsumedCollectService(Properties propertys) {
		this.propertys = propertys;
		this.config = new Configuration();
		this.config.addResource(new Path(this.propertys.getProperty("hadoop.core.site")));
		this.config.addResource(new Path(this.propertys.getProperty("hadoop.hdfs.site")));
	}
	
	public void addDirPathsSpaceConsumed(String date) {
		try {
			Map<String,BusinessPaths> bs = getBusinessHdfsPath();
			Iterator<String> bsNames = bs.keySet().iterator();
			while(bsNames.hasNext()) {
				String name = bsNames.next();
				BusinessPaths bean = bs.get(name);
				ArrayList<String> dirPaths = bean.getPaths();
				long sum = 0;
				for (int i = 0, len = dirPaths.size(); i < len; i++) {
					long consumed = getSpaceConsumed(date, bean.getKey().getBusinessName(), bean.getKey().getBusinessSubName(), dirPaths.get(i));
					sum += consumed;
				}
				BusinessSpaceConsumedSummary spaceConsumedSummary = new BusinessSpaceConsumedSummary(date, bean.getKey().getBusinessName(), bean.getKey().getBusinessSubName(), sum);
				storage.addSummary(spaceConsumedSummary);
			}
			
			if (Boolean.parseBoolean(propertys.getProperty("persist.local"))){
				logger.info("write to local file");
				writeToLocal(date);
			}
			
			if (Boolean.parseBoolean(propertys.getProperty("persist.hdfs"))){
				logger.info("write to hdfs file");
				writeToHdfs(date);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}
	
	private void writeToLocal(String date) {
		String distDir = this.propertys.getProperty("output.path");
		String summaryOutput = String.format("%s/summary-spaceconsumed-%s.json", distDir, date);
		String detailOutput =  String.format("%s/detail-spaceconsumed-%s.json", distDir, date);
		FileHDFSSpaceConsumedPersistent persist = new FileHDFSSpaceConsumedPersistent(summaryOutput,detailOutput);
		persist.addSummaryList(storage.getSummarys());
		persist.addDetailList(storage.getDetails());
	}
	
	private void writeToHdfs(String date) {
		String distDir = this.propertys.getProperty("output.hdfs.spaceconsumed.dir");
		String summaryOutput = String.format("%s/summary-spaceconsumed/summary-spaceconsumed-%s.json", distDir, date);
		String detailOutput =  String.format("%s/detail-spaceconsumed/detail-spaceconsumed-%s.json", distDir, date);
		HDFSSpaceConsumedPersistent persist = new HDFSSpaceConsumedPersistent(this.config, summaryOutput, detailOutput);
		persist.addSummaryList(storage.getSummarys());
		persist.addDetailList(storage.getDetails());
	}
	
	private long getSpaceConsumed(String date, String name, String subName, String dirPath) throws Exception {
		FileSystem hdfs =FileSystem.get(config);
		long sum = 0;
		if (dirPath.contains("*")) {
			List<String> subDirPaths = HDFSUtil.getPaths(hdfs, dirPath);
			for (String sdp: subDirPaths) {
				 long consumed = getPathSpaceConsumed(hdfs, sdp);
				 sum += consumed;
				 BusinessSpaceConsumedDetail spaceConsumedDetail = new BusinessSpaceConsumedDetail(date, name, subName,sdp, consumed);
				 storage.addDetail(spaceConsumedDetail);
			}
			return sum;
		}
		return getPathSpaceConsumed(hdfs,dirPath);
	}
	
	private long getPathSpaceConsumed(FileSystem hdfs, String path) throws Exception {
        long consumed = HDFSUtil.getPathSpaceConsumed(hdfs, path);
        return consumed;
	}
	
	private Map<String,BusinessPaths> getBusinessHdfsPath() throws Exception {
		String file = this.propertys.getProperty("business.path");
		Map<String,BusinessPaths> businessMap = new HashMap<String, BusinessPaths>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			while((line=br.readLine()) !=null) {
				System.out.println(line);
				String[] pair = line.split(":");
				String businessName = pair[0];
				String businessSubName = "";
				String path = "null";
				if (pair.length == 2) {
					path = pair[1];
				} else {
					businessSubName = pair[1];
					path = pair[2];	
				}
				
				BusinessKey key = new BusinessKey(businessName,businessSubName);
				BusinessPaths bp = businessMap.get(key.getName());
				if (bp == null) {
					bp = new BusinessPaths(key);
				}
				bp.addPath(path);
				businessMap.put(key.getName(), bp);
			}			
			return businessMap;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}
	
	private class BusinessPaths {
		private BusinessKey key;
		private ArrayList<String> paths = new ArrayList<String>();
		
		public BusinessPaths(BusinessKey key) {
			this.key = key;
		}
		
		public BusinessKey getKey() {
			return key;
		}
		
		public void addPath(String path) {
			this.paths.add(path);
		}
		
		public ArrayList<String> getPaths() {return this.paths;}
	}
	
	private class BusinessKey {
		private String businessName;
		private String businessSubName;
		
		public BusinessKey(String businessName, String businessSubName) {
			this.businessName = businessName;
			this.businessSubName = businessSubName;
		}
		
		public String getBusinessName() {
			return businessName;
		}

		public String getBusinessSubName() {
			return businessSubName;
		}
		
		public String getName() {
			return this.businessName + ":" + this.businessSubName;
		}
	}
}
