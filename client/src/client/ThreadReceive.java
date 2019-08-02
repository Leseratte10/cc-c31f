package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ThreadReceive extends Thread {
	protected Socket socket;

	public ThreadReceive(Socket clientSocket) {
        this.socket = clientSocket;
    }
	
	public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
    	final DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    	final String ANSI_RED = "\u001B[31m";
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
        } catch (IOException e) {
            return;
        }

        
		java.util.Date now = new java.util.Date(System.currentTimeMillis());
        String line;
        BigInteger BigPrivateKey = TCPClient.privateKey;//BigInteger.valueOf(1025);
        BigInteger BigPublicKey2 = BigInteger.valueOf(TCPClient.publicKey2);//BigInteger.valueOf(2047);
        while (true) {
            try {
                line = brinp.readLine();
                line =zahlfinder(line, BigPrivateKey, BigPublicKey2);
                System.out.println("<"+sdf.format(now)+">" +" "+line);
            }
            catch(SocketException e) {
				System.out.println('\n'+ANSI_RED+"Der Server wurde geschlossen oder die Connection wurde abgebrochen!");
				return;
            } catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println('\n'+ANSI_RED+"Der Server wurde geschlossen oder die Connection wurde abgebrochen!");
				return;
			}
                    	
        }

	}
	public static String devigenere (String text) { //vigenere bloﬂ in die andere Richtung bei Verst‰ndnisproblemen, siehe vigenere Kommentare
		String codewort = "meincodewort";
		String endwort = "";
		int j = 0;
		for (int i = 0; i<text.length(); i++) {
			if (j==codewort.length()) {
				j -= codewort.length();
			}
			int intBuchstabe =  (int) text.charAt(i);
			int intCodeBuchstabe = (int) codewort.charAt(j);
			int newBuchstabe = 0;
			int addB = intCodeBuchstabe - 96;
			newBuchstabe = intBuchstabe - addB;
			if (newBuchstabe < 32) {
				newBuchstabe += 93; 
			}
			System.out.println(newBuchstabe);
			char ausgabe = (char) newBuchstabe;
			j +=1;
			endwort = endwort + ausgabe;
		}
		return endwort;
	}
	public static String primdecodierung(String zuDecodierendeZahlString, BigInteger privatKey, BigInteger publicKey, String decodierterText) {
		
		int zuDecodierendeZahl = Integer.parseInt(zuDecodierendeZahlString);
		BigInteger BigZuDecodierendeZahl = BigInteger.valueOf(zuDecodierendeZahl);
		
		BigInteger decodierteBigZahl = pot (BigZuDecodierendeZahl, BigZuDecodierendeZahl, privatKey, BigInteger.ONE);
		decodierteBigZahl = decodierteBigZahl.mod(publicKey);
		int decodierteZahl = decodierteBigZahl.intValue();
		char decodiert = (char) decodierteZahl;
		decodierterText = decodierterText + decodiert; 
		return decodierterText;
	}
	public static BigInteger pot(BigInteger zahl, BigInteger ursprungsZahl, BigInteger potenz, BigInteger momentan) {
		if (potenz.equals(momentan)) {//potenzberechnung beider mit einer biginteger potenz gerechnet wird
			return zahl;
		}
		else {
			zahl = zahl.multiply(ursprungsZahl);
			zahl = pot (zahl, ursprungsZahl, potenz, momentan.add(BigInteger.ONE) );
			
		}
		return zahl;
		
	}
	public static String zahlfinder(String text, BigInteger privatKey, BigInteger publicKey) {
		String decodierterText = "";
		int zahlenAnzahl = zahlenZaelen(text); //wieviele Zahlen wurden eingegeben?
		String[] parts = text.split(" "); //text unterteilen in die einzelnen buchstaben indem nach den freizeichen dazwischen gesucht und gespalten wird
		for (int i = 0; i < zahlenAnzahl ; i++) { //wird auf jeden buchstaben angewendet
			decodierterText = primdecodierung(parts[i], privatKey, publicKey, decodierterText); //anwendung der decodierung
		}
		String ausgabe = devigenere(decodierterText); //vigenere decodierung am Ende
		return ausgabe;
		
	}
	public static int zahlenZaelen(String text) { //z‰hlt die zahlen aufgrund dessen wie viele freizeichen existieren
		int anzahl = 1;
		for(int i = 0; i<text.length();i++) {
			if (text.charAt(i) == ' ') {
				anzahl += 1;
			}
		}
		return anzahl;
		
		
		
	}
	
	
}
