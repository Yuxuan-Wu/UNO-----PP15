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
	5. beginning clockwise from the dealer x
	6. player can only deal one card each turn 
	 - a legal play is a card either matches the color number or symbol x
	7. after shuffling, x
	 - if the top card flipped over by the dealer is a "draw 4 wild", the card is placed back in deck and draw another card
	 - if it's a wild card, the player left of the dealer pick a color and plays
	 - if it's a draw 2 card, the player left of the dealer draw 2 cards and skips their turn
	 - if it's a reverse card, the dealer goes first
	 - if it's a skip, player to dealer's left misses a turn
	8. skip: the next player forfeit their turn x
	9. reverse: changes the direction of the play x
	 - In a two-player game, the Reverse card acts like a Skip card; when played, the other player misses a turn. x
	10. draw 2: the next player draws two card and forfeit their turn x
	11. wild: maybe played on any color, and allows you to pick a color that the next player must follow x
	12. draw 4 wild: does the same as the wild and in addition the next player has to draw 4 cards and skip their turn, however a draw 4 wild may only be played when you don't have a card in your hand matching the color played x
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
		players = new ArrayList<Player>();
		orderOfPlay = new ArrayList<Integer>();
		//need to be fixed
		players.add(new Human_Player("Charles"));
		for (int i = 1; i < numOfPlayers; i ++) 
			players.add(new Bot_Player("Bot_Player No." + i));
	}
	
	public void game() {
		//initialize the game
		initializeGame();
		//start a round
		while (gameWinner() == null)
			roundStart();
		System.out.println("Winner is: " + gameWinner().getName() + " with the score of: " + gameWinner().getScore());		
	}
	
	public void roundStart() {
		//deal 7 cards to each player
		for (int i = 0; i < players.size(); i ++) 
			for (int j = 0; j < 7; j ++) 
				players.get(i).drawCard(drawDeck, discZone);
		//after dealing the dealer flips over the top card of the deck
		flipCard();
		//object for each round: to be the first player who have empty hand
		if (getPlayer(1).isActive()) {
			play(getPlayer(1));
			getPlayer(1).setActive(false);
		}
		nextTurn();
		while (roundWinner() == null) {
			//let the current player play, if the player can't cast a card, 
			//if you can't play in a turn, you draw one card, 
			//if that card can be played you may play it immediately, otherwise your turn is forfeited
			//when you get down to one card in your hand you must call uno, if you don't call uno by the time your second-to-last card touches the discard zone, any other player may catch you on this, if they did, before the next player plays, you must draw 2 cards
			//if the round hasn't ended and the draw deck runs out of cards shuffle the discard pile and it becomes the new draw deck x
			//when a player plays the last card in their hand the round ends, if that card is a draw card the next player still fulfill the drawing, points are then scored x
			
			//let the player draw from the draw deck
			
			//if the player has no card that can be played
			// - let the player draw a card, if the card matches the top card of discard zone can be played, or the turn will be forfeited
			getPlayer(1).setActive(true);
			play(getPlayer(1));
			//add card effect to the next player
			if (discZone.getTopCard() instanceof Wild_Card || discZone.getTopCard() instanceof Action_Card) 
				addEffect(getPlayer(2));
			//check if uno should be called
			unoDetect(getPlayer(1));
			//move on to the next player
			getPlayer(1).setActive(false);
			nextTurn();
			turnReport();
		}
		Player winnerOfTheRound = roundWinner();
		//calculate and add the score to the winner
		winnerOfTheRound.setScore(winnerOfTheRound.getScore() + countScore(winnerOfTheRound));
		//set the dealer back to player
		for (int i = 0; i < players.size(); i ++)
			players.get(i).setDealer(false);
		//set the winner of the round to be the dealer
		winnerOfTheRound.setDealer(true);
		//set the order for next round
		setOrder();
	}
	
	public void turnReport() {
		String playerStatus = "";
		System.out.println("----------------------------------------------------");
		System.out.println(getPlayer(1).getName() + " is the next player");
		for (int i = 0; i < players.size(); i ++) 
			playerStatus += " - Player: " + players.get(i).getName() + " has " + players.get(i).numOfCardHolding() + " card(s)\n";
		System.out.print(playerStatus);
		System.out.println("----------------------------------------------------");
	}
	
	public void play(Player p) {
		if (p.playCard(discZone)) {
			//System.out.println(p.getName() + ": Card placed successfully");
		}
		else {
			p.drawCard(drawDeck, discZone);
			if (p.playCard(discZone)) {
				//System.out.println(p.getName() + ": Card placed successfully");
			}
			else
				System.out.println(p.getName() + ": Turn forfeited");
		}
	}
	
	public void flipCard() {
		getPlayer(1).drawCard(drawDeck, discZone);
		Card tempCard = getPlayer(1).takeCard(getPlayer(1).numOfCardHolding() - 1);
		if (tempCard instanceof Wild_Card) {
			if (tempCard.cardEffect().compareTo("No effect") == 0) {
				//if it's a wild card, the player left of the dealer pick a color and plays
				getPlayer(2).wildPickColor(tempCard);
			}
			else if (tempCard.cardEffect().compareTo("Draw 4") == 0) {
				//if the top card flipped over by the dealer is a "draw 4 wild", 
				//the card is placed back in deck and draw another card
				drawDeck.insert(tempCard);
				flipCard();
			}				
		}
		else if (tempCard instanceof Action_Card) {
			if (tempCard.cardEffect().compareTo("Reverse") == 0) {
				//if it's a reverse card, the dealer goes first
				getPlayer(1).setActive(true);
			}
			else if (tempCard.cardEffect().compareTo("Skip") == 0) {
				//if it's a skip, player to dealer's left misses a turn
				nextTurn();
			}
			else if (tempCard.cardEffect().compareTo("Draw 2") == 0) {
				//if it's a draw 2 card, the player left of the dealer draw 2 cards and skips their turn
				getPlayer(2).drawCard(drawDeck, discZone);
				getPlayer(2).drawCard(drawDeck, discZone);
				nextTurn();
			}	
		}
		discZone.insert(tempCard);
	}
	
	public void initializeGame() {
		int r1, r2, temp;
		for (int i = 1; i <= players.size(); i ++) 
			orderOfPlay.add(i);
		for (int i = 0; i < 10; i ++) {
			r1 = (int)(Math.random() * players.size()); 
			r2 = (int)(Math.random() * players.size()); 
			temp = orderOfPlay.get(r1);
			orderOfPlay.set(r1, orderOfPlay.get(r2));
			orderOfPlay.set(r2, temp);
		}
		getPlayer(1).setDealer(true);
	}
	
	public void setOrder() {
		int r1, r2, temp, dealer = 0, firstPlayer = 0;
		for (int i = 0; i < 10; i ++) {
			r1 = (int)(Math.random() * players.size()); 
			r2 = (int)(Math.random() * players.size()); 
			temp = orderOfPlay.get(r1);
			orderOfPlay.set(r1, orderOfPlay.get(r2));
			orderOfPlay.set(r2, temp);
		}
		//find dealer's index
		for (int i = 0; i < players.size(); i ++) {
			if (players.get(i).isDealer())
				dealer = i;
		}
		//find the 1st player's index
		for (int i = 0; i < orderOfPlay.size(); i ++) {
			if (orderOfPlay.get(i).intValue() == 1)
				firstPlayer = i;
		}
		//swicth 1st player's position with the dealer
		orderOfPlay.set(firstPlayer, orderOfPlay.get(dealer));
		orderOfPlay.set(dealer, 1);
	}
	
	public void nextTurn() {
		for (int i = 0; i < orderOfPlay.size(); i ++) {
			if (orderOfPlay.get(i).intValue() == 1)
				orderOfPlay.set(i, 4);
			else 
				orderOfPlay.set(i, orderOfPlay.get(i).intValue() - 1);
		}
	}
	
	public void reverseOrder() {
		//if there are only 2 players, reverse works like skip
		if (players.size() == 2)
			nextTurn();
		else if (players.size() == 3) {
			for (int i = 0; i < orderOfPlay.size(); i ++) {
				if (orderOfPlay.get(i) == 1)
					orderOfPlay.set(i, 3);
				else if (orderOfPlay.get(i) == 3)
					orderOfPlay.set(i, 1);
			}
		}
		else if (players.size() == 4) {
			for (int i = 0; i < orderOfPlay.size(); i ++) {
				if (orderOfPlay.get(i) == 1)
					orderOfPlay.set(i, 4);
				else if (orderOfPlay.get(i) == 2)
					orderOfPlay.set(i, 3);
				else if (orderOfPlay.get(i) == 3)
					orderOfPlay.set(i, 2);
				else if (orderOfPlay.get(i) == 4)
					orderOfPlay.set(i, 1);
			}
		}
	}
	
	public Player getPlayer(int targetPlayer) {
		int index = 0;
		for (int i = 0; i < orderOfPlay.size(); i ++) {
			if (orderOfPlay.get(i).intValue() == targetPlayer)
				index = i;
		}
		return players.get(index);
	}
	
	public Player roundWinner() {
		for (int i = 0; i < players.size(); i ++) {
			if (players.get(i).numOfCardHolding() == 0)
				return players.get(i);
		}
		return null;
	}
	
	public Player gameWinner() {
		for (int i = 0; i < players.size(); i ++) {
			if (players.get(i).getScore() >= 500)
				return players.get(i);
		}
		return null;
	}
	
	public void unoDetect(Player p) {
		if (p.numOfCardHolding() == 1) {
			if (!p.calledUno()) {
				p.drawCard(drawDeck, discZone);
				p.drawCard(drawDeck, discZone);	
			}
		}
	}
	
	public int countScore(Player winner) {
		int roundScore = 0;
		for (int i = 0; i < orderOfPlay.size(); i++) {
			if (players.get(i) != winner)
				roundScore += players.get(i).calculatePoints();
		}
		return roundScore;
	}
	
	public void addEffect(Player player) {
		if (discZone.getTopCard().cardEffect().compareTo("Skip") == 0) {
			nextTurn();
		}
		else if (discZone.getTopCard().cardEffect().compareTo("Draw 2") == 0) {
			player.drawCard(drawDeck, discZone);
			player.drawCard(drawDeck, discZone);
			nextTurn();
		}
		else if (discZone.getTopCard().cardEffect().compareTo("Reverse") == 0)
			reverseOrder();	
		else if (discZone.getTopCard().cardEffect().compareTo("Draw 4") == 0) {
			for (int i = 0; i < 4; i ++)
				player.drawCard(drawDeck, discZone);
			nextTurn();
		}
	}
	
}
