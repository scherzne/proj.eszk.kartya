package hu.elte.projeszk;


import hu.elte.projeszk.Card.CardValue;;
/**
 * 
 * @deprecated áthelyezve a két metódus a Card osztályba!
 * Logikailag nem jó itt!
 *
 */
public class StaticCardValueChar {
public static CardValue convertCharacterToCardValue(char c){
		
		CardValue cardValue=null;
		
		//switch elágazással
		
		
	 switch(c){
	 
	 case('0'): cardValue= CardValue.NULLA;break;
	 case('1'): cardValue= CardValue.EGY;break;
	 case('2'): cardValue= CardValue.KETTO;break;
	 case('3'): cardValue= CardValue.HAROM;break;
	 case('4'): cardValue= CardValue.NEGY;break;
	 case('5'): cardValue= CardValue.OT;break;
	 case('6'): cardValue= CardValue.HAT;break;
	 case('7'): cardValue= CardValue.HET;break;
	 case('8'): cardValue= CardValue.NYOLC;break;
	 case('9'): cardValue= CardValue.KILENC;break;
	
	 case('H'): cardValue= CardValue.HUZZKETTOT;break;
	 case('K'): cardValue= CardValue.KIMARADSZ;break;
	 case('M'): cardValue= CardValue.FORDITTO;break;
	 
	 case('S'): cardValue= CardValue.SZINKEREO;break;
	 case('N'): cardValue= CardValue.HUZZNEGYET;break;
	 }
		return cardValue;
	}
	
	
public static char convertCardValueToCharacter(CardValue cardValue){
		
		char c=' ';
		
		 switch(cardValue){
		 
		 case NULLA:  c='0';break;
		 case EGY:    c='1'; break;
		 case KETTO:  c='2';break;
		 case HAROM:  c='3';break;
		 case NEGY:   c='4';break;
		 case OT:     c='5';break;
		 case HAT:    c='6';break;
		 case HET:    c='7';break;
		 case NYOLC:  c='8';break;
		 case KILENC: c='9';break;
		 
		 case HUZZKETTOT:  c='H';break;
		 case FORDITTO:    c='M';break;
		 case KIMARADSZ:   c='K';break;
		 
		 case SZINKEREO:   c='S';break;
		 case HUZZNEGYET:  c='N';break;
		
		 
		 }
		//switch elágazással
		return c;
	}
}
