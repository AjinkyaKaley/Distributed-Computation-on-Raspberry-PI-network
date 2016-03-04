import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *Sends the final sorted file to the master 
 *
 */
public class SendSortedFile {

	String masterIp = MainProgram.MASTER_IP;
	int port = 15002;
	private final static String sdCardPath = System.getProperty("user.home");
	private final static String fileSeparator = System.getProperty("file.separator");

	public static void main(String[] args) {
		SendSortedFile ssf = new SendSortedFile();
		ssf.sendAndReceiveData();
	}
	/**
	 * Sends the final sorted file to the master
	 */
	public void sendingFile() {
		File sortedDir = new File(sdCardPath+fileSeparator+"UnSorted Directory");
		String[] files = sortedDir.list();
		File dataFile = null;
		for (int i = 0; i < files.length; i++) {
			if(files[i].startsWith(".")) {
				continue;
			} else {
				dataFile = new File(sortedDir, files[i]);
				break;
			}
		}
		Socket soc;
		ObjectOutputStream oStream;
		try {
			System.out.println("Sending sorted data to Master's IP :" + masterIp);
			soc = new Socket( MainProgram.MASTER_IP, port);
			FileInputStream fis = new FileInputStream(dataFile);
			Scanner sc = new Scanner(fis);
			oStream = new ObjectOutputStream(soc.getOutputStream());
			File readLength = new File(sdCardPath+"readLength");
			PrintWriter pw = new PrintWriter(new FileWriter(readLength));
			int counter = 0;
			while (sc.hasNext()) {
				oStream.writeObject(sc.next());
				counter++;
				pw.println(counter);
			}
			oStream.flush();
			oStream.close();
			fis.close();
			pw.close();
			soc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendAndReceiveData() {
		
		System.out.println("Entered SendSortedFile : sendAndReceiveData");
		File sortedDir = new File(sdCardPath+fileSeparator+"UnSorted Directory");
		String[] files = sortedDir.list();
		File dataFile = null;
		for (int i = 0; i < files.length; i++) {
			if(files[i].startsWith(".")) {
				continue;
			} else {
				dataFile = new File(sortedDir, files[i]);
				break;
			}
		}
		Socket soc = null;
		FileInputStream fis = null;
		Scanner sc = null;
		ServerSocket server = null;
		try {
			server = new ServerSocket(14141);
			System.out.println("Sending sorted data to Master's IP :" + masterIp + " and Port " + port);
			soc = new Socket(MainProgram.MASTER_IP, port);
			System.out.println("Sending data socket created");
			fis = new FileInputStream(dataFile);
			sc = new Scanner(fis);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ObjectOutputStream out = null;
		ObjectInputStream in;
			try {

				int sendCount = 11;
				out = new ObjectOutputStream(soc.getOutputStream());
				
				while (sc.hasNext()) {
				System.out.println("Trying to send data to Master");
				ArrayList<Integer> list = new ArrayList<Integer>();
				for (int i = 0; (i < sendCount) && sc.hasNext(); i++) {
					list.add(Integer.valueOf(sc.next()));
				}
				out.writeObject(list);
				out.flush();
				sendCount = receiveChunkCount(server);

				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	private int receiveChunkCount(ServerSocket server) {
		int value = 0;
		
		Socket soc= null;
		try {
			System.out.println("Waiting for chunk request");
			soc = server.accept();
			ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
			Object o = in.readObject();
			
			if (o instanceof Integer) {
				value = (Integer)o;
				System.out.println("Request for elements " + value);
			}
			in.close();
			soc.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
}
