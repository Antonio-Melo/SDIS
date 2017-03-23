package server.task.initiatorPeer;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;

import server.main.Peer;
import utils.Utils;

public class Delete implements Runnable {

	String version;
	String fileID;

	public Delete(String version, String fileID) {
		this.version = version;
		this.fileID = fileID;
	}

	@Override
	public void run() {
		try {
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(Peer.mcAddress);
		byte[] sendData = new String(
				"DELETE " + this.version + " " + Peer.serverID + " " + this.fileID + " " + Utils.CRLF + Utils.CRLF)
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
