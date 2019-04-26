
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
		String output = "";
		output += ("--------------------\n");
		output += ("|                  |\n");
		output += ("|                  |\n");
		output += ("|                  |\n");
		if (cardColor() == "yellow") {
    		output += ("|      Yellow      |\n");
    		output += ("|        " + number + "         |\n");
    	} else if (cardColor() == "red") {
    		output += ("|       Red        |\n");
    		output += ("|        " + number + "         |\n");
    	}
    	else if (cardColor() == "green") {
    		output += ("|       Green      |\n");
    		output += ("|         " + number + "        |\n");
    	}
    	else if (cardColor() == "blue") {
    		output += ("|       Blue       |\n");
    		output += ("|        " + number + "         |\n");
    	}
		output += ("|                  |\n");
		output += ("|                  |\n");
		output += ("|                  |\n");
		output += ("|                  |\n");
		output += ("--------------------\n");
		return output;
	}
}
