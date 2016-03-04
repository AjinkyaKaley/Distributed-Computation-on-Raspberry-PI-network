
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;

/**
 * This class has method that adds and takes the elements, inorder to 
 * move to pop the minimum values
 * This class aims to solve the problem of merge sorted files
 * The heap is of contant size
 * @author Ajinkya
 *
 */
class HeapSort{

	static PriorityQueue<Elements_identifier> p = new PriorityQueue<Elements_identifier>();
	File output;
	static PrintWriter pr;
	static FileWriter fr;
	// Tracks the elements to removed
	static HashSet<String> track_slaves;

	public HeapSort(){
		this.output = new File("Output.txt");
		this.track_slaves = new HashSet<String>();
		try {
			this.fr = new FileWriter(output);
			this.pr = new PrintWriter(fr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * adds the elements to the priority que
	 * @param e
	 */
	
	public static void add( Elements_identifier e){
		p.add(e);
	}

	
	/**
	 * Takes the one element at a time
	 */
	public static void take_elements(){

		for(int i =0;i<2;i++){
			Elements_identifier temp = p.poll();
			System.out.println(temp.element);
			pr.println(temp.element);
			track_slaves.add(temp.slave);		// keeps the track of the removed elements
				// used to probe the slaves to ask for new element
		}
		
		
		Send_Reply.send_response(track_slaves);
		
		
	}
	

	

}