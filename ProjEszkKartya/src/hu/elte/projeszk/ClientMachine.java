package hu.elte.projeszk;


import java.io.*;
import java.net.*;
import java.util.*;
import hu.elte.projeszk.Card;
import hu.elte.projeszk.Card.CardColor;
import hu.elte.projeszk.Card.CardValue;

public class ClientMachine {

protected ArrayList<Card> hand = new ArrayList<Card>();	


	
	 public static void main(String[] args) throws UnknownHostException {

		 String gamerName = args[0];
		 String message;
		 
		 try{
				String host = "localhost";
		        int port =  11111;
		        
		        Socket client = new Socket(host, port);
		        System.out.println("A kliens letrejott, es csatlakozott a szerverhez.");
		        
		        PrintWriter pw = new PrintWriter(client.getOutputStream());
		        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			  
 
		        do{
		        	
		        
		        	
		        	
		        	break;
		        	
		        }while(true);
		        
		        
		        client.close();
		        System.out.println("A kliens leallt.");
			
			}  catch (Exception e) {
	                System.err.println("Hiba a klienessel valo kommunikacioban.");
				}
		 
		 

}
			
public void addCardToHand(Card card){
	
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
	
	if(topCard.getValue()<10){
		
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




