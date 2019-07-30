package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadSend extends Thread {
	protected Socket socket;
	
	public ThreadSend(Socket socket) {
		this.socket = socket;
	}
	public void run(){
		
		String sentence;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		DataOutputStream outToServer;
		try {
			outToServer = new DataOutputStream(socket.getOutputStream());
			while (true) {
				try {
				  sentence = inFromUser.readLine();
				  outToServer.writeBytes(sentence + '\n');
				}
				catch(Exception e){
					System.out.println("Error: "+ e);
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error: "+ e1);
			this.stop();
		}
				
		
	}
	
		  	
}
