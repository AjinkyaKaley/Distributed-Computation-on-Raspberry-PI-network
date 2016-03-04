
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connections {

	public Socket sk;
	public ObjectOutputStream output;
	public String ip;
	public int port;
	public String slave;
	public boolean status;
	ServerSocket ser_skt;
	
	public Connections( String ip, int port, String slave){
		this.ip = ip;
		this.port = port;
		this.slave = slave;
		this.status = true;
		
	}
}
