
public class Wild_Card extends Card{
    
    public Wild_Card (int score, boolean drawable, String effect) {
        super(score, drawable, "Not determined", effect);
    }
    
    public void setColor(String c) {
    	super.color = c;
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
		if (cardEffect().compareTo("No effect") == 0) 
			output += ("|       Wild       |\n");
		else 
			output += ("|    Wild draw 4   |\n");
		output += ("|                  |\n");
		output += ("|                  |\n");
		output += ("|                  |\n");
		output += ("|                  |\n");
		output += ("--------------------\n");
		return output;
	}
}