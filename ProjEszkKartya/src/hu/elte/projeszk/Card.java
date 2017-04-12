package hu.elte.projeszk;

/**
 * Kártya objektum, adat küldés egyszerűsítése miatt csak int-ek határozzák meg
 * a fajtáját.
 * @author betti-laptop
 *
 */
public class Card {
	private int color;
	private int value;
	
	public Card(int color, int value){
		this.color=color;
		this.value=value;
	}

	public int getColor() {
		return color;
	}

	public int getValue() {
		return value;
	}
	
	
}
