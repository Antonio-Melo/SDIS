package server.task.commonPeer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import server.main.Peer;
import utils.Utils;

public class Delete implements Runnable {

	int senderID;
	String fileID;

	public Delete(int senderID, String fileID) {
		this.senderID = senderID;
		this.fileID = fileID;
	}

	@Override
	public void run() {
		File dir = new File(Peer.dataPath + Utils.FS + this.fileID);
		if (dir.isDirectory()) {
			for (File c : dir.listFiles())
				try {
					//Delete files inside directory
					Peer.usedCapacity -= c.length();
					Files.delete(c.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			try {
				//Delete directory when it is empty
				Files.delete(dir.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
