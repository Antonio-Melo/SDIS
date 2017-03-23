package server.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MCListener implements Runnable {

	@Override
	public void run() {
		InetAddress mcGroup;
		try {
			mcGroup = InetAddress.getByName(Peer.mcAddress);
			MulticastSocket socket = new MulticastSocket(Peer.mcPort);
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
			}
			socket.leaveGroup(mcGroup);
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}