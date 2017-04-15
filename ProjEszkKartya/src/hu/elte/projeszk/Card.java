package hu.elte.projeszk;

import  hu.elte.projeszk.Card.CardColor;
import  hu.elte.projeszk.Card.CardValue;

/**
 * Kártya objektum, adat küldés egyszerűsítése miatt csak int-ek határozzák meg
 * a fajtáját.
 * @author betti-laptop
 *
 */
public class Card {
	public enum CardColor {S, K, Z, P,F;} ;
	public enum CardValue {NULLA,EGY, KETTO, HAROM, NEGY, OT, HAT, HET, NYOLC,KILENC,FORDITTO,HUZZKETTOT,KIMARADSZ,SZINKEREO,HUZZNEGYET} ;
	
	CardValue cardValue;
	CardColor cardColor;
	private int value;
	
	
	public Card(CardColor cardcolor, CardValue cardValue){
		this.cardValue=cardValue;
		this.cardColor=cardcolor;
		
	//	switch (cardValue){
	//	case (0): break;
		
	//	}		

	}

	public CardColor getCardColor() {
		return cardColor;
	}
	
	public CardValue getCardValue() {
		return cardValue;
	}


	public int getValue() {
		return value;
	}
	
	
}
