package hu.elte.projeszk;


import java.io.*;
import java.net.*;
import java.util.*;
import hu.elte.projeszk.Card;
import hu.elte.projeszk.Card.CardColor;
import hu.elte.projeszk.Card.CardValue;

public class ClientMachine {

protected ArrayList<Card> hand = new ArrayList<Card>();	


	
	 public static void main(String[] args) {
		 

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
	if (!lastPlayerDrawed && declaredColor.equals(CardColor.FEKETE)){
		// nincs színkérés és az utolsó ember nem húzott  kártyát
		if(topCard.getValue()<10){
			
			selectedCard = searchWhenTopNumber(topCard);
		}
		
	}
return selectedCard;	
}


protected Card searchNumberOfCertainColor(CardColor color) {
	//Olyan számot keresünk ami azonos színű a legfelső lappal
	
	Card card = null;
	for (Card h: hand ){

		
		if (h.getCardColor().equals(color) && h.getValue() <10){
			return h;
	
		}

	}
	
	return card;
}


protected Card searchCertainCardAnyColor(CardValue value) {
	//Olyan számot keresünk ami azonos színű a legfelső lappal
	
	Card card = null;
	for (Card h: hand ){

		
		if (h.getCardValue().equals(value)){
			return h;
	
		}

	}
	
	return card;
}

protected Card searchNotNumberSameColor(CardColor color) {
	//Olyan számot keresünk ami azonos színű a legfelső lappal
	
	Card card = null;
	for (Card h: hand ){

		
		if (h.getCardColor().equals(color) && (h.getValue() <=12 & h.getValue() >=10)){
			return h;
	
		}

	}
	
	return card;
}

protected Card searchJokerCard() {
	//Olyan számot keresünk ami azonos színű a legfelső lappal
	
	Card card = null;
	for (Card h: hand ){

		
		if (h.getCardValue().equals(CardValue.SZINKEREO)){
			return h;
	
		}

	}
	
	return card;
}

protected Card searchDrawFourJoker() {
	//Olyan számot keresünk ami azonos színű a legfelső lappal
	
	Card card = null;
	for (Card h: hand ){

		
		if (h.getCardValue().equals(CardValue.HUZZNEGYET)){
			return h;
	
		}

	}
	
	return card;
}

protected Card searchWhenTopNumber(Card topCard){
	// számot keresünk 0-9 ig, ugyanolyan színben
	Card	selectedCard = searchNumberOfCertainColor(topCard.getCardColor());
			
	if (selectedCard == null){
		
		// ugyanazt a számot keresssük bármilyen színben
		selectedCard = searchCertainCardAnyColor(topCard.getCardValue());
		
	}
	
	if (selectedCard == null){
		
		// "szivató kártyát" ugyanolyan színben
		selectedCard = searchNotNumberSameColor(topCard.getCardColor());
		
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


}




