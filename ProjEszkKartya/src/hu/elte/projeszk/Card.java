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
	private int cardValueInt;
	
	
	public Card(CardColor cardcolor, CardValue cardValue){
		this.cardValue=cardValue;
		this.cardColor=cardcolor;
		this.cardValueInt=setCardValue(this.cardValue);
		
		

	}
	
	private int setCardValue( CardValue cardValue){
		
		int value=-1;	
		
		switch (cardValue){
		case NULLA:  	value=0; break;
		case EGY:  	 	value=1; break;
		case KETTO: 	value=2; break;
		case HAROM:  	value=3; break;
		case NEGY:   	value=4; break;
		case OT:     	value=5; break;
		case HAT:    	value=6; break;
		case HET:   	value=7; break;
		case NYOLC: 	value=8; break;
		case KILENC: 	value=9; break;
		
		case HUZZKETTOT:value=10; break;
		case FORDITTO:  value=11; break;
		case KIMARADSZ: value=12; break;
		case SZINKEREO: value=13; break;
		case HUZZNEGYET:value=14; break;
		
		
		default:	break;
			
		}		
		
		
		return value;
	}
	
	public void CardColorSetCardColor(  CardColor cardColor){
		
		//....
		
	}
	
	public CardColor getCardColor() {
		return cardColor;
	}
	
	public void CardColorgetCardColor(){
		
		
		//....
		
	}
	
	public CardValue getCardValue() {
		return cardValue;
	}


	public int getValue() {
		return cardValueInt;
	}
	
	
}
