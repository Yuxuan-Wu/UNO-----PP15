
public class Action_Card extends Card {
	
	public Action_Card (int score, boolean drawable, String color, String effect) {
		super(score, drawable, color, effect);
	}
	
	public String toString() {
		String output = "";
    	output += ("--------------------\n");
		output += ("|                  |\n");
		output += ("|                  |\n");
		output += ("|                  |\n");
		if (cardColor() == "yellow") 
	    	output += ("|      Yellow      |\n");
	    else if (cardColor() == "red") 
	    	output += ("|       Red        |\n");
	   	else if (cardColor() == "green") 
	   		output += ("|       Green      |\n");
	   	else if (cardColor() == "blue") 
	   		output += ("|       Blue       |\n");
	   	else 
    		output += ("|                  |\n");
		if (cardEffect().compareTo("Draw 2") == 0) 
			output += ("|      Draw 2      |\n");
		else if (cardEffect().compareTo("Reverse") == 0)
			output += ("|      Reverse     |\n");
		else if (cardEffect().compareTo("Skip") == 0)
			output += ("|       Skip       |\n");
		output += ("|                  |\n");
		output += ("|                  |\n");
		output += ("|                  |\n");
		output += ("|                  |\n");
		output += ("--------------------\n");
		return output;
	}
}
