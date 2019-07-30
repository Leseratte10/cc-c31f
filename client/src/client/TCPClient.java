package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {
	 public static void main(String argv[]) throws Exception {
	  String sentence;
	  String modifiedSentence;
	  String Benutzername;
	  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	  Socket clientSocket = new Socket("172.24.0.41", 1988);
	  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	  System.out.print("Benutzername eingeben");
	  Benutzername = inFromUser.readLine();
	  outToServer.writeBytes(Benutzername + '\n');
	  sentence = inFromUser.readLine();
	  outToServer.writeBytes(sentence + '\n');
	  modifiedSentence = inFromServer.readLine();
	  System.out.println("FROM SERVER: " + modifiedSentence);
	  clientSocket.close();
	 }
	}