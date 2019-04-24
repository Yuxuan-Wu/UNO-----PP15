import java.util.Scanner;

public class Human_Player extends Player{

	public Human_Player(String name) {
		super(name);
	}
	//a human player should be able to:
	//pick a color for a wild card, 
	//choose a card to deal, 
	//call uno, 
	//
	
	//public void wildPickColor(Card w) {}

	//public void sayUno() {}
	
	/*public boolean playCard(Deck a) {
	       int x = 0;
	       String availableCards = "";
	       System.out.println("it is your turn. Which card would you like to play? (Enter a number between 1 and the number of cards you are currently holding)");
	       for (Integer b : checkCardInHand(a)) {
	    	   availableCards += cardInHand.get(b.intValue()) + " with index of: " + b.intValue() + "\n";
	       }
	       System.out.println(availableCards);
	       Scanner sc = new Scanner(System.in);
	       if (sc.hasNextLine())
	    	   x = Integer.parseInt(sc.nextLine());
	       if (check(cardInHand.get(x), a)) {
	           a.insert(cardInHand.remove(x));
	           sc.close();
	           return true;
	       }
	       sc.close();
	       return false;
	}*/
}
