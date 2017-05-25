package hu.elte.projeszk.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import hu.elte.projeszk.Card;
import hu.elte.projeszk.Card.CardColor;
import hu.elte.projeszk.Card.CardValue;
import hu.elte.projeszk.Consts;

/**
 * Ember jatekos osztaly
 * @author      Hagymasi Daniel
 */

public class ManualClient {
	/**
	 * BufferedReader socket olvasasra
	 */
	private BufferedReader br;
	/**
	 * PrintWriter socket irasra
	 */
	private PrintWriter pw;
	
	private Scanner sc;
	/**
	 * Scanner billentyuzetrol valo beolvasasra
	 */
	private Scanner kbScanner;
	/**
	 * Kliens nevenek tarolasa
	 */
	private String clientName;
	/**
	 * Kezben levo kartyak tarolasa
	 */
	private ArrayList<Card> cardsInHand;
	private boolean hasEnded;

	Socket socket = null;

	public static void main(String[] args) throws UnknownHostException, IOException {
		ManualClient client = new ManualClient();

		client.communicate();
	}

	
	/**
	 * ManualClient osztaly konstruktora
	 * Adattagok inicializálása
	 */
	public ManualClient() throws UnknownHostException, IOException {
		cardsInHand = new ArrayList<Card>();
		socket = new Socket("localhost", Consts.PORT);
		pw = new PrintWriter(socket.getOutputStream(), true);

		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		sc = new Scanner(socket.getInputStream());
		kbScanner = new Scanner(System.in);

		hasEnded = false;

	}

