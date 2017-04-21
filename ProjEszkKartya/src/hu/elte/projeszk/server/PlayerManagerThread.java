package hu.elte.projeszk.server;

import hu.elte.projeszk.Card;
import hu.elte.projeszk.Card.CardColor;
import hu.elte.projeszk.Card.CardValue;
import hu.elte.projeszk.Consts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * 
 * @author scherzne
 *
 */
public class PlayerManagerThread extends Thread {
	private ArrayList<Card> cardPack;
	private HashMap<Integer, PlayerThread> playerThreads;
	private ArrayList<Player> players;//mégis kell, mert különben nem ismert a játékosok sorrendje, úgyis csak referenciákat tartalmaz, nem túl nagy
	private int managerId=-1;	
	
	private int nextPlayer=0;
	private boolean isRunning=false; 
	
	/**
	 * A szerver begyűjtötte a játszani óhajtott csoportot, a manager innen átveheti az irányítást
	 * A szerver várhat a köv. adag játékosra
	 * @param id
	 * @param players
	 */
	public PlayerManagerThread(int id,ArrayList<Player> players) {
		super("manager "+id);
		this.managerId=id;
		this.players=players;
		
		cardPack=new ArrayList<>();
		playerThreads=new HashMap<>();
		for(Player player:players){
			PlayerThread thread=new PlayerThread(player, this);
			playerThreads.put(player.getId(), thread);
		}
		
		generatePack();
	}	
	
	/**
	 * TODO:teljes játékmenet kezelése
	 * @param player
	 * @param row beolvasott sor a kliens-ről
	 * @return
	 */
	public synchronized boolean readRow(Player player, String row){
		return false;
	}
	
	@Override
	public void run() {
		logToConsole("Player Manager "+this.managerId+" started");
		//TODO:szálak indítása
		//TODO:nevek bekérése
		super.run();
	}
	/**
	 * Üzenet a standard outputra, mindenféle loghoz
	 * @param message
	 */
	protected void logToConsole(String message){
		System.out.println(message);
	}
	/**
	 * Szerver üzenete a játékosnak
	 * @param to
	 * @param mess
	 */
	protected void serverMessage(Player to,String mess){
		to.write(Consts.SEND_MESSAGE+Consts.MESSAGE_SEPARATOR+"Szerver: "+mess);
	}

	/**
	 * Legfelső kártya húzása(és eltávolítása) a pakliból
	 * @return vagy egy kártya vagy null ha a pakli már üres!
	 */
	public Card drawCardFromPack(){
		if(cardPack.size()>0){
			return cardPack.remove(cardPack.size()-1);
		}
		
		return null;
	}
	
	/**
	 * Kártyapakli generálása
	 */
	private void generatePack(){
		Card card;
		
		//összes szín
		for(int c=CardColor.SARGA.ordinal();c<CardColor.FEKETE.ordinal();c++){
			//egy-egy darab 0-ás
			card=new Card(c, Card.cardValueToInt(CardValue.NULLA));
			cardPack.add(card);
			//két darab többi számos, plusz húzz kettőt, fordulj, ugorj
			for(int j=0;j<2;j++){
				//számos
				for(int i=CardValue.EGY.ordinal();i<CardValue.FORDITTO.ordinal();i++){
					card=new Card(c, i);
					cardPack.add(card);
				}
				//húzz kettőt
				card=new Card(c, Card.cardValueToInt(CardValue.HUZZKETTOT));
				cardPack.add(card);
				//fordulj
				card=new Card(c, Card.cardValueToInt(CardValue.FORDITTO));
				cardPack.add(card);
				//ugorj
				card=new Card(c, Card.cardValueToInt(CardValue.KIMARADSZ));
				cardPack.add(card);
			}
		}
		//fekete joker és húzz négyet joker
		for(int i=0;i<4;i++){
			card=new Card(CardColor.FEKETE, CardValue.SZINKEREO);
			cardPack.add(card);
			card=new Card(CardColor.FEKETE, CardValue.HUZZNEGYET);
			cardPack.add(card);
		}
		//pakli megkeverése
		Collections.shuffle(cardPack);
	}

	/**
	 * Manager azonosító visszaadása, amit a konstruktornak kell átadni
	 * @return nem az örökölt thread id-t, hanem a manager azonosító
	 */
	public int getManagerId() {
		return this.managerId;
	}
	
	
}
