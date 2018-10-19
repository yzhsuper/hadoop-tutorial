package com.tutorial.hadoop.hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSUtils {
	
	private HDFSUtils() {}
	
	private static String HADOOP_PATH = "hdfs://localhost:9000";
	private static FileSystem fileSystem;
	
	static {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", HADOOP_PATH);
		conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
		try {
			fileSystem = FileSystem.get(conf);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	// 删除文件
    public static boolean deleteFile(String dest) throws IllegalArgumentException, IOException {
        boolean success = fileSystem.delete(new Path(dest), true);
        return success;
    }
}
