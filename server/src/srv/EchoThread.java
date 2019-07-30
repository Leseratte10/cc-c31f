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
        Boolean login = false;
        String Benutzername;
        
        while (true) {
            try {
                
                line = brinp.readLine();
                LocalDateTime now = LocalDateTime.now();
                System.out.print(dtf.format(now));
                System.out.println(line);
                if (login == false) {
                	Benutzername = line;
                	login = true;
                	System.out.println(dft.format(now)+": Der Benutzer" + Benutzername + "hat sich angemeldet.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
