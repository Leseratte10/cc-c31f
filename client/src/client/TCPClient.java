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
		  
		  DataOutputStream outToServer = null;
		  Socket clientSocket = null;
		  
		 try {
			  clientSocket = new Socket("172.24.0.42", 1988);
			  outToServer = new DataOutputStream(clientSocket.getOutputStream());
		 } 
		  catch (java.net.SocketException e) {


					  System.out.println("Kein aufbau zum Server");
					  TimeUnit.MINUTES.sleep(1);
					  System.exit(1);
				
			  }
		  
		  
		 try {
			  clientSocket = new Socket("172.24.0.42", 1988);

		 }
		 catch(ConnectException i) {
			  System.out.println("Kein aufbau zum Server");
			  TimeUnit.MINUTES.sleep(1);
			  System.exit(1);
			  }
		


		  
			java.util.Date now = new java.util.Date(System.currentTimeMillis());
		  
		  String Benutzername;
		  System.out.println("Benutzername eingeben! Wenn du keinen eingibst, wird dein Windows Benutzername gebraucht!");
		  Benutzername = inFromUser.readLine();
	  if (Benutzername == "" || Benutzername == "	"|| Benutzername == " ") {

		  String userName = System.getProperty("user.name");
		  Benutzername = userName;
		  System.out.print(sdf.format(now) +"Dein Benutername ist "+userName+"!");//
	  }
	  else {
		  System.out.print(sdf.format(now) +"Dein Benutername ist "+ Benutzername +"!"); //
		  }
	  outToServer.writeBytes(Benutzername + '\n');
	  
		  
		  new ThreadSend(clientSocket).start();
		  new ThreadReceive(clientSocket).start();

		  
	 }
}