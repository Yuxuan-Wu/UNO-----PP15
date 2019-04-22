
public abstract class Card {
	//A UNO deck consists of 108 cards, of which there are 76 Number cards, 24 Action cards and 8 Wild cards.
	//attributes: score, drawable, color
	private int score;
	private boolean drawable;
	protected String color, effect;
	
	//constructor for all types of card
	public Card(int score, boolean drawable, String color, String effect) {
		this.score = score;
		this.drawable = drawable;
		this.color = color;
		this.effect = effect;
	}
	
	public String cardColor() {
		return color;
	}
	
	public void updtCardStatus(boolean newStatus) {
		drawable = newStatus;
	}
	
	//true if drawable, if it's in the discard zone, change to false
	public boolean cardStatus() {
		return drawable;
	}
	
	public String cardEffect() {
    	return effect;
    }
	
	public int cardScore() {
		return score;
	}
	
	public int cardNum() {
		return -1;
	}
	
	public String toString() {
		return color; 
	}
}
