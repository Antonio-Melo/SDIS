package server.task.commonPeer;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.Random;

import server.main.Peer;
import utils.Utils;

public class PutChunk implements Runnable {

	private String senderID;
	private String fileID;
	private int chunkNo;
	private int replicationDegree;
	private byte[] body;

	public PutChunk(String senderID, String fileID, int chunkNo, int replicationDegree, byte[] body) {
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
				"STORED" + Utils.Space +
				"1.0" + Utils.Space +
				Peer.serverID + Utils.Space +
				this.fileID + Utils.Space +
				this.chunkNo + Utils.Space +
				Utils.CRLF + Utils.CRLF).getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Peer.mcPort);
		clientSocket.send(sendPacket);
		clientSocket.close();
		return true;
	}

	@Override
	public void run() {
		//TODO check se o ficheiro nao e' dele
		if (!Peer.mdMap.containsValue(this.fileID)) {
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
				try {
					Thread.sleep((long)new Random().nextInt(400));
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//RD
				int rds[] = Peer.rdMap.get(this.fileID + Utils.FS + this.chunkNo);
				if(rds == null){
					Peer.rdMap.put(this.fileID + Utils.FS + this.chunkNo, new int[]{this.replicationDegree,0});
				}else{
					Peer.rdMap.put(this.fileID + Utils.FS + this.chunkNo, new int[]{this.replicationDegree,rds[1]});
				}
				//rds = Peer.rdMap.get(this.fileID + Utils.FS + this.chunkNo);
				//if(rds[1] < rds[0]){
					try {
						if (Peer.capacity == 0 || Peer.capacity - Peer.usedCapacity > this.body.length) {
							f.createNewFile();
							Files.write(f.toPath(), this.body);
							Peer.usedCapacity += this.body.length;
							/*try {
								Thread.sleep((long)new Random().nextInt(400));
							} catch (InterruptedException e) {
								e.printStackTrace();
							}*/
							sendStoredReply();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				//}
			}
		}
	}

}
