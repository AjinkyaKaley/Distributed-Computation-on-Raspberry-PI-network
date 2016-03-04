


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
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
			BufferedReader br = new BufferedReader(new FileReader(Master.file_name));


			String line;		
			int count=0;
			while(count < start){
				br.readLine();
				count++;
			}
			
			Socket s = null;
			ObjectOutputStream o = null;
			ObjectInputStream i =null;
			System.out.println("Reading_file ip " + this.ip + " port "+ this.port);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			s = new Socket(this.ip , this.port);
			o =new ObjectOutputStream(s.getOutputStream());
			i = new ObjectInputStream(s.getInputStream());

			chunk = new ArrayList<Integer>();

			while(count<end){

				chunk.add(Integer.valueOf(br.readLine()));
				if(chunk.size()==chunk_length)
				{
					
					o.writeObject(chunk);
					String send = (String)i.readObject();
					System.out.println(send);
					if(!send.equals("ok")){
						throw new Exception();
					}
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();

			// TODO Auto-generated catch block
			
			
			e.printStackTrace();
			System.out.println("HERE IT IS: CRASHED");
			int port = this.port;
			String ip = this.ip;
			int start = this.start;
			int end = this.end;
			int c = this.chunk_length;
			String id = this.thr_name;
			
			try {
				this.sleep(4000);
				
				if(ip.equals("10.10.10.121")){
					Runtime.getRuntime().exec("./restart_pi_121.sh");
				}
				
				else if(ip.equals("10.10.10.122")){
					Runtime.getRuntime().exec("./restart_pi_122.sh");
				}
				else if(ip.equals("10.10.10.133")){
					Runtime.getRuntime().exec("./restart_pi_133.sh");
				}
				else if(ip.equals("10.10.10.134")){
					Runtime.getRuntime().exec("./restart_pi_134.sh");
				}
				else if(ip.equals("10.10.10.136")){
					Runtime.getRuntime().exec("./restart_pi_136.sh");
				}
				else if(ip.equals("10.10.10.137")){
					Runtime.getRuntime().exec("./restart_pi_137.sh");
				}
			} catch (InterruptedException | IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			System.out.println("HELo");
			try {
				Reading_file r = new Reading_file(id, start, end, ip, port, c);
				r.start();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			//e.printStackTrace();
		
		}
	}
}
