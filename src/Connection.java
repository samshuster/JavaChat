import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class Connection {
	private DataInputStream streamIn = null;
	private Socket socket = null;
	private DataOutputStream streamOut = null;
	
	public Connection(Socket socket){
		this.socket = socket;
		try {
			this.streamIn = new DataInputStream(new BufferedInputStream(
					this.socket.getInputStream()));
			this.streamOut = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized DataInputStream getInputStream(){
		return streamIn;
	}
	
	public synchronized DataOutputStream getOutputStream(){
		return streamOut;
	}
	
	public boolean isSocketClosed(){
		return this.socket.isClosed();
	}
	
	public void close() throws IOException{
		if (streamIn != null) streamIn.close();
		if (socket != null) socket.close();
	}
	
}
