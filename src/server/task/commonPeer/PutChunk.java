package server.task.commonPeer;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;

import server.main.Peer;
import utils.Utils;

public class PutChunk implements Runnable {

	public static void main(String[] args) throws IOException {
		byte[] carlos = {(byte) 0xff,(byte)0xff, (byte)0xff};
		new Thread(new PutChunk(
				2,
				"fdahsjkhfuihsdf",
				4,
				1,
				carlos
				)).start();
	}

	private int senderID;
	private String fileID;
	private int chunkNo;
	private int replicationDegree;
	private byte[] body;

	public PutChunk(int senderID, String fileID, int chunkNo, int replicationDegree, byte[] body) {
		this.senderID = senderID;
		this.fileID = fileID;
		this.chunkNo = chunkNo;
		this.replicationDegree = replicationDegree;
		this.body = body;
	}

	private boolean sendStoredReply() throws IOException {
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(Peer.mcAddress);
		byte[] sendData = new String(
				"STORED " + Peer.protocolVersion + " " + Peer.serverID + " " + this.fileID + " " + this.chunkNo + " " + Utils.CRLF + Utils.CRLF)
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
			System.out.println("vou criar chunk");
			File dir = new File(Peer.dataPath + Utils.FS + this.fileID);
			dir.mkdirs();
			File f = new File(Peer.dataPath + Utils.FS + this.fileID + Utils.FS + this.chunkNo);
			if (f.exists() && !f.isDirectory()) {
				System.out.println("ja existia gg");
				try {
					sendStoredReply();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				//RD
				int rds[] = Peer.rdMap.get(this.fileID + Utils.FS + this.chunkNo);
				if(rds == null){
					Peer.rdMap.put(this.fileID + Utils.FS + this.chunkNo, new int[]{this.replicationDegree,0});
				}else{
					Peer.rdMap.put(this.fileID + Utils.FS + this.chunkNo, new int[]{this.replicationDegree,rds[1]});
				}
				
				System.out.println("criei o file");
				try {
					if (Peer.capacity == 0 || Peer.capacity - (new File(Peer.dataPath).getTotalSpace()) > 64) {
						f.createNewFile();
						Files.write(f.toPath(), this.body);
						try {
							Thread.sleep((long)(Math.random() * 400));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						sendStoredReply();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
