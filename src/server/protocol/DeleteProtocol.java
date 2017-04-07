package server.protocol;

import utils.Utils;
import server.main.Peer;
import server.task.initiatorPeer.Delete;

public class DeleteProtocol {
	//private static final int N_DELETES_TRIES = 5;

	 public DeleteProtocol(String filePath){
		 //for(int i=0; i < N_DELETES_TRIES; i++){
			 new Thread(new Delete(Peer.mdMap.get(filePath))).start();
		 //}
		 	Peer.mdMap.remove(filePath);
			Utils.writeMD();
	 }

}
