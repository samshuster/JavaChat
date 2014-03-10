import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


public class ChatServerHelper implements Runnable{

	private Thread thread = null;
	private List<Connection> connList = null;
	private ServerSocket service = null;
	
	public ChatServerHelper(ServerSocket service){
		this.service = service;
		connList = new ArrayList<Connection>();
		this.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
			try {
				System.out.println("Looking for new socket");
				Socket newSocket = service.accept();
				Connection newConnection = new Connection(newSocket);
				connList.add(newConnection);
			} catch (SocketTimeoutException e) {
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println("Done looking for new socket");
	}
	
	public synchronized List<Connection> getConnections(){
		return connList;
	}
	
	public void interrupt(){
		if(thread!=null){
			thread.interrupt();
		}
	}
	
	public void join() throws InterruptedException{
		if(thread!=null){
			thread.join();
		}
	}
	
	public void start(){
		if (thread == null || !thread.isAlive()) {
			thread = new Thread(this);
			thread.start();
		}
	}

}
