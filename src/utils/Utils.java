package utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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



}
