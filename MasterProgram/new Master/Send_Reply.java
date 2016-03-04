
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Send_Reply {

/**
 * 
 * this class send the reply after getting the data from pi
 * @param ip_port
 */

	public Send_Reply(ArrayList<Connection_info> ip_port){

	}


	public static void send_response( HashSet <String> slave_id) {


		Iterator<String > it = slave_id.iterator();

		while(it.hasNext()){
			Connection_info p = Master.slaves_address.get(Integer.valueOf(it.next()));
			try {
				System.out.println("Sending chunk request");
				Socket temp = new Socket(p.ip, 14141);
				ObjectOutputStream out = new ObjectOutputStream(temp.getOutputStream());
				out.writeObject(Integer.valueOf(1));
				temp.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
