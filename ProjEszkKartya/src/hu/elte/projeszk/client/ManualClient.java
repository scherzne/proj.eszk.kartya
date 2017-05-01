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
   
	
	//Szerverrel való kommunikáció biztosítása
	public void communicate() throws IOException {
		
		//szerver által küldött üzenet tárolása
		String serverMessageString = "";
		BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));

		
		/*Addig kérjük be a klienstől a szerver nevét,
		 * amíg "ok" választ nem kapunk a szervertől
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
				 * A szerver lapokat ad,
				 * A karakter után számérték, amennyi lapot kapunk
				 * Majd lapok "konvertálása" és kézhez adása
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
						System.out.println("Lapot kaptál: " + Card.convertCardColorToCharacter(cColor) + Card.convertCardValueToCharacter(cValue));
						
					}
				}
				
				/*
				 * Szerver lapot kér 
				 * Vesszőkkel elválasztott sztring
				 * 2. rész lap mindig az előzőleg eldobott lap
				 * 3. rész: húzott vagy nem az előző játékos
				 * 4. rész: színkényszer megadása
				 */
				if (serverMessageString.charAt(0) == 'L') {

					List<String> messages = new ArrayList<String>();
					messages = Arrays.asList(serverMessageString.split(","));

					CardColor cColor = Card.convertCharacterToCardColor(messages.get(1).charAt(0));
					CardValue cValue = Card.convertCharacterToCardValue(messages.get(1).charAt(1));

					Card card = new Card(cColor, cValue);
					
					System.out.println("Lap, amire tenni kell: " + Card.convertCardColorToCharacter(cColor) + Card.convertCardValueToCharacter(cValue));
					System.out.print("Te lapod: ");
					
					Card choosenCard;
					do {
						String choosenCardString = kbScanner.nextLine();
						
						choosenCard = getCardFromString(choosenCardString);
						
						if (checkIfCardInHand(choosenCard) == -1) {
							System.out.println("A kiválasztott kártya nincs a kezedben!" );
						}
						
						if (!checkIfCardIsAppropriate(choosenCard, card)) {
							System.out.println("A kiválasztott kártya nem megfelelő!" );
						}
					} while (checkIfCardInHand(choosenCard) == -1 && checkIfCardIsAppropriate(choosenCard, card));
					

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
		if (choosenCard.getCardColor() == card.getCardColor() ||
			choosenCard.getCardValue() == card.getCardValue()) {
			return true;
		}
		return false;
	}

	//Annak vizsgálata, hogy a választott kártya ténylegesen a kézben van-e
	//Ha igen, visszaadjuk a kártya indexét a listában
	//Ha nem, -1-et
	private int checkIfCardInHand(Card card) {
		boolean found = false;
		int i = 0;
		for (; i < cardsInHand.size() && !found; i++) {
			if (cardsInHand.get(i).equals(card)) {
				found = true;
			}
		}
		
		if (found) {
			return i;
		}
		else {
			return -1;
		}
		
	}
	
	private Card getCardFromString(String card) {
		CardColor choosenCardColor = Card.convertCharacterToCardColor(card.charAt(0));
		CardValue choosenCardValue = Card.convertCharacterToCardValue(card.charAt(1));
		
		Card choosenCard = new Card(choosenCardColor, choosenCardValue);
		return choosenCard;
		
	}
}
