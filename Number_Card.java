
public class Number_Card extends Card {
	//local attribute: number
	private int number;
	
	public Number_Card(int score, boolean drawable, String color, int number) {
		super(score, drawable, color, "No effect");
		this.number = number;
	}
	
	public int cardNum() {
		return number;
	}
	
	public String toString() {
		return cardColor() + " number card with value of " + number;
	}
}
