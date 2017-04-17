package hu.elte.projeszk.server;

import java.net.Socket;

public class PlayerThread extends Thread {
	private Socket socket=null;
	private int id=-1;
	private String name=null;
	
	private String message;
	
	public PlayerThread(Socket socket, int id) {
		super("Client "+id);
		this.socket = socket;
		this.id = id;
	}
	
	
}
