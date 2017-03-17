package server.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MCListener implements Runnable {

	String address;
	int port;

	public MCListener(String address, int port) {
		this.address = address;
		this.port = port;
	}

	@Override
	public void run() {
		InetAddress mcGroup;
		try {
			mcGroup = InetAddress.getByName(address);
			MulticastSocket socket = new MulticastSocket(port);
			socket.joinGroup(mcGroup);
			// Get GETCHUNK, DELETE or REMOVED command
			byte[] buf = new byte[70000];
			DatagramPacket receivedCmd = new DatagramPacket(buf, buf.length);
			while (!Thread.currentThread().isInterrupted()) {
				socket.receive(receivedCmd);
				String cmdSplit[] = new String(receivedCmd.getData(), receivedCmd.getOffset(), receivedCmd.getLength()).split("\\s+");
				if(cmdSplit[0] == "GETCHUNK"){
					
				} else if(cmdSplit[0] == "DELETE"){
					
				} else if(cmdSplit[0] == "REMOVED"){
					
				}
				// String hostname = recv.getAddress().getHostAddress();
				// int port = Integer.parseInt(new String(recv.getData(),
				// recv.getOffset(), recv.getLength()));
				// System.out.println("multicast: " + address + " " + port + " :
				// " + hostname+ " "+ port);
				// OK, I'm done talking - leave the group...
			}
			socket.leaveGroup(mcGroup);
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}