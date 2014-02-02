
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ChatServer implements Runnable {
	private ServerSocket service = null;
	private Socket socket = null;
	private DataInputStream streamIn = null;
	private Thread thread = null;

	public ChatServer(int portNum) {
		try {
			System.out.println("Creating a Chat Server on portNum: " + portNum);
			service = new ServerSocket(portNum);
			System.out.println("Chat Server Started");
			System.out.println("Waiting for a client");
			System.out.println(service.getInetAddress());
			System.out.println("Success!");
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
	
	private void open() throws IOException {
		this.streamIn = new DataInputStream(new BufferedInputStream(
				this.socket.getInputStream()));
	}

	private void close() throws IOException {
		if (streamIn != null) {
			streamIn.close();
		}
		if (socket != null) {
			socket.close();
		}
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
		try {
			socket = service.accept();
			open();
			boolean done = false;
			while (!done) {
				try {
					String curLine = streamIn.readUTF();
					System.out.println(curLine);
					done = curLine.equals(".bye");
				} catch (IOException ioe) {
					done = true;
				}
			}
			close();
		} catch (IOException e) {
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
