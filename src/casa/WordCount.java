package casa;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.StringTokenizer;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class WordCount
{
    public static void UseDistributedCacheBySymbolicLink() throws Exception
    {
        FileReader reader = new FileReader("god.txt");
        BufferedReader br = new BufferedReader(reader);
        String s1 = null;
        while ((s1 = br.readLine()) != null)
        {
            System.out.println(s1);
        }
        br.close();
        reader.close();
    }
    

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>
    {

        public void configure(JobConf job)
        {
            System.out.println("Now, use the distributed cache and syslink");
            try {
                UseDistributedCacheBySymbolicLink();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException
        {
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens())
            {
                word.set(tokenizer.nextToken());
                output.collect(word, one);
            }
        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>
    {
        public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException
        {
            int sum = 0;
            while (values.hasNext())
            {
                sum += values.next().get();
            }
            output.collect(key, new IntWritable(sum));
        }
    }

    public static void main(String[] args) throws Exception
    {
        JobConf conf = new JobConf(WordCount.class);
        conf.setJobName("wordcount");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);

        conf.setMapperClass(Map.class);
        conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        DistributedCache.createSymlink(conf);
        String path = "/home/casa/input";
        Path filePath = new Path(path);
        String uriWithLink = filePath.toUri().toString() + "#" + "god.txt";
        System.out.println("log");
        DistributedCache.addCacheFile(new URI(uriWithLink), conf);

        JobClient.runJob(conf);
    }
} 