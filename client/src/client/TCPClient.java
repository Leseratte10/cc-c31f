package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class TCPClient {
	static boolean running = true;
	private static final DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	 public static void main(String argv[]) throws Exception {
		  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		  
		  DataOutputStream outToServer;
		  Socket clientSocket;
		 try {
			  clientSocket = new Socket("172.24.0.42", 1988);
			  outToServer = new DataOutputStream(clientSocket.getOutputStream());
		 } 
		  catch (java.net.SocketException e) {
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

		  
			java.util.Date now = new java.util.Date(System.currentTimeMillis());
		  
		  String Benutzername;
		  System.out.print("Benutzername eingeben! Wenn du keinen eingibst, wird dein Windows Benutzername gebraucht!");

		  Benutzername = inFromUser.readLine();
		  
	  if (Benutzername == "" || Benutzername == "	"|| Benutzername == " ") {

		  Benutzername = System.getProperty("user.name");

		  System.out.print(sdf.format(now) +"Dein Benutername ist "+System.getProperty("user.name")+"!");//

	  }
	  else {
		  System.out.print(sdf.format(now) +"Dein Benutername ist "+ Benutzername +"!"); //
		  }
	  outToServer.writeBytes(Benutzername + '\n');
	  
	  System.out.print("In welchen der folgenden Räume möchtest du beitreten?");
      InputStream inp = null;
      BufferedReader brinp = null;
      
      try {
          inp = clientSocket.getInputStream();
          brinp = new BufferedReader(new InputStreamReader(inp));
      } 
      catch (IOException e) {
          return;
      }
      String raumname = brinp.readLine();
      ArrayList<String> raumliste = (ArrayList<String>) Arrays.asList(raumname.split(";"));
      for (String name:raumliste) {
    	  System.out.print(name);
      }
      raumname = inFromUser.readLine();
      outToServer.writeBytes(raumname);
	  
		  new ThreadSend(clientSocket).start();
		  new ThreadReceive(clientSocket).start();

		  
	 }
}