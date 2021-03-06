/**
 * This class provides a board object, methods for checking if a move is valid
 * and placing a piece at the valid spot, checking if there are any valid moves
 * left, printing out the the game board and tallying the final score. score.
 * 
 * @author Alex Baumgartner
 *
 */
public class Board {

	private Piece[][] boardArray;
	private String columns = "  | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |";
	protected String line = "-----------------------------------";
	final public int TOTAL_ROWS = 8;
	final public int TOTAL_COLUMNS = 8;

	/**
	 * Initializer that creates a Board object containing Piece objects, where some
	 * are set to white or black for a new board.
	 */
	public Board() {

		// Creates a new array with each index having a piece object of color ' '.
		boardArray = new Piece[TOTAL_ROWS][TOTAL_COLUMNS];
		for (int i = 0; i < boardArray.length; i++) {
			for (int j = 0; j < boardArray[i].length; j++) {
				boardArray[i][j] = new Piece();
			}
		}
		boardArray[3][3].setWhite();
		boardArray[4][4].setWhite();
		boardArray[3][4].setBlack();
		boardArray[4][3].setBlack();
	}

	/**
	 * Prints out the entire 8x8 board with the the characters 'B', 'W', or ' '
	 * representing each piece.
	 */
	public void printBoard() {

		System.out.println(columns);
		for (int i = 0; i < boardArray.length; i++) {
			System.out.println(line);
			System.out.print((i + 1) + " |");
			for (int j = 0; j < boardArray[i].length; j++) {
				System.out.print(" " + boardArray[i][j].getColor() + " |");
			}
			System.out.println("");
		}
	}

	/**
	 * This method tests to see if the desired move is valid. If it is a valid move,
	 * it will flip the necessary pieces, place the new piece, and return true. If
	 * it is not a valid move it will return false.
	 * 
	 * @param r
	 *            The row a piece is desired to be placed at.
	 * @param c
	 *            The column a piece is desired to be placed at.
	 * @param player
	 *            The current player name.
	 */
	public boolean setPiece(int r, int c, String player) {
		char currentPlayerChar = player.charAt(0);
		// Put the desired row and column in terms of array dimensions.
		r--;
		c--;
		boolean validMove = validPlacement(r, c, currentPlayerChar);

		if (validMove) {
			boardArray[r][c].place(currentPlayerChar);
			return !(checkFlipsAndFlip(r, c, currentPlayerChar, validMove)); // Flips the eligible pieces
		} else {
			System.out.println(line + "\n\tError: Invalid Move\n" + line);
			return false;
		}
	}

	/**
	 * Method that checks if a desired move for a player is allowed
	 * 
	 * @param r
	 *            - desired row to place piece at, where 0 <= r <= 7
	 * @param c
	 *            - desired column to place piece at, where 0 <= c <= 7
	 * @param currentPlayerChar
	 *            - The first letter of the player trying to make the move
	 * @return returns true if the move is valid, false if it is invalid
	 */
	protected boolean validPlacement(int r, int c, char currentPlayerChar) {
		if ((r > 7 || r < 0) || (c > 7 || c < 0))
			return false;
		else if (boardArray[r][c].getColor() != ' ')
			return false;
		else if (!checkCardinalPieces(r, c, currentPlayerChar)) {
			return false;
		} else if (checkFlipsAndFlip(r, c, currentPlayerChar, false)) {
			return true;
		}
		return false;
	}

	private boolean checkCardinalPieces(int r, int c, char currentPlayerChar) {
		/*
		 * Each direction character initially holds a space character so that each
		 * character is initialized, as they may not be initialized later depending on
		 * the desired placement coordinates.
		 */
		char northPiece = ' ';
		char southPiece = ' ';
		char westPiece = ' ';
		char eastPiece = ' ';

		if ((r - 1) >= 0)
			northPiece = boardArray[r - 1][c].getColor();

		if ((r + 1) < boardArray.length)
			southPiece = boardArray[r + 1][c].getColor();

		if ((c - 1) >= 0)
			westPiece = boardArray[r][c - 1].getColor();

		if ((c + 1) < boardArray[r].length)
			eastPiece = boardArray[r][c + 1].getColor();

		if (currentPlayerChar == northPiece || currentPlayerChar == southPiece || currentPlayerChar == eastPiece
				|| currentPlayerChar == westPiece)
			return false;
		else
			return true;
	}

