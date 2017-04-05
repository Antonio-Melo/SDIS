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
		if (this.senderID != Peer.serverID) {
			File dir = new File(Peer.dataPath + Utils.FS + this.fileID);
			dir.mkdirs();
			File f = new File(Peer.dataPath + Utils.FS + this.fileID + Utils.FS + this.chunkNo);
			if (f.exists() && !f.isDirectory()) {
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
				try {
					if (Peer.capacity == 0 || Peer.capacity - Peer.usedCapacity > this.body.length) {
						f.createNewFile();
						Files.write(f.toPath(), this.body);
						Peer.usedCapacity += this.body.length;
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
