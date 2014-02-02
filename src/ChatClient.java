

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatClient {
	private Socket mySocket = null;
	private BufferedReader inputStream = null;
	private DataOutputStream outputStream = null;
	private String endLine = ".bye";
	
	public ChatClient(String serverName, int portNum){
		System.out.println("Trying to connect to server on portNum: " + portNum);
		try{
			mySocket = new Socket(serverName,portNum);
			System.out.println("Connected!");
			start();
		}
		catch (IOException e){
			System.out.println(e);
		}
		haveChat();
		stop();
	}
	
	private void haveChat(){
		String line = "";
		while (!line.equals(this.endLine)){
			try{
				line = this.inputStream.readLine();
				outputStream.writeUTF(line);
				outputStream.flush();
			} catch(IOException ioe){
				System.out.println("Error: " + ioe.getMessage());
			}
		}
	}
	
	private void start() throws IOException{
		inputStream = new BufferedReader(new InputStreamReader(System.in));
		outputStream = new DataOutputStream(this.mySocket.getOutputStream());
	}
	
	private void stop(){
		 try
	      {  if (inputStream   != null)  inputStream.close();
	         if (outputStream != null)  outputStream.close();
	         if (mySocket    != null)  mySocket.close();
	      }
	      catch(IOException ioe)
	      {  System.out.println("Error closing ...");
	      }
	}
	
	public static void main(String args[]){
		ChatClient client = null;
	    if (args.length != 2)
	         System.out.println("Usage: java ChatClient host port");
	    else
	         client = new ChatClient(args[0], Integer.parseInt(args[1]));
	}
}
