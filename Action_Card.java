
public class Action_Card extends Card {
	
	public Action_Card (int score, boolean drawable, String color, String effect) {
		super(score, drawable, color, effect);
	}
	
	public String toString() {
		return cardColor() + " action card with " + cardEffect();
	}
}
