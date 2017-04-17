package hu.elte.projeszk.server;

import java.net.ServerSocket;
import java.util.HashMap;

public class CardServer {
	private HashMap<Integer, PlayerThread> players;
	private static int counter=0;
	
	private ServerSocket serverSocket=null;
	
	public CardServer() {
		super();
		players=new HashMap<>();
	}
		
	public boolean startServer(){
		return false;
	}
}
