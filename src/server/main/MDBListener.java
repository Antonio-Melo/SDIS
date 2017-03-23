package server.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import server.task.StoreChunk;

public class MDBListener implements Runnable {

	@Override
	public void run() {
		InetAddress mdbGroup;
		try {
			mdbGroup = InetAddress.getByName(Peer.mdbAddress);
			MulticastSocket socket = new MulticastSocket(Peer.mdbPort);
			socket.joinGroup(mdbGroup);
			// Get PUTCHUNK command
			byte[] buf = new byte[70000];
			DatagramPacket receivedCmd = new DatagramPacket(buf, buf.length);
			while (!Thread.currentThread().isInterrupted()) {
				socket.receive(receivedCmd);
				String cmdSplit[] = new String(receivedCmd.getData(), receivedCmd.getOffset(), receivedCmd.getLength()).split("\\s+");
				if(cmdSplit[0] == "PUTCHUNK"){
					new Thread(new StoreChunk(
							cmdSplit[1],
							Integer.parseInt(cmdSplit[2]),
							cmdSplit[3],
						    Integer.parseInt(cmdSplit[4]),
						    "fsdf"
						    )).start();
					//TODO parse body
				}
			}
			socket.leaveGroup(mdbGroup);
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
