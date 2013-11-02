package kmeans.rd;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class KmeansPoint implements WritableComparable<KmeansPoint> {
	//其会当作hadoop中map函数的输出，所以，应该实现WritableComparable<T>
	//在WritableComparable<T>之中，这个T是要比较的数据类型，因为WritableComparable<T>
	//实现的是Comparable<T>所以，而Comparable<T>是java本身的数据结构，这个接口好神奇
	//当然你也可以实现这两个接口Comparable<T>, Writable，只要你不怕麻烦
	
	//java语言简单问题：java是可以实现多个接口的
	
	//用@Override来对函数进行重写（屏蔽原函数），而不是实现函数的重载，所以在这个类中
	//要重写的是三个函数，分别是Writable接口中的write和readFields，还有Comparable
	//中的compareTo函数
	
	//程序逻辑方面呢,首先我们要看点的数据都有什么，首先点的数据是要有每个维度的值的。这个
	//我们可以放在一个hadoop内置的数据类型Text里维护todo
	
	private Text point;
	
	public KmeansPoint() {
		// TODO 自动生成的构造函数存根
		this.point=new Text();
	}
	
	@Override
	public void readFields(DataInput di) throws IOException{
		//java语言简单问题：擦，这里为什么还非得是IOExeption呢
		//               一旦有try-catch的报错了，就先抛个异常吧
		
		//只要不是java内置的数据类型，都可以在函数中直接写成员变量的readFields函数
		point.readFields(di);
	}
	
	@Override
	public void write(DataOutput do_) throws IOException{
		//只要不是java内置的数据类型，都可以在函数中直接写成员变量的write函数
		//但是如果你要write一个int或者long这些java内置的数据类型了，就乖乖的
		//将writeInt或writeLong这样的函数写上吧
		point.write(do_);
	}
	
	@Override
	public int compareTo(KmeansPoint kp){
		//还是那个道理，如果是java的内置数据类型，你就要自己去写compareTo函数了，但是
		//这个地方是hadoop的内置数据结构已经给你实现了的
		
		//todo这里应该就是hadoop内部实现的比较器
		return point.compareTo(kp.point);
	}
	
}
