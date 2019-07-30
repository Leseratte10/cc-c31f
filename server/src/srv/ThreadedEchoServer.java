package srv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadedEchoServer {


    static final int PORT = 1988;
    
    static ArrayList<String> benutzer = new ArrayList<String>();
    
    public static void addUser(String username) {
    	benutzer.add(username); 
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
            new EchoThread(socket).start();
        }
    }
}
