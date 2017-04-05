package server.task.initiatorPeer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import server.main.Peer;
import utils.Utils;

public class Delete implements Runnable {

	String fileID;

	public Delete(String fileID) {
		this.fileID = fileID;
	}

	@Override
	public void run() {
		try {
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(Peer.mcAddress);
		byte[] sendData = new String(
				"DELETE" + Utils.Space +
				"1.0" + Utils.Space +
				Peer.serverID + Utils.Space +
				this.fileID + Utils.Space +
				Utils.CRLF + Utils.CRLF)
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
