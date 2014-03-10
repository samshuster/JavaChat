
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer implements Runnable {
	private ServerSocket service = null;
	private ChatServerHelper helper = null;
	private Thread thread = null;

	public ChatServer(int portNum) {
		try {
			System.out.println("Creating a Chat Server on portNum: " + portNum);
			service = new ServerSocket(portNum);
			service.setSoTimeout(1000);
			System.out.println("Chat Server Started");
			System.out.println("Waiting for a client");
			System.out.println(service.getInetAddress());
			System.out.println("Success!");
			helper = new ChatServerHelper(service);
			start();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	private void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	private void close() throws IOException {
		helper.interrupt();
		if (service != null) {
			service.close();
		}
	}
	
	

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		boolean done = false;
		try {
			while (!done) {
					System.out.println("Waiting...");
					helper.join();
					List<Connection> conns = helper.getConnections();
					System.out.println(conns);
						List<String> text = new ArrayList<String>();
						for(int i=0; i<conns.size(); i++){
							Connection curr = conns.get(i);
							String line = "";
							if(curr == null || curr.isSocketClosed()){
								System.out.println("Socket is closed");
								conns.set(i, null);
							} else {
								DataInputStream input = curr.getInputStream();
								//line = input.readUTF();
								//System.out.println(line);
							}
							text.add(line);
							System.out.println(text);
						}
						for(int i=0; i<conns.size(); i++){
							Connection curr = conns.get(i);
							if(curr == null){
								
							} else {
								DataOutputStream output = curr.getOutputStream();
								for(int j=0; j<conns.size(); j++){
									if(j!=i){
										output.writeUTF(text.get(j));
										output.writeUTF("\n");
										output.flush();
									}
								}
							}
						}
					helper.start();
			}
			close();
			} catch (IOException ioe) {
				done = true;
				System.out.println("IOException... exiting");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static void main(String args[]) {
		ChatServer server = null;
		if (args.length != 1)
			System.out.println("Usage: java ChatServer port");
		else
			server = new ChatServer(Integer.parseInt(args[0]));
	}
}