	/**
	 * Szerverrel valo kommunikacio biztositasa 
	 */
	public void communicate() throws IOException {

		// Szerver altal kuldott uzenet tarolasa
		String serverMessageString = "";
		BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));

		/**
		 * Addig kerjuk be a klienstol a nevet, amig ok valaszt
		 * nem kapunk a szervertol
		 */

	//	while (!serverMessageString.equals("ok")) {// nev bekerese
			
			
			serverMessageString = br.readLine();
			System.out.print("Emberi jatekos _____ A szerver uzenete: "+ serverMessageString);
			clientName = stdinReader.readLine();
			pw.println(clientName);
		
	//	}

		System.out.println("Emberi jatekos _____Sikeres csatlakozas.");

		try {
			do {
				String stringToWriteToMonitor = clientName + ": ";

				serverMessageString = br.readLine();
				System.out.print("Emberi jatekos _____ A szerver uzenete: "+ serverMessageString);
					
				/**
				 * A szerver lapokat ad, A karakter utan szamertek, amennyi lapot kapunk
				 *  Majd lapok "konvertalasa" es kezhez adasa
				 */
				if (serverMessageString.charAt(0) == 'A') {			
					
					 String[] splitted = serverMessageString.split(",");
		 
						for (String s:  splitted){
							
							
							if (((s.charAt(0))+"").equals(Consts.SEND_CARD+"") ){
								System.out.println("Emberi JAtekos_Lapot kaptal: " + s.charAt(1)+"darabot");
								
								
							}else{
								
								CardColor cColor = Card.convertCharacterToCardColor(s.charAt(0));
								CardValue cValue = Card.convertCharacterToCardValue(s.charAt(1));
							
								cardsInHand.add(new Card(cColor, cValue));
								System.out.println("Emberi JAtekos_Lapot kaptal: " + Card.convertCardColorToCharacter(cColor)
								+ Card.convertCardValueToCharacter(cValue));

				
							}
				}
				}			
				/**
				 * Szerver lapot ker vesszokkel elvalasztott sztring
				 *  2. resz: lap mindig az elozoleg eldobott lap
				 *  3. resz: rakott vagy nem az elozo jatekos
				 *  4. resz: szinkenyszer megadasa
				 */
				if (serverMessageString.charAt(0) == 'L') {

					List<String> messages = new ArrayList<String>();
					messages = Arrays.asList(serverMessageString.split(","));

					CardColor cColor = Card.convertCharacterToCardColor(messages.get(1).charAt(0));
					CardValue cValue = Card.convertCharacterToCardValue(messages.get(1).charAt(1));

					Card card = new Card(cColor, cValue);

					System.out.println("Lap, amire tenni kell: " + Card.convertCardColorToCharacter(cColor)
							+ Card.convertCardValueToCharacter(cValue));

					//A kivalasztott kartya 
					Card choosenCard = null;
					String choosenCardAsString = "";
					/**Taroljuk, hogy valasztottunk-e kartyat,
					 * ha igen, es megfelelo volt,
					 * elkuldjuk a megfeleo stringet
					 */
					boolean hasChoosenCard = false;
					
					boolean isFalseN = false;
					do {
						hasChoosenCard = false;
						isFalseN = false;
						System.out.print("Te lapod: ");
						String choosenCardString = kbScanner.nextLine();

						// Nincs kartya, nem tud tenni
						if (choosenCardString.equals("N")) {
							if (!checkIfAppropriateCardInHand(cColor, cValue)) {
								pw.println("N");
								String newCardString = br.readLine();
								Card newCard = Card.parseCardFromString(newCardString);
								cardsInHand.add(newCard);
							} else {
								System.out.println("Van kartya nalad, ami megfelelo!");
								isFalseN = true;
							}
						}
						else {
							
							choosenCardAsString = choosenCardString;// beolvasott sort eltároljuk
							choosenCard = getCardFromString(choosenCardString);
							
							if (checkIfCardInHand(choosenCard) == -1) {
								System.out.println("A kivalasztott kartya nincs a kezedben!" + choosenCard.getCardAsString());
							}
	
							if (!checkIfCardIsAppropriate(choosenCard, card)) {
								System.out.println("A kivalasztott kartya nem megfelelo!");
							}
						}

					} while (checkIfCardInHand(choosenCard) == -1 && checkIfCardIsAppropriate(choosenCard, card)
							&& isFalseN);

					//if (hasChoosenCard) {
						
						//Szerver, hogy kezeli UNO es kartya egyuttes kuldeset?
						if (cardsInHand.size() == 2) {
							pw.println("UNO");
							
						}
						pw.println("A,"+choosenCardAsString);
					//}
				}

				if (serverMessageString.charAt(0) == 'S') {
					char choosenColor = ' ';

					do {
						choosenColor = stdinReader.readLine().charAt(0);
					} while (choosenColor != 'P' && choosenColor != 'S' && choosenColor != 'Z' && choosenColor != 'K');

					pw.println(choosenColor);
				}

			} while (!hasEnded);

			System.out.println(clientName + " HASENDED!");

		} catch (IOException e) {
			System.err.println("Kommunikacios hiba a fogado szalban.");
		}

	}

	/**
	 *	Megvizsgaljuk, hogy a kivalasztott kartya megfelelo 	
	 *
	 * @param  choosenCard  Kivalasztott kartya
	 * @param  card Ellenorzendo kartya
	 * @return    	A kivalasztott kartya megfelelo-e
	 */
	private boolean checkIfCardIsAppropriate(Card choosenCard, Card card) {
		if (choosenCard.getCardColor() == card.getCardColor() || choosenCard.getCardValue() == card.getCardValue()) {
			return true;
		}
		return false;
	}


	/**
	 * Annak vizsgalata, hogy a valasztott kartya tenylegesen a kezben van-e
	 * Ha igen, visszaadjuk a kartya indexet a listaban
	 * Ha nem, -1-et
	 *
	 * @param  card Ellenorizendo kartya
	 * @return      A megfelelo kartya valoban a kezben van-e, kartya index, ha igen, -1 egyebkent
	 */
	
	private int checkIfCardInHand(Card card) {
		int i;
		boolean found=false;
		for (i=0; i < cardsInHand.size() && !found; i++) {
			//if (cardsInHand.get(i).equals(card))
			if (cardsInHand.get(i).getCardAsString().equals(card.getCardAsString()))
			{
				found = true;
				//System.out.println("van ilyen a kezedben");
			}
		}

		if (found) {
			return i;
		} else {
			return -1;
		}

	}
	
	/**
	 * Stringbol kartya tipussa alakitas, ellenorzesek miatt
	 *
	 * @param  card Ellenorizendo kartya
	 * @return A konvertalt kartya
	 */

	private Card getCardFromString(String card) {
		CardColor choosenCardColor = Card.convertCharacterToCardColor(card.charAt(0));
		CardValue choosenCardValue = Card.convertCharacterToCardValue(card.charAt(1));

		Card choosenCard = new Card(choosenCardColor, choosenCardValue);
		return choosenCard;

	}
	
	/**
	 * Annak vizsgalata, hogy a megfelelo kartya van-e a kezben
	 *
	 * @param  cColor kartyaszin
	 * @param  cValue kartyaertek
	 * @return      Van-e megfeleo kartya a kezben, ha igen true, egyebkent false
	 */

	private boolean checkIfAppropriateCardInHand(CardColor cColor, CardValue cValue) {
		boolean found = false;
		for (int i = 0; i < cardsInHand.size() && !found; i++) {
			if (cardsInHand.get(i).getCardValue() == cValue || cardsInHand.get(i).getCardColor() == cColor) {
				found = true;
			}
		}

		return found;
	}
}
