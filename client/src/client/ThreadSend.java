package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.math.BigInteger;

public class ThreadSend extends Thread {
	protected Socket socket;
	static Integer privateKey =TCPClient.privateKey.intValue();
	String username;
	String to;
	int type;
	
	public ThreadSend(Socket socket, String username, String to) {
		this.socket = socket;
		this.username = username;
		this.to = to;
	}
	public void run(){
		
		String sentence;
		StringBuilder text = new StringBuilder();
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		DataOutputStream outToServer;
		try {
			outToServer = new DataOutputStream(socket.getOutputStream());
			while (true) {
				try {
					System.out.print("Nachricht / Datei: ");
					Message message = null;
					sentence = inFromUser.readLine();
					
					if(sentence != null) {
						File file = new File(sentence);
						if(file.exists()) {
							type = 2;
						}else {
							type = 1;
						}
						if(type == 1) {
							//System.out.println(sentence);
							message = new Message(username, to, sentence, type);
							System.out.println("Deine Nachricht lautet: " + message.getOutputString());
							}
						} else /*if(type == 2)*/{
							String mes = Message.encodeFile(text.toString());
							message = new Message(username, to, mes, type);
							//System.out.println("Working 2.");
						}
					sentence = codierung(message.getOutputString(),TCPClient.ServerPublicKey1, TCPClient.ServerPublicKey2);
						outToServer.writeBytes(sentence + '\n');
						outToServer.flush();
					
					
					
				}catch(Exception e){
					System.out.println("Error: "+ e);
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error: "+ e1);
			this.stop();
		}
		
				
	//--------------------------------------------------------------------------------------------------------
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
		int randomPrimzahl1 = rand.nextInt(21);
		int randomPrimzahl2 = rand.nextInt(21);
		while (randomPrimzahl1 == randomPrimzahl2) {
			randomPrimzahl2 = rand.nextInt(21);
		}
		int randomPrimzahl3 = rand.nextInt(21);
		while ((randomPrimzahl3 == randomPrimzahl2) || (randomPrimzahl3 == randomPrimzahl1)) {
			randomPrimzahl3 = rand.nextInt(21);
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
	//-------------------------------------------------------------------------------------------------------------------------
	public static String vigenere (String text) {
		String codewort = "meincodewort"; //mit diesem Wort wird vigenere verschlüsselt
		String endwort = "";
		int j = 0;
		for (int i = 0; i<text.length(); i++) {
			if (j==codewort.length()) { //zählen ob das codewort bereits aufgebraucht wurde, falls ja, von vorne codieren
				j -= codewort.length();
			}
			int intBuchstabe =  (int) text.charAt(i); //umwandlung in ascii code vom buchstaben des textes mit dem index des aktuellen i
			int intCodeBuchstabe = (int) codewort.charAt(j);//umwandlung in ascii code vom buchstaben des codewortes mit dem index des aktuellen j
			int newBuchstabe = 0;
			int addB = intCodeBuchstabe - 96; //berechnung welche zahl addiert werden muss
			newBuchstabe = intBuchstabe + addB; //neuer Buchstabe wird berechnet
			if (newBuchstabe > 126) { //falls das ende des Vorgegebenen Ascii alphabets erreicht wurde, fängt es von vorne an
				newBuchstabe -= 93; 
			}
			char ausgabe = (char) newBuchstabe; //umwandlung zurück in buchstabe
			j +=1;
			endwort = endwort + ausgabe; //buchstaben werden zusammengeführt
		}
		return endwort;
	}
	public static String rsa(String text, Integer publicKey1, Integer publicKey2){
		String codeText = "";
		for (int i =0; i < text.length(); i++ ) {
			char zuCodierenderBuchstabe = text.charAt(i); //buchstabe an der stelle i einlesen
			Integer zuCodierendeZahl = (int)zuCodierenderBuchstabe; //eingelesenen buchstabe in ascii zahl umwandeln
			BigInteger codierteZahl = (pot(zuCodierendeZahl, publicKey1)); //ab hier Berechnung 
			BigInteger bigPublicKey2 = BigInteger.valueOf(publicKey2.intValue());
			codierteZahl = codierteZahl.mod(bigPublicKey2);
			codeText = codeText + String.valueOf(codierteZahl)+ " "; //Formatierung
		}
		
		codeText = codeText.substring(0, codeText.length()-1); //freizeichen am Ende löschen
		return codeText;
	}
	
	public static BigInteger pot(Integer zahl, Integer potenz) {
		BigInteger ergebnis = BigInteger.ONE;
		BigInteger bigZahl = BigInteger.valueOf(zahl.intValue());
		for (int i=1; i < (potenz+1); i++) {
			ergebnis = ergebnis.multiply(bigZahl); //einfache potenzberechnung 			
		}
		return ergebnis;
	}
	
	//---------------------------------------------------------------------------------------------------
	
	public BigInteger privatKeyBerechner(Integer Primzahl1, Integer Primzahl2, Integer Primzahl3) {
		BigInteger zwischenergebnis;
		BigInteger BigPrimzahl1 = BigInteger.valueOf(Primzahl1.intValue()); //integers zu big Integers umwandeln, da das Zwischenergebnis eine zu hohe Zahl ist
		BigInteger BigPrimzahl2 = BigInteger.valueOf(Primzahl2.intValue());
		BigInteger BigPrimzahl3 = BigInteger.valueOf(Primzahl3.intValue());
		
		zwischenergebnis = (BigPrimzahl1.subtract(BigInteger.ONE)).multiply(BigPrimzahl2.subtract(BigInteger.ONE)); //berechnung des zwischenergebnisses = (prim1-1)*(prim2-1)
		BigInteger privatKey = BigInteger.TEN; //muss bei 10 anfangen damit es funktioniert
		privatKey = privatKeySchleife(privatKey, zwischenergebnis, BigPrimzahl3); 
		if (privatKey.equals(BigInteger.ZERO)){
			return null; //private Key 0 ist ungünstig
		}
		else {
			return privatKey;
		}
		
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
	public Integer publicKeyBerechner(int primzahl1, int primzahl2, int primzahl3) {
		int publicKey1 = 0;
		int publicKey2 = 0;
		publicKey1 = primzahl3;
		publicKey2 = primzahl1*primzahl2;
		return publicKey2;
	}
	public static String codierung (String text, Integer pub1,Integer pub2) {
		String codeText = "";
		codeText = rsa(vigenere(text), pub1, pub2);
		
		return codeText;
		
	}
}