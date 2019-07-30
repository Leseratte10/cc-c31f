package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
		  System.out.print("Benutzername eingeben! Wenn du keinen eingibst, wird dein Windows-"+'\n'+"Benutzername gebraucht!");

		  Benutzername = inFromUser.readLine();
	  if (Benutzername == "" || Benutzername == "	"|| Benutzername == " ") {

		  Benutzername = System.getProperty("user.name");
		  System.out.print(sdf.format(now) +"Dein Benutzername ist "+System.getProperty("user.name")+"!");
	  }
	  else {
		  System.out.print(sdf.format(now) +"Dein Benutername ist "+Benutzername+"!");
		  }
	  outToServer.writeBytes(Benutzername + '\n');
	  
		  
		  new ThreadSend(clientSocket).start();
		  new ThreadReceive(clientSocket).start();

		  
	 }
}