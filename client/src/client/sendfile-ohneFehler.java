package client;

import java.nio.file.Paths;
import java.io.*;

public class sendfile {

	public static void main(String[] args) throws Exception{
		//TODO string path if textdokument send header and body with endmsg
		// TODO if binary convert to bas64 and send header with body afterwards , decode at reciver
		
		FileReader reader = new FileReader("src/file.txt");
		BufferedReader inBuffer = new BufferedReader(reader);

		String line = inBuffer.readLine();
	}

}
