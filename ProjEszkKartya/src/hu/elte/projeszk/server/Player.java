package hu.elte.projeszk.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Játékos tároló osztály, ő is meri a saját socketjét, nevét, id-jét
 * élőn kell tartani 
 * @author scherzne
 *
 */
public class Player {
	private Socket socket;
	private Scanner scanner;
	private PrintWriter pw;
	private long lastAction;
	private int id;
	private boolean live;
	private String name=null;

	public Player(Socket socket,int id) throws IOException{
		this.socket=socket;
		this.scanner=new Scanner(socket.getInputStream());
		this.pw=new PrintWriter(socket.getOutputStream());
		this.lastAction=System.currentTimeMillis();
		this.id=id;
		this.live=true;
	}

}
