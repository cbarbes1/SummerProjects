import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;

import org.apache.hadoop.mapreduce.*;
import java.lang.Math;

public class PIEstimate{
	public static double PIFormula( int index){
		//create a temp value of k
		int k = index;
		// return the given value of pi at the nth position
		return (1.0/Math.pow(16, k))*((4.0/(8*k+1))-(2.0/(8*k+4))-(1.0/(8*k+5))-(1.0/(8*k+6)));
	}

	public static class valueMapper extends Mapper<Object, Text, IntWritable, DoubleWritable>{
		public void map(Object key, Text Value, Context context) throws IOException, InterruptedException {
			int index = Integer.parseInt(Value.toString());
			context.write(index, new DoubleWritable(PIFormula(index.get()));
		}
	}

	public static class valueReducer extends Reducer<IntWritable, DoubleWritable, IntWritable, DoubleWritable>{
		public void reduce(Iterable<IntWritable indices, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
			double sum = 0.0;
			for(DoubleWritable val : values){
				sum += val.get();
			}
			int count = 0;
			for(IntWritable index : indices){
				count += index.get();
			}

			context.write(new IntWritable(count), new DoubleWritable(sum));
		}
	}
	public static void main(String[] argv) throws Exception {



	

