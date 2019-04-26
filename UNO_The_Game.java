import java.util.Scanner;

/**
 * The runner class
 * @author Charles Wu
 *
 */
public class UNO_The_Game {

	/**
	 * Launch the game Uno
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Uno g = new Uno(4);
		Scanner sc = new Scanner(System.in);
		String userInput = "";
		boolean keepPlaying = true;
		while (keepPlaying) {
			g.game();
			System.out.println("Want to play another game? \"N\" for no, anything else for yes");
			userInput = sc.nextLine();
			if (userInput.compareTo("N") == 0)
				keepPlaying = false;
		}
	}
}
