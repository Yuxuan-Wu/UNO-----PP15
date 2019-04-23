
public class Wild_Card extends Card{
    
    public Wild_Card (int score, boolean drawable, String effect) {
        super(score, drawable, "Not determined", effect);
    }
    
    public void setColor(String c) {
    	super.color = c;
    }
    
    public String toString() {
		return super.color + " wild card with " + super.effect;
	}
}