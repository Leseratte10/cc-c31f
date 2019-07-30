package client;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;


import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.InputStreamReader;
public class TCPClient {
	 public static void main(String args[]) throws Exception {
		 int var = 2;
		 boolean hirn = true;
		  String benutzer = JOptionPane.showInputDialog("Gib deinen Benutzernamen ein!");
		  while(hirn) {
		  	String variante = JOptionPane.showInputDialog("Wähle die Eingabeplattform: (c)onsole oder (f)enster");
		  	if (variante.contentEquals("c")) {
		  	var = 2;
		  	hirn = false;
		  	}else if (variante.contentEquals("f")){
		  		var = 3;
		  		hirn = false;
		  	} 
		  }
		  String sentence;
		  String modifiedSentence;
		  boolean tf = true;
		  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		  Socket clientSocket = new Socket("172.24.0.19", 1980);
		  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		  outToServer.writeBytes(benutzer + " is now online!" + "\n");
		 
		  if (var == 3) {
		  	while (tf) {
					  sentence = JOptionPane.showInputDialog("");
					  
					  if (sentence.contentEquals("")||sentence.contentEquals(".exit")) {
					  tf = false;
					  outToServer.writeBytes(benutzer + " has gone offline!"+"\n");
					  } else {
					  outToServer.writeBytes(benutzer + ": " + sentence + '\n');
					  }
		  
		  	}
		  }else {
			  while (tf) {
				  sentence = inFromUser.readLine();
				  
				  if (sentence.contentEquals("")||sentence.contentEquals(".exit")) {
				  tf = false;
				  outToServer.writeBytes(benutzer + " has gone offline!"+"\n");
				  } else {
				  outToServer.writeBytes(benutzer + ": " + sentence + '\n');
				  }  
		  }
	 }
	 }
}
