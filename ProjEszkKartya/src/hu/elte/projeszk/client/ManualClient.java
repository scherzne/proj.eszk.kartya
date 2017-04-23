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

		hasEnded = false;

	}

	public void communicate() throws IOException {

		String serverMessageString = "";
		BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));

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

				if (serverMessageString.charAt(0) == 'A') {
					int numberOfSendedCards = Character.getNumericValue(serverMessageString.charAt(1));
					for (int i = 0; i < numberOfSendedCards; i++) {
						serverMessageString = br.readLine();
					}
				}

				if (serverMessageString.charAt(0) == 'L') {

					List<String> messages = new ArrayList<String>();
					messages = Arrays.asList(serverMessageString.split(","));

					CardColor cColor = Card.convertCharacterToCardColor(messages.get(1).charAt(0));
					CardValue cValue = Card.convertCharacterToCardValue(messages.get(1).charAt(1));

					Card card = new Card(cColor, cValue);
					cardsInHand.add(card);

				}

				if (serverMessageString.charAt(0) == 'S') {

					
				}

			} while (!hasEnded);

			System.out.println(clientName + " HASENDED!");

		} catch (IOException e) {
			System.err.println("Kommunikacios hiba a fogado szalban.");
		}

	}
}
