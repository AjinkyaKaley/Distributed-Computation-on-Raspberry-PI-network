import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
* This class SSH in to the slave machines and tranfers the slave codes.
*/
public class ExecuteSSH {

	public ExecuteSSH() {
		// TODO Auto-generated method stub
		
		execute("10.10.10.121");
		execute("10.10.10.122");
		execute("10.10.10.133");
		execute("10.10.10.134");
		execute("10.10.10.136");
		execute("10.10.10.137");
	}
	
	public void execute(String ip) {
		JSch jsch = new JSch();
		try {
			Session session = jsch.getSession("pi", ip, 22);
			session.setPassword("raspberry");
			Properties config = new Properties();
		    config.put("StrictHostKeyChecking", "no");
		    session.setConfig(config);
		    session.connect();
		    
		    Channel c = session.openChannel("exec");
		    ChannelExec ce = (ChannelExec) c;
		    
//		    ce.setCommand("cd SlaveCode; javac *.java; java MainProgram");
		    ce.setCommand("cd group2 \n" + "javac  *.java \n" + "java MainProgram");
		    ce.setErrStream(System.err);

		    ce.connect();

		    BufferedReader reader = new BufferedReader(new InputStreamReader(ce.getInputStream()));
		    String line;
		    while ((line = reader.readLine()) != null) {
		      System.out.println(line);
		    }

		    ce.disconnect();
		    session.disconnect();

		    System.out.println("Exit code: " + ce.getExitStatus());
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
