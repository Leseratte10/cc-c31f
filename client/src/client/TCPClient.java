package client;
<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

=======
>>>>>>> 36e8899f973545cfe875d4ea5f70ac305c2ca01c

import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
<<<<<<< HEAD
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
=======
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class TCPClient {
	static boolean running = true;
    private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	 public static void main(String argv[]) throws Exception {
		  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		  
		  DataOutputStream outToServer;
		  Socket clientSocket;
		 try {
			  clientSocket = new Socket("172.24.0.42", 1988);
			  outToServer = new DataOutputStream(clientSocket.getOutputStream());
		 } 
		  catch (ConnectException e) {
			  while(true) {
				  try {
					  clientSocket = new Socket("172.24.0.42", 1988);
					  outToServer = new DataOutputStream(clientSocket.getOutputStream());  
					  break;
				  }
				  catch(ConnectException i){
					  clientSocket = new Socket("172.24.0.42", 1988);
					  outToServer = new DataOutputStream(clientSocket.getOutputStream());
					  System.out.println("Kein aufbau zum Server");
					  TimeUnit.SECONDS.sleep(30);
				  }
			  }
		  }

		  
	      LocalDateTime now = LocalDateTime.now();
		  
		  String Benutzername;
		  System.out.print("Benutzername eingeben! Wenn du keinen eingibst, wird dein Windows-"+'\n'+"benutzername gebraucht!");
		  Benutzername = inFromUser.readLine();
	  if (Benutzername == "" || Benutzername == "	"|| Benutzername == " ") {

		  Benutzername = System.getProperty("user.name");
		  System.out.print(sdf.format(now) +"Dein Benutername ist "+System.getProperty("user.name")+"!");
	  }
	  else {
		  System.out.print(sdf.format(now) +"Dein Benutername ist "+Benutzername+"!");
		  }
	  outToServer.writeBytes(Benutzername + '\n');
	  
		  
		  new ThreadSend(clientSocket).start();
		  new ThreadReceive(clientSocket).start();

		  
	 }
}
>>>>>>> 36e8899f973545cfe875d4ea5f70ac305c2ca01c
