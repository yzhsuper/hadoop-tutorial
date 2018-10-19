package com.tutorial.hadoop.example;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class UserOrderCount {
    //继承mapper接口，设置map的输入类型为<Object,Text>
    //输出类型为<Text,IntWritable>
    public static class UserOrderCountMapper extends Mapper<Object,Text,Text,IntWritable>{
        //one表示单词出现一次
        private static IntWritable one = new IntWritable(1);
        //word存储切下的单词
        private Text word = new Text();
        public void map(Object key,Text value,Context context) throws IOException,InterruptedException{
            //对输入的行切
        	String line = value.toString();
        	String[] c = line.substring(0, 40).split("\"");
        	String user_id = c[3].trim();
        	word.set(user_id);
    		context.write(word, one);
        }
    }
    //继承reducer接口，设置reduce的输入类型<Text,IntWritable>
    //输出类型为<Text,IntWritable>
    public static class UserOrderCountReduce extends Reducer<Text,IntWritable,Text,IntWritable>{
        //result记录单词的频数
        private static IntWritable result = new IntWritable();
        public void reduce(Text key,Iterable<IntWritable> values,Context context) throws IOException,InterruptedException{
            int sum = 0;
            //对获取的<key,value-list>计算value的和
            for(IntWritable val:values){
                sum += val.get();
            }
            //将频数设置到result
            result.set(sum);
            //收集结果
            context.write(key, result);
        }
    }
    
    public static void run() throws Exception {

		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://localhost:9000");
		conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        String[] args = {"/input/2018-10-11.txt", "/output/userorder"};
        FileSystem fileSystem = FileSystem.get(conf);
		fileSystem.delete(new Path(args[1]), true);
		
		// 配置作业名
		Job job = Job.getInstance(conf, "userOrderCount");
		// 配置作业各个类
		job.setJarByClass(UserOrderCount.class);
		job.setMapperClass(UserOrderCountMapper.class);
		job.setCombinerClass(UserOrderCountReduce.class);
		job.setReducerClass(UserOrderCountReduce.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        UserOrderCount.run();
    }

}
