package hu.elte.projeszk;


import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


public class CardTest {
    
    Card red6;
    Card red6String;
    Card red6Int;
    
    @Before
    public void setUpClass() {
        this.red6 = new Card(Card.CardColor.PIROS, Card.CardValue.HAT);
        this.red6String = new Card("P6");
        this.red6Int = new Card(3, 6);
    }

    /**
     * A háromfajta konstruktor tesztelése, hogy valóban azt hozza-e létre, amit akarunk.
     */
    @Test
    public void testCardConstructors() {
        assertEquals(Card.CardColor.PIROS, red6.getCardColor());
        assertEquals(Card.CardColor.PIROS, red6String.getCardColor());
        assertEquals(Card.CardColor.PIROS, red6Int.getCardColor());
        
        
        assertEquals(Card.CardValue.HAT, red6.getCardValue());
        assertEquals(Card.CardValue.HAT, red6String.getCardValue());
        assertEquals(Card.CardValue.HAT, red6Int.getCardValue());
    }

    /**
     * CardColor-ból integer konvertálás tesztelése.
     */
    @Test
    public void testCardColorToInt() {
        assertEquals(4, Card.cardColorToInt(Card.CardColor.FEKETE));
        assertEquals(1, Card.cardColorToInt(Card.CardColor.KEK));
        assertEquals(2, Card.cardColorToInt(Card.CardColor.ZOLD));
        assertEquals(-1, Card.cardColorToInt(Card.CardColor.INVALID));
    }
    
    
    /**
     * Integer-ből CardColor konvertálás tesztelése.
     */
    @Test
    public void testIntToCardColor() {
        assertEquals(Card.CardColor.FEKETE, Card.intToCardColor(4));
        assertEquals(Card.CardColor.PIROS, Card.intToCardColor(3));
        assertEquals(Card.CardColor.SARGA, Card.intToCardColor(0));
        assertEquals(Card.CardColor.INVALID, Card.intToCardColor(446));
    }
    
    

    /**
     * Integer-ből CardValue konvertálás tesztelése.
     */
    @Test
    public void testIntToCardValue() {
        assertEquals(Card.CardValue.HUZZNEGYET, Card.intToCardValue(14));
        assertEquals(Card.CardValue.HAROM, Card.intToCardValue(3));
        assertEquals(Card.CardValue.NULLA, Card.intToCardValue(0));
        assertEquals(Card.CardValue.INVALID, Card.intToCardValue(446));
    }

    /**
     * CardValue-ból integer konvertálás tesztelése.
     */
    @Test
    public void testCardValueToInt() {
        assertEquals(0, Card.cardValueToInt(Card.CardValue.NULLA));
        assertEquals(10, Card.cardValueToInt(Card.CardValue.HUZZKETTOT));
        assertEquals(3, Card.cardValueToInt(Card.CardValue.HAROM));
        assertEquals(-1, Card.cardValueToInt(Card.CardValue.INVALID));
    }

    /**
     * Karakterből CardColor-ra konvertálás tesztelése.
     */
    @Test
    public void testConvertCharacterToCardColor() {
        assertEquals(Card.CardColor.FEKETE, Card.convertCharacterToCardColor('F'));
        assertEquals(null, Card.convertCharacterToCardColor('X'));
        assertEquals(Card.CardColor.KEK, Card.convertCharacterToCardColor('K'));
        assertEquals(Card.CardColor.ZOLD, Card.convertCharacterToCardColor('Z'));
    }

    /**
     * CardColor-ról karakterre konvertálás tesztelése.
     */
    @Test
    public void testConvertCardColorToCharacter() {
        assertEquals('S', Card.convertCardColorToCharacter(Card.CardColor.SARGA));
        assertEquals('P', Card.convertCardColorToCharacter(Card.CardColor.PIROS));
        assertEquals('F', Card.convertCardColorToCharacter(Card.CardColor.FEKETE));
    }

    /**
     * Karakterből CardValue-ra konvertálás tesztelése.
     */
    @Test
    public void testConvertCharacterToCardValue() {
        assertEquals(Card.CardValue.OT, Card.convertCharacterToCardValue('5'));
        assertEquals(Card.CardValue.HUZZKETTOT, Card.convertCharacterToCardValue('H'));
        assertEquals(null, Card.convertCharacterToCardValue('Q'));
        assertEquals(Card.CardValue.KIMARADSZ, Card.convertCharacterToCardValue('K'));
    }

    /**
     * CardValue-ról karakterre konvertálás tesztelése.
     */
    @Test
    public void testConvertCardValueToCharacter() {
        assertEquals('3', Card.convertCardValueToCharacter(Card.CardValue.HAROM));
        assertEquals('H', Card.convertCardValueToCharacter(Card.CardValue.HUZZKETTOT));
        assertEquals('9', Card.convertCardValueToCharacter(Card.CardValue.KILENC));
        assertEquals('S', Card.convertCardValueToCharacter(Card.CardValue.SZINKEREO));
    }

    /**
     * String bemenetből új kártya létrehozásának tesztelése.
     */
    @Test
    public void testParseCardFromString() {
        assertEquals(new Card(Card.CardColor.PIROS, Card.CardValue.NEGY).getCardColor(), Card.parseCardFromString("P4").getCardColor());
        assertEquals(new Card(Card.CardColor.KEK, Card.CardValue.HUZZKETTOT).getCardColor(), Card.parseCardFromString("KH").getCardColor());
        assertEquals(new Card(Card.CardColor.SARGA, Card.CardValue.OT).getCardColor(), Card.parseCardFromString("S5").getCardColor());
        
        assertEquals(new Card(Card.CardColor.PIROS, Card.CardValue.NEGY).getCardValue(), Card.parseCardFromString("P4").getCardValue());
        assertEquals(new Card(Card.CardColor.KEK, Card.CardValue.HUZZKETTOT).getCardValue(), Card.parseCardFromString("KH").getCardValue());
        assertEquals(new Card(Card.CardColor.SARGA, Card.CardValue.OT).getCardValue(), Card.parseCardFromString("S5").getCardValue());
        
        
    }
    
}
