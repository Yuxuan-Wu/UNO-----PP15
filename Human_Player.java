import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
/*
 * this method is a constructor method that initializes all variables and makes a call to the class PLayer by using the key word super
 * this method also includes the declaration of a timer 
 */
public class Human_Player extends Player{
	private Timer timer;
	private TimerTask task;
	private int secondsPassed;
	public Human_Player(String name) {
		super(name);
		timer = new Timer();
		task = new TimerTask() {
			public void run() {
				secondsPassed = 0;
				System.out.println("Seconds Passed: " + secondsPassed);
			}
		};
	}
	
	/*
	 * this method allows the human player to choose a color based on their liking 
	 * Card w - the current card that the player is playing and that determines the color 
	 */
	public void wildPickColor(Card w) {
		Scanner sc = new Scanner(System.in);
		System.out.println("What would you like your color to be? (Enter red, blue, green, or yellow)");
		String x = sc.nextLine();
		if (x == "red") {
			w.setColor("red");
			System.out.println("The color is now red");
		} else if (x == "blue") {
			w.setColor("blue");
			System.out.println("The color is now blue");
		} else if (x == "green") {
			w.setColor("green");
			System.out.println("The color is now green");
		} else if (x == "yellow") {
			System.out.println("The color is now yellow");
			w.setColor("yellow");
		}
		sc.close();
	}
	/*
	 * this method has a timer that is defined in the constructor and called in this method and basically the human player has 10 seconds to say uno and if they don't, unoCalled stays at false and the player gets a penalty
	 * the second line of code in this method determines how often the timer ticks 
	 */
	public void sayUno() {
		Scanner sc = new Scanner(System.in);
		timer.scheduleAtFixedRate(task, 1000, 1000);
		while (secondsPassed < 10) {
			if (sc.nextLine() == "uno" || sc.nextLine() == "Uno") {
				setUnoCalled(true);
				System.out.println(getName() + " said Uno!");
			}
		}	
	}
	/*
	 * this method allows the player to play a card of their choosing 
	 * Deck a - the current deck of cards that the player is going to play a card onto 
	 * returns true if the player can play the current card base on its color, effect, or number and false otherwise
	 */
	public boolean playCard(Deck a) {
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
	}
}