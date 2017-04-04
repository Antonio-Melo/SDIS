package server.protocol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import server.main.Peer;
import utils.Utils;

public class CheckState {

	public CheckState(){
	}

	public String getState() {
		// TODO Auto-generated method stub
		String state = new String("");


		try{
			//READ FILES
			FileInputStream fis = new FileInputStream(Peer.mdFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			
			state += "FILES BACKED UP\n";
			String FilePath = reader.readLine();
			while(FilePath != null){
				reader.readLine();
				String FileId = reader.readLine();
				
				state += "  FilePath: " + FilePath +"\n";
				state += "  FileId: " + FileId +"\n";
				state += "  Desired replication degree: "+ Peer.rdMap.get(FileId+Utils.FS+0)[0]+"\n";
				state += "  Chunk files:"+"\n";
				int chunkNo = 0;
				int[] rds = Peer.rdMap.get(FileId+Utils.FS+chunkNo);
				while(rds != null){
					state += "    Chunk id: " +chunkNo+" Perceived Replication Degree: "+rds[1]+"\n";
					chunkNo++;
				}
				state += "-----------------------------\n";
				FilePath = reader.readLine();
			}
			state += "----------------------------------------------------\n";
			state += "FILES STORED\n";
			
			File dir = new File(Peer.dataPath);
			String[] fileIds = dir.list();
			for(String file:fileIds){
				state += "  File id: "+ file+"\n";
				File fileDir = new File(Peer.dataPath + Utils.FS+file);
				String[] chunks = fileDir.list();
				for(String chunkNumber:chunks){
					File chunk = new File(Peer.dataPath+ Utils.FS+ file+Utils.FS+chunkNumber);
					state += "    Chunk id: "+ chunkNumber + " Size: "+ chunk.getTotalSpace()/1000+ " Perceived Replication Degree: "+ Peer.rdMap.get(file+Utils.FS+chunkNumber)[1]+"\n"; ;
				} 
			}
			state += "----------------------------------------------------\n";
			state += "PEER CAPACITY\n";
			state += "  Total capacity: ";
			if(Peer.capacity != 0){
				state += Peer.capacity/1000+"\n";
			}else state += "infinite\n";
			state += "  Capacity used: "+ dir.getTotalSpace()/1000;
			
			

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
