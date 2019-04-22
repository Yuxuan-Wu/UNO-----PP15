import java.util.Scanner;

public class Human_Player extends Player{

	public Human_Player(String name, String status, int score, boolean isDealer) {
		super(name, status, score, isDealer);
	}
	
	//a human player should be able to:
	//pick a color for a wild card, 
	//choose a card to deal, 
	//call uno, 
	//
	
	public void wildPickColor() {
		
	}
	
	/*
	public boolean playCard(Discard_Zone a) {
	       int x;
	       System.out.println("it is your turn. Which card would you like to play? (Enter a number between 1 and the number of cards you are currently holding)");
	       Scanner sc = new Scanner(System.in);
	       x = sc.nextInt();
	       if (check(cardInHand.get(x - 1), a)) {
	           a.insert(cardInHand.remove(x - 1));
	       }
	       sc.close();
	}*/
}
