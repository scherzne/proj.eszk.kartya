package hu.elte.projeszk;

import  hu.elte.projeszk.Card.CardColor;

public class StaticCardColorChar {
	
	public static CardColor convertCharacterToCardColor(char c){
		
		CardColor cardColor=null;
		
		//switch elágazással
		
		
	 switch(c){
	 
	 case('P'): cardColor= CardColor.PIROS;break;
	 case('K'): cardColor= CardColor.KEK;break;
	 case('Z'): cardColor= CardColor.ZOLD;break;
	 case('S'): cardColor= CardColor.SARGA;break;
	 case('F'): cardColor= CardColor.FEKETE;break;
	 }
		return cardColor;
	}
	
	
public static char convertCardColorToCharacter(CardColor cardColor){
		
		char c=' ';
		
		 switch(cardColor){
		 
		 case PIROS:  c='P';break;
		 case KEK:  c='K'; break;
		 case ZOLD: c='Z';break;
		 case SARGA:  c='S';break;
		 case FEKETE:  c='F';break;
		 }
		//switch elágazással
		return c;
	}

}
