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
}
