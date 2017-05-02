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
		
		clientMachineTester = new ClientMachine();
	   
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
		
		 clientMachineTester = new ClientMachine();
	   
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
		
		
		 clientMachineTester = new ClientMachine();
	
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
		
		 clientMachineTester = new ClientMachine();
		
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
		 
		    clientMachineTester = new ClientMachine();
		 
		    clientMachineTester.addCardToHand(new Card(CardColor.ZOLD,CardValue.HAT));
		    clientMachineTester.addCardToHand(new Card(CardColor.KEK,CardValue.KILENC));
		    clientMachineTester.addCardToHand(new Card(CardColor.ZOLD,CardValue.FORDITTO));
		    clientMachineTester.addCardToHand(new Card(CardColor.FEKETE,CardValue.HUZZNEGYET));
		   
		    assertEquals("There is no SZINKERO ",null, clientMachineTester.searchJokerCard());
		   
		    clientMachineTester.addCardToHand(new Card(CardColor.FEKETE,CardValue.SZINKEREO));
		    
		    assertEquals("There is now one SZINKERO ",CardValue.SZINKEREO, clientMachineTester.searchJokerCard().getCardValue());
			 
		    clientMachineTester.removeCardFromHand(clientMachineTester.searchJokerCard());
			  
		   
		    assertEquals("There is no SZINKERO again ",null, clientMachineTester.searchJokerCard());
			  
		    
	}
	
	
	@Test
    public void clientMachineHandTestSearchDrawFourJoker() {
		 
		    clientMachineTester = new ClientMachine();
		 
		    clientMachineTester.addCardToHand(new Card(CardColor.ZOLD,CardValue.HAT));
		    clientMachineTester.addCardToHand(new Card(CardColor.KEK,CardValue.KILENC));
		    clientMachineTester.addCardToHand(new Card(CardColor.ZOLD,CardValue.FORDITTO));
		    clientMachineTester.addCardToHand(new Card(CardColor.FEKETE,CardValue.HUZZNEGYET));
		   
		    assertEquals("There is one HUZZNEGYET ",CardValue.HUZZNEGYET, clientMachineTester.searchDrawFourJoker().getCardValue());
		   
		    clientMachineTester.removeCardFromHand(clientMachineTester.searchDrawFourJoker());
			     
		    assertEquals("The  HUZZNEGYET is removed from hand ",null, clientMachineTester.searchDrawFourJoker());
			  
		    
	}
	
	@Test
    public void clientMachineHandTestMachineCardChooseAlgorithm_First_Case() {
		//Az elso eset amikor a legfelso eldobott lap (top card) csak szam, elöttünk lévő játékos rakta, és nincs színkényszer
		
		    clientMachineTester = new ClientMachine();
	
		    Card  expectCard1 = new Card(CardColor.ZOLD,CardValue.HAT);
		    Card  expectCard2 = new Card(CardColor.KEK,CardValue.KILENC);
		    
		    clientMachineTester.addCardToHand(expectCard1);
		    clientMachineTester.addCardToHand(new Card(CardColor.ZOLD,CardValue.FORDITTO));
		    clientMachineTester.addCardToHand(expectCard2);
		    clientMachineTester.addCardToHand(new Card(CardColor.FEKETE,CardValue.HUZZNEGYET));
		    clientMachineTester.addCardToHand(new Card(CardColor.PIROS,CardValue.FORDITTO));
		    
	
		    Card topCard =  new Card(CardColor.ZOLD,CardValue.KILENC);
		    assertEquals("If top card is ZOLD KILENC the return card should be ZOLD HAT", expectCard1,  clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE));
	
		    topCard =   new Card(CardColor.SARGA,CardValue.HAT);
		    assertEquals("If top card is SARGA HAT the return card should be ZOLD HAT", expectCard1,  clientMachineTester.machineCardChooseAlgorithm(topCard,false,CardColor.FEKETE));
			
		    topCard =  new Card(CardColor.PIROS,CardValue.KILENC);
		    assertEquals("If top card is PIROS KILENC the return card should be KEK KILENC", expectCard2,clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE));
		    
		    clientMachineTester.removeCardFromHand(expectCard2);
		    assertEquals("If top card is PIROS KILENC and after removed is KEK KILENC the return card should be PIROS FORDITTO", CardValue.FORDITTO, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE).getCardValue());
			 
		    
		    clientMachineTester.removeCardFromHand(clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE));
		    assertEquals("If top card is PIROS KILENC and after removed is PIROS FORDITTO the return card should be FEKETE HUZZNEGYET", CardValue.HUZZNEGYET, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE).getCardValue());
			
		    clientMachineTester.removeCardFromHand(clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE));
		    clientMachineTester.addCardToHand(new Card(CardColor.PIROS,CardValue.SZINKEREO));
		    assertEquals("If top card is PIROS KILENC and after removed is HUZZNEGYET  the return card should be FEKETE SZINKERO", CardValue.SZINKEREO, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE).getCardValue());
			
		    clientMachineTester.removeCardFromHand(clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE));
		    assertEquals("If top card is PIROS KILENC and after removed is HUZZNEGYET  the return card should be null", null, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE));
			 
		    
		    
	}
	
	
	@Test
    public void clientMachineHandTestMachineCardChooseAlgorithm_Second_Case() {
		//Az elso eset amikor a legfelso eldobott lap szivatos kártya, elöttünk lévő játékos rakta, és nincs színkényszer
	    clientMachineTester = new ClientMachine();
		
	    Card  expectCard1 = new Card(CardColor.FEKETE,CardValue.SZINKEREO);
	    Card  expectCard2 = new Card(CardColor.KEK,CardValue.HUZZKETTOT);
	    Card  expectCard3 = new Card(CardColor.FEKETE,CardValue.HUZZNEGYET);
	   
	    
	    //clientMachineTester.addCardToHand(expectCard1);
	    clientMachineTester.addCardToHand(new Card(CardColor.ZOLD,CardValue.FORDITTO));
	 //   clientMachineTester.addCardToHand(expectCard2);
	    clientMachineTester.addCardToHand(new Card(CardColor.KEK,CardValue.HAROM));
	    clientMachineTester.addCardToHand(new Card(CardColor.PIROS,CardValue.KILENC));
	    clientMachineTester.addCardToHand(new Card(CardColor.SARGA,CardValue.KIMARADSZ));
	    clientMachineTester.addCardToHand(new Card(CardColor.KEK,CardValue.NULLA));
	    clientMachineTester.addCardToHand(expectCard1);
		
	    
	     Card topCard =   new Card(CardColor.SARGA,CardValue.HUZZKETTOT); 
	     assertEquals("If top card is KEK HUZZKETTOT  the return card should be null", null, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE));
	     topCard =   new Card(CardColor.FEKETE,CardValue.HUZZNEGYET);
	     assertEquals("If top card is FEKETE HUZZNEGYET  the return card should be null", null, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE));
		
		
	     topCard =   new Card(CardColor.FEKETE,CardValue.SZINKEREO);
	     assertEquals("If top card is FEKETE SZINKERO   the return card should be FEKETE SZINKERO  ", expectCard1, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE));
		
	     topCard =   new Card(CardColor.SARGA,CardValue.HUZZKETTOT);
	     clientMachineTester.addCardToHand(expectCard3);
	     clientMachineTester.addCardToHand(expectCard2);
	     assertEquals("If top card is SARGA HUZZKETTOT   the return card should be KEK HUZZKETTO  ", expectCard2, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE));
	    
	     clientMachineTester.removeCardFromHand(expectCard2);
	     assertEquals("If top card is SARGA HUZZKETTOT   the return card should be FEKETE  HUZZNEGYET  ", expectCard3, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE));
		   
	     
	     topCard =   new Card(CardColor.FEKETE,CardValue.HUZZNEGYET);
	     assertEquals("If top card is FEKETE HUZZNEGYET   the return card should be FEKETE HUZZNEGYET  ", expectCard3, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.FEKETE));
			
	     
	     
	    
	}
	
	@Test
    public void clientMachineHandTestMachineCardChooseAlgorithm_Third_Case() {
		//A harmdaik eset amikor a legfelso eldobott lap szam , elöttünk lévő játékos rakta, és VAN színkényszer
		//Mivel a színkényszer csak  darab játékosra vonatkozik ezért ha az elözö játékos lapott rakott az csak PluszNégyes v színkérő lehet 
		clientMachineTester = new ClientMachine();
		
	    Card  expectCard1 = new Card(CardColor.FEKETE,CardValue.SZINKEREO);
	    Card  expectCard2 = new Card(CardColor.SARGA,CardValue.NULLA);	  
	    Card  expectCard3 = new Card(CardColor.SARGA,CardValue.HUZZKETTOT);
	    Card  expectCard4 = new Card(CardColor.FEKETE,CardValue.HUZZNEGYET);
	   
	    
	    clientMachineTester.addCardToHand(new Card(CardColor.PIROS,CardValue.HUZZKETTOT));
	    clientMachineTester.addCardToHand(new Card(CardColor.ZOLD,CardValue.FORDITTO));
	    clientMachineTester.addCardToHand(new Card(CardColor.KEK,CardValue.HAROM));
	    clientMachineTester.addCardToHand(new Card(CardColor.PIROS,CardValue.KILENC));
	     clientMachineTester.addCardToHand(new Card(CardColor.KEK,CardValue.NULLA));
	//    clientMachineTester.addCardToHand(expectCard1);
		
	    
	     Card topCard =   new Card(CardColor.FEKETE,CardValue.SZINKEREO); 
	     assertEquals("If top card is FEKETE SZINKEREO  the return card should be null", null, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.SARGA));
	   
	  
	     clientMachineTester.addCardToHand(expectCard2);
	     assertEquals("If top card is FEKETE SZINKEREO  the return card should be SARGA NULLA", expectCard2, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.SARGA));
	   
	     clientMachineTester.addCardToHand(expectCard3);
	     assertEquals("If top card is FEKETE SZINKEREO  the return card should be SARGA NULLA", expectCard2, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.SARGA));
	    
	     clientMachineTester.removeCardFromHand(expectCard2);
	     assertEquals("If top card is FEKETE SZINKEREO  the return card should be SARGA HUZZKETTOT", expectCard3, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.SARGA));
		    
	     
	     
	     //forditott sorrendben is a szamot adja elsonek vissza
	     clientMachineTester.removeCardFromHand(expectCard2);
	     clientMachineTester.removeCardFromHand(expectCard3);
	     clientMachineTester.addCardToHand(expectCard3);
	     clientMachineTester.addCardToHand(expectCard2);
	     assertEquals("If top card is FEKETE SZINKERO  the return card should be SARGA NULLA", expectCard2, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.SARGA));
	  
   
	     clientMachineTester.removeCardFromHand(expectCard2);
	     clientMachineTester.removeCardFromHand(expectCard3);
	     assertEquals("If top card is FEKETE SZINKERO  the return card should be null", null, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.SARGA));
		  
	     //HA plussznegy és szinkero IS van a kezben elsonek a szinkerot adja vissza
	     clientMachineTester.addCardToHand(expectCard4);
	     clientMachineTester.addCardToHand(expectCard1);
	     assertEquals("If top card is FEKETE SZINKERO  the return card should be FEKETE SZINKERO", expectCard1, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.SARGA));
			
	     //HA csak plussznegy  van a kezben elsonek azt  adja vissza
	     clientMachineTester.removeCardFromHand(expectCard1);
	     assertEquals("If top card is FEKETE SZINKERO  the return card should be FEKETE HUZZNEGYET", expectCard4, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.SARGA));
			
	      topCard =   new Card(CardColor.FEKETE,CardValue.HUZZNEGYET);
	     
	      clientMachineTester.addCardToHand(expectCard3);
		  clientMachineTester.addCardToHand(expectCard2);
		  clientMachineTester.addCardToHand(expectCard1);
		  assertEquals("If top card is FEKETE HUZZNEGYET  the return card should be null", expectCard4, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.SARGA));
			
		  clientMachineTester.removeCardFromHand(expectCard4);
		  assertEquals("If top card is FEKETE HUZZNEGYET  the return card should be null", null, clientMachineTester.machineCardChooseAlgorithm( topCard,false,CardColor.SARGA));
			
		  
	    
	}
	
	
	
	
	
}
