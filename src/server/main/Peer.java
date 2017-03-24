package server.main;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import server.protocol.ClientInterface;
import utils.Utils;

public class Peer {

	public static int serverID = 1;
	public static String mcAddress = "224.0.0.1";
	public static int mcPort = 9001;
	public static String mdbAddress = "224.0.0.2";
	public static int mdbPort = 9002;
	public static String mdrAddress = "224.0.0.3";
	public static int mdrPort = 9003;
	public static String dataPath = "." + Utils.FS + serverID + "data";
	public static String rdPath = "." + Utils.FS + serverID + "rd";
	public static int capacity = 0;
	public static String remoteObject = "peer" + serverID;

	/**
	 * Main service starter
	 * @param args Server arguments by the following order:
	 * 1 - Server ID
	 * 2 - MC Address
	 * 3 - MC Port
	 * 4 - MDB Address
	 * 5 - MDB Port
	 * 6 - MDR Address
	 * 7 - MDR Port
	 * 8 - RMI Remote Object name
	 */
	public static void main(String[] args) {
		if(args.length != 7 && args.length != 9){
			System.out.println("Usage: MainServer <Server ID> <MC Address> <MC Port> <MDB Address> <MDB Port> <MDR Address> <MDR Port> <Data Path> <RMI Remote Object Name>");
			return;
		}

		serverID = Integer.parseInt(args[0]);
		if (serverID < 0){
			System.out.println("Server ID wrong!");
			return;
		}

		mcAddress = args[1];
		if (mcAddress == null){
			System.out.println("MC Address wrong!");
			return;
		}

		mcPort = Integer.parseInt(args[2]);
		if (mcPort < 1024){
			System.out.println("MC Port wrong!");
			return;
		}
		mdbAddress = args[3];
		if (mdbAddress == null){
			System.out.println("MDB Address wrong!");
			return;
		}

		mdbPort = Integer.parseInt(args[4]);
		if (mdbPort < 1024){
			System.out.println("MDB Port wrong!");
			return;
		}

		mdrAddress = args[5];
		if (mdrAddress == null){
			System.out.println("MDR Address wrong!");
			return;
		}

		mdrPort = Integer.parseInt(args[6]);
		if (mdrPort < 1024){
			System.out.println("MDR Port wrong!");
			return;
		}

		if(args.length == 9){
			dataPath = args[7];
			if (dataPath == null){
				System.out.println("Data Path wrong!");
				return;
			}

			remoteObject = args[8];
			if (remoteObject == null){
				System.out.println("Remote Object Name wrong!");
				return;
			}
		}

		System.out.println("Starting services...");

		MCListener mcListener = new MCListener();
		MDBListener mdbListener = new MDBListener();
		ClientAppListener clientAppListener = new ClientAppListener();

		Thread mcThread = new Thread(mcListener);
		Thread mdbThread = new Thread(mdbListener);
        // Bind the remote object's stub in the registry
        Registry registry;
		try {
			ClientInterface stub = (ClientInterface) UnicastRemoteObject.exportObject(clientAppListener, 0);
			registry = LocateRegistry.getRegistry();
	        registry.bind(remoteObject, stub);
		} catch (RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mcThread.start();
		mdbThread.start();

		System.out.println("Services running...");

		System.out.println("Running configurations:");
		System.out.println("Server ID: " + serverID);
		System.out.println("MC Multicast Channel: " + mcAddress + ":" + mcPort);
		System.out.println("MDB Multicast Channel: " + mdbAddress + ":" + mdbPort);
		System.out.println("MDR Multicast Channel: " + mdrAddress + ":" + mdrPort);
		System.out.println("Data Path: " + dataPath);
		System.out.println("Max capacity: " + 0);
		System.out.println("Remote Object Name: " + remoteObject);
	}

}
