package srv;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ThreadedEchoServer {


    static final int PORT = 1980;
    
    static ArrayList<String> benutzer = new ArrayList<String>();
    static ArrayList<Socket> sockets = new ArrayList<Socket>();
    static ArrayList<Room> rooms = new ArrayList<Room>();
    public static ArrayList <SocketKeys> userList = new ArrayList();
    public static Integer privateKey;
    public static ArrayList<Integer> keys = keyGenerator();
    //public static ArrayList<ArrayList<String>> socketKeyList = new ArrayList<ArrayList<String>>();
    public static void addUser(String username) {
    	benutzer.add(username); 
    }
    
    public static void addSocket(Socket socket) {
    	sockets.add(socket);
    }
    
    public static void addRoom(String name) {
    	rooms.add(new Room(name));
    }
    
    public static ArrayList<Socket> getSockets(){
    	return sockets;
    }
    
    public static ArrayList<Room> getRooms(){
    	return rooms;
    }
    
    public static ArrayList<String> getUser(){
    	return benutzer;
    }
    
    public static void removeSocket(Socket socket) {
    	sockets.remove(socket);
    }
    
    public static void removeUser(int ind) {
    	benutzer.remove(ind);
    }
    
    public static int findRoom(String roomname) {
    	for (int i=0;i<rooms.size()-1;i++) {
    		if (rooms.get(i).getRoomname().equals(roomname)) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    public static String roomsToString() {
    	String s = "";
    	for (Room room:rooms) {
    		s += room.getRoomname();
    		s += ";";
    	}
    	return s;
    }
    
    public static boolean roomExists(String name) {
    	boolean exists = false;
    	for (Room room:rooms) {
    		if(room.getRoomname().equals(name)) {
    			exists = true;
    		}
    	}
    	return exists;
    }
    
    public static void sendToAll(String line, Socket selfSocket) throws IOException {
        DataOutputStream out = null;
    	for (Socket socket:sockets) {
    		if ( socket != selfSocket) {
    			out = new DataOutputStream(socket.getOutputStream());
    			out.writeBytes(line+'\n');
    		}
    	}
    }


    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        addRoom("global");
        
        

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.print("Kann nicht über diesen Port Starten!" +'\n');
            System.exit(0);

        }

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            } 

            // new thread for a client
            addSocket(socket);
            System.out.println("socket initialized");
            new EchoThread(socket).start();
        }
    }
    //-----------------------------------------------------------------------------------
    public static ArrayList<Integer> keyGenerator() {
    	ArrayList <Integer> primzahlArray = new ArrayList<Integer>();
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
		int randomPrimzahl1 = rand.nextInt(20);
		int randomPrimzahl2 = rand.nextInt(20);
		while (randomPrimzahl1 == randomPrimzahl2) {
			randomPrimzahl2 = rand.nextInt(20);
		}
		int randomPrimzahl3 = rand.nextInt(20);
		while ((randomPrimzahl3 == randomPrimzahl2) || (randomPrimzahl3 == randomPrimzahl1)) {
			randomPrimzahl3 = rand.nextInt(20);
		}
		//System.out.println(primzahlArray.get(randomPrimzahl1));
		//System.out.println(primzahlArray.get(randomPrimzahl2));
		//System.out.println(primzahlArray.get(randomPrimzahl3));
		BigInteger bigPrivateKey = privatKeyBerechner(primzahlArray.get(randomPrimzahl1),primzahlArray.get(randomPrimzahl2),primzahlArray.get(randomPrimzahl3));
		privateKey = bigPrivateKey.intValue();
		Integer publicKey2 = publicKeyBerechner(primzahlArray.get(randomPrimzahl1),primzahlArray.get(randomPrimzahl2),primzahlArray.get(randomPrimzahl3));
		Integer publicKey1 = primzahlArray.get(randomPrimzahl3);
		ArrayList<Integer> localKeys = new ArrayList<Integer>();
		localKeys.add(privateKey);
		localKeys.add(publicKey1);
		localKeys.add(publicKey2);
		return localKeys;
		
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
	public static BigInteger privatKeyBerechner(Integer Primzahl1, Integer Primzahl2, Integer Primzahl3) {
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
	public static Integer publicKeyBerechner(int primzahl1, int primzahl2, int primzahl3) {
		int publicKey1 = 0;
		int publicKey2 = 0;
		publicKey1 = primzahl3;
		publicKey2 = primzahl1*primzahl2;
		return publicKey2;
	}

		
}
