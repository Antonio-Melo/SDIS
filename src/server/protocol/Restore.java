package server.protocol;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import server.main.Peer;
import server.task.initiatorPeer.GetChunk;
import utils.Utils;

public class Restore {

	public Restore(String filePath){
		int chunkNumber = 0;		
		String fileId = Utils.getFileIDfromMD(filePath);
		OutputStream output = null;
		
		if(fileId != null){
			try {
				output = new BufferedOutputStream(new FileOutputStream(filePath));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] chunk = null;
			do{
				GetChunk getChunk = new GetChunk(Peer.serverID,Peer.protocolVersion,chunkNumber);
				Thread getChunkThread = new Thread(getChunk);
				getChunkThread.start();

				try {
					getChunkThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
				chunk = getChunk.getChunk();
				if(chunk != null){
					try {
						output.write(chunk, chunkNumber*64000, chunk.length);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else break;

				chunkNumber++;
			}while(chunk.length != 64000);
		}
	}

}
