import java.util.ArrayList;
import java.util.Scanner;

public class Uno {
	Deck drawDeck, discZone;
	ArrayList<Player> players;
	ArrayList<Integer> orderOfPlay;
	
	public Uno(int numOfPlayers) {
		drawDeck = new Deck();
		drawDeck.insert();
		drawDeck.shuffle();
		discZone = new Discard_Zone();
		players = new ArrayList<Player>();
		orderOfPlay = new ArrayList<Integer>();
		Scanner sc = new Scanner(System.in);
		System.out.println("Add human player? \"Y\" for yes, anything else for no");
		String userInput = sc.nextLine();
		if (userInput.compareTo("Y") == 0) {
			System.out.println("Name for the human player?");
			userInput = sc.nextLine();
			players.add(new Human_Player(userInput));
			for (int i = 1; i < numOfPlayers; i ++) 
				players.add(new Bot_Player("Bot_Player No." + i));
		}
		else 
			for (int i = 0; i < numOfPlayers; i ++) 
				players.add(new Bot_Player("Bot_Player No." + (i + 1)));
	}
	
	public void game() throws InterruptedException {
		//initialize the game
		initializeGame();
		//start a round
		while (gameWinner() == null)
			roundStart();
		System.out.println("=============================================================");
		System.out.println("Winner of the game is: " + gameWinner().getName() + " with the score of: " + gameWinner().getScore());	
		System.out.println("=============================================================");
		Thread.sleep(5000);
		//reset the game
		gameReset();
	}
	
	public void gameReset() {
		drawDeck = new Deck();
		drawDeck.insert();
		drawDeck.shuffle();
		discZone = new Discard_Zone();
		orderOfPlay = new ArrayList<Integer>();
		for (Player p : players) {
			p.setScore(0);
			p.setActive(false);
			p.setDealer(false);
			p.removeHand();
			p.setUnoCalled(false);
		}
	}
	
	//object for each round: to be the first player who have empty hand
	public void roundStart() throws InterruptedException {
		System.out.println("The dealer is: " + getPlayer(1).getName());
		printOrder();
		Thread.sleep(5000);
		System.out.println("=============================================================");
		//deal 7 cards to each player
		for (int i = 0; i < players.size(); i ++) 
			for (int j = 0; j < 7; j ++) 
				players.get(i).drawCard(drawDeck, discZone);
		//after dealing the dealer flips over the top card of the deck
		flipCard();
		Thread.sleep(5000);
		if (getPlayer(1).isActive()) {
			play(getPlayer(1));
			getPlayer(1).setActive(false);
		}
		nextTurn();
		System.out.println("The round starts!");
		//the round starts
		while (roundWinner() == null) {
			getPlayer(1).setActive(true);
			play(getPlayer(1));
			Thread.sleep(3000);
			getPlayer(1).setActive(false);
			//add card effect to the next player
			if ((discZone.getTopCard() instanceof Wild_Card 
				|| discZone.getTopCard() instanceof Action_Card)
				&& discZone.getTopCard().isEffective()) {
				addEffect(getPlayer(2));
				//the card is no longer effective after it's effect been added to the next player
				discZone.getTopCard().setEffective(false);
			}
			nextTurn();
			if ((discZone.getTopCard() instanceof Wild_Card 
					|| discZone.getTopCard() instanceof Action_Card)) {
				printOrder();
			}
			//turnReport();
		}
		Player winnerOfTheRound = roundWinner();
		//calculate and add the score to the winner
		winnerOfTheRound.setScore(winnerOfTheRound.getScore() + countScore(winnerOfTheRound));
		//set the order for next round
		setOrder();
		//for (Player p : players)
			//cardsToBeScored += p.getCardsInHand();
		System.out.println("---------------------xxxxxxxxxxxxxxxxxxx---------------------");
		//System.out.print(cardsToBeScored);
		System.out.println("Round winner is: " + roundWinner().getName() + " with the score of: " + roundWinner().getScore());
		System.out.println("---------------------xxxxxxxxxxxxxxxxxxx---------------------");
		Thread.sleep(5000);
		//reset the game
		for(Player p : players) {
			p.removeHand();
			p.setUnoCalled(false);
			p.setDealer(false);
		}
		drawDeck = new Deck();
		drawDeck.insert();
		drawDeck.shuffle();
		discZone = new Discard_Zone();
		//set the winner of the round to be the dealer
		winnerOfTheRound.setDealer(true);
		
	}
	
