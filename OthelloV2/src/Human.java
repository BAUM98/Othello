import java.util.ArrayList;
import java.util.Scanner;

public class Human extends Player {
	private String line = "-----------------------------------";
	private String error = line + "\n       Error: Invalid Input!\n" + line;

	public Human(String c, int num) {
		color = c;
		numOfAI = num;
	}

	/**
	 * This method gets a move from a player and makes the desired move, 
	 * either pass, hint, quit, or a set of coords
	 * 
	 * @return true if move was made or passed, false if the player wants to quit
	 */
	public boolean makeMove(Board b) {
		boolean moveMade = false;
		boolean returnValue = true;
		String entry;
		while (!moveMade) {
			entry = returnMove(b);
			switch (moveType(entry)) {
			case 1:
				moveMade = true;
				break;
			case 2:
				moveMade = true;
				returnValue = false;
				break;
			case 3:
				getHint(b);
				break;
			case 4:
				moveMade = submitCoords(b, entry);
				break;
			}
		}
		return returnValue;
	}

	/**
	 * Gets a move from the user, and returns the move
	 * 
	 * @return The move the user would like to make, either a hint, pass, quit, or a
	 *         set of coords
	 */
	protected String returnMove(Board b) {
		b.printBoard();
		System.out.println("It is " + color + "'s turn");
		boolean validMove = false;
		String entry = "";
		while (!validMove) {
			System.out.println(
					"-Enter pass to skip turn\n-Enter quit to end game\n-Enter hint for a move suggestion\n-Or enter desired move with the form:\n"
							+ "Row Column");
			entry = getEntry();
			entry.toLowerCase();
			if (isMovePass(entry) || isMoveQuit(entry) || isMoveHint(entry) || isMoveCoords(entry))
				validMove = true;
			else {
				System.out.println(error);
				b.printBoard();
			}
		}
		return entry;
	}

	private String getEntry() {
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}

	/**
	 * Gets coords from string and attempts to place a piece on the board
	 * 
	 * @return returns true if the move was made, false if it is invalid
	 */
	protected boolean submitCoords(Board b, String entry) {
		int r = Integer.parseInt(entry.substring(0, 1));
		int c = Integer.parseInt(entry.substring(2));
		return b.setPiece(r, c, color);
	}

	private int moveType(String entry) {
		if (isMovePass(entry))
			return 1;
		else if (isMoveQuit(entry))
			return 2;
		else if (isMoveHint(entry))
			return 3;
		else
			return 4;
	}

	private boolean isMovePass(String entry) {
		if (entry.equals("pass"))
			return true;
		return false;
	}

	private boolean isMoveQuit(String entry) {
		if (entry.equals("quit"))
			return true;
		return false;
	}

	private boolean isMoveHint(String entry) {
		if (entry.equals("hint"))
			return true;
		return false;
	}

	private boolean isMoveCoords(String entry) {
		int r, c;
		if (entry.length() != 3)
			return false;
		if (!entry.substring(1, 2).equals(" "))
			return false;
		try {
			r = Integer.parseInt(entry.substring(0, 1));
			c = Integer.parseInt(entry.substring(2));
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private void getHint(Board b) {
		b.validMovesLeft(color, true);
	}

	/**
	 * This method returns the color of the player
	 * 
	 * @return the Players color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Asks a player if they wish to play another game
	 * 
	 * @return Returns true if they do, false if they don't
	 */
	public boolean playAgain() {
		boolean validEntry = false;
		String entry;
		System.out.println(color + ", do you want to play again?");
		while (!validEntry) {
			entry = getEntry();
			entry = entry.toLowerCase();
			if (entry.equals("yes"))
				return true;
			else if (entry.equals("no"))
				return false;
			else
				System.out.println(error);
			return true;
		}
		return false;

	}

}
