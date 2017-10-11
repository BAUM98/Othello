import java.util.ArrayList;
import java.util.Scanner;

public class Player {

	private String color;
	private boolean isAI;
	private String line = "-----------------------------------";
	private String error = line + "\n       Error: Invalid Input!\n" + line;

	public Player(String c) {
		color = c;
	}

	/**
	 * This method asks the player to make a move and makes the move if it is valid.
	 * 
	 * @param b
	 *            Current Board
	 * @param choice
	 *            Which game mode, 1 player, 2 player, or simulation, is being
	 *            played
	 * @return Returns true if the player passed or moved, false if the player
	 *         wishes to quit
	 */
	public boolean makeMove(Board b, int choice) {
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
				if (isAI && choice != 0)
					System.out.println(b.line + "\n\t  AI moved at " + entry + "\n" + b.line);
				break;
			}
		}
		return returnValue;
	}

	private String returnMove(Board b) {
		if (isAI)
			return returnMoveAI(b);
		else
			return returnMovePlayer(b);
	}

	private String returnMovePlayer(Board b) {
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

	private String returnMoveAI(Board b) {
		int index;
		ArrayList<String> list = allValidMoves(b);
		index = (int) (list.size() * Math.random());
		return list.get(index);
	}

	private ArrayList<String> allValidMoves(Board b) {
		ArrayList<String> list = new ArrayList<String>();
		boolean valid = false;
		String coord;
		for (int i = 0; i < b.TOTAL_ROWS; i++) {
			for (int j = 0; j < b.TOTAL_COLUMNS; j++) {
				valid = b.validPlacement(i, j, color.charAt(0));
				if (valid) {
					i++;
					j++;
					coord = i + " " + j;
					list.add(coord);
				}
			}
		}
		return list;
	}

	private String getEntry() {
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}

	/**
	 * Prompts the player what game mode, 1 player, 2 player, or simulation, they
	 * wish to play
	 * 
	 * @param two
	 *            the other player
	 * @return Returns 1 for 1 player, 2 for 2 player, or 0 for simulation
	 */
	public int numOfPlayers(Player two) {
		String entry;
		boolean validPlayerEntry = false;
		while (!validPlayerEntry) {
			System.out.println("1 player, 2 players or Simulation?");
			entry = getEntry();
			entry = entry.toLowerCase();
			switch (entry) {
			case "1":
				two.makeAI();
				return 1;
			case "2":
				return 2;
			case "simulation":
				this.makeAI();
				two.makeAI();
				return 0;
			}
			System.out.println(error);
		}
		return -1; // dummy return, will never actually occur
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

	private boolean submitCoords(Board b, String entry) {
		int r = Integer.parseInt(entry.substring(0, 1));
		int c = Integer.parseInt(entry.substring(2));
		return b.setPiece(r, c, color);
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

	/**
	 * This method switches the current player
	 * 
	 * @param one
	 *            First player
	 * @param two
	 *            Second player
	 * @param curr
	 *            Current player
	 * @return returns the player whose turn is next
	 */
	public Player nextTurn(Player one, Player two, Player curr) {
		if (curr == one)
			return two;
		else
			return one;

	}

	/**
	 * This method makes a player an AI
	 */
	public void makeAI() {
		isAI = true;
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
		if (isAI)
			return true;
		else {
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
			}
			return true;
		}

	}
}
