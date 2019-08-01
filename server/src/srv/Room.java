package srv;

import java.net.Socket;
import java.util.ArrayList;

public class Room {
	private String roomname;
	private ArrayList<String> users;
	private ArrayList<Socket> sockets;
	
	public Room(String name) {
		this.roomname = name;
		this.sockets = new ArrayList<Socket>();
		this.users = new ArrayList<String>();
	}
	
	public void setRoomname(String name) {
		roomname = name;
	}
	
	public void addUser(String user) {
		users.add(user);
	}
	
	public void addSocket(Socket socket){
		sockets.add(socket);
	}
	
	public String getRoomname() {
		return roomname;
	}
	
	public ArrayList<String> getUser() {
		return users;
	}
	
	public ArrayList<Socket> getSockets(){
		return sockets;
	}
}
