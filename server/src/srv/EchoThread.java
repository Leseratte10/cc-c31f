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

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    //das ist ein Kommentar
    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                
                line = brinp.readLine();
                LocalDateTime now = LocalDateTime.now();
                System.out.print(dtf.format(now));
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
