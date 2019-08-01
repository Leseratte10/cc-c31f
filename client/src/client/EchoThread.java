package server.src.srv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import client.src.client.Message;

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
        BufferedWriter writer = null;
        DataOutputStream out = null;
        int zahl = 1;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
            writer = new BufferedWriter(new OutputStreamWriter(out));
        } catch (IOException e) {
            System.out.println("Bruder muss los, Exception! " + e.toString());
        }
        
        String line;
        Boolean login = false;
        String Benutzername;
        
        while (true) {
            try {
            	if(brinp.ready()) {
            		//System.out.println(String.valueOf(brinp.ready()));
            		line = brinp.readLine();
                    //System.out.println(line);
                    Message mes = new Message(line);
                    LocalDateTime now = LocalDateTime.now();
                    if (login == false) {
                    	Benutzername = mes.getUsername();
                    	login = true;

                    	System.out.println("<"+dft.format(now)+"> Der Benutzer " + Benutzername + " hat sich angemeldet.");
    					ThreadedEchoServer.addUser(Benutzername);
                    }
                    
                    else {
                    	System.out.print("<"+dtf.format(now)+"> ");
                        System.out.println(line);

            	}
                }
            	
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
