import java.util.ArrayList;
import java.util.Scanner;

public class Player {

	private String color;
	private boolean currentPlayer;
	private boolean isAI;

	public Player(String c) {
		color = c;
		currentPlayer = false;
	}



	public String returnMove(Board b) {
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
		System.out.println(
				"-Enter pass to skip turn\n-Enter quit to end game\n-Enter hint for a move suggestion\n-Or enter desired move with the form:\n"
						+ "Row Column");
		while (!validMove) {
			entry = getEntry();
			entry.toLowerCase();
			if (isMovePass(entry) || isMoveQuit(entry) || isMoveHint(entry) || isMoveCoords(entry))
				validMove = true;
			else
				System.out.println("Error: Invalid Entry");
		}
		return entry;
	}

	public String returnMoveAI(Board b) {
		int index;
		ArrayList<String> list = allValidMoves(b);
		index = (int) (list.size() * Math.random());
		return list.get(index);
	}

	private ArrayList<String> allValidMoves(Board b) {
		ArrayList<String> list = new ArrayList<String>();
		boolean valid = false;
		String coord;
		for (int i = 0; i < b.totalRows; i++) {
			for (int j = 0; j < b.totalColumns; j++) {
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

	public String getEntry() {
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();

	}

	public boolean isMovePass(String entry) {
		if (entry.equals("pass"))
			return true;
		return false;
	}

	public boolean isMoveQuit(String entry) {
		if (entry.equals("quit"))
			return true;
		return false;
	}

	public boolean isMoveHint(String entry) {
		if (entry.equals("hint"))
			return true;
		return false;
	}

	public boolean isMoveCoords(String entry) {
		int r;
		int c;
		try {
			r = Integer.parseInt(entry.substring(0, 1));
			c = Integer.parseInt(entry.substring(2));
		} catch (NumberFormatException e) {
			return false;
		}
		if (!entry.substring(1, 2).equals(" ")) {
			System.out.println("Error: Invalid Entry!");
			return false;
		}
		return true;
	}

	public void switchPlayer() {
		currentPlayer = !currentPlayer;
	}

	public boolean isCurrentPlayer() {
		return currentPlayer;
	}

	public boolean stillValidMoves(Board b) {
	return b.validMovesLeft(color, false);
	}

	public void makeAI() {
		isAI = true;
	}
	
	public void getHint(Board b) {
		b.validMovesLeft(color, true);
	}
	
	public String getColor() {
		return color;
	}
}