	public void turnReport() {
		//String playerStatus = "";
		System.out.println("--------------------MMMMMMMMMMMMMMMMMMMMM--------------------");
		System.out.println(getPlayer(1).getName() + " is the next player");
		System.out.println("--------------------WWWWWWWWWWWWWWWWWWWWW--------------------");
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
			//else
				//System.out.println(p.getName() + ": Turn forfeited");
		}
		if (p.numOfCardHolding() == 1)
			p.sayUno();
		//check if uno should be called
		unoDetect(getPlayer(1));
	}
	
	public void flipCard() throws InterruptedException {
		getPlayer(1).drawCard(drawDeck, discZone);
		Card tempCard = getPlayer(1).takeCard(getPlayer(1).numOfCardHolding() - 1);
		System.out.println(tempCard + "was flipped over by the dealer");
		if (tempCard instanceof Wild_Card) {
			if (tempCard.cardEffect().compareTo("No effect") == 0) {
				//if it's a wild card, the player left of the dealer pick a color and plays
				getPlayer(2).wildPickColor(tempCard);
			}
			else if (tempCard.cardEffect().compareTo("Draw 4") == 0) {
				//if the top card flipped over by the dealer is a "draw 4 wild", 
				//the card is placed back in deck and draw another card
				System.out.println(tempCard + "was flipped over, so the dealer will flip again");
				drawDeck.insert(tempCard);
				//flip for another time and will not insert this card
				flipCard();
				return;
			}	
			Thread.sleep(5000);
		}
		else if (tempCard instanceof Action_Card) {
			if (tempCard.cardEffect().compareTo("Reverse") == 0) {
				//if it's a reverse card, the dealer goes first
				getPlayer(1).setActive(true);
				printOrder();
			}
			else if (tempCard.cardEffect().compareTo("Skip") == 0) {
				//if it's a skip, player to dealer's left misses a turn
				nextTurn();
				printOrder();
			}
			else if (tempCard.cardEffect().compareTo("Draw 2") == 0) {
				//if it's a draw 2 card, the player left of the dealer draw 2 cards and skips their turn
				getPlayer(2).drawCard(drawDeck, discZone);
				getPlayer(2).drawCard(drawDeck, discZone);
				nextTurn();
				printOrder();
			}	
		}
		discZone.insert(tempCard);
	}
	
	public void initializeGame() {
		int r1, r2, temp;
		System.out.println("Welcome to the game UNO, "
				+ "\nthis game allows one human player and 1 to 3 bot players to participate, and uses the official uno rule");
		for (int i = 0; i < players.size(); i ++) 
			orderOfPlay.add(i + 1);
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
			if (!p.unoPenalty()) {
				p.drawCard(drawDeck, discZone);
				p.drawCard(drawDeck, discZone);	
				System.out.println(p.getName() + " didn't call uno, draw 2 cards");
			}
		}
	}
	
	public void printOrder() {
		String order = "The current order is: \n";
		for (int i = 1; i <= players.size(); i ++) 
			order += i + ". " + getPlayer(i).getName() + "\n";
		System.out.print(order);
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
			System.out.println(player.getName() + " is skipped");
		}
		else if (discZone.getTopCard().cardEffect().compareTo("Draw 2") == 0) {
			player.drawCard(drawDeck, discZone);
			player.drawCard(drawDeck, discZone);
			nextTurn();
			System.out.println(player.getName() + " is skipped");
		}
		else if (discZone.getTopCard().cardEffect().compareTo("Reverse") == 0) {
			reverseOrder();	
			System.out.println("The order is reversed");
		}
		else if (discZone.getTopCard().cardEffect().compareTo("Draw 4") == 0) {
			for (int i = 0; i < 4; i ++)
				player.drawCard(drawDeck, discZone);
			nextTurn();
			System.out.println(player.getName() + " is skipped");
		}
	}
}
