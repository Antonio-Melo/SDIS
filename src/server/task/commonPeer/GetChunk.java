package server.task.commonPeer;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;

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
		File f = new File(Peer.dataPath + Utils.FS + this.fileID + Utils.FS + this.chunkNumber);
		if (f.exists() && !f.isDirectory()) {
			//SEND CHUNK
			try {
				//Get body from file
				this.chunk = Files.readAllBytes(f.toPath());
				//CHUNK <Version> <SenderId> <FileId> <ChunkNo> <CRLF><CRLF><Body>
				byte[] msg = new String("CHUNK"+Utils.Space+"1.0"+Utils.Space+this.senderID+Utils.Space+this.fileID+Utils.Space+this.chunkNumber+Utils.Space+Utils.CRLF+Utils.CRLF+this.chunk).getBytes();

				InetAddress mdrGroup = InetAddress.getByName(Peer.mdrAddress);
				DatagramSocket mdrSocket = new DatagramSocket();
				//Wait random time between 0 400ms
				mdrSocket.setSoTimeout((int)Math.random()*400);
				DatagramPacket p = new DatagramPacket();
				mdrSocket.receive(p);
				
				
				DatagramPacket sendChunk = new DatagramPacket(msg,msg.length,mdrGroup,Peer.mdrPort);


			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}

