package server.protocol;

import server.main.Peer;
import server.task.initiatorPeer.PutChunk;
import server.task.commonPeer.GetChunk;
import utils.Utils;

public class Restore {
	
	 public Restore(String filePath){			 
		int chunkNumber = 0;		 
		do{
			//TODO
			// 1- Get fileId from md file from filePath
			new Thread(new GetChunk(Peer.serverID,"1",chunkNumber));
			chunkNumber++;	
		}while();
	 }

}
