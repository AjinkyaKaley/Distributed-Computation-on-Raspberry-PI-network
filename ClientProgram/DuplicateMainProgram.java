import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class DuplicateMainProgram {
	static int  chunk_number;
	public static final String HOME = "/home/pi/demo/submissions/group2";
	private static String slash = System.getProperty("file.separator");
	File sortedDir, unSortedDir, receivedData, mergeSortDone, mergingDone, transferringToMasterStarted;
	BlockingQueue<ArrayList<Integer>> buffer = new LinkedBlockingQueue<ArrayList<Integer>>();
	
	public static void main(String[] args) {
		
		
		
		DuplicateMainProgram mainProg = new DuplicateMainProgram();
		mainProg.sortedDir = new File(HOME+ slash + "Sorted Directory");
		mainProg.unSortedDir = new File(HOME+ slash +"UnSorted Directory");
		mainProg.receivedData = new File(HOME+ slash + "ReceivedData");
		mainProg.mergeSortDone = new File(HOME+ slash + "MergeSortDone");
		mainProg.mergingDone = new File(HOME+ slash + "MergingDone");
		mainProg.transferringToMasterStarted = new File(HOME+ slash + "TransferringToMasterStarted");
		
		if (mainProg.checkDirectoriesExist(mainProg.sortedDir, mainProg.unSortedDir)) {
			
			if (mainProg.fileExists(mainProg.transferringToMasterStarted)) {
				// Checks if sorted data transferring from Slave has started
				
			} else if (mainProg.fileExists(mainProg.mergingDone)) {
				// Checks if Merging to single data file is done
			} else if (mainProg.fileExists(mainProg.mergeSortDone)) {
				// Checks if Merge sort is performed
				
			} else if (mainProg.fileExists(mainProg.receivedData)) {
			// Checks if file transfer from master is done
				
			} else {
				// In this case program is interrupted during file transfer from Master
				//First delete any existing files from UnSorted Directory
				String[] files = mainProg.unSortedDir.list();
				for (int i = 0; i < files.length; i++) {
					File f = new File(mainProg.unSortedDir, files[i]);
					f.delete();
				}
				// Wait for Master to give the number of chunks being sent
				mainProg.getInputChunks();
				
			}
		} else {
			// Begin execution here, start with creating directories
			if(!mainProg.sortedDir.exists() && !mainProg.unSortedDir.exists()) {
				mainProg.sortedDir.mkdir();
				mainProg.unSortedDir.mkdir();
			}
			// Wait for Master to give the number of chunks being sent
			mainProg.getInputChunks();
		}
	}
	
	public boolean checkDirectoriesExist(File f1, File f2) {
		if (f1.exists() && f2.exists()) {
			return true;
		} 
		return false;
	}
	
	public boolean fileExists (File f) {
		if (f.exists()) {
			return true;
		}
		return false;
	}
	
	public void getInputChunks() {
		try {
			ServerSocket sc = new ServerSocket(12341);
			Socket skt = sc.accept();
			System.out.println("Accept");
			ObjectInputStream inp = new ObjectInputStream(skt.getInputStream());
//			chunk_number = inp.readInt(); 
			Object o = inp.readObject();
			if (o instanceof Integer) {
				chunk_number = (Integer)o;
			} else {
				chunk_number = 0;
			}
			System.out.println(chunk_number);
			skt.close();
			inp.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startProcess() {
		ReceiveFrommaster r = new ReceiveFrommaster(15000, buffer);
		r.start();
		WriteToFile w = new WriteToFile(buffer);
		w.start();
		MergeSortController msc = new MergeSortController();
		msc.start();
	}
	

}
