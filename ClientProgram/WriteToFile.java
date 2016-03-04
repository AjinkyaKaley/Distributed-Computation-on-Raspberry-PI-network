import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * Writes the unsorted data received by the slave
 *
 */
public class WriteToFile extends Thread{

	File chuk_file;
	int num=1;
	BlockingQueue<ArrayList<Integer>> buffer;
	private final String HOME = "/home/pi/group2" + System.getProperty("file.separator")+"UnSorted Directory";
	
	
	public WriteToFile(BlockingQueue<ArrayList<Integer>> buffer){
		this.buffer = buffer;
	}


	/**
	 * Creates new data files  
	 */
	public void run(){
		while(num <=  MainProgram.chunk_number){
			
			File f = new File(HOME + System.getProperty("file.separator")+ "Chunk" + String.valueOf(num) + ".txt");
			try {
				FileWriter fr = new FileWriter(f);
				PrintWriter pr = new PrintWriter(fr);

				ArrayList<Integer> temp = buffer.take();
				//System.out.println(temp);
				for(int i : temp){
					//System.out.println(i);
					pr.println(String.valueOf(i));
				
				}
				pr.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			num++;
		}

	}
}
