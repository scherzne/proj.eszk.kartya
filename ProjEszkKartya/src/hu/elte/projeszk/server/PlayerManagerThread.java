package hu.elte.projeszk.server;

import hu.elte.projeszk.Card;
import hu.elte.projeszk.Card.CardColor;
import hu.elte.projeszk.Card.CardValue;

import java.util.ArrayList;

public class PlayerManagerThread extends Thread {
	private ArrayList<Card> cardPack=new ArrayList<>();
	private int id=-1;	
	
	public PlayerManagerThread(int id) {
		super("manager "+id);
		this.id=id;
		
		generatePack();
	}
	
	/**
	 * Kártyapakli generálása
	 */
	private void generatePack(){
		Card card;
		
		for(int c=CardColor.SARGA.ordinal();c<CardColor.FEKETE.ordinal();c++){
			card=new Card(c, Card.cardValueToInt(CardValue.NULLA));
			cardPack.add(card);
			for(int i=CardValue.EGY.ordinal();i<CardValue.FORDITTO.ordinal();i++){
				card=new Card(c, i);
			}
		}
	}
}
