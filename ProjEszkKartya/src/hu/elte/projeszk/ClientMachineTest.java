package hu.elte.projeszk;

import static org.junit.Assert.*;

import org.junit.Test;

import  hu.elte.projeszk.Card.CardValue;
import  hu.elte.projeszk.Card.CardColor;

public class ClientMachineTest {


	@Test
    public void ClientMachineHandTestAddAndRemoveCardMethods() {
		
	    ClientMachine clientMachinetester = new ClientMachine(); // MyClass is tested
	   
	    clientMachinetester.addCardToHand(new Card (CardColor.PIROS,CardValue.HAT));
	    clientMachinetester.addCardToHand(new Card (CardColor.SARGA,CardValue.KILENC));
	    clientMachinetester.addCardToHand(new Card (CardColor.SARGA,CardValue.KIMARADSZ));
	    
	    
        // assert statements
        assertEquals("hand size must be 3 after ading 2 card", 3, clientMachinetester.hand.size());
      
        clientMachinetester.removeCardFromHand(new Card (CardColor.SARGA,CardValue.KILENC));
 	   
        assertEquals("hand size must be 2 after removing 1 card", 3, clientMachinetester.hand.size());
        
        assertEquals("first card of hand must be PIROS HAT", CardColor.PIROS, clientMachinetester.hand.get(0).getCardColor());
        assertEquals("first card of hand must be PIROS HAT", CardValue.HAT, clientMachinetester.hand.get(0).getCardValue());
        
        assertEquals("second card of hand must be SARGA KILENC", CardColor.SARGA, clientMachinetester.hand.get(0).getCardColor());
        assertEquals("second card of hand must be SARGA KILENC",  CardValue.KILENC, clientMachinetester.hand.get(0).getCardValue());
        
        
        
	}
	
}
