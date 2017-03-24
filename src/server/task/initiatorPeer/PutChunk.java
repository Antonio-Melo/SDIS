package server.task.initiatorPeer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;


import server.main.Peer;
import utils.Utils;

public class PutChunk implements Runnable{
	
	public static void main(String[] args){
		byte[] carlos = {(byte) 0xff,(byte)0xff, (byte)0xff};
		new Thread(new PutChunk(
				"1.0",
				3,
				"PutChunk_teste_initPeer",
				10,
				1,
				carlos
				)).start();
	}
	
	private String version;
	private int senderID;
	private String fileID;
	private int chunkNo;
	private int replicationDegree;
	private byte[] body;
	
	public PutChunk(String version, int senderID, String fileID, int chunkNo, int replicationDegree, byte[] body) {
		this.version = version;
		this.senderID = senderID;
		this.fileID = fileID;
		this.chunkNo = chunkNo;
		this.replicationDegree = replicationDegree;
		this.body = body;
	}
	
	@Override
	public void run() {
		// TODO 
		// Generate String with chunks and send it to the multicast channel
		InetAddress mdbGroup;
		try {
			mdbGroup = InetAddress.getByName(Peer.mdbAddress);
			MulticastSocket socket = new MulticastSocket(Peer.mdbPort);
			socket.joinGroup(mdbGroup);
			//SEND PUTCHUNK msg
			byte[] header = new String("PUTCHUNK "+ this.version + Utils.Space+ this.senderID + Utils.Space + this.fileID+ Utils.Space+ this.chunkNo+ Utils.Space + this.replicationDegree+Utils.Space+Utils.CRLF+Utils.CRLF).getBytes();
			byte[] chunk = new byte[header.length + this.body.length];
			System.arraycopy(header, 0, chunk, 0, header.length);
			System.arraycopy(this.body, 0, chunk, header.length, this.body.length);
			

			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


}
