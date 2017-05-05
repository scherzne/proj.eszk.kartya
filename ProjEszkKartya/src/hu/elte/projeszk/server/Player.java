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
	private int cardCount=0;

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
	/**
	 * kézben tartott kártyák darabszáma. Ez az osztó szeme, mivel a lapokat magukat
	 * megegyezés alapján nem láthatja. Azt viszont egy igazi játékban is látja az osztó
	 * , hogy a játékosnak hány lap van a kezében.
	 * Erre az UNO bemondása miatt van szükség, mivel a 2 kézben tartott lap és bedobás
	 * esetén, amennyiben a játékos nem mond hozzá UNO-t is, büntetést érdemel,
	 * lap osztást fog kapni.
	 * @return
	 */
	public int getCardCount(){
		return this.cardCount;
	}
	/**
	 * UNO miatt muszáj tudni hány lapja van a játékosnak, 
	 * ezért kiosztáskor növelni kell
	 * @param c kapott lapok száma
	 */
	public synchronized void increaseCardCount(int c){
		if(c>0)
			this.cardCount+=c;
	}
	
	/**
	 * UNO miatt muszáj tudni hány lapja van a játékosnak, 
	 * ezért bedobáskor csökkenteni kell
	 */
	public synchronized void decreaseCardCount(){
		if(this.cardCount-1>0)
			this.cardCount--;
	}
	
	@Override
	public String toString() {
		return name;
	}


}
