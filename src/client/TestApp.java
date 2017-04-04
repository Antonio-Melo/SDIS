package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.protocol.ClientInterface;


public class TestApp {

	public static void main(String[] args) {
		if(args.length < 1){
			System.out.println("Usage: TestApp <remote_object_name> <sub_protocol> <opnd_1> <opnd_2>");
			return;
		}
		String remoteObject = args[0];

		try {
			Registry registry = LocateRegistry.getRegistry();
			ClientInterface stub = (ClientInterface) registry.lookup(remoteObject);
			if(args.length == 4){
				if(args[1].equals("BACKUP")){
					stub.backup(args[2],Integer.parseInt(args[3]));
				}
			} else if(args.length == 3){
				if(args[1].equals("RESTORE")){
					stub.restore(args[2]);
				}else if(args[1].equals("DELETE")){
					stub.delete(args[2]);
				}else if(args[1].equals("RECLAIM")){
					stub.reclaim(Integer.parseInt(args[2]));
				}
			} else if(args.length == 2){
				if(args[1].equals("STATE")){
					System.out.println(stub.state());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		

	}

}
