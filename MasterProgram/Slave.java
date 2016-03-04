
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 
 * 
 * This class is for receiving the sorted data from the pis
 * and build the heap out of it
 * 
 * @author Ajinkya
 *
 */
public class Slave extends Thread{

	BlockingQueue<Elements_identifier> buffer;
	ServerSocket serve_skt;
	ObjectInputStream inp;
	ObjectOutputStream out;
	Socket skt;
	int port;
	String ip;
	boolean receive;
	public  Object o;
	public String slave_number;
	ArrayList<Integer> lst_for_test;
	Connections slave_connection;


	public Slave(String slave, int port, Object o, BlockingQueue<Elements_identifier> que) throws IOException{
		this.buffer = que;
		this.o = o;
		this.serve_skt = new ServerSocket(port);
		System.out.println("PORT: " + port);
		this.skt = serve_skt.accept();
		System.out.println("Accepted");
		this.inp = new ObjectInputStream(skt.getInputStream());
		this.out = new ObjectOutputStream(skt.getOutputStream());
		this.slave_number = slave;
	}

	public void run(){

		int lines_done = 0;
		int stopping_condition = Master.number_of_lines;


		System.out.println("Inside receiving run");


		ArrayList<Integer> recieve_chunk = null;


		while(lines_done <= stopping_condition){
			try {
				Thread.sleep(1000);
				recieve_chunk = (ArrayList<Integer>)inp.readObject();
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(Integer sorted_elements : recieve_chunk){
				Elements_identifier e = new Elements_identifier(sorted_elements, this.slave_number);
				try {
					buffer.put(e);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			lines_done++;
		}


	}


}
