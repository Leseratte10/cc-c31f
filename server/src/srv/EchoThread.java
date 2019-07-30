package srv;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class EchoThread extends Thread {
    protected Socket socket;

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
                System.out.println(line);
                if (login == false) {
                	Benutzername = line;
                	login = true;
                	System.out.println("Der Benutzer" + Benutzername + "hat sich angemeldet.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}