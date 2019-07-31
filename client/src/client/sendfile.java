package client;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.io.*;

public class sendfile {

		public static String sendfile(String filepath) throws IOException {			
			Path file = Paths.get(filepath);
			byte[] fileArray;
			fileArray = Files.readAllBytes(file);
			String encodedfile = Base64.getEncoder().encodeToString(fileArray);
			String result = "§" + file.getFileName() + "§" + encodedfile + "§";
			return result;
		}
}
