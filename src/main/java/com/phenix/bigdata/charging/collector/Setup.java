package com.phenix.bigdata.charging.collector;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Setup {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 2) {
			return;
		}
		String outputDate = args[0];
		String configDir = args[1];
		String configFile = configDir + "/config.properties";
		Setup setup = new Setup();
		run(outputDate, configDir, configFile);
	}
	
	public static void run(String outputDate, String configDir, String configFile) {
		Properties properties = new Properties();
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(configFile));
			properties.load(in);
			properties.put("business.path", configDir + "/business.hdfs.path");
			HDFSSpaceConsumedCollectService service = new HDFSSpaceConsumedCollectService(properties);
			service.addDirPathsSpaceConsumed(outputDate);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}