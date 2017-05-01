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
	private Card lastCard;
	
	private ArrayList<Player> players;//mégis kell, mert különben nem ismert a játékosok sorrendje, úgyis csak referenciákat tartalmaz, nem túl nagy
	private int managerId=-1;	
	
	/**
	 * a következő játékos playerId-je
	 */
	private int nextPlayer=-1;//még senki
	private boolean isRunning=false; 
	
	private String lastMessage;
	private boolean lastPlayerDrawed=false;
	private Card.CardColor lastColorRequest;
	/**
	 * Előre irány true, visszafelé irány a false
	 */
	private boolean playingDirection=true;
	
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
					droppedCards.add(card);
					lastCard=card;
					
					for(int i=0;i<players.size();i++){
						//módosítva, egy újabb üzenet típussal kiegészítve
						serverMessage(players.get(i), Consts.CARD_INFORMATION+"", new String[]{card.getCardAsString()});						
					}
				}
			}else{//nevét már megadta, de lehet, hogy nem lehet még kezdeni
				if(canPlay){//elméletileg mehet a játék, de még most sem biztos hogy ő jön
					//TODO:játék
					if(player.getId()==nextPlayer){//ha ő jön
						char firstChar=row.charAt(0);//na mit küld-kér.						
						
						//ez nem lesz jó, rosszul vannak az üzenet típusok definiálva!
						switch(firstChar){
							//a kliens valamilyen lapot küld. ez a módosítási javaslatom, 
							//az egyik nem egyértelmű üzenet megoldására 
							case Consts.SEND_CARD:
								//dolgozzuk fel, amit küldött
								String pars[]=row.split(Consts.MESSAGE_SEPARATOR+"");
								//lehet hogy rosszat küldött, ekkor várunk egy másik lapra, nem szabad menteni
								boolean canSaveLastCard=true;
								
								//első string lesz a kártya, ezt elő kell állítani
								//ezt el kell tenni, el lett dobva
								Card clientCard=new Card(pars[1]);
								
								//csak ezeknek van jelentősége a szerver szempontjából:
								//HUZZKETTOT,FORDITTO,KIMARADSZ,SZINKEREO,HUZZNEGYET
								//a többinél csak továbbítjuk a lapot, közöljük a játékosokkal
								//hogy folyik a játszma
								switch(clientCard.getCardValue()){//TODO: majd refaktor! külön func-okba kitenni a belsejét, boolen visszatéréssel
									case HUZZKETTOT://köv játékos kimarad, kap két lapot is
										if(lastCard.getCardColor()==clientCard.getCardColor() ||
												lastCard.getCardValue()==CardValue.HUZZKETTOT){//előző lap színe egyezik vagy ez is húzz kettőt volt
											nextPlayer=getNextPlayerId();//léptetés, ő fog kimaradni
											//2 lap húzás
											Card cards[]=drawCardsFromPack(2);
											String cardStrs[]=getCardStringArray(cards);
											//ez szerintem a gépi játékosnak kellhet
											//közöljük vele, mi a helyzet, mit dobott az utolsó lépő
											serverMessage(playerThreads.get(nextPlayer).getPlayer(), Consts.CARD_INFORMATION+"", new String[]{pars[1]});
											//elküldjük neki a két húzott lapot
											serverMessage(playerThreads.get(nextPlayer).getPlayer(), Consts.SEND_CARD+"2", cardStrs);
											//a többieknek pedig szintén infót
											String mess=playerThreads.get(nextPlayer).getPlayer().getName()+
													" "+clientCard.cardValueToString()+" "+clientCard.getCardValueAsChar()+
													" kártyát tett le.";
											serverMessageToOthers(nextPlayer, mess);
											//továbblépés a következő játékosra, ő már kell tegyen lapot, így kérünk tőle
											nextPlayer=getNextPlayerId();
											serverMessage(playerThreads.get(nextPlayer).getPlayer(), 
													Consts.REQUEST_CARD+"", new String[]{clientCard.getCardAsString(),
														Consts.HUZOTT,clientCard.getCardColorAsChar()+""});
										}else{//nem teheti le a húzz kettőt lapot, sem a színe sem a típusa nem jó!
											serverMessage(player, "Ezt a lapot nem dobhatod be!");
											canSaveLastCard=false;
										}
										break;
									case FORDITTO://játékirány megfordul
										if(lastCard.getCardColor()==clientCard.getCardColor() ||
											lastCard.getCardValue()==CardValue.FORDITTO){
											//játékos irány fordítás, illetve új játékos kiválasztása											
											switchDirection();
											nextPlayer=getNextPlayerId();
											//gépi játékosnak kellhet
											serverMessage(playerThreads.get(nextPlayer).getPlayer(), Consts.CARD_INFORMATION+"", new String[]{pars[1]});
											//szöveges üzenetek
											serverMessage(playerThreads.get(nextPlayer).getPlayer(), "Te következel, mert egy fordítót dobtak!");
											serverMessageToOthers(nextPlayer, "Játékirány megfordult");
											//miért fordult meg a játékirány
											String mess=playerThreads.get(nextPlayer).getPlayer().getName()+
													" "+clientCard.cardValueToString()+" "+clientCard.getCardValueAsChar()+
													" kártyát tett le.";
											serverMessageToOthers(nextPlayer, mess);
											//a lapot is küldjük
											serverMessage(playerThreads.get(nextPlayer).getPlayer(), 
													Consts.REQUEST_CARD+"", new String[]{clientCard.getCardAsString(),
														Consts.NEM_HUZOTT,clientCard.getCardColorAsChar()+""});
										}else{
											serverMessage(player, "Ezt a lapot nem dobhatod be!");
											canSaveLastCard=false;
										}
										break;
									case KIMARADSZ://a köv.játékos egyszer kimarad
										if(lastCard.getCardColor()==clientCard.getCardColor() ||
										lastCard.getCardValue()==CardValue.KIMARADSZ){
											//tudjuk ki marad ki, és közöljük is vele
											nextPlayer=getNextPlayerId();
											//gépi játékosnak kellhet
											serverMessage(playerThreads.get(nextPlayer).getPlayer(), Consts.CARD_INFORMATION+"", new String[]{pars[1]});
											//szöveges üzenetek
											serverMessage(playerThreads.get(nextPlayer).getPlayer(), "Egyszer kimaradsz, mert kimaradsz kártyát dobtak!");
											//és miért
											String mess=playerThreads.get(nextPlayer).getPlayer().getName()+
													" "+clientCard.cardValueToString()+" "+clientCard.getCardValueAsChar()+
													" kártyát tett le.";
											serverMessageToOthers(nextPlayer, mess);
											//tovább lépés arra játékosra aki tányleg jön
											nextPlayer=getNextPlayerId();
											//a lapot is küldjük
											serverMessage(playerThreads.get(nextPlayer).getPlayer(), 
													Consts.REQUEST_CARD+"", new String[]{clientCard.getCardAsString(),
														Consts.NEM_HUZOTT,clientCard.getCardColorAsChar()+""});
										}else{
											serverMessage(player, "Ezt a lapot nem dobhatod be!");
											canSaveLastCard=false;
										}
										break;
									case SZINKEREO://kérdezzük meg milyen színt kér
										//ez egyenlőre csak ennyi:
										serverMessage(player, Consts.REQUEST_COLOR+"", null);
										//módosítás: elmentjük az utolsó lapot, de nem tesszük el, ezt a send_color-ban kell lekezelni
										//mert: itt még várunk egy színre
										lastCard=clientCard;
										canSaveLastCard=false;
										break;
									case HUZZNEGYET://na itt lenne egy olyan ellenőrzés, 
										//amit szerver oldalon nem lehet megnézni, az itt kimarad, azt a kliensnek mindenképpen ellenőriznie kell
										//mégpedig azt, hogy az utolsó lappal nincs-e egyező színe.
										//A szerver ugyanis nem ismeri a játékosok kezében lévő lapokat.
										
										//bekérjük a színt, de tudni kellene hogy ez volt az utolsó lépés
										serverMessage(player, Consts.REQUEST_COLOR+"", null);
										//módosítás: elmentjük az utolsó lapot, de nem tesszük el, ezt a send_color-ban kell lekezelni
										//mert: itt még várunk egy színre
										lastCard=clientCard;
										canSaveLastCard=false;
										break;
									default://minden egyéb esetben léptetünk és küldjük a bedobott lap infókat
										nextPlayer=getNextPlayerId();
										//gépi játékosnak kellhet
										serverMessage(playerThreads.get(nextPlayer).getPlayer(), Consts.CARD_INFORMATION+"", new String[]{pars[1]});
										//szöveges üzenetek
										serverMessage(playerThreads.get(nextPlayer).getPlayer(), "Te jössz!");
										//és miért
										String mess=playerThreads.get(nextPlayer).getPlayer().getName()+
												" "+clientCard.cardValueToString()+" "+clientCard.getCardValueAsChar()+
												" kártyát tett le.";
										serverMessageToOthers(nextPlayer, mess);
										//a lapot is küldjük
										serverMessage(playerThreads.get(nextPlayer).getPlayer(), 
												Consts.REQUEST_CARD+"", new String[]{clientCard.getCardAsString(),
													Consts.NEM_HUZOTT,clientCard.getCardColorAsChar()+""});
										break;
								}
								
								//nem volt hiba, be lett dobva a lap
								if(canSaveLastCard){
									lastCard=clientCard;
									droppedCards.add(clientCard);
								}
								
								lastPlayerDrawed=false;
								break;
							case Consts.SEND_COLOR://ha színt kért, ezt a színt kell a köv-nek rakni
								//ez csak akkor fordulhat elő, ha az előző kártya színkérő joker
								//vagy húzz négyet joker volt!!!
								if(lastCard.getCardValue()==CardValue.SZINKEREO){
									//ekkor lehet léptetni a kört a köv játékosra
									String temp[]=row.split(Consts.MESSAGE_SEPARATOR+"");
									nextPlayer=getNextPlayerId();
									lastColorRequest=Card.convertCharacterToCardColor(temp[1].charAt(0));
									serverMessage(playerThreads.get(nextPlayer).getPlayer(), 
											Consts.REQUEST_CARD+"", new String[]{lastCard.getCardAsString(),
												((lastPlayerDrawed)?Consts.HUZOTT:Consts.NEM_HUZOTT),lastColorRequest+""});
									lastPlayerDrawed=false;
								}else if(lastCard.getCardValue()==CardValue.HUZZNEGYET){
									//húzz négyet esetén a köv.játékos kimarad és négy lapot is kap
									nextPlayer=getNextPlayerId();//léptetés, ő fog kimaradni
									String temp[]=row.split(Consts.MESSAGE_SEPARATOR+"");//ezt a színt választotta a jokeres
									lastColorRequest=Card.convertCharacterToCardColor(temp[1].charAt(0));
									//4 lap húzás
									Card cards[]=drawCardsFromPack(2);
									String cardStrs[]=getCardStringArray(cards);
									//ez szerintem a gépi játékosnak kellhet
									//közöljük vele, mi a helyzet, mit dobott az utolsó lépő
									serverMessage(playerThreads.get(nextPlayer).getPlayer(), Consts.CARD_INFORMATION+"", new String[]{lastCard.getCardAsString()});
									serverMessage(playerThreads.get(nextPlayer).getPlayer(), "Sajnos most kapsz 4 lapot :(");
									//elküldjük neki a két húzott lapot
									serverMessage(playerThreads.get(nextPlayer).getPlayer(), Consts.SEND_CARD+"4", cardStrs);
									//a többieknek pedig szintén infót
									String mess=playerThreads.get(nextPlayer).getPlayer().getName()+
											" "+lastCard.cardValueToString()+" "+lastCard.getCardValueAsChar()+
											" kártyát tett le.";
									serverMessageToOthers(nextPlayer, mess);
									//továbblépés a következő játékosra, ő már kell tegyen lapot, így kérünk tőle
									nextPlayer=getNextPlayerId();
									serverMessage(playerThreads.get(nextPlayer).getPlayer(), 
											Consts.REQUEST_CARD+"", new String[]{lastCard.getCardAsString(),
												Consts.HUZOTT,lastColorRequest+""});
								}else{
									serverMessage(player, "Hibás üzenet, nem válaszolhatsz színnel ha nem kérhetted!");
								}
								break;
							case Consts.NO_CARD://nem tud rakni a játékos,
								//a pakliból leveszünk egyet és elküldjük
								//a következő még nem jöhet, mert lehet hogy ugyanaz lerakja
								Card card=drawCardFromPack();
								serverMessage(player, Consts.SEND_CARD+"1", new String[]{card.getCardAsString()});
								lastPlayerDrawed=true;
								break;
						}
						lastMessage=row;
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
	 * szerver üzenet mindenki másnak
	 * @param notTo csak neki nem
	 * @param mess
	 */
	protected void serverMessageToOthers(int notTo, String mess){
		for(Player pl:players){
			if(pl.getId()!=notTo)
				pl.write(Consts.SEND_MESSAGE+Consts.MESSAGE_SEPARATOR+"Szerver: "+mess);
		}
	}
	/**
	 * Szerver nem sima üzenet típusú, hanem egyéb funkciójú üzenete a játékosnak
	 * @param to a játékos
	 * @param messageType az üzenet típusa, ez egy String, pl: A3, vagy S , szín kérés vagy 3 lapot ad a szerver
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
	 * Üzenet a játékosoknak, kivéve a megadottat
	 * @param notTo egyedül neki (player id-t kell megadni) nem fog menni az itt megadott üzenet
	 * @param messageType az üzenet típusa, ez egy String, pl: A3 ami annyit jelent, hoyg 3 lapot ad a szerver
	 * @param messageParts  az ebben lévő darabokat vesszővel elválasztva összeilleszti és 
	 * hozzáfűzi az üzenet típust meghatározó karakterhez
	 */
	protected void serverMessageToOthers(int notTo, String messageType, String[] messageParts){
		for(Player pl:players){
			if(pl.getId()!=notTo)
				serverMessage(pl, messageType, messageParts);
		}
	}
	
	/**
	 * FIXME: üres paklit megoldani!!!
	 * Legfelső kártya húzása(és eltávolítása) a pakliból
	 * @return vagy egy kártya vagy null ha a pakli már üres!
	 */
	protected synchronized Card drawCardFromPack(){
		if(cardPack.size()>0){
			return cardPack.remove(cardPack.size()-1);
		}
		
		return null;
	}
	/**
	 * egyszerre több kártya húzása a pakliból
	 * @param cardNum
	 * @return
	 */
	protected synchronized Card[] drawCardsFromPack(int cardNum){
		Card cards[]=new Card[cardNum];
		
		for(int i=0;i<cardNum;i++){
			cards[i]=drawCardFromPack();
		}
		
		return cards;
	}
	/**
	 * String tömbben elő lehet állítani a kártyákat szövegesre 
	 * @param cards
	 * @return
	 */
	protected String[] getCardStringArray(Card cards[]){
		String cardStrings[]=new String[cards.length];
		
		for(int i=0;i<cardStrings.length;i++)
			cardStrings[i]=cards[i].getCardAsString();
		
		return cardStrings;
	}
	
	/**
	 * Kártyapakli generálása
	 */
	protected void generatePack(){
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
	 * Nulla számú játékos kihagyással a következő játékos id-je  
	 * @return
	 */
	protected synchronized int getNextPlayerId(){
		return getNextPlayerId(0);
	}
	/**
	 * Meghatározható számú játékos kihagyással a következő játékos id-je  
	 * @param skippedPlayerNum hány játékost hagyjunk ki a következőhöz
	 * @return
	 */
	protected synchronized int getNextPlayerId(int skippedPlayerNum){
		int currentInd=0;
		int nextPlayerId;
		
		//léptetéshez megkeressük most hanyas !indexű! a tömbben a játékos
		for(int i=0;i<players.size();i++){
			if(nextPlayer==players.get(i).getId()){
				currentInd=i;
				break;
			}
		}
		//az indexeket nem szabad túllépni, ezt ellenőrizni kell
		if(playingDirection){//előre
			if(currentInd+skippedPlayerNum+1>=players.size()){
				nextPlayerId=players.get(0).getId();
			}else{
				nextPlayerId=players.get(currentInd+skippedPlayerNum+1).getId();
			}
		}else{//hátrafelé megy a játék
			if((currentInd-skippedPlayerNum-1)<0){
				nextPlayerId=players.get(players.size()-1).getId();
			}else{
				nextPlayerId=players.get(currentInd-skippedPlayerNum-1).getId();
			}
		}
		return nextPlayerId;
	}
	
	protected synchronized void switchDirection(){
		playingDirection=!playingDirection;
	}
	
	/**
	 * Manager azonosító visszaadása, amit a konstruktornak kell átadni
	 * @return nem az örökölt thread id-t, hanem a manager azonosító
	 */
	public int getManagerId() {
		return this.managerId;
	}
	
	
}
