package hu.elte.projeszk;

import static org.junit.Assert.*;

import org.junit.Test;

import  hu.elte.projeszk.Card;
import  hu.elte.projeszk.Card.CardColor;
import  hu.elte.projeszk.Card.CardValue;
import  hu.elte.projeszk.StaticCardColorChar;

public class TestCardClass {
	
	 final CardColor cardColor = CardColor.KEK;
	 final CardValue cardValue = CardValue.HAROM;
	 
	  @Test
	    public void CardContructorTest() {
		  
		   
		  Card myCard = new Card(cardColor,cardValue);

	       // String result = myUnit.concatenate("one", "two");
	       // assertEquals("onetwo", result);
		  
		  assertEquals(CardColor.KEK,myCard.getCardColor());
		  assertEquals(CardValue.HAROM ,myCard.getCardValue());
		  
		//  assertEquals(CardValue.NEGY ,myCard.getCardValue());
		   assertEquals(StaticCardColorChar.convertCharacterToCardColor('P'),CardColor.PIROS);
		   assertEquals(StaticCardColorChar.convertCharacterToCardColor('K'),CardColor.KEK);
			 
		   assertEquals(StaticCardColorChar.convertCardColorToCharacter(CardColor.FEKETE),'F');
		   assertEquals(StaticCardColorChar.convertCardColorToCharacter(CardColor.ZOLD),'Z');
			
		   
		   Card myCard2 = new Card(StaticCardColorChar.convertCharacterToCardColor('S'),cardValue);
		   assertEquals(myCard2.getCardColor(),CardColor.SARGA);
			
		   
	  }
}
