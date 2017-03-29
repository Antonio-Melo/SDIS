package server.task.initiatorPeer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
		this.chunk = null;
	}

	@Override
	public void run() {
		//Creating GETCHUNK msg
		//GETCHUNK <Version> <SenderId> <FileId> <ChunkNo> <CRLF><CRLF>
		byte[] msg = new String("GETCHUNK"+Utils.Space+this.senderID+Utils.Space+this.fileID+Utils.Space+this.chunkNumber+Utils.Space+Utils.CRLF+Utils.CRLF).getBytes();
		
		try {
			//Group and socket creations
			InetAddress mcGroup = InetAddress.getByName(Peer.mcAddress);
			DatagramSocket mcSocket = new DatagramSocket();
			
			//Send GETCHUNK and receive CHUNK
			DatagramPacket sendCommand = new DatagramPacket(msg,msg.length,mcGroup,Peer.mcPort);
			mcSocket.send(sendCommand);
			mcSocket.close();
			
			//Call RECEIVECHUNK
			Thread receivedThread = new Thread(new ReceiveChunk());
			receivedThread.start();
			receivedThread.join(400);
			if(receivedThread.isAlive()) receivedThread.interrupt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
					if(cmdSplit[0].equals("CHUNK") && cmdSplit[2].equals(fileID) && cmdSplit[2].equals(chunkNumber)){
						int bodyIndex = receivedCmdString.indexOf(Utils.CRLF+Utils.CRLF)+4;
						chunk = Arrays.copyOfRange(receiveCommand.getData(),bodyIndex,receiveCommand.getLength());
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