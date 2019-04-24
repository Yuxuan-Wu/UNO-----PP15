import java.util.ArrayList;

public class Deck {
	//be able to shuffle
	//insert the right amount of cards
	//show the last card dealt
	protected ArrayList<Card> deck;
	
	public Deck() {
		deck = new ArrayList<Card>();
	}
	
	public void insert() {
		//insert number cards
		insertNumberCard(0);
		for (int i = 1; i < 10; i ++) {
			insertNumberCard(i);
			insertNumberCard(i);
		}
		//insert action cards
		for (int i = 0; i < 2; i ++) {
			insertActionCard("Draw 2");
			insertActionCard("Reverse");
			insertActionCard("Skip");
		}
		//insert wild cards
		for (int i = 0; i < 4; i ++) {
			deck.add(new Wild_Card(50, true, "No effect"));
			deck.add(new Wild_Card(50, true, "Draw 4"));
		}
	}
	
	private void insertActionCard(String effect) {
		deck.add(new Action_Card(20, true, "red", effect));
		deck.add(new Action_Card(20, true, "yellow", effect));
		deck.add(new Action_Card(20, true, "blue", effect));
		deck.add(new Action_Card(20, true, "green", effect));
	}
	
	private void insertNumberCard(int value) {
		deck.add(new Number_Card(value, true, "red", value));
		deck.add(new Number_Card(value, true, "yellow", value));
		deck.add(new Number_Card(value, true, "blue", value));
		deck.add(new Number_Card(value, true, "green", value));
	}
	
	public void insert(Card c) {
        deck.add(0, c);
    }
	
	public void shuffle() {
		int r1;
		int r2;
		Card tempCard;
		for (int i = 0; i < deck.size(); i++) {
			r1 = (int)(Math.random()*200%deck.size());
			r2 = (int)(Math.random()*300/2%deck.size());
			if (r1 == r2) {
				r1 = (int)((Math.random()*31+30)%deck.size());
				r2 = (int)((Math.random()*240+15)%deck.size());
			}
			tempCard = deck.get(r1);
			deck.set(r1, deck.get(r2));
			deck.set(r2, tempCard);
 		}
		//set all card to be effective
		for (int i = 0; i < deck.size(); i ++)
			deck.get(i).setEffective(true);
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}
	
	public Card getTopCard() {
        return deck.remove(deck.size() - 1);
    }
	
	public String toString() {
		String output = ""; 
		for(int i = 0; i < deck.size(); i ++) {
			output += deck.get(i) + " " + i + "\n";
		}
		return output;
	}
}
