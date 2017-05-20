package hu.elte.projeszk;

/**
 * Egy UNO játékkártyát reprezentáló osztály.
 * 
 */
public class Card {
	/**
	* A kártyák lehetséges színeit reprezentáló enum.
	* 
	*/
	public enum CardColor {SARGA, KEK, ZOLD, PIROS,FEKETE, INVALID} ;
	/**
	* A kártyák lehetséges típusait reprezentáló enum.
	* 
	*/
	public enum CardValue {NULLA,EGY, KETTO, HAROM, NEGY, OT, HAT, HET, NYOLC,KILENC,FORDITTO,HUZZKETTOT,KIMARADSZ,SZINKEREO,HUZZNEGYET,INVALID} ;
	
	CardValue cardValue;
	CardColor cardColor;
	private int cardValueInt;
	
	/**
	 * CardColor és CardValue paramétert kérő konstruktor.
	 * @param cardColor kártya színe 
	 * @param cardValue kártya típusa
	 */
	public Card(CardColor cardcolor, CardValue cardValue){
		this.cardValue=cardValue;
		this.cardColor=cardcolor;
		this.cardValueInt=cardValueToInt(this.cardValue);
	}
	
	/**
	 * Két integerből kártyát létrehozó konstruktor.
	 * @param color színt reprezentáló szám
	 * @param value típust reprezentáló szám
	 */
	public Card(int color, int value){
		this.cardColor=intToCardColor(color);
		this.cardValue=intToCardValue(value);
		this.cardValueInt=value;
	}
	/**
	 * Stringből kártya előállítása konstruktor
	 * @param cardString bemeneti String pl.: P5
	 */
	public Card(String cardString){
		this.cardColor=convertCharacterToCardColor(cardString.charAt(0));
		this.cardValue=convertCharacterToCardValue(cardString.charAt(1));
		this.cardValueInt=cardValueToInt(this.cardValue);
	}
	/**
	 * CardColor-> Int konverzió
	 * @param color átalakítandó CardColor
	 * @return a megfelelő szám
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
	 * @param val átalakítandó szám
	 * @return a megfelelő CardColor
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
	 * @param val átalakítandó szám
	 * @return a megfelelő CardValue
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
	
	/**
	 * A kártya színe szövegesen, pl: Piros
	 * @return Egy szó, ami a kártya színe
	 */
	public String cardColorToString(){
		switch(cardColor){
			case SARGA:return "Sárga";
			case KEK:return "Kék";
			case PIROS:return "Piros";
			case ZOLD:return "Zöld";
			case FEKETE:return "Fekete";
			default:return "Érvénytelen";
		}
	}
	
	/**
	 * Kártya típusok szöveges reprezentációja, 
	 * csak szöveges üzenetekhez, hogy olvashatóak legyenek emberek számára is.
	 * @return Egy szó, ami a kártya típusa
	 */
	public String cardValueToString(){
		switch(cardValue){
			case NULLA: return "Nulla";
			case EGY: return "Egy";
			case KETTO: return "Kettő";
			case HAROM: return "Három";
			case NEGY: return "Négy";
			case OT: return "Öt";
			case HAT: return "Hat";
			case HET: return "Hét";
			case NYOLC: return "Nyolc";
			case KILENC: return "Kilenc";
			
			case HUZZKETTOT: return "Húzz kettőt!";
			case FORDITTO: return "Fordulj!";
			case KIMARADSZ: return "Ugorj!";
			case SZINKEREO: return "Joker!";
			case HUZZNEGYET: return "Húzz négyet!";
			default: return "Érvénytelen!";
		}
	}
	
	/**
	 * CardValue-> Int konverzió
	 * @param color átalakítandó CardValue
	 * @return a megfelelő szám
	 */
	public static int cardValueToInt( CardValue cardValue){
		
		int value=-1;	
	//
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

	/**
	 * Character -> CardColor konverzió
	 * @param c átalakítandó karakter
	 * @return a megfelelő CardColor
	 */
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

	/**
	 * CardColor -> Character konverzió
	 * @param cardColor átalakítandó CardColor
	 * @return a megfelelő karakter
	 */
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
	
	/**
	 * Character -> CardValue konverzió
	 * @param c átalakítandó karakter
	 * @return a megfelelő CardValue
	 */
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
	
	/**
	 * CardValue -> Character konverzió
	 * @param cardValue átalakítandó CardValue
	 * @return a megfelelő karakter
	 */
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

	/**
	 * Egy kártya string üzenet feldolgozása
	 * @param str pl: P4 , ami piros 4-est jelent
	 * @return egy kártyával vagy null-al ha nem tudta parsolni
	 */
	public static Card parseCardFromString(String str){
		if(str!=null){
			str=str.trim();
			if(str.length()==2){//ha már nem két karakter, tuti nem jó
				char c=str.charAt(0);
				char v=str.charAt(1);
				
				CardColor color=convertCharacterToCardColor(c);
				CardValue val=convertCharacterToCardValue(v);
				
				if(color!=null && val!=null){
					return new Card(color, val);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Kártya értéke stringként visszaadva
	 * @return kártyastring
	 */
	public String getCardAsString(){
		return ""+getCardColorAsChar()+getCardValueAsChar();
	}
	/**
	 * Karakterként visszaadja a kártyaszínt
	 * @return a kártya színe karakterként, pl: P,K,S...
	 */
	public char getCardColorAsChar(){
		return convertCardColorToCharacter(cardColor);
	}
	
	/**
	 * Karakterként visszaadja a kártyatípust
	 * @return kártya értéke karakterként, pl: 0,1,...
	 */
	public char getCardValueAsChar(){
		return convertCardValueToCharacter(cardValue);
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
