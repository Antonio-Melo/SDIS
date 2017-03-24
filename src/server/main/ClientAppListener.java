package server.main;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import server.protocol.*;

public class ClientAppListener implements ClientInterface{
	
	@Override
	public void backup(String filePath, int replicationDeg) throws RemoteException {
		System.out.println("boas, sou o server, vou comecar backup e já digo alguma coisa");
		new Backup(filePath, replicationDeg);
	}

	@Override
	public void restore(String filePath) throws RemoteException {
		System.out.println("boas, sou o server, vou comecar restore e já digo alguma coisa");
		new Restore(filePath);
	}

	@Override
	public void delete(String filePath) throws RemoteException {
		System.out.println("boas, sou o server, vou comecar delete e já digo alguma coisa");
		new DeleteProtocol(filePath);
	}

	@Override
	public void reclaim(int diskSpace) throws RemoteException {
		System.out.println("boas, sou o server, vou comecar reclaim e já digo alguma coisa");
		new Reclaim(diskSpace);
	}

	@Override
	public String state() throws RemoteException {
		System.out.println("boas, sou o server, vou comecar state e já digo alguma coisa");
		return new CheckState().getState();
	}

}
