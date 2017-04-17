package hu.elte.projeszk;

/**
 * Sajnos enumok a színek és a számok, ezért nehezebb kezelni!
 * 
 */
public class Card {
	public enum CardColor {SARGA, KEK, ZOLD, PIROS,FEKETE, INVALID} ;
	public enum CardValue {NULLA,EGY, KETTO, HAROM, NEGY, OT, HAT, HET, NYOLC,KILENC,FORDITTO,HUZZKETTOT,KIMARADSZ,SZINKEREO,HUZZNEGYET,INVALID} ;
	
	CardValue cardValue;
	CardColor cardColor;
	private int cardValueInt;
	
	
	public Card(CardColor cardcolor, CardValue cardValue){
		this.cardValue=cardValue;
		this.cardColor=cardcolor;
		this.cardValueInt=cardValueToInt(this.cardValue);
	}
	
	/**
	 * Integerekkel is létre kell tudni hozni
	 * @param color 
	 * @param value
	 */
	public Card(int color, int value){
		this.cardColor=intToCardColor(color);
		this.cardValue=intToCardValue(value);
		this.cardValueInt=value;
	}
	
	/**
	 * CardColor->Int konverzió
	 * @param color
	 * @return
	 */
	public static int cardColorToInt(CardColor color){
		switch(color){
			case SARGA:return 0;
			case KEK:return 1;
			case ZOLD:return 2;
			case PIROS:return 3;
			case FEKETE:return 4;
			default:return -1;
		}
	}
	/**
	 * Integer->CardColor konverzió
	 * @param val
	 * @return
	 */
	public static CardColor intToCardColor(int val){
		switch(val){
			case 0:return CardColor.SARGA;
			case 1:return CardColor.KEK;
			case 2:return CardColor.ZOLD;
			case 3:return CardColor.PIROS;
			case 4:return CardColor.FEKETE;
			default:return CardColor.INVALID;
		}
	}
	
	/**
	 * Integer->CardValue konverzió
	 * @param val
	 * @return
	 */
	public static CardValue intToCardValue(int val){
		switch(val){
			case 0:return CardValue.NULLA;
			case 1:return CardValue.EGY;
			case 2:return CardValue.KETTO;
			case 3:return CardValue.HAROM;
			case 4:return CardValue.NEGY;
			case 5:return CardValue.OT;
			case 6:return CardValue.HAT;
			case 7:return CardValue.HET;
			case 8:return CardValue.NYOLC;
			case 9:return CardValue.KILENC;
			case 10:return CardValue.HUZZKETTOT;
			case 11:return CardValue.FORDITTO;
			case 12:return CardValue.KIMARADSZ;
			case 13:return CardValue.SZINKEREO;
			case 14:return CardValue.HUZZNEGYET;
			default:return CardValue.INVALID;
		}
	}
	
	public static int cardValueToInt( CardValue cardValue){
		
		int value=-1;	
		
		switch (cardValue){
		case NULLA:  	value=0; break;
		case EGY:  	 	value=1; break;
		case KETTO: 	value=2; break;
		case HAROM:  	value=3; break;
		case NEGY:   	value=4; break;
		case OT:     	value=5; break;
		case HAT:    	value=6; break;
		case HET:   	value=7; break;
		case NYOLC: 	value=8; break;
		case KILENC: 	value=9; break;
		
		case HUZZKETTOT:value=10; break;
		case FORDITTO:  value=11; break;
		case KIMARADSZ: value=12; break;
		case SZINKEREO: value=13; break;
		case HUZZNEGYET:value=14; break;
		
		
		default:	break;
			
		}		
		
		
		return value;
	}

	public CardColor getCardColor() {
		return cardColor;
	}
	
	public CardValue getCardValue() {
		return cardValue;
	}

	public int getValue() {
		return cardValueInt;
	}
	
	
}
