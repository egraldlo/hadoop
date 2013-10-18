package kmeans.rd;

import java.lang.String;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class KData implements Writable{
	public String lag;
	public String pageName;
	public double array[];
	
	public static final int DIMENTION=20;
	
	public KData(){
		lag=new String("");
		pageName=new String("");
		array=new double[DIMENTION];
	}
	
	public static double getDist(KData kd1,KData kd2){
		double dist=0.0;
		for(int i=0;i<DIMENTION;i++){
			dist+=(kd1.array[i]-kd2.array[i])*(kd1.array[i]-kd2.array[i]);
		}
		return Math.sqrt(dist);
	}
	
	@Override
    public void readFields(DataInput in) throws IOException {
        String str[]=in.readUTF().split("\\s+");
        for(int i=0;i<DIMENTION;++i)
            array[i]=Double.parseDouble(str[i]);
    }

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		  out.writeUTF(this.toString());
	}
	
}
