package client;

import java.nio.file.Paths;
import java.io.*;

public class sendfile {

	public static void main(String[] args) throws Exception{
		//TODO string path if textdokument send header and body with endmsg
		// TODO if binary convert to bas64 and send header with body afterwards , decode at reciver
		
		FileReader reader = new FileReader("Z:\\Prgrammieren 3.5 Projekt\\cc-c31f\\cc-c31f\\client\\src\\file.txt"); // Dateispeicherort muss später selber eingegeben werden
		BufferedReader inBuffer = new BufferedReader(reader);

		String line = inBuffer.readLine();
	}

}
