package hu.elte.projeszk;

import static org.junit.Assert.*;

import org.junit.Test;

import  hu.elte.projeszk.Card;
import  hu.elte.projeszk.Card.CardColor;
import  hu.elte.projeszk.Card.CardValue;
import  hu.elte.projeszk.StaticCardColorChar;

import  hu.elte.projeszk.StaticCardValueChar;

public class TestCardClass {
	
	 final CardColor cardColor = CardColor.KEK;
	 final CardValue cardValue = CardValue.HAROM;
	 
	  @Test
	    public void CardContructorTest() {
		  
		   
		  Card myCard = new Card(cardColor,cardValue);

	  // TESZT KÁRTYA PÉLDÁNYOSÍTÁSRA
		  assertEquals(CardColor.KEK,myCard.getCardColor());
		  assertEquals(CardValue.HAROM ,myCard.getCardValue());
		  
		//  assertEquals(CardValue.NEGY ,myCard.getCardValue());
		  
		  // TESZT KAREKTERRŐL ENUMMÁ ÁTALAKÍTÁS - SZÍN
		   assertEquals(StaticCardColorChar.convertCharacterToCardColor('P'),CardColor.PIROS);
		   assertEquals(StaticCardColorChar.convertCharacterToCardColor('K'),CardColor.KEK);
			 
		   // TESZT ENUMBÓL KARAKTERRÉ ÁTALAKÍTÁS - SZÍN
		   assertEquals(StaticCardColorChar.convertCardColorToCharacter(CardColor.FEKETE),'F');
		   assertEquals(StaticCardColorChar.convertCardColorToCharacter(CardColor.ZOLD),'Z');
			
		   // TESZT KÁRTYA PÉDÁNYOSÍTÁSA KARAKTERBŐL ENUMMÁ ALAKÍTÁSSAL
		   Card myCard2 = new Card(StaticCardColorChar.convertCharacterToCardColor('S'),cardValue);
		   assertEquals(myCard2.getCardColor(),CardColor.SARGA);
			
		   // TESZT ENUMBÓL KARAKTERRÉ ÁTALAKÍTÁS - ÉRTÉK
		   assertEquals(StaticCardValueChar.convertCardValueToCharacter(CardValue.HUZZKETTOT),('H'));
		   assertEquals(StaticCardValueChar.convertCardValueToCharacter(CardValue.KILENC),('9'));
		   assertEquals(StaticCardValueChar.convertCardValueToCharacter(CardValue.NULLA),('0'));
			 
		  
		   // TESZT KAREKTERRŐL ENUMMÁ ÁTALAKÍTÁS - ÉRTÉK
		   assertEquals(StaticCardValueChar.convertCharacterToCardValue('N'),(CardValue.HUZZNEGYET));
		   assertEquals(StaticCardValueChar.convertCharacterToCardValue('3'),(CardValue.HAROM));
		   assertEquals(StaticCardValueChar.convertCharacterToCardValue('S'),(CardValue.SZINKEREO));
		   
		   // TESZT KÁRTYA PÉDÁNYOSÍTÁSA KARAKTERBŐL ENUMMÁ ALAKÍTÁSSAL, ÉRTÉK és SZÍN SZERINT
		    myCard2 = new Card(StaticCardColorChar.convertCharacterToCardColor('F'),StaticCardValueChar.convertCharacterToCardValue('S'));
		    assertEquals(myCard2.getCardColor(),CardColor.FEKETE);
		    assertEquals(myCard2.getCardValue(),CardValue.SZINKEREO);
		   
		   myCard2 = new Card(StaticCardColorChar.convertCharacterToCardColor('S'),StaticCardValueChar.convertCharacterToCardValue('2'));
		   assertEquals(myCard2.getCardColor(),CardColor.SARGA);
		   assertEquals(myCard2.getCardValue(),CardValue.KETTO);
		   
		   // TESZT KÁRTYA PÉDÁNYOSÍTÁSA KARAKTERBŐL ENUMMÁ ALAKÍTÁSSAL, ÉRTÉK és SZÍN SZERINT
			 
		   
		   char  charValue = '7';
		   char charColor = 'P';
		   myCard2 = new Card(StaticCardColorChar.convertCharacterToCardColor(charColor),StaticCardValueChar.convertCharacterToCardValue(charValue));
		   assertEquals(myCard2.getCardColor(),CardColor.PIROS);
		   assertEquals(myCard2.getCardValue(),CardValue.HET);
		   
		     charColor = 'F';
		     charValue = 'S';
		   myCard2 = new Card(StaticCardColorChar.convertCharacterToCardColor(charColor),StaticCardValueChar.convertCharacterToCardValue(charValue));
		   assertEquals(myCard2.getCardColor(),CardColor.FEKETE);
		   assertEquals(myCard2.getCardValue(),CardValue.SZINKEREO);
		   
		   
		   
	  }
}
