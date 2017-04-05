package server.task.commonPeer;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

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
		int[] rds = Peer.rdMap.get(this.fileID + Utils.FS + this.chunkNo);
		ArrayList<Integer> detailed;
		if(rds == null){
			Peer.rdMap.put(this.fileID + Utils.FS + this.chunkNo, new int[]{0,0});
			detailed = new ArrayList<Integer>();
			detailed.add(this.senderID);
			Peer.rdDetailedMap.put(this.fileID + Utils.FS + this.chunkNo, detailed);
			return;
		}

		detailed = Peer.rdDetailedMap.get(this.fileID + Utils.FS + this.chunkNo);
		if(detailed == null){
					rds = Peer.rdMap.get(this.fileID + Utils.FS + this.chunkNo);
					Peer.rdMap.put(this.fileID + Utils.FS + this.chunkNo, new int[]{rds[0],1});
					detailed = new ArrayList<Integer>();
					detailed.add(this.senderID);
					Peer.rdDetailedMap.put(this.fileID + Utils.FS + this.chunkNo, detailed);
					return;
				}

		if(!detailed.contains(this.senderID)){
					rds = Peer.rdMap.get(this.fileID + Utils.FS + this.chunkNo);
						Peer.rdMap.put(this.fileID + Utils.FS + this.chunkNo, new int[]{rds[0], rds[1]+1});
			detailed.add(this.senderID);
			Peer.rdDetailedMap.put(this.fileID + Utils.FS + this.chunkNo, detailed);
			System.out.println("DETAILED("+Peer.serverID+","+this.chunkNo+")"+detailed);
		}
		/*if(Utils.saveRD()){
			System.out.println("GRAVEI NO RD");
		}*/
	}

}
