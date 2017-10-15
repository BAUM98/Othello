/**
 * Abstract class for implementing different player types
 */

import java.util.ArrayList;


public abstract class Player {

	protected String color;
	
	/**
	 * Ask players if they want to play again
	 * @return boolean representing their answer
	 */
	public abstract boolean playAgain();

	/**
	 * Getter for the String color
	 * @return the color String
	 */
	public abstract String getColor();

	/**
	 * Method to submit move coordinates to another class
	 * @param b the game board
	 * @param entry desired move
	 * @return boolean reflecting if move was made 
	 */
	protected abstract boolean submitCoords(Board b, String entry);

	/**
	 * Get entry from a player
	 * @param b game board
	 * @return the entered move
	 */
	protected abstract String returnMove(Board b);

	/**
	 * make a move for the player
	 * @param b game board
	 * @return boolean reflecting if the move was made
	 */
	public abstract boolean makeMove(Board b);

	protected int numOfAI;
	
	/**
	 * Default constructor
	 */
	public Player() {
		super();
	}

}