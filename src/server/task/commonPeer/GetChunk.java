package server.task.commonPeer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import server.main.Peer;
import utils.Utils;

public class GetChunk implements Runnable{
	
	public static void main(String[] args) throws IOException {
		
	}
	
	private int senderID;
	private String fileID;
	private int chunkNumber;
	private byte[] chunk;
	
	public GetChunk(int senderID,String fileID,int chunkNumber){
		this.senderID = senderID;
		this.fileID = fileID;
		this.chunkNumber = chunkNumber;
	}

	@Override
	public void run() {
		// TODO 
		//Check if you have chunk
		
		
		//SEND CHUNK
		try {
			//Get body from file
			//CHUNK <Version> <SenderId> <FileId> <ChunkNo> <CRLF><CRLF><Body>
			byte[] msg = new String("CHUNK"+Utils.Space+"1.0"+Utils.Space+this.senderID+Utils.Space+this.fileID+Utils.Space+this.chunkNumber+Utils.Space+Utils.CRLF+Utils.CRLF).getBytes();

			InetAddress mdrGroup = InetAddress.getByName(Peer.mdrAddress);
			DatagramSocket mdrSocket = new DatagramSocket(Peer.mdrPort);
			//Wait random time between 0 400ms
			//DatagramPacket sendChunk = new DatagramPacket();
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

