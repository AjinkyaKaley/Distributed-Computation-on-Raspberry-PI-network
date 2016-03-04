import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Slave pings master periodically
 *
 */
public class SlavePing {
	private String MASTER_IP = MainProgram.MASTER_IP;
	private final int PORT = 10101;
	public static void main(String[] args) {
		SlavePing sp = new SlavePing();
		sp.pingingMaster();
	}
	
	/**
	 * Sends a datagram packet to master for every 1.5 seconds
	 */
	public void pingingMaster() {
		DatagramSocket socket = null;
		while (true) {
			try {
				 socket = new DatagramSocket();
				InetAddress IPAddress =InetAddress.getByName(MASTER_IP);
				String message = "ALIVE"; 
				DatagramPacket request = new DatagramPacket(message.getBytes(), message.length(), IPAddress, PORT);
				socket.send(request);
				Thread.sleep(1500);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				socket.close();
			}
		}
		
	}
}
