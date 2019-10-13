
public class HDFSConf {
	/**
	  public static Configuration conf = null;
	    public static Configuration getConf(){
	        if (conf == null){
	            conf = new Configuration();
	            String path  = Constant.getSysEnv("HADOOP_HOME")+"/etc/hadoop/";
	            HDFSReadLog.writeLog("Get hadoop home : " + Constant.getSysEnv("HADOOP_HOME"));
	            // hdfs conf
	            conf.addResource(path+"core-site.xml");
	            conf.addResource(path+"hdfs-site.xml");
	            conf.addResource(path+"mapred-site.xml");
	            conf.addResource(path+"yarn-site.xml");
	        }
	        return conf;
	    }
	 */
}
