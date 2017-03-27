package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import server.main.Peer;

public final class Utils {

	public static final String FS = System.getProperty("file.separator");
	public static final String CRLF = "\r\n";
	public static final String Space = " ";
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static void main(String[] args) {
		getFileID("C:\\Users\\Telmo\\Desktop\\18515482.jpg");
	}

	public static final String getFileID(String filePath){
		File file = new File(filePath);

		String raw = file.getAbsolutePath() + file.length() + file.lastModified();
		System.out.println(raw);

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(raw.getBytes("UTF-8"));
			byte[] hash = md.digest();
			System.out.println(hash);
			String fileID = bytesToHex(hash);
			System.out.println(fileID);

			return fileID;
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static final boolean loadRD(){
		try{
			FileInputStream fis = new FileInputStream(Peer.rdFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

			String fileID = reader.readLine();
			int[] rds = null;
			while(fileID != null){
				rds[0] = Integer.parseInt(reader.readLine());
				rds[1] = Integer.parseInt(reader.readLine());
				Peer.rdMap.put(fileID, rds);
				fileID = reader.readLine();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static final boolean saveRD(){
		try{
	    PrintWriter writer = new PrintWriter(Peer.rdFile, "UTF-8");
	    Iterator it = Peer.rdMap.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry<String,int[]> pair = (HashMap.Entry)it.next();
	        writer.println(pair.getKey());
	        writer.println(pair.getValue()[0]);
	        writer.println(pair.getValue()[1]);
	        it.remove();
	    }
	    writer.close();
		return true;
	} catch (IOException e) {
		return false;
	}
	}

}
