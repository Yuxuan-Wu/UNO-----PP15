import java.util.ArrayList;

public class Uno {
	Deck drawDeck, discZone;
	ArrayList<Player> players;
	ArrayList<Integer> orderOfPlay;
	
	/*
	1. 
	2. object: first player who reach 500 points
	3. 
	4. 
	5. beginning clockwise from the dealer
	6. player can only deal one card each turn
	 - a legal play is a card either matches the color number or symbol
	7. after shuffling, 
	 - if the top card flipped over by the dealer is a "draw 4 wild", the card is placed back in deck and draw another card
	 - if it's a wild card, the player left of the dealer pick a color and plays
	 - if it's a draw 2 card, the player left of the dealer draw 2 cards and skips their turn
	 - if it's a reverse card, the dealer goes first
	 - if it's a skip, player to dealer's left misses a turn
	8. skip: the next player forfeit their turn
	9. reverse: changes the direction of the play
	 - In a two-player game, the Reverse card acts like a Skip card; when played, the other player misses a turn.
	10. draw 2: the next player draws two card and forfeit their turn
	11. wild: maybe played on any color, and allows you to pick a color that the next player must follow
	12. draw 4 wild: does the same as the wild and in addition the next player has to draw 4 cards and skip their turn, however a draw 4 wild may only be played when you don't have a card in your hand matching the color played
	13. a wild card represent no color until they are played 
	14. if you can't play in a turn, you draw one card, if that card can be played you may play it immediately, otherwise your turn is forfeited
	15. you may also choose not to play on your turn but instead draw a card, if that card can be played your may only play that card or end your turn
	16. when you get down to one card in your hand you must call uno, if you don't call uno by the time your second-to-last card touches the discard zone, any other player may catch you on this, if they did, before the next player plays, you must draw 2 cards
	17. if the round hasn't ended and the draw deck runs out of cards shuffle the discard pile and it becomes the new draw deck
	18. when a player plays the last card in their hand the round ends, if that card is a draw card the next player still fulfill the drawing, points are then scored 
	19. the player who won that round goes out to receive point for every unplayed card in other players' hands
	 - all cards 0 - 9 are worth face value
	 - action cards are worth 20 points
	 - wild cards are worth 50 points
	20. if the round ends and none of the players have reached to 500 points, play another round
	21. the winner of each round becomes the next rounds' dealer
	*/
	
	public Uno(int numOfPlayers) {
		drawDeck = new Deck();
		drawDeck.insert();
		drawDeck.shuffle();
		discZone = new Discard_Zone();
		//need to be fixed
		players.add(new Human_Player(null, null, 0, false));
		for (int i = 0; i < numOfPlayers - 1; i ++) 
			players.add(new Bot_Player(null, null, i, false));
	}
	
	public void game() {
		
	}
	
	public void roundStart() {
		//deal 7 cards to each player
		for (int i = 0; i < players.size(); i ++) 
			for (int j = 0; j < 7; j ++) 
				players.get(i).drawCard(drawDeck);
		//after dealing the dealer flips over the top card of the deck
		
		//object for each round: to be the first player who have empty hand
		while (!winnerDetect()) {
			
		}
	}
	
	public boolean winnerDetect() {
		for (int i = 0; i < players.size(); i ++) {
			if (players.get(i).numOfCardHolding() == 0)
				return true;
		}
		return false;
	}
	
	public void swapDeck() {
		//swap drawDeck with discardZone
		drawDeck.setDeck(discZone.getDeck());
		drawDeck.shuffle();
		discZone.setDeck(new ArrayList<Card>());
	}
	
	public int countScore(int winner) {
		int roundScore = 0;
		for (int i = 0; i < orderOfPlay.size(); i++) {
			if (i != winner)
				roundScore = players.get(i).calculatePoints();
		}
		return roundScore;
	}
	
	public void updtStatus(Player player, Deck unoDeck) {
		if (player.getStatus().compareTo("Draw 2") == 0) {
			player.drawCard(unoDeck);
			player.drawCard(unoDeck);
		}
		else if (player.getStatus().compareTo("Draw 4") == 0) {
			for (int i = 0; i < 4; i ++)
				player.drawCard(unoDeck);
		}
		else if (player.getStatus().compareTo("Reverse") == 0) {
			   
		}
		else if (player.getStatus().compareTo("Skip") == 0) {
			   
		}
		player.changeStatus("");
	}
	
}
