package server.main;

import java.rmi.RemoteException;

import server.protocol.*;

public class ClientAppListener implements ClientInterface{

	@Override
	public void backup(String filePath, int replicationDeg) throws RemoteException {
		new Backup(filePath, replicationDeg);
	}

	@Override
	public void restore(String filePath) throws RemoteException {
		new Restore(filePath);
	}

	@Override
	public void delete(String filePath) throws RemoteException {
		new Delete(filePath);
	}

	@Override
	public void reclaim(int diskSpace) throws RemoteException {
		new Reclaim(diskSpace);
	}

	@Override
	public String state() throws RemoteException {
		return new CheckState().getState();
	}

}
