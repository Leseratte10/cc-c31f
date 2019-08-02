package srv;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
        Message message;
        
        while (true) {
            try {
                
                line = brinp.readLine();
                
                for (int i = 0; i < ThreadedEchoServer.benutzer.size();  i++) {
                	if (line.equals(ThreadedEchoServer.benutzer.get(i))) {
                		line = brinp.readLine();
                		if(line.equals(ThreadedEchoServer.passwd.get(i))) {
                			login = false;
                		}else {
                			
                		}
                		
                	}
                	else {
                		
                	}
                }
                
                LocalDateTime now = LocalDateTime.now();
                if (login) {
                	Benutzername = line;
                	login = false;
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
                	message = new Message(line);
                	System.out.print("<"+message.getTime()+"> " );
                    System.out.println(message.getText());
                    ThreadedEchoServer.sendToRoom(message.getOutputString(), socket, message.getEmpfänger());
                }
            } catch (IOException e) {
            	LocalDateTime now = LocalDateTime.now();
            	int ind = ThreadedEchoServer.getSockets().indexOf(socket);
            	try {
            		System.out.println("<"+dtf.format(now)+"> "+ThreadedEchoServer.getUser().get(ind)+" just left");
					ThreadedEchoServer.removeUser(ind);
					ThreadedEchoServer.removeSocket(socket);
					ThreadedEchoServer.sendToAll("<"+dft.format(now)+"> "+ThreadedEchoServer.getUser().get(ind)+" just left", socket);
					this.stop();
				} catch (IOException e1) {
					//e1.printStackTrace();
					System.out.println(ThreadedEchoServer.getUser().get(ind)+" just left" + socket + " left.");
				}
            	
            	ThreadedEchoServer.removeSocket(socket);
                return;
            }
        }
    }
}
