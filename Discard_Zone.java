
public class Discard_Zone extends Deck{
    Card lastCardPlayed;
	
    public Discard_Zone() {
        super();
    }
    
    public void insert(Card c) {
        deck.add(c);
    }
    
    public Card getTopCard() {
    	if (deck.size() > 0) {
    		lastCardPlayed = deck.get(deck.size() - 1);
    		return deck.get(deck.size() - 1);
    	}
    	else 
    		return lastCardPlayed;
    }
}