import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is used for summing up unique values sent to the Slave
 * 
 *
 */
public class AddingUniqueValues {

	int PORT = 12323;
	ServerSocket server;
	Socket soc;
	ObjectInputStream objIn;
	ObjectOutputStream objOut;
	final String master = MainProgram.MASTER_IP;
	/**
	 * Retrieves data from master and send its sum at the end
	 */
	public void addData() {
		Integer sum = 0;
		try {
			server = new ServerSocket(PORT);
			while(true) {
				soc = server.accept();
				objIn = new ObjectInputStream(soc.getInputStream());
				Object o = objIn.readObject();
				Integer value;
				if (o instanceof Integer) {
					if ((value = (Integer)o) >= 0) {
					sum = sum + value;
					} else {
						break;
					}
				}
			}
			Socket sendSoc = new Socket(master, PORT);
			objOut = new ObjectOutputStream(sendSoc.getOutputStream());
			objOut.writeObject(sum);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
