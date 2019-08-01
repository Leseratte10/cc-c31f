package client.src.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

public class Message {
	
	private String from;
	private String to;
	private String message;
	private String time;
	private String separator = "§";
	private String type;
	
	
	
	/*Types:
	 * TextMessage = "1"
	 * File = "2"
	 */
	public Message(String from, String to, String message, String type) {
		this.from = from;
		this.to = to;
		this.message = message;
		this.type = type;
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		this.time = time.format(formatter);
	}
	
	public Message(InputStream in) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder text = new StringBuilder("");
		String read;
		try {
			read = reader.readLine();
			while(read != null) {
				text.append(read);
				read = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] list = new String(text).split(separator);
		this.to = list[0];
		this.from = list[1];
		this.time = list[2];
		this.type = list[3];
		this.message = list[4];

	}
	
	public Message(String full) {
		String[] list = full.split(separator);
		this.to = list[0];
		this.from = list[1];
		this.time = list[2];
		this.type = list[3];
		this.message = list[4];
	}
	
	public String getOutputString() {
		return (to + separator + from + separator + time  + separator + type + separator + message);
	}
	
	public void updateOutputStream(DataOutputStream stream) {
		String s = this.getOutputString();
		try {
			stream.write(s.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean createFile() {
		if(type.equals("2")) {
			try {
				this.recieveFile(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}else {
			return false;
		}
	}

	
	public String getUsername() {
		return from;
	}
	
	public String getEmpfänger() {
		return to;
	}
	
	public String getText() {
		return message;
	}

	public String getTime() {
		return this.time;
	}
	
	public static String encodeFile(String filepath) throws IOException {			
		Path file = Paths.get(filepath);
		byte[] fileArray;
		fileArray = Files.readAllBytes(file);
		String encodedfile = Base64.getEncoder().encodeToString(fileArray);
		String result = "§" + file.getFileName() + "$" + encodedfile;
		return result;
	}
	
	private void recieveFile(String basefile) throws IOException {
		String[] content = basefile.split("$");
		String creatorPath = System.getProperty("user.dir") + "\\" + content[0];
		File createdFile = new File(creatorPath);
		if (!createdFile.exists()) {
			createdFile.createNewFile();
		}
		byte[] actualByte= Base64.getDecoder().decode(content[1]);
		OutputStream os = new FileOutputStream(createdFile);
        os.write(actualByte);
        os.close();
		
	
	}
}
