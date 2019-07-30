package client;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.io.*;
import java.net.URI;

public class sendfile {
		
		private Path path;
		
		public sendfile(URI filepath) {
			this.path = Paths.get(filepath);
		}

		public String run(URI filepath) {
			this.path = Paths.get(filepath);
			FileReader reader = null;
			String line = null;
			
			try {
				reader = new FileReader(path.toString());
			    BufferedReader inBuffer = new BufferedReader(reader);
				line = inBuffer.readLine();
				
				//test of base encoding and decoding
				String basetest = Base64.getEncoder().encodeToString(line.getBytes());
				byte[] actualByte= Base64.getDecoder().decode(basetest);
				line = new String(actualByte);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return line;
		}
}
