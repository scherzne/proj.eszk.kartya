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
	/**
	 * Aktív pakli tárolása
	 */
	private ArrayList<Card> cardPack;
	/**
	 * Bedobott és az első felforgatott kártyalap is ebbe kerül, hogy újra lehessen keverni
	 */
	private ArrayList<Card> droppedCards;
	private HashMap<Integer, PlayerThread> playerThreads;
	private ArrayList<Player> players;//mégis kell, mert különben nem ismert a játékosok sorrendje, úgyis csak referenciákat tartalmaz, nem túl nagy
	private int managerId=-1;	
	
	private int nextPlayer=-1;//még senki
	private boolean isRunning=false; 
	
	private String lastMessage;
	private boolean canPlay=false;//mindenki megadta-e a nevét, addig nincs kezdés
	private int nameCount=0;//segéd, amiben számláljuk hányan adták meg a nevük
	//ez már nem fog kelleni ha mindenki, átbeszi a helyét a canPlay, ekkor már nem 
	//számlálgatunk arraylist-et
	
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
		//init
		cardPack=new ArrayList<>();
		droppedCards=new ArrayList<>();
		playerThreads=new HashMap<>();
		//thread létrehozása és indítása
		for(Player player:players){
			PlayerThread thread=new PlayerThread(player, this);
			playerThreads.put(player.getId(), thread);
		}
		//pakli generálás
		generatePack();
	}	
	
	/**
	 * TODO:teljes játékmenet kezelése
	 * @param player
	 * @param row beolvasott sor a kliens-ről
	 * @return
	 */
	public synchronized boolean readRow(Player player, String row){
		if(row!=null){
			//első lépés: a játékos meg kell adja a nevét, addig nem mehet tovább a játék
			//(nem kezdődhet el) amíg nincs mindenkinek neve
			//ezt legegyszerűbb úgy megállapítani, hogy a neve még null-e vagy sem
			//erre válasz a 7 leosztott lap neki
			if(player.getName()==null){
				player.setName(row.trim());
				nameCount++;
				if(nameCount>=players.size())canPlay=true;
				//osztás
				Card card;
				String mess1=Consts.SEND_CARD+"7";//7 lapot adunk
				String arr[]=new String[7];//itt lesznek a lapok
				for(int i=0;i<7;i++){
					card=drawCardFromPack();
					arr[i]=card.getCardAsString();
				}
				serverMessage(player, mess1, arr);//lapok küldése a játékosnak
				
				//ha ő az utolsó aki megadta a nevét, mindenkinek küldjük ki hogy kezdődik
				//illetve az első játékosnak aki jön, hogy ő jön
				if(canPlay && nextPlayer<0){//ez akkor teljesül, ha már játszhatunk, de még nincs köv. játékos
					//kezdő játékos (0.) nevét közöljük mindenkivel, meg persze saját magával is
					String pName=players.get(0).getName();//pl:jenő
					serverMessage(players.get(0), "Kedves "+pName+"! Te kezdesz!");
					for(int i=1;i<players.size();i++){
						serverMessage(players.get(i), "A játék kezdődik, "+pName+" kezd.");
					}
					nextPlayer=players.get(0).getId();//a köv játékos azonosítóját tesszük most el,
					// lehetne az indexe is, mert úgyis így jöttek sorba a körbe, mindegy majd kiderül
					//melyik kényelmesebb
					
					//egy lap felforgatás, ezt el is tesszük a gyűjtőbe, mert senki nem kapja meg
					//valamint mindenkinek elküldjük, hogy erről kell indulni
					card=drawCardFromPack();
					for(int i=0;i<players.size();i++){
						serverMessage(players.get(i), Consts.SEND_CARD+"1", new String[]{card.getCardAsString()});
					}
				}
			}else{//nevét már megadta, de lehet, hogy nem lehet még kezdeni
				if(canPlay){//elméletileg mehet a játék, de még most sem biztos hogy ő jön
					//TODO:játék
					if(player.getId()==nextPlayer){//ha ő jön
						char firstChar=row.charAt(0);//na mit küld-kér
						switch(firstChar){
						case Consts.REQUEST_CARD:break;
						case Consts.SEND_COLOR:break;
						case Consts.NO_CARD:break;
						}
					}else{//nem ő jön
						serverMessage(player, "Nem te következel, várj egy kicsit!");
					}					
				}else{//még nem küldhet lapot
					serverMessage(player, "Nem te jössz, még nem adta meg mindenki a nevét!");
				}
			}
		}else{//gáz van, kilépett vagy leszakadt. jó esetben a streamen ekkor jön a null,
			//rossz esetben semmi, csak nincs köv. játékos
			return false;
			//TODO:valamit kezdeni a többiekkel, vagy mindenkit ledobni, mert nem
			//lehet tudni milyen lapjai voltak
		}
		
		return true;
	}
	
	@Override
	public void run() {
		logToConsole("Player Manager "+this.managerId+" started");

		for(PlayerThread thread:playerThreads.values()){
			thread.start();
			//első üzenet: add meg a neved, egy sima szöveges és egy névbekérő
			serverMessage(thread.getPlayer(), "Add meg a neved:");
			serverMessage(thread.getPlayer(), Consts.REQUEST_NAME+"", null);
		}
		isRunning=true;
		
		//ő csak egy szál, ami tud a többiről, nem kell túl gyorsan pörögjön, 
		while(isRunning){
			try {
				sleep(300);
			} catch (InterruptedException e) {
				isRunning=false;
			}
			//TODO: checkPlayersAreLiving, ellenőrzés hogy van-e még élő játékos, ne maradjon a szál a levegőben
		}
				
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
	 * Szerver nem sima üzenet típusú, hanem egyéb funkciójú üzenete a játékosnak
	 * @param to a játékos
	 * @param messageType az üzenet típusa, ez egy String
	 * @param messageParts az ebben lévő darabokat vesszővel elválasztva összeilleszti és 
	 * hozzáfűzi az üzenet típust meghatározó karakterhez
	 */
	protected void serverMessage(Player to, String messageType, String[] messageParts){
		StringBuilder builder=new StringBuilder();
		
		builder.append(messageType).append(Consts.MESSAGE_SEPARATOR);
		if(messageParts!=null){
			for(int i=0;i<messageParts.length;i++){
				builder.append(messageParts[i]);
				if(i<messageParts.length-1)
					builder.append(Consts.MESSAGE_SEPARATOR);
			}
		}
		
		to.write(builder.toString());
	}
	/**
	 * Legfelső kártya húzása(és eltávolítása) a pakliból
	 * @return vagy egy kártya vagy null ha a pakli már üres!
	 */
	public synchronized Card drawCardFromPack(){
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
