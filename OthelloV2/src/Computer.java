import java.util.ArrayList;

public class Computer extends Player {
	public Computer(String c, int num) {
		color = c;
		numOfAI = num;
	}

	/**
	 * Makes a move based on current valid moves
	 * 
	 * @return returns true always
	 */
	@Override
	public boolean makeMove(Board b) {
		boolean moveMade = false;
		boolean returnValue = true;
		String entry;
		while (!moveMade) {
			entry = returnMove(b);
			moveMade = submitCoords(b, entry);
			if (numOfAI == 1)
				System.out.println(b.line + "\n\t  AI moved at " + entry + "\n" + b.line);
		}
		return returnValue;
	}

	/**
	 * Selects a valid move and returns it as a string
	 * 
	 * @return the set of coords
	 */
	@Override
	protected String returnMove(Board b) {
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

	/**
	 * Gets coords from string and attempts to place a piece
	 * 
	 * @return returns true if the move was made, false if it is invalid
	 */
	@Override
	protected boolean submitCoords(Board b, String entry) {
		int r = Integer.parseInt(entry.substring(0, 1));
		int c = Integer.parseInt(entry.substring(2));
		return b.setPiece(r, c, color);
	}

	/**
	 * Returns the players color
	 * 
	 * @return the color
	 */
	@Override
	public String getColor() {
		return color;
	}

	/**
	 * Asks a player if they wish to play another game
	 * 
	 * @return Always returns true
	 */
	@Override
	public boolean playAgain() {
		return true;

	}
}
