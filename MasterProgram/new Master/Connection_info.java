
public class Connection_info
{

	public String ip;
	public int port_for_establishing_connection;
	public int port_for_sending;
	public int port_for_receiving;
	
	int slave_id;
	

	public Connection_info(  int id, String i ,int port_for_estab, int port_for_sending, int port_for_receiving ) {
		this.ip = i;
		this.port_for_establishing_connection = port_for_estab;
		this.port_for_sending = port_for_sending;
		this.port_for_receiving = port_for_receiving;
		this.slave_id = id;
	}

}