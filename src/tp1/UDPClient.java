package tp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

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
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(hostname);
		byte[] receiveData = new byte[1024];
		byte[] sendData = sentence.getBytes();
		System.out.println("Sending to " + hostname + ":" + port + "  : " + sentence);
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String response = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
		if(response.startsWith("-1")){
			System.out.println("ERROR");
		} else {
			System.out.println(response);
		}
		clientSocket.close();

	}

}
