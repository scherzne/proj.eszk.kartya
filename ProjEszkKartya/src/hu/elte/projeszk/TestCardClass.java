package hu.elte.projeszk;

import static org.junit.Assert.*;

import org.junit.Test;

import  hu.elte.projeszk.Card;
import  hu.elte.projeszk.Card.CardColor;
import  hu.elte.projeszk.Card.CardValue;


public class TestCardClass {
	
	 final CardColor cardColor = CardColor.K;
	 final CardValue cardValue = CardValue.HAROM;
	 
	  @Test
	    public void CardContructorTest() {
		  
		   
		  Card myCard = new Card(cardColor,cardValue);

	       // String result = myUnit.concatenate("one", "two");
	       // assertEquals("onetwo", result);
		  
		  assertEquals(CardColor.K ,myCard.getCardColor());
		  assertEquals(CardValue.HAROM ,myCard.getCardValue());
		  
		//  assertEquals(CardValue.NEGY ,myCard.getCardValue());
	  }
}
