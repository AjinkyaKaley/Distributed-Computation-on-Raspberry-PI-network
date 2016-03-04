
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * This Class fills the buffer ready for use to heapsort
 * @author Ajinkya
 *
 */
public class Receive_Slave extends Thread {

	BlockingQueue<Elements_identifier> que;
	File output;
	PrintWriter pr;
	FileWriter fr;

	public Receive_Slave( BlockingQueue<Elements_identifier> que){
		this.que = 	que;
		this.output = new File("Output.txt");
		try {
			this.fr = new FileWriter(output);
			this.pr = new PrintWriter(fr);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run(){
		HeapSort hs = new HeapSort();
		int count = 0;
		int max_limit=2;

		try {
			this.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(que.size() > 1) {
			try {
				HeapSort.add(que.take());
				if(count == max_limit){
					hs.take_elements();
					count=0;
				}
				count++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		pr.close();
		try {
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
