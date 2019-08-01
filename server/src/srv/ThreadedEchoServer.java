package srv;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadedEchoServer {


    static final int PORT = 1988;
    
    static ArrayList<String> benutzer = new ArrayList<String>();
    static ArrayList<Socket> sockets = new ArrayList<Socket>();
    
    public static void addUser(String username) {
    	benutzer.add(username); 
    }
    
    public static void addSocket(Socket socket) {
    	sockets.add(socket);
    }
    
    public static ArrayList<Socket> getSockets(){
    	return sockets;
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
    
    public static void sendToAll(String line) throws IOException {
        DataOutputStream out = null;
    	for (int i=0; i<sockets.size() ; i++ ) {
    		out = new DataOutputStream(sockets.get(i).getOutputStream());
    		out.writeBytes(line+'\n');
    	}
    }


    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.print("Kann nicht über diesen Port Starten!" +'\n');
            System.exit(0);

        }
/*        try {
        	serverSocket = new ServerSocket(PORT);
        }
        catch(BindException e) {
        	
        }
        
        Vielleicht Sinnlos!
*/
        while (true) {
            try {
                if(socket == null) {
                	System.out.print("Kann nicht über diesen Port Starten!" +'\n');	
                	System.exit(0);
                }
                else {
                socket = serverSocket.accept();
                }
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            } 

            // new thread for a client
            addSocket(socket);
            new EchoThread(socket).start();
        }
    }
}
