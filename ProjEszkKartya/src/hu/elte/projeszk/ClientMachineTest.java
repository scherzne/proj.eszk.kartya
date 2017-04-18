package hu.elte.projeszk;

import static org.junit.Assert.*;

import org.junit.Test;

import  hu.elte.projeszk.Card.CardColor;
import  hu.elte.projeszk.Card.CardValue;
import  hu.elte.projeszk.Card;
import org.junit.runners.Parameterized.Parameters; 

public class ClientMachineTest {

	ClientMachine clientMachineTester = new ClientMachine();
	
	  
	@Test
    public void clientMachineHandTestAddAndRemoveCardMethods() {
		
	  
	   
	    clientMachineTester.addCardToHand(new Card (CardColor.PIROS,CardValue.HAT));
	    clientMachineTester.addCardToHand(new Card (CardColor.SARGA,CardValue.KILENC));
	    clientMachineTester.addCardToHand(new Card (CardColor.SARGA,CardValue.KIMARADSZ));
	    
	    
        // assert statements
        assertEquals("hand size must be 3 after ading 2 card", 3, clientMachineTester.hand.size());
      
        clientMachineTester.removeCardFromHand(new Card (CardColor.SARGA,CardValue.KILENC));
 	   
        assertEquals("hand size must be 2 after removing 1 card", 3, clientMachineTester.hand.size());
        
        assertEquals("first card of hand must be PIROS HAT", CardColor.PIROS, clientMachineTester.hand.get(0).getCardColor());
        assertEquals("first card of hand must be PIROS HAT", CardValue.HAT, clientMachineTester.hand.get(0).getCardValue());
        
        assertEquals("second card of hand must be SARGA KILENC", CardColor.SARGA, clientMachineTester.hand.get(1).getCardColor());
        assertEquals("second card of hand must be SARGA KILENC",  CardValue.KILENC, clientMachineTester.hand.get(1).getCardValue());
        
        
        
	}
	
	@Test
    public void clientMachineHandTestSearchSameColor0_9_Number() {
		
	   
	   
	    Card card1= new Card(CardColor.PIROS,CardValue.HAT);
	    Card card2= new Card(CardColor.KEK,CardValue.NULLA);
	    clientMachineTester.addCardToHand(card1);
	    clientMachineTester.addCardToHand(new Card(CardColor.ZOLD,CardValue.NYOLC));
	    clientMachineTester.addCardToHand(new Card(CardColor.KEK,CardValue.HUZZKETTOT));
	    clientMachineTester.addCardToHand(new Card(CardColor.KEK,CardValue.FORDITTO));
	    clientMachineTester.addCardToHand(new Card(CardColor.KEK,CardValue.KIMARADSZ));
	    clientMachineTester.addCardToHand(card2);
	    
	
	
		assertEquals("The first PIROS numbered card in the hand is PIROS HAT ",card1, clientMachineTester.searchAnyNumberOfCertainColor(CardColor.PIROS));
		
		assertEquals("The first KEK numbered card in the hand is KEK  NULLA ",card1, clientMachineTester.searchAnyNumberOfCertainColor(CardColor.PIROS));
		
		assertEquals("There is no SARGA numbered cards",null, clientMachineTester.searchAnyNumberOfCertainColor(CardColor.SARGA));
		
	}
	
