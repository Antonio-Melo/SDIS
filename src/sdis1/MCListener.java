package sdis1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MCListener implements Runnable{
	
	String address;
	int port;
	
	public MCListener(String address, int port){
		this.address = address;
		this.port = port;
	}

	@Override
	public void run() {
		InetAddress mcGroup;
		try {
			mcGroup = InetAddress.getByName(address);
		 MulticastSocket s = new MulticastSocket(port);
		 s.joinGroup(mcGroup);
		 //Get GETCHUNK, DELETE or REMOVED command
		 byte[] buf = new byte[70000];
		 DatagramPacket recv = new DatagramPacket(buf, buf.length);
		 s.receive(recv);
		 //String hostname = recv.getAddress().getHostAddress();
		 //int port = Integer.parseInt(new String(recv.getData(), recv.getOffset(), recv.getLength()));
		 //System.out.println("multicast: " + address + " " + port + " : " + hostname+ " "+ port);
		 // OK, I'm done talking - leave the group...
		 s.leaveGroup(mcGroup);
		 s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}
