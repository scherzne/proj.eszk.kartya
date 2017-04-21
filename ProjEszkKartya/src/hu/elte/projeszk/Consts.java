package hu.elte.projeszk;

/**
 * Konstansok, például kommunikáciüós port, játékosok száma, időzítések
 * @author scherzne
 *
 */
public class Consts {
	/**
	 * Szerver port
	 */
	public static final int PORT=65456;
	/**
	 * A játék elindulhat ha legalább ennyi játékos van már és START_GAME_TIMEOUT-on túl senki nem csatlakozott
	 * azután csatlakozók következő körben jönnek.
	 */
	public static final int MIN_PLAYERS=2;
	public static final int MAX_PLAYERS=10;

	/**
	 * Másodpercben ennyit várunk, hogy a játék elindulhasson
	 */
	public static final int START_GAME_TIMEOUT=60;
	/**
	 * Másodpercben. Ha valaki ennyi inaktivitás után nem csinál semmit le kell dobni.
	 */
	public static final int INACTIVE_TIMEOUT=60*5;
	
	/**
	 * Megegyezés alapján a kliens-szerver üzenetek kezdő karakterének konstansai
	 */
	
	/**
	 * Lapot 'A'd a szerver
	 */
	public static final char SEND_CARD='A';
	/**
	 * 'L'apot kér a kliens
	 */
	public static final char REQUEST_CARD='L';
	/**
	 * 'S'zín kérésre válaszolunk
	 */
	public static final char SEND_COLOR='S';
	/**
	 * 'N'em tudunk lapot rakni
	 */
	public static final char NO_CARD='N';
	/**
	 * Játékos nevének 'B'ekérése
	 */
	public static final char REQUEST_NAME='B';
	/**
	 * Szerver küld egy 'M'essage-t
	 */
	public static final char SEND_MESSAGE='M';
}
