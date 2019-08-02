package srv;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
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
        String line = "";
        String room;
        String Benutzername = null;
        Boolean login = false;
        LocalDateTime now = LocalDateTime.now();
        int ind = 0;
        boolean forbreak = false;
        
        
        //login and room selection
        try {
        
        //username and password input
        Benutzername = brinp.readLine(); 
        String passwd = brinp.readLine();
    	
    	//username request
    	for (int i = 0; i < ThreadedEchoServer.benutzer_datei.size(); i++) {
    		if (ThreadedEchoServer.benutzer_datei.get(i).equals(Benutzername)) {
    			System.out.println("Benutzer " +ThreadedEchoServer.benutzer_datei.get(i)  + " versucht sich anzumelden.");
    			ind = i;
    			forbreak = true;
    		}
    	}
    	
    	//password request
    		if  (ThreadedEchoServer.passwd.get(ind).equals(passwd) && forbreak) {
    			System.out.println(Benutzername + " hat sich erfolgreich angemeldet mit dem Passwort " + passwd + " angemeldet.");
    			out.writeBytes("true");
    		}
    		else if (forbreak) {
    			System.out.println("Falsches Passwort: " + passwd);
    			out.writeBytes("fick dich falsches passwd du huso \n");
    			System.out.println("wir haben einen huso unter uns.. die Dummheit wurde eleminiert.");
    			socket.close();
    		}
    	
    	//add new user
    	if (!forbreak) {
    	System.out.println("Neuer Nutzer versucht sicht anzumelden.");
		ThreadedEchoServer.benutzer_datei.add(Benutzername);   			
		ThreadedEchoServer.passwd.add(passwd);
		String creatorPath = System.getProperty("user.dir") + "\\usernames.txt";
		File username = new File(creatorPath);
		BufferedWriter buffer = new BufferedWriter(new FileWriter(username));
		for (int i = 0; i < ThreadedEchoServer.benutzer_datei.size(); i++) {
			buffer.append(ThreadedEchoServer.benutzer_datei.get(i) + "§" + ThreadedEchoServer.passwd.get(i) + "\n");
		}
		buffer.flush();
		buffer.close();
		System.out.println("Neuer Benutzer " + Benutzername + " wurde mit dem Passwort " + passwd + "erfolgreich erstellt.");
    	}
    	
    	ThreadedEchoServer.addUser(Benutzername);
    	String roomlist =ThreadedEchoServer.roomsToString();
    	out.writeBytes(roomlist+'\n');
    	room = brinp.readLine();
    	
    	ind = ThreadedEchoServer.findRoom(room);
    	
    	if(ind < 0) {
    		ThreadedEchoServer.addRoom(room);
    		ind = ThreadedEchoServer.getRooms().size()-1;
    	}
		ThreadedEchoServer.getRooms().get(ind).addSocket(socket);
		ThreadedEchoServer.getRooms().get(ind).addUser(Benutzername);

    	String text = "Der Benutzer " + Benutzername + " hat Raum "+room+" betreten.";
    	System.out.println("<"+dft.format(now)+"> "+text);
			ThreadedEchoServer.sendToAll(text, socket);
		} 
        catch (IOException e2) {
			e2.printStackTrace();
		}
        	
		
        while (true) {
            try {               
            	line = brinp.readLine();	//user input
                now = LocalDateTime.now();
                
                System.out.print("<"+dtf.format(now)+"> ");
                System.out.println(line);
                ThreadedEchoServer.sendToAll(line, socket);
                
            } catch (IOException e) {
            	now = LocalDateTime.now();
            	int index = ThreadedEchoServer.getSockets().indexOf(socket);
            	try {
            		System.out.println("<"+dtf.format(now)+"> "+ThreadedEchoServer.getUser().get(index)+" just left");
					ThreadedEchoServer.removeUser(index);
					ThreadedEchoServer.removeSocket(socket);
					ThreadedEchoServer.sendToAll("<"+dft.format(now)+"> "+Benutzername+" just left", socket);
					this.stop();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            	
            	ThreadedEchoServer.removeSocket(socket);
                return;
            }
        }
    }
}
