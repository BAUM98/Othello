/**
 * This class provides a piece object, as well as the ability to change who the
 * piece belongs to and find out who it belongs to with a variety of methods.
 * 
 * @author Alex Baumgartner
 *
 */
public class Piece {

	private char colorChar; // Character that holds either 'B', 'W', or ' ' which represent which player the
							// piece belongs to.

	/**
	 * Initializes a Piece object as a blank piece.
	 */
	public Piece() {
		colorChar = ' ';
	}

	/**
	 * This method simply returns the first character of the player the piece
	 * belongs to.
	 * 
	 * @return colorChar The color of the piece.
	 */
	public char getColor() {
		return colorChar;
	}

	/**
	 * This method sets a piece's owner to White.
	 */
	public void setWhite() {
		colorChar = 'W';
	}

	/**
	 * This method sets a piece's owner to Black.
	 */
	public void setBlack() {
		colorChar = 'B';
	}

	/**
	 * This method sets a piece's owner to that of the passed in player character.
	 * 
	 * @param c
	 *            First character of the current player.
	 */
	public void place(char c) {
		colorChar = c;
	}

	/**
	 * Returns the opposite color character of the piece, so if if the piece is
	 * owned by Black, it returns W, and if the owner is White it returns 'B'. If
	 * nobody owns the piece, it returns ' '.
	 * 
	 * @return The opposite color char or ' '.
	 */
	public char oppositeColor() {
		if (colorChar == 'B')
			return 'W';
		else if (colorChar == 'W')
			return 'B';
		else
			return ' ';

	}

	/**
	 * "Flips" the current piece to the opposite color based on its current owner.
	 */
	public void flip() {
		if (colorChar == 'B')
			colorChar = 'W';
		else if (colorChar == 'W')
			colorChar = 'B';
	}
}
