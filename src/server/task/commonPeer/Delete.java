package server.task.commonPeer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import server.main.Peer;
import utils.Utils;

public class Delete implements Runnable {
	public static void main(String[] args) throws IOException {
		new Thread(new Delete("1.0", 2, "fdahsjkhfuihsdf")).start();
	}

	String version;
	int senderID;
	String fileID;

	public Delete(String version, int senderID, String fileID) {
		this.version = version;
		this.senderID = senderID;
		this.fileID = fileID;
	}

	@Override
	public void run() {
		System.out.println("vou apagar file");
		File dir = new File(Peer.dataPath + Utils.FS + this.fileID);
		if (dir.isDirectory()) {
			for (File c : dir.listFiles())
				try {
					//Delete files inside directory
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
