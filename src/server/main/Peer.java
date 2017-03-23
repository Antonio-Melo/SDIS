package server.main;

public class Peer {
	
	public static int serverID = 1;
	public static String mcAddress = "166.0.0.1";
	public static int mcPort = 9001;
	public static String mdbAddress = "166.0.0.2";
	public static int mdbPort = 9002;
	public static String mdrAddress = "166.0.0.3";
	public static int mdrPort = 9003;
	public static String dataPath = ".\\" + serverID + "data";
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
		// TODO Auto-generated method stub
		if(args.length != 7 && args.length != 9){
			System.out.println("Usage: MainServer <Server ID> <MC Address> <MC Port> <MDB Address> <MDB Port>  <MDR Address> <MDR Port>");
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
		
		Thread mcThread = new Thread(mcListener);
		Thread mdbThread = new Thread(mdbListener);
		
		mcThread.start();
		mdbThread.start();
		
		System.out.println("Services running...");
		
		System.out.println("Starting services...");
	}

}
