package hu.elte.projeszk;

import static org.junit.Assert.*;

import org.junit.Test;

import  hu.elte.projeszk.Card;
import  hu.elte.projeszk.Card.CardColor;
import hu.elte.projeszk.Card.CardValue;


public class TestCardClass {
	
	 final CardColor cardColor = CardColor.F;
	 final CardValue cardValue = CardValue.HAROM;
	 
	  @Test
	    public void CardContructorTest() {
		  
		   
		  Card myCard = new Card(cardColor,cardValue);

	       // String result = myUnit.concatenate("one", "two");
	       // assertEquals("onetwo", result);
		  
		  assertEquals(CardColor.F ,myCard.getCardColor());
		  assertEquals(CardValue.HAROM ,myCard.getCardValue());
		  
		  myCard = new Card(cardColor,CardValue.SZINKEREO);
		  assertEquals(CardValue.SZINKEREO ,myCard.getCardValue());
		  
		//  assertEquals(CardValue.NEGY ,myCard.getCardValue());
	  }
}
