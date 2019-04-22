import java.util.ArrayList;
import java.util.Scanner;
public abstract class Player {
   private String name, status;
   private int score;
   private boolean dealer, unoCalled;
   private ArrayList<Card> cardInHand;
   
   public Player (String name, String status, int score, boolean isDealer) {
	   this.cardInHand = new ArrayList<Card>();
	   this.name = name;
	   this.status = status;
	   this.score = score;
	   this.dealer = isDealer;
	   unoCalled = false;
   }
   
   public String getStatus() {
	   return status;
   }
   
   public void changeStatus(String newStatus) {
	   status = newStatus;
   }
   
   public void drawCard(Deck d) {
       cardInHand.add(d.getTopCard());
   }
   
   public void wildPickColor(Wild_Card w) {
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
   }
   
   public boolean playCard(Discard_Zone a) {
	   int randomFactor;
	   ArrayList<Integer> avaliableCards = checkCardInHand(a);
	   if (avaliableCards.size() == 0)
		   return false;
	   else {
		   randomFactor = (int) (Math.random()*(avaliableCards.size() + 1));
		   a.insert(cardInHand.remove(avaliableCards.get(randomFactor).intValue()));
		   return true;
	   }
   }
   
   public boolean check (Card c, Discard_Zone a) {
	   if(c instanceof Wild_Card) {
		   c.cardColor();
		   return true;
	   }
	   else if(c instanceof Number_Card && a.getTopCard() instanceof Number_Card) {
		   if (c.cardColor() == a.getTopCard().cardColor() || c.cardNum() == a.getTopCard().cardNum()) 
			   return true;
	   	   else
	   		   return false;
	   }
	   else if(c instanceof Action_Card && a.getTopCard() instanceof Action_Card) {
		   if (c.cardColor() == a.getTopCard().cardColor() || c.cardEffect() == a.getTopCard().cardEffect()) 
			   return true;
		   else
			   return false;
	   }
	   else
		   if (a.getTopCard().cardColor() == c.cardColor())
			   return true;
		   else
			   return false;
   }
   
   public ArrayList<Integer> checkCardInHand(Discard_Zone a) {
	   ArrayList<Integer> cardsAvailable = new ArrayList<Integer>();
	   for (int i = 0; i < cardInHand.size(); i ++) {
		   if (check(cardInHand.get(i), a))
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
   
   public void addEffect(Card c) {
	   status = c.cardEffect();
   }
   
   public int getScore() {
	   return score;
   }
   
   public void setScore(int score) {
	   this.score += score;
   }
   
   public int numOfCardHolding() {
	   return cardInHand.size();
   }
   
   public int calculatePoints() {
	   int totalPoint = 0;
	   for (int i = 0; i < cardInHand.size(); i ++) 
		   totalPoint += cardInHand.get(i).cardScore();
	   return totalPoint;
   }
}
