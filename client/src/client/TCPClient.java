package client;



import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class TCPClient {
	static boolean running = true;
    private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	 public static void main(String argv[]) throws Exception {
		  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		  Socket clientSocket = new Socket("172.24.0.42", 1988);
		  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	      LocalDateTime now = LocalDateTime.now();
	        
		  new ThreadSend(clientSocket).start();
		  new ThreadReceive(clientSocket).start();
		  
		  String Benutzername;  
		  System.out.print(sdf.format(now) +"Dein Benutername ist "+System.getProperty("user.name")+"!");
		  
		  outToServer.writeBytes(System.getProperty("user.name") + '\n');

	 }
}