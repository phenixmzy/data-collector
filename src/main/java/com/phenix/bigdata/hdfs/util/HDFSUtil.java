package com.phenix.bigdata.hdfs.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HDFSUtil {
	private static final Logger logger = LoggerFactory.getLogger(HDFSUtil.class);
	
	public static long getPathSpaceConsumed(FileSystem hdfs, String path) throws Exception {
		Path filenamePath = new Path(path);
		try {
			long spaceConsumed = hdfs.getContentSummary(filenamePath).getSpaceConsumed();
			return spaceConsumed;
		} catch (IOException e) {
			throw e;
		}
	}

	public static long getPathLength(FileSystem hdfs, String path) throws Exception {
		Path filenamePath = new Path(path);
		try {
			long length = hdfs.getContentSummary(filenamePath).getLength();
			return length;
		} catch (IOException e) {
			throw e;
		}
	}
	
	public static List<String> getPaths(FileSystem hdfs, String dir) throws IOException {
		Path dirPath = new Path(dir);
		FileStatus[] status = hdfs.globStatus(dirPath);
		int size = status.length;
		List<String> paths = new ArrayList<String>(size);
		for (FileStatus stats : status) {
			paths.add(stats.getPath().toString());
		}
		return paths;
	}
}
