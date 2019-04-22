import java.util.Scanner;

public class Wild_Card extends Card{
    
    public Wild_Card (int score, boolean drawable, String effect) {
        super(score, drawable, "Not determined", effect);
    }
    
    public void setColor(String c) {
    	super.color = c;
    }
    
    public String cardColor() {
    	if (cardStatus()) {
    		Scanner scan = new Scanner(System.in);
    		System.out.println("What would you like the new color to be? (Red, Yellow, Blue, Green)");
    		super.color = scan.nextLine();
    	}
    	return super.color;
    }
    
    public String toString() {
		return "Wild card with " + super.effect;
	}
}