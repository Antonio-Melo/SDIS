package server.protocol;

import java.io.BufferedReader;
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
				
				state += "FilePath: " + FilePath +"\n";
				state += "FileId: " + FileId +"\n";
				state += "Desired replication degree: "+ Peer.rdMap.get(FileId+Utils.FS+0)[0];
				state += "Chunk files:";
				//for(int i = 0; i <Peer.rdMap.get(FileId+Utils.FS+0))
				state += "-----------------------------\n";
				FilePath = reader.readLine();
			}

			//GET CHUNK ID's
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
