package hu.elte.projeszk;

import static org.junit.Assert.*;

import org.junit.Test;

import  hu.elte.projeszk.Card.CardColor;
import  hu.elte.projeszk.Card.CardValue;
import  hu.elte.projeszk.Card;
import org.junit.runners.Parameterized.Parameters; 

public class ClientMachineTest {
	  Card card1= new Card(CardColor.PIROS,CardValue.HAT);
	  Card card2=new Card(CardColor.PIROS,CardValue.HAT);

	
	  
	@Test
    public void clientMachineHandTestAddAndRemoveCardMethods() {
		
	    ClientMachine clientMachineTester = new ClientMachine(); // MyClass is tested
	   
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
		
	    ClientMachine clientMachineTester = new ClientMachine(); // MyClass is tested
	   
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
		
		assertEquals("Thehere is no SARGA numbered cards",null, clientMachineTester.searchAnyNumberOfCertainColor(CardColor.SARGA));
		
	}
	
	
	
}
