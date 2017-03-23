package server.protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Backup {

	public Backup(String filePath, int replicationDeg) {
		//TODO

		File f = new File(filePath);
		long fileLength = f.length();
		byte[] chunk = new byte[64000];
		
		try {
			InputStream in = new FileInputStream(f);
			
			int numChunks = (int)(fileLength/64000)+1;
			int lastChunkSize = (int)(fileLength%64000);
			for(int i = 0;i < numChunks;i++){
				if(i == numChunks-1){
					chunk = new byte[lastChunkSize];
				}
				int numbytesRead = in.read(chunk, i*64000, 64000);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
