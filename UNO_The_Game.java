import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class UNO_The_Game extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Uno g = new Uno(4);
		g.game();
	}

	/**
	 * Create the frame.
	 */
	public UNO_The_Game() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnNewButton.setBounds(59, 62, 93, 23);
		contentPane.add(btnNewButton);
	}
	
	//https://www.jianshu.com/p/232f72f55d79
	//https://blog.csdn.net/qq_28859405/article/details/52562131
	//http://zetcode.com/tutorials/javaswingtutorial/draganddrop/
	//https://www.letsplayuno.com/news/guide/20181213/30092_732567.html
	//create several bot players according to user input
	
	/*
	1. deal 7 cards to each player
	2. object: first player who reach 500 points
	3. object for each round: to be the first player who have empty hand
	4. after dealing the dealer flips over the top card of the deck
	5. beginning clockwise from the dealer
	6. player can only deal one card each turn
	 - a legal play is a card either matches the color number or symbol
	7. after shuffling, 
	 - if the top card flipped over by the dealer is a "draw 4 wild", the card is placed back in deck and draw another card
	 - if it's a wild card, the player left of the dealer pick a color and plays
	 - if it's a draw 2 card, the player left of the dealer draw 2 cards and skips their turn
	 - if it's a reverse card, the dealer goes first
	 - if it's a skip, player to dealer's left misses a turn
	8. skip: the next player forfeit their turn
	9. reverse: changes the direction of the play
	 - In a two-player game, the Reverse card acts like a Skip card; when played, the other player misses a turn.
	10. draw 2: the next player draws two card and forfeit their turn
	11. wild: maybe played on any color, and allows you to pick a color that the next player must follow
	12. draw 4 wild: does the same as the wild and in addition the next player has to draw 4 cards and skip their turn, however a draw 4 wild may only be played when you don't have a card in your hand matching the color played
	13. a wild card represent no color until they are played 
	14. if you can't play in a turn, you draw one card, if that card can be played you may play it immediately, otherwise your turn is forfeited
	15. you may also choose not to play on your turn but instead draw a card, if that card can be played your may only play that card or end your turn
	16. when you get down to one card in your hand you must call uno, if you don't call uno by the time your second-to-last card touches the discard zone, any other player may catch you on this, if they did, before the next player plays, you must draw 2 cards
	17. if the round hasn't ended and the draw deck runs out of cards shuffle the discard pile and it becomes the new draw deck
	18. when a player plays the last card in their hand the round ends, if that card is a draw card the next player still fulfill the drawing, points are then scored 
	19. the player who won that round goes out to receive point for every unplayed card in other players' hands
	 - all cards 0 - 9 are worth face value
	 - action cards are worth 20 points
	 - wild cards are worth 50 points
	20. if the round ends and none of the players have reached to 500 points, play another round
	21. the winner of each round becomes the next rounds' dealer
	*/
	
	
	
	/*
	 * 1. The first player is normally the player to the left of the dealer
	 * 2. gameplay usually follows a clockwise 
	 * 3. You have to match cards either by the number, color, or the symbol/Action
	 * 4. If the player has no matches or they choose not to play any of their cards
	 *  even though they might have a match, they must draw a card from the Draw pile
	 */
}
