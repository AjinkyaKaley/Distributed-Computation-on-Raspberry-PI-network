


import java.io.BufferedReader;
import java.net.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 
 * This is the master code, program beings here
 * @author Ajinkya
 *
 */
public class Master {


	static boolean done=false; // variable to indicate reading and sending is done
	static int chunk_length = 1000;	// determines the chunk size
	static int number_of_lines;	// Numbr of lines in file
	static int num_of_slaves = 1;
	static boolean start_couting_average = false;


	static Map<Integer,Connection_info> slaves_address = new HashMap<Integer,Connection_info>();

	/**
	 * This block parses the files to count the lines
	 */
	static {
		
		Scanner sc = new Scanner(System.in);
		String file_name = sc.nextLine();
		System.out.println(file_name);
		File f = new File(file_name);
		String line;
		int count=0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			while((line = br.readLine())!=null){
				count++;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		number_of_lines = count;
	}


	/**
	 * This is main program
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {

		/*
		 * It reads the ip and required ports from the file ip_port.txt
		 */
		BufferedReader br = new BufferedReader(new FileReader(new File ("Ip_port.txt")));
		String ip;
		int num_of_ip = 0;
		try {
			while((ip=br.readLine())!=null){
				String [] temp = ip.split(",");
				slaves_address.put(num_of_ip++, new Connection_info(num_of_ip++, temp[0], 
						Integer.parseInt(temp[1].trim()), Integer.parseInt(temp[2].trim()), Integer.parseInt(temp[2].trim())));
			}
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ExecuteSSH e = new ExecuteSSH();

		// Calculates the number of chunks to be made
		int num_of_lines_per_slave = number_of_lines / num_of_slaves;

		/**
		 * 
		 * Call function to establish the connection
		 */
		establish_the_connection();


		ArrayList<Thread> thread_pool = new ArrayList<Thread>();
		Reading_file r = null;

		/**
		 * Starts the threads for reading 
		 */

		for(int i =0; i< num_of_slaves; i++){

			int start = i*num_of_lines_per_slave;
			int end = start + num_of_lines_per_slave;	
			try {
				String thread_name = String.valueOf(i);
				Connection_info  to_read = slaves_address.get(Integer.valueOf(i));
				r = new Reading_file( thread_name, start, end, to_read.ip, 
						to_read.port_for_sending, chunk_length);

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Thread th1 = r;
			thread_pool.add(th1);

		}

		for(Thread t : thread_pool)
		{
			t.start();
			try {
				t.join();		// make the main to wait 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



		/**
		 * sending the msg to indicate reading is done
		 */
		if(done){


			try {
				String send_complete = "COMPLETE"; 
				Socket DONE = new Socket("10.10.10.121" , 10000);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(DONE.getOutputStream()));
				bw.write(send_complete);
				bw.close();

				/*Socket DONE_1 = new Socket("10.10.10.122" , 10000);
				BufferedWriter bw_1 = new BufferedWriter(new OutputStreamWriter(DONE_1.getOutputStream()));
				bw_1.write(send_complete);
				bw_1.close();

				Socket DONE_2 = new Socket("10.10.10.133" , 10000);
				BufferedWriter bw_2 = new BufferedWriter(new OutputStreamWriter(DONE_2.getOutputStream()));
				bw_2.write(send_complete);
				bw_2.close();

				Socket DONE_3 = new Socket("10.10.10.134" , 10000);
				BufferedWriter bw_3 = new BufferedWriter(new OutputStreamWriter(DONE_3.getOutputStream()));
				bw_3.write(send_complete);
				bw_3.close();

				Socket DONE_4 = new Socket("10.10.10.136" , 10000);
				BufferedWriter bw_4 = new BufferedWriter(new OutputStreamWriter(DONE_4.getOutputStream()));
				bw_4.write(send_complete);
				bw.close();

				Socket DONE_5 = new Socket("10.10.10.137" , 10000);
				BufferedWriter bw_5 = new BufferedWriter(new OutputStreamWriter(DONE_5.getOutputStream()));
				bw_5.write(send_complete);
				bw_5.close();*/
/*
				Socket DONE_6 = new Socket("10.10.10.137" , 10000);
				BufferedWriter bw_6 = new BufferedWriter(new OutputStreamWriter(DONE_6.getOutputStream()));
				bw_6.write(send_complete);
				bw_6.close();*/


			} catch (ConnectException ce) {
			}
			catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} 
			start_receiveing();
		}

		if(start_couting_average){
			Calculate_average.Average();
		}
	}



	private static void start_receiveing() {
		// TODO Auto-generated method stub
		BlockingQueue<Elements_identifier> shared_que = new LinkedBlockingQueue<Elements_identifier>();
		Receive_Slave r = new Receive_Slave(shared_que);
		Object synchronized_object = new Object();


		try {
			Slave  s1 = new Slave("0",15002,synchronized_object, shared_que);
			/*Slave  s2 = new Slave("1",15002,synchronized_object, shared_que);
			Slave  s3 = new Slave("2",15002,synchronized_object, shared_que);
			Slave  s4 = new Slave("3",15002,synchronized_object, shared_que);
			Slave  s5 = new Slave("4",15002,synchronized_object, shared_que);
			Slave  s6 = new Slave("5",15002,synchronized_object, shared_que);

*/
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.start();
		try {
			r.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		start_couting_average = true;

	}


	/**
	 * 
	 * This function is the first point of contact with the slaves,
	 * it tells the slvaes that how many chunks to expect
	 */


	private static void establish_the_connection() {

		Iterator it = slaves_address.entrySet().iterator();

		while(it.hasNext()){
			Map.Entry<Integer, Connection_info> info = (Map.Entry<Integer, Connection_info>)it.next();
			Connection_info obj = info.getValue();

			Socket temp_soc;
			try {
				temp_soc = new Socket(obj.ip, obj.port_for_establishing_connection);
				ObjectOutputStream out = new ObjectOutputStream(temp_soc.getOutputStream());
				out.writeObject((Integer.valueOf((number_of_lines / (num_of_slaves * chunk_length)))));
				out.close();
				temp_soc.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}

