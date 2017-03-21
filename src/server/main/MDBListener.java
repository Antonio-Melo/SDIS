package server.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import server.task.StoreChunk;

public class MDBListener implements Runnable {

	int serverID;
	String address;
	int port;
	String mcAddress;
	int mcPort;

	public MDBListener(int serverID, String address, int port, String mcAddress, int mcPort) {
		this.serverID = serverID;
		this.address = address;
		this.port = port;
		this.mcAddress = mcAddress;
		this.mcPort = mcPort;
	}

	@Override
	public void run() {
		InetAddress mdbGroup;
		try {
			mdbGroup = InetAddress.getByName(address);
			MulticastSocket socket = new MulticastSocket(port);
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
						    "fsdf",
							serverID, 
							mcAddress, 
							mcPort)).start();
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
