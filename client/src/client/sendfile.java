package client;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
			} catch (IOException e) {
				e.printStackTrace();
			}
			return line;
		}
}
