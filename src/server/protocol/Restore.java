package server.protocol;

import server.main.Peer;
import server.task.initiatorPeer.GetChunk;
import utils.Utils;

public class Restore {
	
	 public Restore(String filePath){			 
		int chunkNumber = 0;		 
		do{
			//TODO
			// 1- Get fileId from md file from filePath
			GetChunk getChunk = new GetChunk(Peer.serverID,"1",chunkNumber);
			Thread getChunkThread = new Thread(getChunk);
			getChunkThread.start();
			try {
				getChunkThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
			/*if(this.chunk != null){
				//Guardar o chunk na pasta certa
			}else ; //Não recebeu*/
			chunkNumber++;	
		}while(1==1);
	 }

}
