package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
        } catch (IOException e) {
            return;
        }

        
		java.util.Date now = new java.util.Date(System.currentTimeMillis());
        String line;
        while (true) {
            try {
                line = brinp.readLine();
                System.out.println("<"+sdf.format(now)+">" +" "+line);
            }
            catch(SocketException e) {
				System.out.println('\n'+"Der Server wurde geschlossen oder die Connection wurde abgebrochen!");
				return;
            } catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println('\n'+"Der Server wurde geschlossen oder die Connection wurde abgebrochen!");
				return;
			}
                    	
        }

	}
	
}
