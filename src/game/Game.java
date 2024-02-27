package game;

import java.util.List;

class Game {
	List<String> playerOneHand;
	List<String> playerTwoHand;
	List<String> deck;

	public Game(List<String> playerOneHand, List<String> playerTwoHand, List<String> deck) {
        this.playerOneHand = playerOneHand;
        this.playerTwoHand = playerTwoHand;
        this.deck = deck;
    }
}