import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.*;

/**
 * Receives unsorted data from master pi
 * @author Pavan Kumar
 * @author Ajinkya
 *
 */
public class ReceiveFrommaster extends Thread {

	BlockingQueue<ArrayList<Integer>> buffer;
	Socket skt;
	ServerSocket sr_skt ;
	ObjectInputStream inp;
	boolean check;
	
	/**
	 * Constructor for the class
	 * @param port
	 * @param buffer Buffer for listening the received data
	 */
	public ReceiveFrommaster(int port, BlockingQueue<ArrayList<Integer>> buffer){
	
		try {
			
			this.sr_skt = new ServerSocket(port);
		
			this.skt = sr_skt.accept();
			this.inp = new ObjectInputStream(skt.getInputStream());
			this.buffer = buffer;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives data object from master
	 */
	public void run(){
		
		
		int count = 0;
		check = true;
		
		while(check){
			
			try {
				
				ArrayList<Integer> temp = (ArrayList<Integer>) inp.readObject();
				buffer.put(temp);
				System.out.println(temp);
				count++;
				
				if(count == MainProgram.chunk_number){
					check = false;
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			skt.close();
			inp.close();
			System.out.println("END");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
