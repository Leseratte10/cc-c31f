package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Message {
	
	private String from;
	private String to;
	private String message;
	private String time;
	private String separator = "§";
	
	public Message(String from, String to, String message) {
		this.from = from;
		this.to = to;
		this.message = message;
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
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
		this.message = list[3];

	}
	
	public String getOutputString() {
		return (to + separator + from + separator + time + separator + message);
	}
	
	public DataOutputStream getOutputStream(DataOutputStream stream) {
		String s = this.getOutputString();
		DataOutputStream stream1 = stream;
		try {
			stream1.write(s.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stream1;
		
	}
}
