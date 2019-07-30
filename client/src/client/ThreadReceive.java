package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadReceive extends Thread {
	protected Socket socket;

	public ThreadReceive(Socket clientSocket) {
        this.socket = clientSocket;
    }
	
	public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
        } catch (IOException e) {
            return;
        }
        
        String line;
        while (true) {
            try {
                line = brinp.readLine();
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        
        
        
	
	}
	
}
