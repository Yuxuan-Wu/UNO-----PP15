
public class Discard_Zone extends Deck{
    
    public Discard_Zone() {
        super();
    }
    
    public void insert(Card c) {
        deck.add(c);
    }
    
    public Card getTopCard() {
        return deck.get(deck.size() - 1);
    }
}