	private boolean checkFlipsAndFlip(int r, int c, char currentPlayerChar, boolean flipping) {
		int directionR = r;
		int directionC = c;
		int iteratorR = 0;
		int iteratorC = 0;
		// The following boolean values are set to true or false depending on if the
		// index specified in their name is within the array bounds.
		boolean upIsAValidIndex = (r - 1) >= 0;
		boolean downIsAValidIndex = (r + 1) < boardArray.length;
		boolean rightIsAValidIndex = (c + 1) < boardArray[r].length;
		boolean leftIsAValidIndex = (c - 1) >= 0;
		boolean upRightIsAValidIndex = (upIsAValidIndex && rightIsAValidIndex);
		boolean downRightIsAValidIndex = (downIsAValidIndex && rightIsAValidIndex);
		boolean downLeftIsAValidIndex = (downIsAValidIndex && leftIsAValidIndex);
		boolean upLeftIsAValidIndex = (upIsAValidIndex && leftIsAValidIndex);

		if (downIsAValidIndex || upIsAValidIndex || rightIsAValidIndex || leftIsAValidIndex || upRightIsAValidIndex
				|| downRightIsAValidIndex || downLeftIsAValidIndex || upLeftIsAValidIndex) {
			for (int i = 1; i < 9; i++) {
				switch (i) {
				case 1: // Checking North of desired placement.
					directionR = r - 1;
					iteratorR = -1;
					break;
				case 2: // Checking South of desired placement.
					directionR = r + 1;
					iteratorR = 1;
					break;
				case 3: // Checking West of desired placement.
					directionC = c - 1;
					directionR = r;
					iteratorR = 0;
					iteratorC = -1;
					break;
				case 4: // Checking East of desired placement.
					directionC = c + 1;
					directionR = r;
					iteratorR = 0;
					iteratorC = 1;
					break;
				case 5: // Checking Northeast of desired placement.
					directionR = r - 1;
					directionC = c + 1;
					iteratorR = -1;
					iteratorC = 1;
					break;
				case 6: // Checking Southeast of desired placement.
					directionR = r + 1;
					directionC = c + 1;
					iteratorR = 1;
					iteratorC = 1;
					break;
				case 7: // Checking Southwest of desired placement.
					directionR = r + 1;
					directionC = c - 1;
					iteratorR = 1;
					iteratorC = -1;
					break;
				case 8: // Checking Northwest of desired placement.
					directionR = r - 1;
					directionC = c - 1;
					iteratorR = -1;
					iteratorC = -1;
					break;
				}
				int counter = 1; // Counter to see if when a same color piece is found it is not directly next to
									// a piece.
				boolean endPieceFound = false;
				for (int j = directionR, k = directionC; (j < boardArray.length && j >= 0)
						&& (k < boardArray[j].length && k >= 0); j += iteratorR, k += iteratorC) {

					if ((!upIsAValidIndex && i == 1) || (!downIsAValidIndex && i == 2) || (!leftIsAValidIndex && i == 3)
							|| (!rightIsAValidIndex && i == 4) || (!upRightIsAValidIndex && i == 5)
							|| (!downRightIsAValidIndex && i == 6) || (!downLeftIsAValidIndex && i == 7)
							|| (!upLeftIsAValidIndex && i == 8))
						break;
					if (boardArray[j][k].getColor() == ' ')
						break;

					if (boardArray[j][k].getColor() == currentPlayerChar && counter != 1) {
						if (flipping)
							endPieceFound = true; // End piece is set to true so the next for loop knows that a valid
													// flip
													// can be made in this direction.
						else
							return true;
					}
					// If statement breaks the loop if the first piece checked is the same color.
					if (counter == 1 && boardArray[j][k].getColor() == currentPlayerChar)
						break;

					counter++; // Counter increments if no statement was true.
				}
				/*
				 * If statement checks to see if an end piece was found, if one was found, it
				 * runs a for loop to flip the pieces in the direction of the current switch
				 * statement case.
				 */
				if (endPieceFound) {
					for (int j = directionR, k = directionC; (j < boardArray.length && j >= 0)
							&& (k < boardArray[j].length && k >= 0); j += iteratorR, k += iteratorC) {
						if (boardArray[j][k].oppositeColor() == currentPlayerChar)
							boardArray[j][k].flip();
						else
							break;
					}
					endPieceFound = false;
				}
			}
		}
		return false;
	}

