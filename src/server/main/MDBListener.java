package server.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MDBListener implements Runnable {

	String address;
	int port;

	public MDBListener(String address, int port) {
		this.address = address;
		this.port = port;
	}

	@Override
	public void run() {
		InetAddress mdbGroup;
		try {
			mdbGroup = InetAddress.getByName(address);
			MulticastSocket s = new MulticastSocket(port);
			s.joinGroup(mdbGroup);
			// Get PUTCHUNK command
			byte[] buf = new byte[70000];
			DatagramPacket receivedCmd = new DatagramPacket(buf, buf.length);
			while (!Thread.currentThread().isInterrupted()) {
				s.receive(receivedCmd);
				// String hostname = recv.getAddress().getHostAddress();
				// int port = Integer.parseInt(new String(recv.getData(),
				// recv.getOffset(), recv.getLength()));
				// System.out.println("multicast: " + address + " " + port + " :
				// " + hostname+ " "+ port);
				// OK, I'm done talking - leave the group...

			}
			s.leaveGroup(mdbGroup);
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
