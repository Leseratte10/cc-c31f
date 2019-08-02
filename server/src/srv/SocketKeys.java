package srv;

import java.net.Socket;

public class SocketKeys {
	Socket socket;
	Integer key1;
	Integer key2;
	
	public SocketKeys(Socket socket, Integer key1, Integer key2) {
		this.key1 = key1;
		this.key2 = key2;
		this.socket = socket;
	}

}