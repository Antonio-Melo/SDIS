package tp2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class MulticastTask implements Runnable{
	
	String multicast_address = null;
	int multicast_port = 0;
	
	public MulticastTask(String multicast_address, int multicast_port){
		this.multicast_address = multicast_address;
		this.multicast_port = multicast_port;
	}

	@Override
	public void run() {
        // Get the address that we are going to connect to.
        InetAddress addr;
		try {
			addr = InetAddress.getByName(multicast_address);
     
        // Open a new DatagramSocket, which will be used to send the data.
        DatagramSocket serverSocket = new DatagramSocket();
                String msg = "ora boas pessoal!";

                // Create a packet that will contain the data
                // (in the form of bytes) and send it.
                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, addr, multicast_port);
                serverSocket.send(msgPacket);
     
                System.out.println("Server sent packet with msg: " + msg);
                serverSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
	}

	

}
