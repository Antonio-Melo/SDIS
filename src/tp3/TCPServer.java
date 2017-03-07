package tp3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class TCPServer {
	public static void main(String[] args) throws IOException {
		HashMap<String,String> db = new HashMap<String,String>();
		int port = Integer.parseInt(args[0]);
		ServerSocket serverSocket = new ServerSocket(port);
		String sendData = null;
		while(true)
		{
			Socket responseSocket = serverSocket.accept();
			PrintWriter out = null;
			BufferedReader in = null;
			in = new BufferedReader(new InputStreamReader(responseSocket.getInputStream()));
			out = new PrintWriter(responseSocket.getOutputStream(), true);

			String sentence = in.readLine();
			System.out.println("Received: " + sentence);
			String sentenceSplit[] = sentence.split("\\s+");
			if (sentenceSplit[0].equals("register")){
				if(!db.containsKey(sentenceSplit[1])){
					db.put(sentenceSplit[1], sentenceSplit[2]);
					sendData = new String(db.size() + "\n" + sentenceSplit[1] + " " + sentenceSplit[2]);
				} else {
					sendData = new String("-1\n" + sentenceSplit[1] + " " + sentenceSplit[2]);
				}
			}else if (sentenceSplit[0].equals("lookup")){
				System.out.println(sentenceSplit[1]);
				if(db.containsKey(sentenceSplit[1])){
					String owner = db.get(sentenceSplit[1]);
					sendData = new String(db.size() + "\n" + sentenceSplit[1] + " " + owner);
				} else {
					sendData = new String("-1\n" + sentenceSplit[1]);
				}
			}
			out.println(sendData);
			System.out.println("Response sent: " + sendData);
			out.close();
			in.close();
			responseSocket.close();
		}
	}
}
