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
	 
public Card machineCardChooseAlgorithm(ArrayList<Card> cardInHand, boolean lastPlayerDrawed, CardColor DeclaredColor  ){

	//szabályok egy kártyát ad vissza az alapján hogy mi van a kezében, az előző játékos huzott-e és van e színkötelezettség
	
	
return null;
	
}



}