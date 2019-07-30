package srv;

import java.io.DataOutputStream;
import java.io.IOException;
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
    
    public static void sendToAll(String line) throws IOException {
        DataOutputStream out = null;
    	for (int i=0; i<sockets.size() ; i++ ) {
    		out = new DataOutputStream(sockets.get(i).getOutputStream());
    		out.writeBytes(line+"\n");
    	}
    }


    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();

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
