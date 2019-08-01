package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import javax.swing.JOptionPane;

public class TCPClient {
	static boolean running = true;
	private static final DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	 public static void main(String argv[]) throws Exception {
		 boolean ent = true;
		 int eingabe = 2;
		 String Benutzername;
		 
			 String eingabeForm = JOptionPane.showInputDialog("Wähle die Eingabeform: (f)enster oder (c)onsole!");
			 if (eingabeForm.equals("f")) {
				 ent = false;
				 eingabe = 1;
			 }else if (eingabeForm.equals("c")){
				 ent = false;
				 eingabe = 2;
			 } else if(eingabeForm.equals("console")){
				 ent = false;
				 eingabe = 2;
			 } else if(eingabeForm.equals("fenster")) {
				 ent = false;
				 eingabe = 1;
			 } else if(eingabeForm.equals("Console")){
				 ent = false;
				 eingabe = 2;
			 } else if(eingabeForm.equals("Fenster")) {
				 ent = false;
				 eingabe = 1;
			 }
			 
		 
		 
			  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			  DataOutputStream outToServer = null;
			  Socket clientSocket = null;
			 //Fehlerbehebung von zeile 50 bis 83
			  
			  
			 try {
				  clientSocket = new Socket("172.24.0.19", 1988);
				  outToServer = new DataOutputStream(clientSocket.getOutputStream());
			 } 
			  catch (SocketException e) {
	
				  if (eingabe == 1) {
					  	JOptionPane.showMessageDialog(null,"Warnung" + '\n'+ "Kein Aufbau zum Server");
					  	TimeUnit.SECONDS.sleep(30);
					  	System.exit(1);
				  }
				  else {
					  System.out.println("Kein Aufbau zum Server");
					  TimeUnit.SECONDS.sleep(30);
					  System.exit(1);
				  }
					
			  }
			 try {
				  clientSocket = new Socket("172.24.0.19", 1988);
	
			 }
			 catch(ConnectException i) {
				  if (eingabe == 1) {
					  JOptionPane.showMessageDialog(null,"Warnung" + '\n'+ "Kein Aufbau zum Server");
					  TimeUnit.SECONDS.sleep(30);
					  System.exit(1);
				  }
				  else {
				  System.out.println("Kein Aufbau zum Server");
				  TimeUnit.SECONDS.sleep(30);
				  System.exit(1);
			  	}
			 }
			

				java.util.Date now = new java.util.Date(System.currentTimeMillis());
			  
			  
			  
		if(eingabe == 2) {
			System.out.println("Gib deinen Benutzernamen ein! (Windows-Benutzername wird als Default verwendet.)");
		
			  Benutzername = inFromUser.readLine();
		  if (Benutzername == "" || Benutzername == "	"|| Benutzername == " ") {
	
			  String userName = System.getProperty("user.name");
			  Benutzername = userName;
			  System.out.print("<"+sdf.format(now)+">"+" Dein Benutername ist "+userName+"!"+'\n');//
		  }
		  else {
			  System.out.print("<"+sdf.format(now)+">"+" Dein Benutername ist "+ Benutzername +"!"+'\n'); //
		  }
		  	outToServer.writeBytes(Benutzername + '\n');
		}
		else {
			Benutzername = JOptionPane.showInputDialog("Gib deinen Benutzernamen ein! (Windows-Benutzername wird als Default verwendet.)");
			if ((Benutzername.equals(""))||(Benutzername.equals("	"))|| (Benutzername.equals(" "))) {
				String userName = System.getProperty("user.name");	
				Benutzername = userName;
				JOptionPane.showMessageDialog(null, sdf.format(now) +"Dein Benutername ist "+userName+"!"+'\n');//
				}
			 	else {
			 		JOptionPane.showMessageDialog(null, sdf.format(now) +"Dein Benutername ist "+ Benutzername +"!"+'\n'); //
			 	}

				  
		}
		  
			  
		if (eingabe == 1) {
			
			  JOptionPane.showInputDialog("In welchen der folgenden Räume möchtest du beitreten?");
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
		  
	
	

		  }
		else {
	  
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
		 
}
	 