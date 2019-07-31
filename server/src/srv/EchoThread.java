package srv;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

public class EchoThread extends Thread {
    protected Socket socket;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter dft = DateTimeFormatter.ofPattern("HH:mm:ss");

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        int zahl = 1;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        Boolean login = false;
        String Benutzername;
        
        while (true) {
            try {
                
                line = brinp.readLine();
                LocalDateTime now = LocalDateTime.now();
                if (login == false) {
                	Benutzername = line;
                	login = true;
                	String text = "Der Benutzer " + Benutzername + " hat sich angemeldet.";
                	System.out.println("<"+dft.format(now)+"> "+text);

					ThreadedEchoServer.addUser(Benutzername);
					ThreadedEchoServer.sendToAll(text);
                }
                else {
                	System.out.print("<"+dtf.format(now)+"> ");
                    System.out.println(line);
                    ThreadedEchoServer.sendToAll(line);
                }
            } catch (IOException e) {
            	LocalDateTime now = LocalDateTime.now();
            	int ind = ThreadedEchoServer.getSockets().indexOf(socket);
            	try {
            		System.out.println("<"+dtf.format(now)+"> "+ThreadedEchoServer.getUser().get(ind)+" just left");
					ThreadedEchoServer.sendToAll("<"+dft.format(now)+"> "+ThreadedEchoServer.getUser().get(ind)+" just left");
					ThreadedEchoServer.removeUser(ind);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            	ThreadedEchoServer.removeSocket(socket);
                return;
            }
        }
    }
}
