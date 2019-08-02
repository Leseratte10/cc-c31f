package srv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;

public class ThreadedEchoServer {

    static final int PORT = 1988;
    
    public static ArrayList<String> benutzer = new ArrayList<String>();
    public static ArrayList<String> benutzer_datei = new ArrayList<String>();
    public static ArrayList<String> passwd = new ArrayList<String>();
    static ArrayList<Socket> sockets = new ArrayList<Socket>();
    static ArrayList<Room> rooms = new ArrayList<Room>();
    
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
    	for (int i=0;i<rooms.size();i++) {
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
    
    public static void sendToRoom(String line, Socket selfSocket, String room) throws IOException {
    	DataOutputStream out = null;
    	int ind = findRoom(room);
    	for (Socket socket:getRooms().get(ind).getSockets()) {
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
            System.out.print("Kann nicht über diesen Port Starten! Oder der Server läuft bereits über diesen Port" +'\n');
            System.exit(0);

        }
        
      //import username list
        String creatorPath = System.getProperty("user.dir") + "\\usernames.txt";
        File username = new File(creatorPath);
        if (!username.exists()) {
			username.createNewFile();
		}
        BufferedReader usernameFile = new BufferedReader(new FileReader(username));
        //String[] content = basefile.split("$");
       
        String buffer = usernameFile.readLine();
        while (buffer != null) {
        	String[] content = buffer.split("�");
        	benutzer_datei.add(content[0]);
        	passwd.add(content[1]);
        	buffer = usernameFile.readLine();
        }

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            } 
            
            // new thread for a client
            addSocket(socket);
            new EchoThread(socket).start();
        }
    }
}