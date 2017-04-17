package hu.elte.projeszk.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Játékos tároló osztály, ő ismeri a saját socketjét, nevét, id-jét
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
	/**
	 * Csak olvas egyet a scanner-el
	 * @return
	 */
	public String read(){
		String w=null;

		try{
			if(scanner.hasNextLine()){				
				w=scanner.nextLine();
				updateLastAction();
			}else die();
		}catch (IllegalStateException e) {
			die();
			w=null;
		}
		
		return w;
	}
	/**
	 * Ír a saját kimenetére
	 * @param str
	 */
	public void write(String str){
		pw.println(str);
		pw.flush();
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public long getLastAction() {
		return lastAction;
	}

	public synchronized void updateLastAction() {
		this.lastAction = System.currentTimeMillis();
	}

	public int getId() {
		return id;
	}

	public synchronized boolean isLive() {
		return live;
	}

	/**
	 * Nem élővé tesszük a játékost
	 */
	public void die() {
		this.live = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}


}
