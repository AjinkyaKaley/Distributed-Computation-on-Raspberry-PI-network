import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the sequence of sorting and merging
 * @author Ajinkya Kale
 *
 */
public class MergeSortController extends Thread{
	final int PORT = 10000;
	ServerSocket serverSocket;
	private final String RECEIVE_COMPLETE = "COMPLETE";
	private final String HOME = "/home/pi/group2";
	public static boolean receivedData = false;
	

	/**
	 * Constructor for the class
	 */
	public MergeSortController() {
		try {
			System.out.println("MergeSortController : obj initialized");
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("MergeSortController : Error creating server socket");
			e.printStackTrace();
		}
	}
	/**
	 * run method for this thread
	 * This thread starts performing the merge of the sorted the files
	 */
	public void run() {
		Socket soc;
		System.out.println("MergeSortController : Inside run method");
		
			try {
				soc = serverSocket.accept();
				System.out.println("Accepted!!!");
				BufferedReader input = new BufferedReader(new InputStreamReader(soc.getInputStream()));
				Object o = input.readLine();
				if (o instanceof String) {
					String new_name = (String) o;
					System.out.println("MergeSortController : " + new_name);
					if (new_name.equals(RECEIVE_COMPLETE)) {
						receivedData = true;
						System.out.println("Complete data received on Slave");
						notMain();
						MergeManager merge = new MergeManager();
						merge.iterativeMerge();
						SendSortedFile sf = new SendSortedFile();
						sf.sendAndReceiveData();
					}
					
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
	
	private void callMethods() {
		notMain();
		MergeManager merge = new MergeManager();
		merge.iterativeMerge();
	}

	/**
	 * Iterates through the files and sorts them using mergesort
	 */
	public void notMain() {
		String lineSeperator = System.getProperty("file.separator");
		/*********** Change to file paths on SD CARD **************/
		File unsortedDir = new File(HOME + lineSeperator + "UnSorted Directory");
		System.out.println(unsortedDir.getAbsolutePath());
		String[] fileNames = unsortedDir.list();
		System.out.println("Number of file in UnSorted Directory before MergeSort" + fileNames.length);
		for (int j = 0; j < fileNames.length; j++) {
			try {
				if (fileNames[j].startsWith(".")) {
					continue;
				}
				File fileObj = new File(unsortedDir +lineSeperator + fileNames[j]);
				System.out.println(fileObj.getAbsolutePath());
				Scanner sc = new Scanner(fileObj);
				ArrayList<Integer> list = new ArrayList<Integer>();

				while (sc.hasNext()) {
					
					list.add(Integer.valueOf(sc.next()));
				}
				MergeSort ms = new MergeSort();
				System.out.println("Sorting elements on in the file : " + fileNames[j] + " using Merge sort");
				list = ms.sort(list);
				try {
					FileWriter fw = new FileWriter(fileObj);
			        BufferedWriter bw = new BufferedWriter(fw);
			        for (int i = 0; i < list.size(); i++) {
			            bw.write(list.get(i).toString());
			            bw.newLine();
			        }
			        bw.flush();
			        bw.close();
					
				} catch(Exception ex) {
				    ex.printStackTrace();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
