package tp2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UDPServer {

	public static void main(String[] args) throws IOException {
		HashMap<String,String> db = new HashMap<String,String>();
		int port = Integer.parseInt(args[0]);
		DatagramSocket serverSocket = new DatagramSocket(port);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		MulticastTask task = new MulticastTask(args[1], Integer.parseInt(args[2]),port);
		
		executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
		
		while(true)
		{
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
			System.out.println("Received: " + sentence);
			String sentenceSplit[] = sentence.split("\\s+");
			if (sentenceSplit[0].equals("register")){
				if(!db.containsKey(sentenceSplit[1])){
					db.put(sentenceSplit[1], sentenceSplit[2]);
					sendData = new String(db.size() + "\n" + sentenceSplit[1] + " " + sentenceSplit[2]).getBytes();
				} else {
					sendData = new String("-1\n" + sentenceSplit[1] + " " + sentenceSplit[2]).getBytes();
				}
			}else if (sentenceSplit[0].equals("lookup")){
				System.out.println(sentenceSplit[1]);
				if(db.containsKey(sentenceSplit[1])){
					String owner = db.get(sentenceSplit[1]);
					sendData = new String(db.size() + "\n" + sentenceSplit[1] + " " + owner).getBytes();
				} else {
					sendData = new String("-1\n" + sentenceSplit[1]).getBytes();
				}
			}
			InetAddress IPAddress = receivePacket.getAddress();
			int clientPort = receivePacket.getPort();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
			serverSocket.send(sendPacket);
			System.out.println("Response sent...");
			receiveData = new byte[1024];
		}

	}

}
