package kmeans.rd;

import java.io.IOException;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class KmeansMapper extends Mapper<LongWritable,Text,IntWritable,KmeansPoint>{
	//Mapper是一个类，提供了四个函数setup,map,run,cleanup四个函数，可以重写这四个函数
	//这里不同的版本是不一样的,并且确定，参数mapper中参数的输入和输出key-value的数据类型
	//Mapper参数分析：
	//第一个参数：
	//为mapper的key输入，因为mapper读每一个block然后将这个block按行读取
	//，所以第一个参数是LongWritable，我所知道的应该是不会变的
	//第二个参数：
	//为mapper输入的value，这个value就是每一行的数据，一般是string类型的
	//，因为hadoop中的string就是Text类型，所以我所知道的这个也是不变的
	//第三个参数，第四个参数：
	//可以想想，得到的输入是<行号，每一行的值>，这样就可以将这个mapper task
	//中要处理的数据都遍历一遍，具体怎么处理是你自由发挥的，所以你可以自己设置
	//你输出的<key,value>，可以自己定义数据结构，然后import到这个文件中，可
	//想而知，既然hadoop封装java的数据结构，定义自己的数据结构的时候是不能随
	//便定义的，要按照hadoop的规矩来，这里我们看一下接口：WritableComparable<T>
	//这个接口被实现了很多，这个接口按照他的名字就知道实现他可以有比较的功能，很
	//多的hadoop的的内置的数据结构都实现了这个接口，比如：BooleanWritable,
	//BytesWritable, ByteWritable, DocumentID, DoubleWritable, 
	//FloatWritable, ID, ID, IntWritable, JobID, JobID, Key, 
	//LongWritable, MD5Hash, MultiFileWordCount.WordOffset, 
	//NullWritable, Record, RecordTypeInfo, SecondarySort.
	//IntPair, Shard, TaskAttemptID, TaskAttemptID, TaskID, 
	//TaskID, Text, TypedBytesWritable, UTF8, VIntWritable, VLongWritable
	
	//由于本人不写java这里补充一点：接口--实现，基类--继承
	
	//为了完成kmeans程序，我们写一个数据结构，这个数据结构是表示kmeans中做操作的点
	//KmeansPoint.java中就是我写的用来做kmeans的数据结构
	
	@Override
	public void setup(Context context) throws IOException{
		//重写setup函数是在task初期被调用一次的函数，cleanup也是在map task初期被调用一次
		//但是map函数是在对个记录都调用一次map函数，map函数不是task仅被调用一次了，map是在每个
		//record都被调用一次。这样的话就setup会在map task中做一些前期工作，比如说分布式缓存
		//的文件读取，不然你要是将分布式缓存文件放在map中，那就是每个record调用map函数的时候都要
		//读一次分布式缓存了。
		
		//其实这个函数的主要作用是在将中间点从分布式缓存中读取，然后将这些点放在二维数组中，然后每个
		//map函数读取的key,value对将用这些点做比较计算
		
		//本函数的参数，cleanup函数和run函数的参数也都是context，上下文作为参数很好理解
		//上下文这个变量中包含Configuration,RecordReader,RecordWriter...
		
		// 注意参数是configuration
		Path[] caches=DistributedCache.getLocalCacheFiles(context.getConfiguration());
		
	}
	
	public void map(LongWritable key,Text value,Context context) throws IOException,InterruptedException{
		//这里的Context也是在前面提到过的，在map函数中主要是用来收集输出的record的个数
		
		//还有一个很重要的一个点，就是累计器的实现，其实站在系统实现的角度，累计器是一个像
		//分布式缓存一样必须实现的内容，在spark的论文中也特别的将这两块拿出来说
		
		//关于累加器，counter是每一个task都有一个，位于tasktracker，最后（或周期性地）
		//jobtracker汇总所有counter，但是计数器到底是怎么申明的todo
	}
	
}
