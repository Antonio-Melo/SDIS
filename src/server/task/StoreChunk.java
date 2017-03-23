package server.task;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;

import server.main.Peer;

public class StoreChunk implements Runnable {

	public static void main(String[] args) throws IOException {
		byte[] carlos = {(byte) 0xff,(byte)0xff, (byte)0xff};
		new Thread(new StoreChunk(
				"1.0",
				2,
				"fdahsjkhfuihsdf",
				4,
				carlos
				)).start();
	}

	private String version;
	private int senderID;
	private String fileID;
	private int chunkNo;
	private byte[] body;

	public StoreChunk(String version, int senderID, String fileID, int chunkNo, byte[] body) {
		this.version = version;
		this.senderID = senderID;
		this.fileID = fileID;
		this.chunkNo = chunkNo;
		this.body = body;
	}

	private boolean sendStoredReply() throws IOException {
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(Peer.mcAddress);
		byte[] sendData = new String(
				"STORED " + this.version + " " + Peer.serverID + " " + this.fileID + " " + this.chunkNo + " \r\n")
						.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Peer.mcPort);
		clientSocket.send(sendPacket);
		clientSocket.close();
		return true;
	}

	@Override
	public void run() {
		System.out.println("nem vou criar chunk, ja sou eu mesmo lol");
		if (this.senderID != Peer.serverID) {
			String fileSeparator = System.getProperty("file.separator");
			System.out.println("vou criar chunk");
			File dir = new File(Peer.dataPath + fileSeparator + this.fileID);
			dir.mkdirs();
			File f = new File(Peer.dataPath + fileSeparator + this.fileID + fileSeparator + this.chunkNo);
			if (f.exists() && !f.isDirectory()) {
				System.out.println("ja existia gg");
				try {
					sendStoredReply();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("criei o file");
				try {
					if (Peer.capacity == 0 || Peer.capacity - (new File(Peer.dataPath).getTotalSpace()) > 64) {
						f.createNewFile();
						Files.write(f.toPath(), this.body);
						sendStoredReply();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
