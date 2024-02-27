package game;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamePlay {

	public static void runningGame(Game decks) {
		Scanner scanner = new Scanner(System.in);
		boolean turn = true;
		boolean wait = true;
		String topCard;
		String placedCard = null;
		String tempCard = null;
		int k = -1;

		// Getting top card and declaring discard pile
		List<String> discardPile = new ArrayList<>();
		discardPile.add(decks.deck.get(0));
		decks.deck.remove(0);

		System.out.println("Player 1 (you) goes first...");

		while (decks.playerOneHand.size() > 0 || decks.playerTwoHand.size() > 0) {
			// TODO Check if deck is empty
			topCard = discardPile.get(discardPile.size() - 1);
			// Player 1
			if (turn) {
				while (wait) {
					System.out.println("Top card: " + topCard);
					System.out.println("Player 1s hand: " + decks.playerOneHand);

					// Check if a card can be placed
					if (!checkToDraw(topCard, decks.playerOneHand)) {
						System.out.println("No cards in hand are playable, drawing +1 and ending turn.");
						draw(1, decks.playerOneHand, decks.deck);
						turn = !turn;
						break;
					}
					placedCard = scanner.nextLine();
					// Check if valid card in players deck
					for (k = 0; k < decks.playerOneHand.size(); k++)
						if (placedCard.equals(decks.playerOneHand.get(k))) {
							wait = !wait;
							break;
						}
					if (wait)
						System.out.println("Please select a card in your hand!");
				}
				// Check if card can be placed on topCard
				if (cardCheck(topCard, placedCard)) {
					discardPile.add(topCard);
					decks.playerOneHand.remove(k);
					//System.out.println(decks.playerOneHand);
					tempCard = special(turn, topCard, placedCard, decks.playerOneHand, decks.playerTwoHand, decks.deck);
					if (tempCard == null) {
						topCard = placedCard;
					} 
					else if (tempCard.equals("S")) {
						topCard = placedCard;
					}
					else if (tempCard.equals("R")) {
						topCard = placedCard;
					} 
					else {
						topCard = tempCard;
					}

					//System.out.println(topCard);
					turn = !turn;
				} else
					System.out.println("Card does not have a number or color in common with Top Card!");
				wait = true;
			}
			//Player 2 (AI)
			else {
				while (wait) {
					// Check if a card can be placed
					if (!checkToDraw(topCard, decks.playerTwoHand)) {
						draw(1, decks.playerTwoHand, decks.deck);
						turn = !turn;
						break;
					}
					for (int i = 0; i < decks.playerTwoHand.size() - 1; i++) {
						if (decks.playerTwoHand.get(i).charAt(0) == topCard.charAt(0) || decks.playerTwoHand.get(i).charAt(0) == topCard.charAt(1)) {
							topCard = decks.playerTwoHand.get(i);
							decks.playerTwoHand.remove(i);
							wait = !wait;
							turn = true;
							break;
						}
					}
				}
			}

		}
	}

	public static String special(Boolean turn, String topCard, String placedCard, List<String> playerOneDeck,
			List<String> playerTwoDeck, List<String> deck) {
		Scanner scanner = new Scanner(System.in);
		// Wild Card
		if (placedCard.equals("WR")) {
			System.out.println("Wild Card! Pick a color (R, B, G, Y): ");
			while (true) {
				String newColor = scanner.nextLine();
				if (newColor.equals("R") || newColor.equals("B") || newColor.equals("G") || newColor.equals("Y")) {
					topCard = topCard.charAt(0) + newColor;
					return (topCard);
				} else {
					System.out.println("Please select a color (R, B, G, Y):");
				}
			}
		}
		// Wild Card +4
		if (placedCard.equals("WDR")) {
			System.out.println("Wild Card +4! Pick a color (R, B, G, Y): ");
			while (true) {
				String newColor = scanner.nextLine();
				if (newColor.equals("R") || newColor.equals("B") || newColor.equals("G") || newColor.equals("Y")) {
					topCard = topCard.charAt(0) + newColor;
					if (turn) {
						draw(4, playerTwoDeck, deck);
					} else {
						draw(4, playerOneDeck, deck);
					}
					return (topCard);
				} else {
					System.out.println("Please select a color (R, B, G, Y):");
				}
			}

		}
		// Skip
		// TODO edit for more players
		if (placedCard.equals("SR") || topCard.equals("SB") || topCard.equals("SG") || topCard.equals("SY")) {
			System.out.println("Skip!");
			return ("S");
		}
		// Reverse
		// TODO edit for more players
		if (placedCard.equals("RR") || topCard.equals("RB") || topCard.equals("RG") || topCard.equals("RY")) {
			System.out.println("Reverse!");
			return ("R");
		}
		// Draw +2
		if (placedCard.equals("DR") || topCard.equals("DB") || topCard.equals("DG") || topCard.equals("DY")) {
			if (turn) {
				draw(2, playerTwoDeck, deck);
			} else {
				draw(2, playerOneDeck, deck);
			}
		}
		return null;
	}

	public static List<String> draw(int numberOfDraws, List<String> playersDeck, List<String> deck) {
		// TODO check for empty deck, if true -> shuffle add to deck
		for (int i = 0; i < numberOfDraws; i++) {
			playersDeck.add(deck.get(0));
			deck.remove(0);
		}
		return (playersDeck);
	}

	public static boolean checkToDraw(String topCard, List<String> playersDeck) {
		for (int i = 0; i < playersDeck.size(); i++) {
			if (cardCheck(topCard, playersDeck.get(i)))
				return (true);
		}

		return (false);
	}

	public static boolean cardCheck(String topCard, String placedCard) {
		if (placedCard.equals("WR") || placedCard.equals("WDR") || topCard.equals("WR") || topCard.equals("WDR")) {
			return (true);
		} else if (topCard.charAt(0) == placedCard.charAt(0) || topCard.charAt(1) == placedCard.charAt(1))
			return (true);

		return (false);
	}
}
