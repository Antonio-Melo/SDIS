package server.task.initiatorPeer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import server.main.Peer;
import utils.Utils;

public class Removed implements Runnable {

	String fileID;
	int chunkNo;

	public Removed(String fileID, int chunkNo) {
		this.fileID = fileID;
		this.chunkNo = chunkNo;
	}

	@Override
	public void run() {
		try {
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(Peer.mcAddress);
		byte[] sendData = new String(
				"REMOVED " + Peer.protocolVersion + " " + Peer.serverID + " " + this.fileID + " " + this.chunkNo + " " + Utils.CRLF + Utils.CRLF)
				.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Peer.mcPort);
		clientSocket.send(sendPacket);
		clientSocket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
