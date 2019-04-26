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
   
   public void removeHand() {
	   cardInHand = new ArrayList<Card>();
   }
   
   public void swapDeck(Deck drawDeck, Deck discZone) {
		//swap drawDeck with discardZone
		drawDeck.setDeck(discZone.getDeck());
		drawDeck.shuffle();
		discZone.setDeck(new ArrayList<Card>());
		//System.out.println("!\n!\n!\n!\n\n\n\n\n\n\n\n\n\n\n\n");
	}
   
   public void wildPickColor(Card w) {
	   int randomFactor = 0;
	   randomFactor = (int) (Math.random()*4);
	   switch(randomFactor) {
	   case 0: w.setColor("red");
	   break;
	   case 1: w.setColor("green");
	   break;
	   case 2: w.setColor("yellow");
	   break;
	   case 3: w.setColor("blue");
	   break;
	   }
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
	   	   //System.out.println(name + ": A " + a.getTopCard() + " was placed on the discard zone");
	   	   return true;
	   }
	   else
		   //System.out.println(name + ": No card could be played");
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
	   boolean matchingCard;
	   for (int i = 0; i < cardInHand.size(); i ++) {
		   if (cardInHand.get(i).cardEffect().compareTo("Draw 4") == 0) {
			   matchingCard = false;
			   for (int j = 0; j < cardInHand.size(); j ++) {
				   //if there is no card in hand that has matching color with the top card on the discard zone, Draw 4 Wild can be used
				   if (cardInHand.get(j).cardColor().compareTo(a.getTopCard().cardColor()) == 0 
						   && cardInHand.get(j).cardColor().compareTo("Not determined") != 0)
					   matchingCard = true;
			   }
			   if (!matchingCard) {
				   cardsAvailable.add(i);		   }
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
   
   public String getCardsInHand() {
	   String allCards = name + " have: \n";
	   for (Card c : cardInHand)
		   allCards += c + "\n";
	   return allCards;
   }
   
   /*public String getDealableCardsInHand() {
	   String dealableCards = name + " have: \n";
	   for (int i = 0; i < )
		   allCards += c + "\n";
	   return allCards;
   }*/
   
   public int calculatePoints() {
	   int totalPoint = 0;
	   for (int i = 0; i < cardInHand.size(); i ++) 
		   totalPoint += cardInHand.get(i).cardScore();
	   return totalPoint;
   }
   
   public boolean unoPenalty() {
	   return unoCalled;
   }
   
   public void sayUno() {
	   int randomFactor = (int) (Math.random() * 2);
	   if (randomFactor == 1)
		   unoCalled = true;
	   System.out.println("\u001B[31m" + name + " said uno!" + "\u001B[0m");
   }
   
   public void setUnoCalled(boolean unoCalled) {
	   this.unoCalled = unoCalled;
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
