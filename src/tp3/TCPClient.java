package tp3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {

	public static void main(String[] args) throws IOException {
		String sentence = null;
		if (args.length == 5 && args[2].equals("register")){
			sentence = args[2] + " " +  args[3] + " " + args[4];
		} else if (args.length == 4 && args[2].equals("lookup")){
			sentence = args[2] + " " +  args[3];
		} else {
			return;
		}
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		Socket clientSocket = new Socket(hostname, port);
		PrintWriter out = null;
		BufferedReader in = null;
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		out.println(sentence);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String response = in.readLine();
		if(response.startsWith("-1")){
			System.out.println("ERROR");
		} else {
			System.out.println(response);
		}
		out.close();
		in.close();
		clientSocket.close();

	}

}
