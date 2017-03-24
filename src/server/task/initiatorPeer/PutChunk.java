package server.task.initiatorPeer;

public class PutChunk implements Runnable{
	
	private String version;
	private int senderID;
	private String fileID;
	private int chunkNo;
	private int replicationDegree;
	private byte[] body;
	
	public PutChunk(String version, int senderID, String fileID, int chunkNo, int replicationDegree, byte[] body) {
		this.version = version;
		this.senderID = senderID;
		this.fileID = fileID;
		this.chunkNo = chunkNo;
		this.replicationDegree = replicationDegree;
		this.body = body;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


}
