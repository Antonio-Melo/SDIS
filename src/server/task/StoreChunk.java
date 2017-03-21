package server.task;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;

public class StoreChunk implements Runnable {
	
	public static void main(String[] args) throws IOException {
		new Thread(new StoreChunk(
				"1.0",
				2,
				"fdahsjkhfuihsdf",
			    4,
			    "fsdf",
				2, 
				"127.0.0.1", 
				4455)).start();
	}
	
	String version;
	int senderID;
	String fileID;
	int chunkNo;
	String body;
	int serverID;
	String replyAddress;
	int replyPort;

	public StoreChunk(String version, int senderID, String fileID, int chunkNo, String body, int serverID, String replyAddress, int replyPort) {
		this.version = version;
		this.senderID = senderID;
		this.fileID = fileID;
		this.chunkNo = chunkNo;
		this.body = body;
		this.serverID = serverID;
		this.replyAddress = replyAddress;
		this.replyPort = replyPort;
	}
	
	private boolean sendStoredReply() throws IOException{
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(this.replyAddress);
		byte[] sendData = new String("STORED " + this.version + " " + this.serverID + " " + this.fileID + " " + this.chunkNo + " \r\n").getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, this.replyPort);
		clientSocket.send(sendPacket);
		clientSocket.close();
		return true;
	}
	
	@Override
	public void run() {

		System.out.println("nem vou criar chunk, ja sou eu mesmo lol");
		if(this.senderID != this.serverID){
			System.out.println("vou criar chunk");
			File dir = new File(".\\" + this.fileID + "\\" + this.version);
			dir.mkdirs();
			File f = new File(".\\" + this.fileID + "\\" + this.version + "\\" + this.chunkNo);
			if(f.exists() && !f.isDirectory()){
				System.out.println("ja existia gg");
				try {
					sendStoredReply();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("criei o file");
				try {
					f.createNewFile();
					sendStoredReply();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//TODO write body in file
		}
	}

}
