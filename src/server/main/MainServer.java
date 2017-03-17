package server.main;

public class MainServer {

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
		if(args.length != 8){
			System.out.println("Usage: MainServer <Server ID> <MC Address> <MC Port> <MDB Address> <MDB Port>  <MDR Address> <MDR Port>");
			return;
		}
		
		int serverID = Integer.parseInt(args[0]);
		if (serverID < 0){
			System.out.println("Server ID wrong!");
			return;
		}
		String mcAddress = args[1];
		if (mcAddress == null){
			System.out.println("MC Address wrong!");
			return;
		}
		int mcPort = Integer.parseInt(args[2]);
		if (mcPort < 1024){
			System.out.println("MC Port wrong!");
			return;
		}
		String mdbAddress = args[3];
		if (mdbAddress == null){
			System.out.println("MDB Address wrong!");
			return;
		}
		int mdbPort = Integer.parseInt(args[4]);
		if (mdbPort < 1024){
			System.out.println("MDB Port wrong!");
			return;
		}
		String mdrAddress = args[5];
		if (mdrAddress == null){
			System.out.println("MDR Address wrong!");
			return;
		}
		int mdrPort = Integer.parseInt(args[6]);
		if (mdrPort < 1024){
			System.out.println("MDR Port wrong!");
			return;
		}
		String remoteObject = args[7];
		if (remoteObject == null){
			System.out.println("Remote Object Name wrong!");
			return;
		}
		
		System.out.println("Starting services...");
		
		MCListener mcListener = new MCListener(mcAddress, mcPort);
		MDBListener mdbListener = new MDBListener(mdbAddress, mdbPort);
		
		Thread mcThread = new Thread(mcListener);
		Thread mdbThread = new Thread(mdbListener);
		
		System.out.println("Services running...");
		
		
	}

}
