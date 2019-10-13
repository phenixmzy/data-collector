import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;


public class ReadHDFS {
    /**
	public static void main(String[]args){

        try {
            getFile(Constant.URI + Constant.PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getFile(String filePath) throws IOException {

        FileSystem fs = FileSystem.get(URI.create(filePath), HDFSConf.getConf());
        Path path = new Path(filePath);
        if (fs.exists(path) && fs.isDirectory(path)) {

            FileStatus[] stats = fs.listStatus(path);
            FSDataInputStream is;
            FileStatus stat;
            byte[] buffer;
            int index;
            StringBuilder lastStr = new StringBuilder();
            for(FileStatus file : stats){
                try{
                    is = fs.open(file.getPath());
                    stat = fs.getFileStatus(path);
                    int sum  = is.available();
                   
                    buffer = new byte[sum];

                    // 注意一点，如果文件太大了，可能会内存不够用。在本机测得时候，读一个100多M的文件，导致内存不够。
                    is.readFully(0,buffer);
                    String result = Bytes.toString(buffer);
                    // 写到 hbase
                    WriteHBase.writeHbase(result);
                    
                    is.close();
                    HDFSReadLog.writeLog("read : " + file.getPath() + " end");
                }catch (IOException e){
                    e.printStackTrace();
                    HDFSReadLog.writeLog("read " + file.getPath() +" error");
                    HDFSReadLog.writeLog(e.getMessage());
                }
            }
            HDFSReadLog.writeLog("Read End");
            fs.close();

        }else {
            HDFSReadLog.writeLog(path + " is not exists");
        }

    }
     */
}
