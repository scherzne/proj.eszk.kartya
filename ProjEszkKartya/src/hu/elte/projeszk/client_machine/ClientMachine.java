package hu.elte.projeszk.client_machine;


import java.io.*;

import java.lang.reflect.Array;
import java.net.*;
import java.util.*;
import hu.elte.projeszk.Card;
import hu.elte.projeszk.Consts;
import hu.elte.projeszk.Card.CardColor;
import hu.elte.projeszk.Card.CardValue;

public class ClientMachine {

protected ArrayList<Card> hand = new ArrayList<Card>();
private Socket client;	
private String gamerName;

	
public static void main(String[] args) throws UnknownHostException {


	ClientMachine clientMachine= new ClientMachine(args[0]);
		
		 
		 

}

public  ClientMachine(String name){
	

	 String answer="";
	 gamerName = name;
	 boolean gameRunning= true;
	
	
	 
	
			String host = "localhost";
	        int portNumber = Consts.PORT;
	        
	        try {
				client = new Socket(host, portNumber);
	//		} catch (UnknownHostException e) {
	//			System.out.println("UnknownHostException, ismeretlen host "+ e);
	//			e.printStackTrace();
	//		} catch (IOException e) {
	//			System.out.println("IOException t "+ e);
		//		e.printStackTrace();
		//	}
	    	System.out.println("A gépi kliens letrejott, es csatlakozott a szerverhez.");
		    
	    
	    	PrintWriter pw= null;
			//try {
				
				pw = new PrintWriter(client.getOutputStream());
				
		//	} catch (IOException e) {
		//		System.out.println("A Hiba  a printwriter léterhozásakor.");
	//			e.printStackTrace();
				
	//		}
			
			BufferedReader  br = null;
		//	try {
			     br = new BufferedReader(new InputStreamReader(client.getInputStream()));
					
		//	} catch (IOException e) {
		//		System.out.println("A Hiba  a BufferedReader léterhozásakor.");
		//		e.printStackTrace();
				
		//	}
	      
	      
	        do{
	        	  
	        	
	  	          try {
					answer =  switchAtInputCharacter(br);
	  	          
	  	          	} catch (NullPointerException e) {
					// TODO Auto-generated catch block
	  	          	System.out.println("A szerver bontotta a kapcsolatot.");
	  	          	gameRunning= false;
				//	e.printStackTrace();
				}
	  	         
	  	         
	  	          if (answer!=null){
	  	         
	  	          System.out.println("Az"+name +" által elküldött válasz: " + answer);	  
	  	          pw.println(answer);
	  	          pw.flush();
	  	         
	  	          }
	        	
	        }while(gameRunning);
	        
	        
	      //  try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("A kliens leallt.");
		
		
	
	
}

protected String switchAtInputCharacter( BufferedReader br ) throws IOException{
	

	CardColor declaredColorByMachine;
	Card otherPlayersCard;
	String answer=null;
	
	String message= br.readLine();
	

    System.out.println(gamerName +" Kapott üzenet:"+message); 
	
	switch (message.charAt(0)){
	
	case (Consts.SEND_CARD): //'A'
		// lapokat ad a szerver
		System.out.println(gamerName+ " Kártyát kapunk :");
			messageBeginWithCharA(message, br);
		
		break;
	
		case (Consts.REQUEST_CARD): //'L'
		
			System.out.println("Lapot kér a szerver:");
			if (hand.size()!=0)
			{
				answer = cardChoosing(message);
			}
			// válasz kiirása standard outputra
			
		break;
	
	 case(Consts.REQUEST_COLOR)://'S'
			
		 System.out.println("Színt kér a szerver!");
		
		 //bővíthetőség: legyen olyan szín ami (sok) van

		 declaredColorByMachine = randomDeclareColor();     		 			
		 answer =Consts.SEND_COLOR+","+Card.convertCardColorToCharacter(declaredColorByMachine);
	
				 
    			
		 break;
	 case Consts.CARD_INFORMATION: //'I'
		
	//	 otherPlayersCard = new Card(Card.convertCharacterToCardColor(message.charAt(2)), Card.convertCharacterToCardValue(message.charAt(3)));
			
		// System.out.println("Másik játékos rakott lapot:" + otherPlayersCard.getCardColor() + " " +otherPlayersCard.getCardValue());
		 
		 
		 break;
	 case Consts.SEND_MESSAGE: //M
		 
		 System.out.println(message);
		

		 
		 break;
	 	
	  case Consts.REQUEST_NAME:// 'B'
			answer = gamerName;
		
		 break;	 
		 
			 
	 
}
	
	//System.out.println("Valasz (meg nem elkuldott) "+answer);
	
	
	
	
	return answer;
}


protected int handGetSize(){
	
	return hand.size();
	
}

protected void remove(Card card){
	
	 hand.remove(card);
	
}


protected void setSocket(Socket socket) throws UnknownHostException, IOException {
	 client = socket;
	

}

protected String cardChoosing(String message){
	
	String choosenCardString;
	String unoMessage="";
	Card topCard = new Card(Card.convertCharacterToCardColor(message.charAt(2)), Card.convertCharacterToCardValue(message.charAt(3)));
	System.out.println(topCard);
	Boolean lastPlayerDrawed = initLastPlayerDrawed(message);
	System.out.println(lastPlayerDrawed);
	CardColor declaredColor = Card.convertCharacterToCardColor(message.charAt(7));
	System.out.println(declaredColor);
	
	Card returnCard = machineCardChooseAlgorithm(topCard, lastPlayerDrawed, declaredColor);
	removeCardFromHand(returnCard);
	

	if (returnCard == null){
		
		choosenCardString =Consts.NO_CARD+"";//"N"
	}else{

	// UNO ESET!
	if (hand.size()==1){
	   unoMessage = randomizeUno();

	}
	choosenCardString = Consts.SEND_CARD+","+Card.convertCardColorToCharacter(returnCard.getCardColor())+Card.convertCardValueToCharacter(returnCard.getCardValue())+unoMessage;
	
	
	}
	
	return choosenCardString;
	
}

protected Card.CardColor randomDeclareColor(){
	
	Random rand = new Random();
	 int random = rand.nextInt(4); 
		
	Card.CardColor declaredColorByMachine= CardColor.FEKETE;
	
	switch (random){
		
		case 0: declaredColorByMachine =  CardColor.KEK;   break;
		case 1: declaredColorByMachine =  CardColor.SARGA; break;
		case 2: declaredColorByMachine =  CardColor.ZOLD;  break;
		case 3: declaredColorByMachine =  CardColor.PIROS; break;
		//default: 
		//	declaredColorByMachine= CardColor.FEKETE;
		//	System.out.println("Hibás gépi színkérés:"+ random);
			
	//		break;
	}
	
	
	 System.out.println(" A gép által választott szín: "+declaredColorByMachine);
	return declaredColorByMachine;
	
}
protected String  randomizeUno(){
	
String unoMessage="";

	if (hand.size()==1){
		
		
		Random rand = new Random();
		int   random = rand.nextInt(3);
		//int   random = rand.nextInt(1);
			switch (random){
			
			case 0: unoMessage=","+Consts.UNO;  
			
			System.out.println("UNO-t mond a gép!");
			
			break;
		
			default: unoMessage="";  
			System.out.println("Elfelejtett UNO-t mondani a gép.");  
			
			break;
			
			}
		
		
	}
	
return	unoMessage;
	
}



protected void messageBeginWithCharA(String message, BufferedReader br) throws IOException {
	//split!!
	 String[] splitted = message.split(",");
	
	 
	//int index = message.charAt(1);
	 
	 System.out.println(message);
	 System.out.println("Kaptunk " +splitted[0].charAt(1)+"db kártyát:");

	/*for (int i =1; i < splitted[0].charAt(1); i++){
		
		System.out.println(splitted[0].charAt(1));
		System.out.println(i);
	
		addCardToHand(new Card(Card.convertCharacterToCardColor(splitted[i].charAt(0)), Card.convertCharacterToCardValue(splitted[i].charAt(1))));
		System.out.println(hand.get(hand.size()-1).getCardColor()+ " " +hand.get(hand.size()-1).getCardValue());
		
		if (i == (int) (splitted[0].charAt(1))){
			
			System.out.println("belepett"); 
			break;}
	}/*/

	 
	for (String s:  splitted){
		
		//if ((s.charAt(0))=='A' ){
		if (((s.charAt(0))+"").equals(Consts.SEND_CARD+"") ){
		//	System.out.println("Nem kártya");
			
		}else{
			
		addCardToHand(new Card(Card.convertCharacterToCardColor(s.charAt(0)), Card.convertCharacterToCardValue(s.charAt(1))));
		System.out.println(hand.get(hand.size()-1).getCardColor()+ " " +hand.get(hand.size()-1).getCardValue());
		}
	}
	
	
}

protected boolean initLastPlayerDrawed(String message){

	//if (message.charAt(5)== 'H'){
		
	if ((message.charAt(5)+"").equals(Consts.HUZOTT+"")){
		return true;
		
	}else //if  (message.charAt(5)== 'N'){
		if ((message.charAt(5)+"").equals(Consts.NEM_HUZOTT+"")){
			
		
		return false; 
	
	}else{
		
		
		System.out.println("Hibás lap kérés Üzenet!");
		return false;
	}
			
}

public  void  addCardToHand(Card card){
	
	hand.add(card);
	
}

protected void removeCardFromHand(Card card){
	
	hand.remove(card);
	
}


	 
public Card machineCardChooseAlgorithm(Card topCard, boolean lastPlayerDrawed, CardColor declaredColor  ){

		Card selectedCard= null;
	
		//szabályok egy kártyát ad vissza az alapján hogy mi van a kezében, az előző játékos huzott-e és van e színkötelezettség
		//ELSO ESET
		if (!lastPlayerDrawed && declaredColor.equals(CardColor.FEKETE)){
			// nincs színkérés és az utolsó ember nem húzott  kártyát azaz az előző játékos rakta
		
			selectedCard = commonCardChoosing(topCard);
			
			
		}
	
	
	
		if (!lastPlayerDrawed && !declaredColor.equals(CardColor.FEKETE)){
			
			// VAN színkényszer és az utolsó ember nem húzott  (kártyát azaz az előző játékos rakta)
			// mivel a színkényszer csak 1 játékosra vonatkozik, ezért ha raktak elöttünk, és van színkényszer
			//akkor az elöttünk lévő rakta a szinkényszer kártyát. ekkor csak 2 lehetőség van, 
			// ha plussznegyet vagy ha jokert raktak.
			
			selectedCard = specialCardChoosingNotDrawedAndNotDeclaredColor(topCard,declaredColor );
				
				
		
		}
		
		if (lastPlayerDrawed && declaredColor.equals(CardColor.FEKETE)){
			// Negyedik eset, előző játékos húzott - azaz nem tudott rakni- és nincs színkényszer
			
			selectedCard = commonCardChoosing(topCard);
			
			
		}
		
		if (lastPlayerDrawed && !declaredColor.equals(CardColor.FEKETE)){
			//ötödik eset
			// előző játékos húzott és színkényszer van.
			// mivel elöttünk húztak, bármit rakhatunk az adott színben
	
			
			selectedCard = specialCardChoosingDrawedAndDeclaredColor(declaredColor);
				
			}
				

	
return selectedCard;	
}


protected Card commonCardChoosing(Card topCard){
	
	Card selectedCard = null;
	
	if(topCard.getValue()<10 ){
		
		selectedCard = searchWhenTopNumber(topCard);
		
	}else{
		//legfelso kartya nem szam!
		// ha az előző játékos rakta akkor a szivatósok közül nem lehet se fordító
		// csak plussz 2 es plussz 4 es és szinkérő
		
		selectedCard = searchWhenTopNotNumber(topCard);
	}
	
	return selectedCard;
}


protected Card specialCardChoosingNotDrawedAndNotDeclaredColor( Card topCard, CardColor declaredColor){
	
	Card selectedCard = null;

	if(topCard.getCardValue().equals(CardValue.HUZZNEGYET)){
		
		selectedCard = searchDrawFourJoker();
		
	}else /*if (topCard.getCardValue().equals(CardValue.SZINKEREO))*/{
		
		
		selectedCard=	searchAnyNumberOfCertainColor(declaredColor);
		
		if (selectedCard== null){
			selectedCard = searchWhenTopNotNumberCertainColor(declaredColor);}
		
	
	}
	
	
	return selectedCard;
}


protected Card specialCardChoosingDrawedAndDeclaredColor(CardColor declaredColor){

	Card selectedCard = null;
	 selectedCard = searchAnyNumberOfCertainColor(declaredColor);
	
	if (selectedCard == null){
	
	selectedCard = searchSameColorNotNumber(declaredColor);
	}
	if (selectedCard == null){
		
		selectedCard = searchJokerCard();
	}
	
	if (selectedCard == null){
		
		selectedCard = searchDrawFourJoker();
	}
	
	return selectedCard;
}



protected Card searchWhenTopNotNumberCertainColor(CardColor cardColor){
	

							
		Card selectedCard = searchSameColorNotNumber(cardColor);
	
	if (selectedCard == null){
	
		
		selectedCard = searchJokerCard();
	
	}
	
	if (selectedCard == null){
	
		
		selectedCard = searchDrawFourJoker();
	
	}
	
	
	return selectedCard;
}

protected Card searchWhenTopNotNumber(Card topCard){
	
	
	
	Card selectedCard = searchCertainCardAnyColor(topCard.getCardValue());
	
	
	if (selectedCard == null){
	
		
		selectedCard = searchDrawFourJoker();
	
	}
	
	
	return selectedCard;
}

protected Card searchWhenTopNumber(Card topCard){
	// számot keresünk 0-9 ig, ugyanolyan színben
	Card	selectedCard = searchAnyNumberOfCertainColor(topCard.getCardColor());

	if (selectedCard == null){
		
		// ugyanazt a számot keresssük bármilyen színben
		selectedCard = searchCertainCardAnyColor(topCard.getCardValue());
		
	}
	
	if (selectedCard == null){
		
		// "szivató kártyát" ugyanolyan színben
		selectedCard = searchSameColorNotNumber(topCard.getCardColor());
		
	}
	
	if (selectedCard == null){
		
		// Joker kártyát keresünk
		selectedCard = searchJokerCard();
		
	}
	

	
	
	if (selectedCard == null){
		
		// huzznegyet kártyát keresünk
		selectedCard = searchDrawFourJoker();
		
	}
	
	if (selectedCard == null){
		
		//NEM TUDUNK RAKNI
		
	}
return selectedCard;


}
protected Card searchWhenTopNumberCertainColor(CardColor cardColor){
	
	
	
	Card selectedCard = searchAnyNumberOfCertainColor(cardColor);
	
	if (selectedCard == null){
	
		
		selectedCard = searchSameColorNotNumber(cardColor);
	
	
	
	}
	if (selectedCard == null){
	
		
		selectedCard = searchJokerCard();
	
	}
	
	if (selectedCard == null){
	
		
		selectedCard = searchDrawFourJoker();
	
	}
	
	
	return selectedCard;
}





protected Card searchAnyNumberOfCertainColor(CardColor color) {
	//kártyát keresünk adott szín alapján (sima számmal ellátott lapot)
	Card card = null;
	for (Card h: hand ){

		
		if (h.getCardColor().equals(color) && h.getValue() <10){
			
			return h;
			
		}

	}
	
	return card;
}


protected Card searchCertainCardAnyColor(CardValue value) {
	//kártyát keresünk azonos érték alapján
	
	Card card = null;
	for (Card h: hand ){

		
		if (h.getCardValue().equals(value)){
			return h;
	
		}

	}
	
	return card;
}

protected Card searchSameColorNotNumber(CardColor color) {
	//kártyát keresünk adott szín alapján (csak "szivató kártyát")
	
	Card card = null;
	for (Card h: hand ){

		
		if (h.getCardColor().equals(color) && (h.getValue() <=12 & h.getValue() >=10)){
			return h;
	
		}

	}
	
	return card;
}

protected Card searchJokerCard() {
	//Szinkérőt azaza Jokert keresünk
	
	Card card = null;
	for (Card h: hand ){

		
		if (h.getCardValue().equals(CardValue.SZINKEREO)){
			return h;
	
		}

	}
	
	return card;
}

protected Card searchDrawFourJoker() {
	//plusz 4-est keresünk
	
	Card card = null;
	for (Card h: hand ){

		
		if (h.getCardValue().equals(CardValue.HUZZNEGYET)){
			return h;
	
		}

	}
	
	return card;
}




}




