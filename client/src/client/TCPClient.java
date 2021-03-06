package client;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

public class TCPClient {
	static boolean running = true;
    private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static ArrayList <Integer> primzahlArray = primzahlGenerator();
	public static BigInteger privateKey = privatKeyBerechner(primzahlArray.get(0),primzahlArray.get(1),primzahlArray.get(2));
	public static Integer publicKey1 = primzahlArray.get(2);
	public static  Integer publicKey2 = publicKeyBerechner(primzahlArray.get(0),primzahlArray.get(1),primzahlArray.get(2));
	public static Integer ServerPublicKey1;
	public static Integer ServerPublicKey2;
	
	
	public static void main(String[] args) throws Exception {

		 int eingabe = 2;
		 String Benutzername;
		 String raumname;
		 String[] raumliste;
			java.util.Date now = new java.util.Date(System.currentTimeMillis());

			 String eingabeForm = JOptionPane.showInputDialog("Waehle die Eingabeform: (f)enster oder (c)onsole!");
			 if (eingabeForm.equalsIgnoreCase("F") || eingabeForm.equalsIgnoreCase("FENSTER")) {
				 eingabe = 1;
			 }else if (eingabeForm.equalsIgnoreCase("C") || eingabeForm.equalsIgnoreCase("CONSOLE")){
				 eingabe = 2;
			 }
			  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			  DataOutputStream outToServer = null;
			  Socket clientSocket = null;

		      InputStream inp = null;
		      BufferedReader brinp = null;

			 try {
				  clientSocket = new Socket("172.24.0.16", 1988);
				  outToServer = new DataOutputStream(clientSocket.getOutputStream());
				  inp = clientSocket.getInputStream();
		          brinp = new BufferedReader(new InputStreamReader(inp));
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

		if(eingabe == 2) {
			System.out.println("Gib deinen Benutzernamen ein! (Windows-Benutzername wird als Default verwendet.)");
			InputStream inp2 = null;
				  inp2 = clientSocket.getInputStream();
			  	  BufferedReader brinp2 = new BufferedReader(new InputStreamReader(inp2));
			  	  ServerPublicKey1 = Integer.parseInt(brinp2.readLine());
			  	  ServerPublicKey2 = Integer.parseInt(brinp2.readLine());
			  	  outToServer.writeBytes(Integer.toString(publicKey1) + '\n');
			  	  outToServer.writeBytes(Integer.toString(publicKey2) + '\n');
			  Benutzername = inFromUser.readLine();

		  if (Benutzername.trim().equals("")) {

			  String userName = System.getProperty("user.name");
			  Benutzername = userName;
		  }
		System.out.print("<"+sdf.format(now)+">"+" Dein Benutername ist "+ Benutzername +"!"+'\n'); //
		  
		Benutzername = ThreadSend.codierung(Benutzername, ServerPublicKey1, ServerPublicKey2);
			  	  outToServer.writeBytes(Benutzername + '\n');
		
		raumname = brinp.readLine();
	      System.out.println("In welchen der folgenden Raeume moechtest du beitreten?");
	      raumliste =  raumname.split(";");
	      for (String name:raumliste) {
	    	  System.out.println(name);
	      }
	      raumname = inFromUser.readLine();
	      raumname = ThreadSend.codierung(raumname, ServerPublicKey1, ServerPublicKey2);

	      outToServer.writeBytes(raumname + '\n');
		} 
		else if (eingabe == 1){

			Benutzername = JOptionPane.showInputDialog("Gib deinen Benutzernamen ein! (Windows-Benutzername wird als Default verwendet.)");
			if (Benutzername.trim().equals("")) {
					String userName = System.getProperty("user.name");	
					Benutzername = userName;
				}
			 	JOptionPane.showMessageDialog(null, "<"+sdf.format(now)+">" +" Dein Benutername ist "+ Benutzername +"!"+'\n'); //
			 	
			 	outToServer.writeBytes(Benutzername + '\n');
			      raumname = brinp.readLine();
			      raumliste = raumname.split(";");
			 	raumname = JOptionPane.showInputDialog(null, "In welchen der folgenden R�ume m�chtest du beitreten?" + '\n' + "Diese R�ume gibt es bereits:" + '\n' + raumliste );
			      
			      outToServer.writeBytes(raumname);
		} else {
			String userName = System.getProperty("user.name");	
			Benutzername = userName;
			System.out.print("<"+sdf.format(now)+">"+" Dein Benutername ist "+ Benutzername +"!"+'\n');
			raumname = brinp.readLine();
			System.out.print("In welchen der folgenden Raeume moechtest du beitreten?");
		      raumliste =  raumname.split(";");
		      for (String name:raumliste) {
		    	  System.out.print(name);
		      }
		      raumname = inFromUser.readLine();

		      outToServer.writeBytes(raumname + '\n');
		}

			new ThreadSend(clientSocket, Benutzername, raumname).start();
			new ThreadReceive(clientSocket).start();

		 }	
	public static ArrayList<Integer> primzahlGenerator() {
		ArrayList <Integer> primzahlArray = new ArrayList<Integer>();
		ArrayList <Integer> ausgabenArray = new ArrayList<Integer>();
		for (int i = 10; i <96; i++) {
			Integer primzahl = primzahlberechner(i);
			if (primzahlArray.size() == 0) {
				primzahlArray.add(primzahl);
			}
			else if ((primzahl != primzahlArray.get(primzahlArray.size()-1))) {
				primzahlArray.add(primzahl);
			}
			//System.out.println(primzahlArray);
						
		}
		System.out.println(primzahlArray);
		Random rand = new Random();
		int randomPrimzahl1 = rand.nextInt(13);
		int randomPrimzahl2 = rand.nextInt(13);
		while (randomPrimzahl1 == randomPrimzahl2) {
			randomPrimzahl2 = rand.nextInt(13);
		}
		int randomPrimzahl3 = rand.nextInt(13);
		while ((randomPrimzahl3 == randomPrimzahl2) || (randomPrimzahl3 == randomPrimzahl1)) {
			randomPrimzahl3 = rand.nextInt(13);
		}
		/*System.out.println(primzahlArray.get(randomPrimzahl1));
		System.out.println(primzahlArray.get(randomPrimzahl2));
		System.out.println(primzahlArray.get(randomPrimzahl3));*/
		ausgabenArray.add(primzahlArray.get(randomPrimzahl1));
		ausgabenArray.add(primzahlArray.get(randomPrimzahl2));
		ausgabenArray.add(primzahlArray.get(randomPrimzahl3));
		return ausgabenArray;
	}
	public static int primzahlberechner(int anfang) {
		int Primzahl = anfang+1;
		for (int i=2; i<Primzahl;i++) {
			if ((Primzahl % i) == 0){
				Primzahl = primzahlberechner (Primzahl);
				break;
			}
		}
		return Primzahl;
	}
	public static Integer publicKeyBerechner(int primzahl1, int primzahl2, int primzahl3) {
		int publicKey2 = 0;
		publicKey2 = primzahl1*primzahl2;
		return publicKey2;
	}
	
	public static BigInteger privatKeySchleife(BigInteger privatKey, BigInteger zwischenergebnis, BigInteger primzahl3){
		
		if (BigInteger.ONE.equals((privatKey.multiply(primzahl3)).mod(zwischenergebnis))) { //wenn (private key * prim 3) modulo zwischenergebnis = 1 ist
			return privatKey;
		}
		else {
			privatKey = privatKeySchleife(privatKey.add(BigInteger.ONE), zwischenergebnis, primzahl3); //finde die richtige Zahl
		}
		return privatKey;
		
	}
	public static BigInteger privatKeyBerechner(Integer Primzahl1, Integer Primzahl2, Integer Primzahl3) {
		BigInteger zwischenergebnis;
		BigInteger BigPrimzahl1 = BigInteger.valueOf(Primzahl1.intValue()); //integers zu big Integers umwandeln, da das Zwischenergebnis eine zu hohe Zahl ist
		BigInteger BigPrimzahl2 = BigInteger.valueOf(Primzahl2.intValue());
		BigInteger BigPrimzahl3 = BigInteger.valueOf(Primzahl3.intValue());
		
		zwischenergebnis = (BigPrimzahl1.subtract(BigInteger.ONE)).multiply(BigPrimzahl2.subtract(BigInteger.ONE)); //berechnung des zwischenergebnisses = (prim1-1)*(prim2-1)
		BigInteger privatKey = BigInteger.TEN; //muss bei 10 anfangen damit es funktioniert
		privatKey = privatKeySchleife(privatKey, zwischenergebnis, BigPrimzahl3); 
		if (privatKey.equals(BigInteger.ZERO)){
			return null; //private Key 0 ist ung�nstig
		}
		else {
			return privatKey;
		}
		
	}
	
}

