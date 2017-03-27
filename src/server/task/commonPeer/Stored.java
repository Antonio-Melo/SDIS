package server.task.commonPeer;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import server.main.Peer;
import utils.Utils;

//RD
public class Stored implements Runnable {

	private int senderID;
	private String fileID;
	private int chunkNo;

	public Stored(int senderID, String fileID, int chunkNo) {
		this.senderID = senderID;
		this.fileID = fileID;
		this.chunkNo = chunkNo;
	}

	@Override
	public void run() {
		int[] rds = Peer.rdMap.get(this.fileID + this.chunkNo);
		if(rds != null){
			ArrayList<Integer> detailed = Peer.rdDetailedMap.get(this.fileID + this.chunkNo);
			if(!Arrays.asList(detailed).contains(this.senderID)){
				Peer.rdMap.put(this.fileID + this.chunkNo, new int[]{rds[0], rds[1]+1});
				detailed.add(this.senderID);
				Peer.rdDetailedMap.put(this.fileID + this.chunkNo, detailed);
			}
		}
	}

}