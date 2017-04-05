package server.task.commonPeer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import server.main.Peer;
import server.task.initiatorPeer.PutChunk;
import utils.Utils;

//RD
public class Removed implements Runnable {

	private int senderID;
	private String fileID;
	private int chunkNo;

	public Removed(int senderID, String fileID, int chunkNo) {
		this.senderID = senderID;
		this.fileID = fileID;
		this.chunkNo = chunkNo;
	}

	@Override
	public void run() {
		int[] rds = Peer.rdMap.get(this.fileID + Utils.FS + this.chunkNo);
		if(rds != null){
			Peer.rdMap.put(this.fileID + Utils.FS + this.chunkNo, new int[]{rds[0], rds[1]-1});
			if(rds[1]-1 < rds[0]  && this.senderID != Peer.serverID){
				File f = new File(Peer.dataPath + Utils.FS + this.fileID + Utils.FS + this.chunkNo);
				if (f.exists() && !f.isDirectory()) {
					InputStream in;
					try {
						in = new FileInputStream(f);
					byte chunk[] = new byte[(int) f.length()];
						in.read(chunk, 0,(int) f.length());
					new Thread(new PutChunk(
							Peer.serverID,
							this.fileID,
							this.chunkNo,
							rds[0],
							chunk
							)).start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
