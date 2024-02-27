package game;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import game.GamePlay;

public class GameStart {

	final static int CARDS_PER_DECK = 108;
	final static int CARDS_ON_START = 7;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

//		System.out.print("Enter number of decks (default 108 cards): ");
//		int decks = scanner.nextInt();
		int decks = 1;

//        System.out.print("Enter number of AI players (max 4): ");
//        int players = scanner.nextInt();
		int players = 1;

		// Gets shuffled deck
		List<String> deck = new ArrayList<>(deckHandler(decks));

		// Deals 7 cards to each player
		Game playerStartingHands = startingHand((players + 1), (CARDS_PER_DECK * decks), deck);
		
		GamePlay.runningGame(playerStartingHands);
	}

	public static Game startingHand(int players, int max, List<String> deck) {
		// One to one game
		if (players == 2) {
			List<String> playerOneHand = new ArrayList<>();
			List<String> playerTwoHand = new ArrayList<>();

			for (int i = 0; i < players * 7; i++) {
				int random = randomInt(max);
				if (i % 2 != 0)
					playerOneHand.add(deck.get(random));
				else
					playerTwoHand.add(deck.get(random));

				deck.remove(random);
				max--;
			}

			return new Game(playerOneHand, playerTwoHand, deck);
		}

		return null;
	}

	public static List<String> deckHandler(int decks) {
		int max = CARDS_PER_DECK * decks;
		String[] wholeDeck = new String[] { "0R", "1R", "2R", "3R", "4R", "5R", "6R", "7R", "8R", "9R", "SR", "RR",
				"DR", "1R", "2R", "3R", "4R", "5R", "6R", "7R", "8R", "9R", "SR", "RR", "DR", "0B", "1B", "2B", "3B",
				"4B", "5B", "6B", "7B", "8B", "9B", "SB", "RB", "DB", "1B", "2B", "3B", "4B", "5B", "6B", "7B", "8B",
				"9B", "SB", "RB", "DB", "0G", "1G", "2G", "3G", "4G", "5G", "6G", "7G", "8G", "9G", "SG", "RG", "DG",
				"1G", "2G", "3G", "4G", "5G", "6G", "7G", "8G", "9G", "SG", "RG", "DG", "0Y", "1Y", "2Y", "3Y", "4Y",
				"5Y", "6Y", "7Y", "8Y", "9Y", "SY", "RY", "DY", "1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y",
				"SY", "RY", "DY", "WR", "WDR", "WR", "WDR", "WR", "WDR", "WR", "WDR" };

		List<String> tempDeck = new ArrayList<>(List.of(wholeDeck));
		List<String> shuffledDeck = new ArrayList<>();

		// Iterates through tempDeck until empty in random order
		for (int i = max; i > 0; i--) {
			int random = randomInt(i);
			shuffledDeck.add(tempDeck.get(random));
			tempDeck.remove(random);
		}

		return shuffledDeck;
	}

	public static int randomInt(int max) {
		Random random = new Random();

		int randomInt = random.nextInt(max);

		return (randomInt);
	}

}
