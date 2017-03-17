package server.protocol;

import java.rmi.*;

public interface ClientInterface extends Remote
{
    public void backup(String filePath, int replicationDeg) throws RemoteException;
    public void restore(String filePath) throws RemoteException;
    public void delete(String filePath) throws RemoteException;
    public void reclaim(int diskSpace) throws RemoteException;
    public String state() throws RemoteException;
} 