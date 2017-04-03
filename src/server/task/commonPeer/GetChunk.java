package server.task.commonPeer;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.Arrays;

import server.main.Peer;
import server.task.commonPeer.GetChunk.ReceiveChunk;
import utils.Utils;

public class GetChunk implements Runnable{

	public static void main(String[] args) throws IOException {

	}

	private int senderID;
	private String fileID;
	private int chunkNumber;
	private byte[] chunk;
	private boolean chunkAlreadySent = false;

	public void setChunkAlreadySent(boolean chunkAlreadySent) {
		this.chunkAlreadySent = chunkAlreadySent;
	}

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
				
				
				//Call RECEIVECHUNK
				Thread receivedThread = new Thread(new ReceiveChunk());
				receivedThread.start();
				receivedThread.join((long)Math.random()*400);
				if(receivedThread.isAlive()) receivedThread.interrupt();
				
				if(!this.chunkAlreadySent){
					InetAddress mdrGroup = InetAddress.getByName(Peer.mdrAddress);
					DatagramSocket mdrSocket = new DatagramSocket();
					
					DatagramPacket sendChunk = new DatagramPacket(msg,msg.length,mdrGroup,Peer.mdrPort);
					mdrSocket.close();
				}


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
	
	class ReceiveChunk implements Runnable {

		@Override
		public void run() {
			try {
				InetAddress mdrGroup = InetAddress.getByName(Peer.mdrAddress);
				MulticastSocket mdrSocket = new MulticastSocket(Peer.mdrPort);
				byte[] receiveMsg = new byte[70000];
				DatagramPacket receiveCommand = new DatagramPacket(receiveMsg,receiveMsg.length);
				mdrSocket.joinGroup(mdrGroup);
				mdrSocket.setSoTimeout(400);
				
				while(!Thread.currentThread().isInterrupted()){
					mdrSocket.receive(receiveCommand);
					String receivedCmdString = new String(receiveCommand.getData(), receiveCommand.getOffset(), receiveCommand.getLength());
					String cmdSplit[] = receivedCmdString.split("\\s+");
					if(cmdSplit[0].equals("CHUNK") && cmdSplit[3].equals(fileID) && cmdSplit[4].equals(chunkNumber)){
						setChunkAlreadySent(true);
						break;
					}
				}
				mdrSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

