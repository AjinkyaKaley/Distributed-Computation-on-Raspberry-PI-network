import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
 * This class is the entry point of Slave code.
 */
public class MainProgram {

	static int  chunk_number;
	static String HOME = "/home/pi/group2";
	public final static String MASTER_IP = "10.10.10.119";
	public static ServerSocket sc;
	public static void main(String[] args) {
		MainProgram mp = new MainProgram();
		mp.createDirectories();
		BlockingQueue<ArrayList<Integer>> buffer = new LinkedBlockingQueue<ArrayList<Integer>>();
		
		try {
			sc = new ServerSocket(15000);		// listenes
			Socket skt = sc.accept();
			System.out.println("Accept");
			ObjectInputStream inp = new ObjectInputStream(skt.getInputStream());
//			chunk_number = inp.readInt(); 
			Object o = inp.readObject();
			if (o instanceof Integer) {
				chunk_number = (Integer)o;		// gets the number of chunks to be received
			} else {
				chunk_number = 0;
			}
			System.out.println(chunk_number);
			
			skt.close();
			inp.close();
			sc.close();
			//System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * Once we know the number of chunks we start the receveing threads that continously
		 * accepts the chunks and write it to the file
		 */
		
		ReceiveFrommaster r = new ReceiveFrommaster(12345, buffer);
		r.start();		// receving from file thread
		WriteToFile w = new WriteToFile(buffer);	// thread to write to file
		w.start();
		try {
			r.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		MergeSortController msc = new MergeSortController(); // merge
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msc.start();		// thread to  merge
	}
	
	/*
	 * Creates Sorted and UnSorted Directories where files will be saved in different stages of the sorting cycle
	 */
	private void createDirectories() {
		File sortedDir = new File(HOME+System.getProperty("file.separator") + "Sorted Directory");
		File unSortedDir = new File(HOME+System.getProperty("file.separator") + "UnSorted Directory");
		if(!sortedDir.exists() && !unSortedDir.exists()) {
			sortedDir.mkdir();
			unSortedDir.mkdir();
		}
	}
	
}
