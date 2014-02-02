import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


public class ChatServerHelper implements Runnable{

	public Thread thread = null;
	private List<Connection> connList = null;
	private ServerSocket service = null;
	private boolean stopServer = false;
	
	public ChatServerHelper(ServerSocket service){
		this.service = service;
		connList = new ArrayList<Connection>();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!stopServer) {
			try {
				System.out.println("Looking for new socket");
				Socket newSocket = service.accept();
				Connection newConnection = new Connection(newSocket);
				synchronized (connList) {
					connList.add(newConnection);
				}
			} catch (SocketTimeoutException e) {
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public List<Connection> getConnections(){
		return connList;
	}
	
	public void close(){
		stopServer = true;
	}

}
