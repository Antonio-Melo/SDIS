package server.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

import server.task.commonPeer.PutChunk;

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
				String receivedCmdString = new String(receivedCmd.getData(), receivedCmd.getOffset(), receivedCmd.getLength());
				String cmdSplit[] = receivedCmdString.split("\\s+");
				if(cmdSplit[0].equals("PUTCHUNK")){
					String CRLF = new String("\r\n\r\n");
					int bodyIndex = receivedCmdString.indexOf(CRLF)+4;
					
					byte[] body = Arrays.copyOfRange(receivedCmd.getData(),bodyIndex,receivedCmd.getLength());
					new Thread(new PutChunk(
							cmdSplit[1],
							Integer.parseInt(cmdSplit[2]),
							cmdSplit[3],
						    Integer.parseInt(cmdSplit[4]),
						    Integer.parseInt(cmdSplit[5]),
						    body
						    )).start();
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
