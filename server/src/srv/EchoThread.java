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
        String room;
        Boolean login = true;
        String Benutzername;
        
        while (true) {
            try {
                
                line = brinp.readLine();
                LocalDateTime now = LocalDateTime.now();
                System.out.println("out"+login);
                if (login) {
                	Benutzername = line;
                	login = false;
                	System.out.println("in"+login);
                	ThreadedEchoServer.addUser(Benutzername);
                	String roomlist =ThreadedEchoServer.roomsToString();
                	out.writeBytes(roomlist+'\n');
                	room = brinp.readLine();
                	
                	int ind = ThreadedEchoServer.findRoom(room);
                	
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
                else {
                	System.out.print("<"+dtf.format(now)+"> ");
                    System.out.println(line);
                    ThreadedEchoServer.sendToAll(line, socket);
                }
            } catch (IOException e) {
            	LocalDateTime now = LocalDateTime.now();
            	int ind = ThreadedEchoServer.getSockets().indexOf(socket);
            	try {
            		System.out.println("<"+dtf.format(now)+"> "+ThreadedEchoServer.getUser().get(ind)+" just left");
					ThreadedEchoServer.removeUser(ind);
					ThreadedEchoServer.sendToAll("<"+dft.format(now)+"> "+ThreadedEchoServer.getUser().get(ind)+" just left", socket);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            	ThreadedEchoServer.removeSocket(socket);
                return;
            }
        }
    }
}
