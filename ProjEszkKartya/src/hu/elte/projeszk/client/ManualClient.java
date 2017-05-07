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

public class ManualClient {
	private BufferedReader br;
	private PrintWriter pw;
	private Scanner sc;
	private Scanner kbScanner;
	private String clientName;
	private ArrayList<Card> cardsInHand;
	private boolean hasEnded;

	Socket socket = null;

	public static void main(String[] args) throws UnknownHostException, IOException {
		ManualClient client = new ManualClient();

		client.communicate();
	}

	public ManualClient() throws UnknownHostException, IOException {
		cardsInHand = new ArrayList<Card>();
		socket = new Socket("localhost", Consts.PORT);
		pw = new PrintWriter(socket.getOutputStream(), true);

		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		sc = new Scanner(socket.getInputStream());
		kbScanner = new Scanner(System.in);

		hasEnded = false;

	}

	//Szerverrel valo kommunikacio biztositasa
	public void communicate() throws IOException {

		// Szerver altal kuldott uzenet tarolasa
		String serverMessageString = "";
		BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));

		/*
		 * Addig kerjuk be a klienstol a nevet, amig ok valaszt
		 * nem kapunk a szervertol
		 */

		while (!serverMessageString.equals("ok")) {
			System.out.print("Nev: ");

			clientName = stdinReader.readLine();
			pw.println(clientName);
			serverMessageString = br.readLine();
		}

		System.out.println("Sikeres csatlakozas.");

		try {
			do {
				String stringToWriteToMonitor = clientName + ": ";

				serverMessageString = br.readLine();

				/*
				 * A szerver lapokat ad, A karakter utan szamertek, amennyi lapot kapunk
				 *  Majd lapok "konvertalasa" es kezhez adasa
				 */
				if (serverMessageString.charAt(0) == 'A') {

					int numberOfSendedCards = Character.getNumericValue(serverMessageString.charAt(1));
					for (int i = 0; i < numberOfSendedCards; i++) {
						serverMessageString = br.readLine();
						List<String> messages = new ArrayList<String>();
						messages = Arrays.asList(serverMessageString.split(","));

						CardColor cColor = Card.convertCharacterToCardColor(messages.get(1).charAt(0));
						CardValue cValue = Card.convertCharacterToCardValue(messages.get(1).charAt(1));
						
						cardsInHand.add(new Card(cColor, cValue));
						System.out.println("Lapot kaptal: " + Card.convertCardColorToCharacter(cColor)
								+ Card.convertCardValueToCharacter(cValue));

					}
				}

				/*
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
					/*Taroljuk, hogy valasztottunk-e kartyat,
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
							
							choosenCardAsString = choosenCardString;
							choosenCard = getCardFromString(choosenCardString);
							
							if (checkIfCardInHand(choosenCard) == -1) {
								System.out.println("A kivalasztott kartya nincs a kezedben!");
							}
	
							if (!checkIfCardIsAppropriate(choosenCard, card)) {
								System.out.println("A kivalasztott kartya nem megfelelo!");
							}
						}

					} while (checkIfCardInHand(choosenCard) == -1 && checkIfCardIsAppropriate(choosenCard, card)
							&& isFalseN);

					if (hasChoosenCard) {
						
						//Szerver, hogy kezeli UNO es kartya egyuttes kuldeset?
						if (cardsInHand.size() == 2) {
							pw.println("UNO");
							
						}
						pw.println(choosenCardAsString);
					}
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

	private boolean checkIfCardIsAppropriate(Card choosenCard, Card card) {
		if (choosenCard.getCardColor() == card.getCardColor() || choosenCard.getCardValue() == card.getCardValue()) {
			return true;
		}
		return false;
	}

	// Annak vizsgalata, hogy a valasztott kartya tenylegesen a kezben
	// van-e
	// Ha igen, visszaadjuk a kartya indexet a listaban
	// Ha nem, -1-et
	private int checkIfCardInHand(Card card) {
		int i = 0;
		for (; i < cardsInHand.size() && !found; i++) {
			if (cardsInHand.get(i).equals(card)) {
				found = true;
			}
		}

		if (found) {
			return i;
		} else {
			return -1;
		}

	}

	private Card getCardFromString(String card) {
		CardColor choosenCardColor = Card.convertCharacterToCardColor(card.charAt(0));
		CardValue choosenCardValue = Card.convertCharacterToCardValue(card.charAt(1));

		Card choosenCard = new Card(choosenCardColor, choosenCardValue);
		return choosenCard;

	}

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
