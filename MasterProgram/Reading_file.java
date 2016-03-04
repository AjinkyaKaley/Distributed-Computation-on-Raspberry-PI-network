


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Reading_file extends Thread{

	String thr_name;
	int start, end;
	Stack<Integer> s;
	ObjectOutputStream out;
	Socket send;
	BlockingQueue<Integer> que;
	ArrayList<Integer> chunk;
	int  chunk_length;
	//Map<String,Pair> slaves = null;
	String ip;
	int port;

	public Reading_file(String th_name, int start, int end, String Slave_ip,int port ,int c) throws UnknownHostException, IOException
	{
		this.thr_name = th_name;
		this.start = start;
		this.end = end;
		this.chunk = new ArrayList<Integer>();
		//this.slaves = s;
		this.ip = Slave_ip;
		this.port = port;
		this.chunk_length = c;

	}
	
	/**
	 * 
	 * This code reads the data from usig threads that are running parallel
	 */

	public void run()
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader("RandomNumbers1000.txt"));


			String line;		
			int count=0;
			while(count < start){
				br.readLine();
				count++;
			}
			
			Socket s = null;
			ObjectOutputStream o = null;

			System.out.println("Reading_file ip " + this.ip + " port "+ this.port);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			s = new Socket(this.ip , this.port);
			o =new ObjectOutputStream(s.getOutputStream());


			chunk = new ArrayList<Integer>();

			while(count<end){

				chunk.add(Integer.valueOf(br.readLine()));
				if(chunk.size()==chunk_length)
				{
					o.writeObject(chunk);
					start = count;
					System.out.println(chunk);
					chunk = new ArrayList<Integer>();

				}

				count++;

			}

			o.close();
			s.close();
			
			/**
			 * Indiciates the master that it has finised reading the whole file and sent it
			 */
			
			if(count == Master.number_of_lines){
				Master.done = true;
			}



		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
