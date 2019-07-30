package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {
	static boolean running = true;
	 public static void main(String argv[]) throws Exception {
		 String sentence;
		 String modifiedSentence = null;
		 BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		 Socket clientSocket = new Socket("172.24.0.11", 56788);
		 while(running) {

			 DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			 BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 sentence = inFromUser.readLine();
			 outToServer.writeBytes(sentence + '\n');
			 modifiedSentence = inFromServer.readLine();
			  
			  System.out.println("FROM SERVER: " + modifiedSentence);
			  
		 }
		 clientSocket.close(); 
	 }
	}