	/**
	 * This method checks if either player has a valid move. If the current player
	 * has no valid moves, it will return the opposite player. If both players have
	 * no more moves, it returns a Player who has a color of "Empty"
	 * 
	 * @param one
	 *            First Player
	 * @param two
	 *            Second Player
	 * @param curr
	 *            Player which has the same reference as one or two, represents the
	 *            current player
	 * @return returns the the current player
	 */
	public int validMovesForPlayers(Player one, Player two, Player curr) {
		boolean oneValid = validMovesLeft(one.getColor(), false);
		boolean twoValid = validMovesLeft(two.getColor(), false);

		if (!oneValid && !twoValid)
			return 0;
		else if (!oneValid && one == curr) {
			return 2;
		} else if (!twoValid && two == curr) {
			return 1;
		}
		return -1;
	}

	/**
	 * This method checks each space to see if the specified player has a valid move
	 * there. If a valid move is found, the method immediately returns true, and
	 * returns false if there are no valid moves for the specified player. If the
	 * player passes in true for hint, it will instead print a legal move for the
	 * passed in color.
	 * 
	 * @param player
	 *            color of the player asking if they have any valid moves left
	 * @param hint
	 *            If true, method will print a possible move for the player, If
	 *            false will check to see if there are any valid moves
	 * @return returns true if there are valid moves left, returns false if there
	 *         are no valid moves for the passed in color or if a hint was asked for
	 */
	public boolean validMovesLeft(String player, boolean hint) {
		char currentPlayerChar = player.charAt(0);

		for (int i = 0; i < boardArray.length; i++) {
			for (int j = 0; j < boardArray[i].length; j++) {
				if (boardArray[i][j].getColor() != ' ')
					continue;
				else {
					if (validPlacement(i, j, currentPlayerChar)) {
						if (hint) {
							System.out.println(line + "\n\t     Hint: " + (i + 1) + " " + (j + 1) + "\n" + line);
							return false;
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	//Generate Javadoc
	public int score(String c) {
		int score = 0;
		char colorChar = c.charAt(0);
		for (int i = 0; i < boardArray.length; i++) {
			for (int j = 0; j < boardArray[i].length; j++) {
				if (boardArray[i][j].getColor() == colorChar)
					score++;
			}
		}
		return score;
	}

	private int winner() {
		int whiteScore = score("White");
		int blackScore = score("Black");
		if (blackScore > whiteScore)
			return 0;
		else if (blackScore == whiteScore)
			return 2;
		else
			return 1;

	}

	/**
	 * This method declares which player has won or if a tie has occurred, and then
	 * asks both players if they wish to play again
	 * 
	 * @param one
	 *            Player one
	 * @param two
	 *            Player two
	 * @return returns true if both players want to play again, false if one or both
	 *         don't wish to play again
	 */
	public boolean finalScore(Player one, Player two) {
		int winner = 0;
		System.out.println("Black score: " + score("Black"));
		System.out.println("White score: " + score("White"));
		winner = winner();
		if (winner == 0) {
			System.out.println("Black Wins!");
		} else if (winner == 1) {
			System.out.println("White Wins!");
		} else
			System.out.println("Tie!");
		if (one.playAgain() && two.playAgain())
			return true;
		else
			return false;
	}


}
