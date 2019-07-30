package client;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {
	 public static void main(String argv[]) throws Exception {
	  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	  Socket clientSocket = new Socket("172.24.0.42", 1988);
	  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	  new ThreadReceive(clientSocket).start();
	 }
	}
