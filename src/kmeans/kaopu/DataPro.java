package kmeans.kaopu;

import java.io.DataInput;  
import java.io.DataOutput;  
import java.io.IOException;  
  
import org.apache.hadoop.io.IntWritable;  
import org.apache.hadoop.io.Text;  
import org.apache.hadoop.io.WritableComparable;  
  
public class DataPro implements WritableComparable<DataPro>{  
  
    private Text center;  
    private IntWritable count;  
      
    public DataPro(){  
        set(new Text(),new IntWritable());  
    }  
    public void set(Text text, IntWritable intWritable) {  
        // TODO Auto-generated method stub  
        this.center=text;  
        this.count=intWritable;  
    }  
      
    public Text getCenter(){  
        return center;  
    }  
    public IntWritable getCount(){  
        return count;  
    }  
      
      
    @Override  
    public void readFields(DataInput arg0) throws IOException {  
        // TODO Auto-generated method stub  
        center.readFields(arg0);  
        count.readFields(arg0);  
    }  
  
    @Override  
    public void write(DataOutput arg0) throws IOException {  
        // TODO Auto-generated method stub  
        center.write(arg0);  
        count.write(arg0);  
    }  
  
    @Override  
    public int compareTo(DataPro o) {  
        // TODO Auto-generated method stub  
        int cmp=count.compareTo(o.count);  
        if(cmp!=0){  
            return cmp;  
        }  
        return center.compareTo(o.center);  
    }  
  
} 