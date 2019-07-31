package client;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.io.*;
import java.net.URI;

public class sendfile {
		
		private static Path path;
		
		//public sendfile(URI filepath) {
			//this.path = Paths.get(filepath);
		//}

		public static void run() throws IOException {
			int Count = 0;
			//this.path = Paths.get(filepath);
			Path file = Paths.get("H:/C31F/Code Projekt 3.5/Jona Gräf/cc-c31f/TEST.txt");
			byte[] fileArray;
			fileArray = Files.readAllBytes(file);
			while(true)
			{
				System.out.println(fileArray[Count]);
				Count = Count + 1;
				if (Count == fileArray.length)
				{
					break;
				}
			}
		}

		public static Path getPath() {
			return path;
		}

		//public void setPath(Path path) {
			//this.path = path;
		//}
}
