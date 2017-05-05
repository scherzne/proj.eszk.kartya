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
	 * Egy kártya információt küld a szerver, például arról, hogy mi a felforgatott lap!
	 * Ezzel az információval a kliensnek is kell gazdálkodni!
	 * Tudni a kell a következő és a többi játékosnak, mit tett le az előző,
	 * és ezt nem csak szöveges üzenettel, hogy a kliens program is tudjon ellenőrizni! 
	 * Gépi játékosnak kellhet?
	 */
	public static final char CARD_INFORMATION='I';
	/**
	 * Lapot 'A'd a szerver vagy a kliens
	 */
	public static final char SEND_CARD='A';
	/**
	 * 'L'apot kér a kliens
	 */
	public static final char REQUEST_CARD='L';
	/**
	 * Színt kérünk is kell!!!
	 */
	public static final char REQUEST_COLOR='C';
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
	/**
	 * Üzenet részei közti elválasztó karakter
	 */
	public static final char MESSAGE_SEPARATOR=',';
	
	/**
	 * Konstans az üzenetben húzott/nem húzott
	 */
	public static final String HUZOTT="H";
	/**
	 * Konstans az üzenetben húzott/nem húzott
	 */
	public static final String NEM_HUZOTT="N";
	
	/**
	 * UNO szövege
	 */
	public static final String UNO="UNO";
}