	@Test
    public void clientMachineHandTestsearchCertainCardAnyColor() {
		
		
		
	
	    Card card1= new Card(CardColor.KEK,CardValue.FORDITTO);
	    
	    clientMachineTester.addCardToHand(new Card(CardColor.PIROS,CardValue.HAT));
	    clientMachineTester.addCardToHand(new Card(CardColor.ZOLD,CardValue.KETTO));
	    clientMachineTester.addCardToHand(new Card(CardColor.SARGA,CardValue.NYOLC));
	    clientMachineTester.addCardToHand(new Card(CardColor.ZOLD,CardValue.HAROM));
	    clientMachineTester.addCardToHand(new Card(CardColor.ZOLD,CardValue.NULLA));
	    clientMachineTester.addCardToHand(new Card(CardColor.SARGA,CardValue.HUZZKETTOT));
	    clientMachineTester.addCardToHand(new Card(CardColor.KEK,CardValue.NULLA));
	    clientMachineTester.addCardToHand(card1);
	    clientMachineTester.addCardToHand(new Card(CardColor.PIROS,CardValue.HUZZKETTOT));
	    
	    assertEquals("The first FORDITO  card in the hand is KEK FORDITO ",card1, clientMachineTester.searchCertainCardAnyColor(CardValue.FORDITTO));
		
		assertEquals("The first NULLA numbered card in the hand is ZOLD  NULLA ",CardColor.ZOLD, clientMachineTester.searchCertainCardAnyColor(CardValue.NULLA).getCardColor());
		
		assertEquals("The first HUZZKETTOT card in the hand is SARGA  HUZZKETTOT ",CardColor.SARGA, clientMachineTester.searchCertainCardAnyColor(CardValue.HUZZKETTOT).getCardColor());
		
		assertEquals("There is no KIMARADSZ card in the hand ",null, clientMachineTester.searchCertainCardAnyColor(CardValue.KIMARADSZ));
		
	    clientMachineTester.addCardToHand(new Card(CardColor.PIROS,CardValue.KIMARADSZ));
		
		assertNotEquals("There is now a KIMARADSZ card in the hand ",null, clientMachineTester.searchCertainCardAnyColor(CardValue.KIMARADSZ));
		
		assertEquals("The KIMARADSZ card is the PIROS KIMARADSZ ",CardColor.PIROS, clientMachineTester.searchCertainCardAnyColor(CardValue.KIMARADSZ).getCardColor());
		
	    
	}
	
	@Test
    public void clientMachineHandTestsearchSameColorNotNumber() {
		
		
		
	    Card card1= new Card(CardColor.KEK,CardValue.FORDITTO);
	    Card card2= new Card(CardColor.KEK,CardValue.HUZZKETTOT);
	  
	    clientMachineTester.addCardToHand(new Card(CardColor.KEK,CardValue.HAT));
	    clientMachineTester.addCardToHand(new Card(CardColor.KEK,CardValue.NULLA));
	    clientMachineTester.addCardToHand(new Card(CardColor.ZOLD,CardValue.KETTO));
	    clientMachineTester.addCardToHand(new Card(CardColor.SARGA,CardValue.NYOLC));
	    clientMachineTester.addCardToHand(card1);
	    clientMachineTester.addCardToHand(new Card(CardColor.KEK,CardValue.KIMARADSZ));
	    clientMachineTester.addCardToHand(new Card(CardColor.ZOLD,CardValue.NULLA));
	    clientMachineTester.addCardToHand(card2);
	
	    assertEquals("The KEK FORDITO card is the first not numbered KEK colored card ",card1, clientMachineTester.searchSameColorNotNumber(CardColor.KEK));
	    
	    clientMachineTester.removeCardFromHand(card1);
	    
	    assertEquals("The KEK KIMARADSZ card is the first not numbered KEK colored card ",CardValue.KIMARADSZ, clientMachineTester.searchSameColorNotNumber(CardColor.KEK).getCardValue());
		
	    clientMachineTester.removeCardFromHand(clientMachineTester.searchSameColorNotNumber(CardColor.KEK));
	    
	    assertEquals("The KEK HUZZKETTOT card is the first not numbered KEK colored card ",CardValue.HUZZKETTOT, clientMachineTester.searchSameColorNotNumber(CardColor.KEK).getCardValue());
		
	    clientMachineTester.removeCardFromHand(clientMachineTester.searchSameColorNotNumber(CardColor.KEK));
	     
	    assertEquals("There is no more KEK not-numbered card ",null, clientMachineTester.searchSameColorNotNumber(CardColor.KEK));
		
	}
	
	@Test
    public void clientMachineHandTestSearchJokerCard() {
		 
	
		
	}
	
	
}
