import java.util.ArrayList;
import java.util.Scanner;
public abstract class Player {
   private String name;
   private int score;
   private boolean dealer, unoCalled, active;
   protected ArrayList<Card> cardInHand;
   
   public Player (String name) {
	   this.name = name;
	   score = 0;
	   cardInHand = new ArrayList<Card>();
	   dealer = false;
	   unoCalled = false;
	   active = false;
   }
   
   public void drawCard(Deck drawDeck, Deck discZone) {
	   if (drawDeck.getDeck().size() > 0)
		   cardInHand.add(drawDeck.getTopCard());
	   else {
		   //if draw deck is empty, replenish from the discard zone
		   swapDeck(drawDeck, discZone);
		   cardInHand.add(drawDeck.getTopCard());
	   }
   }
   
   public void swapDeck(Deck drawDeck, Deck discZone) {
		//swap drawDeck with discardZone
		drawDeck.setDeck(discZone.getDeck());
		drawDeck.shuffle();
		discZone.setDeck(new ArrayList<Card>());
		System.out.println("!\n!\n!\n!\n\n\n\n\n\n\n\n\n\n\n\n");
	}
   
   public void wildPickColor(Card w) {
	   int randomFactor = 0;
	   randomFactor = (int) (Math.random()*5);
	   switch(randomFactor) {
	   case 1: w.setColor("red");
	   break;
	   case 2: w.setColor("green");
	   break;
	   case 3: w.setColor("yellow");
	   break;
	   case 4: w.setColor("blue");
	   break;
	   }
	   System.out.println("The wild card has color of: " + w.cardColor());
   }
   
   public boolean playCard(Deck a) {
	   int randomFactor;
	   ArrayList<Integer> avaliableCards = checkCardInHand(a);
	   if (avaliableCards.size() != 0) {
		   randomFactor = (int) (Math.random()*avaliableCards.size());
		   if (cardInHand.get(avaliableCards.get(randomFactor)) instanceof Wild_Card) {
			   wildPickColor(cardInHand.get(avaliableCards.get(randomFactor)));
			   a.insert(cardInHand.remove(avaliableCards.get(randomFactor).intValue()));
		   }			   
		   else
			   a.insert(cardInHand.remove(avaliableCards.get(randomFactor).intValue()));
	   	   System.out.println(name + ": A " + a.getTopCard() + " was placed on the discard zone");
	   	   return true;
	   }
	   System.out.println(name + ": No card could be played");
	   return false;
   }
   
   public boolean check (Card c, Deck a) {
	   if(c instanceof Wild_Card) {
		   return true;
	   }
	   else if(c instanceof Number_Card && a.getTopCard() instanceof Number_Card) {
		   if (c.cardColor().compareTo(a.getTopCard().cardColor()) == 0 || c.cardNum() == a.getTopCard().cardNum()) 
			   return true;
	   	   else
	   		   return false;
	   }
	   else if(c instanceof Action_Card && a.getTopCard() instanceof Action_Card) {
		   if (c.cardColor().compareTo(a.getTopCard().cardColor()) == 0 || c.cardEffect().compareTo(a.getTopCard().cardEffect()) == 0) 
			   return true;
		   else
			   return false;
	   }
	   else
		   if (c.cardColor().compareTo(a.getTopCard().cardColor()) == 0)
			   return true;
		   else
			   return false;
   }
   
   public ArrayList<Integer> checkCardInHand(Deck a) {
	   ArrayList<Integer> cardsAvailable = new ArrayList<Integer>();
	   boolean matchingCard = false;
	   for (int i = 0; i < cardInHand.size(); i ++) {
		   if (cardInHand.get(i).cardEffect().compareTo("Draw 4") == 0) {
			   for (int j = 0; j < cardInHand.size(); j ++) {
				   if (cardInHand.get(j).cardColor().compareTo(a.getTopCard().cardColor()) == 0)
					   matchingCard = true;
			   }
			   if (!matchingCard) {
				   cardsAvailable.add(i);
				   matchingCard = false;
			   }
		   }
		   else if (check(cardInHand.get(i), a))
			   cardsAvailable.add(i);
	   }
	   return cardsAvailable;
   }
   
   public void setDealer(boolean dealer) {
	   this.dealer = dealer;
   }
   
   public boolean isDealer() {
	   return dealer;
   }
   
   public int getScore() {
	   return score;
   }
   
   public void setScore(int score) {
	   this.score = score;
   }
   
   public int numOfCardHolding() {
	   return cardInHand.size();
   }
   
   public Card takeCard(int index) {
	   return cardInHand.remove(index);
   }
   
   public int calculatePoints() {
	   int totalPoint = 0;
	   for (int i = 0; i < cardInHand.size(); i ++) 
		   totalPoint += cardInHand.get(i).cardScore();
	   return totalPoint;
   }
   
   public boolean calledUno() {
	   return unoCalled;
   }
   
   public void setActive(boolean active) {
	   this.active = active;
   }
   
   public boolean isActive() {
	   return active;
   }
   
   public String getName() {
	   return name;
   }
}
