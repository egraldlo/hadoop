package hdfs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

public class URLCat {

	static{
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputStream in=null;
		try {
			in=new URL(args[0]).openStream();
			IOUtils.copyBytes(in, System.out, 4096, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			IOUtils.closeStream(in);
		}
	}

}
