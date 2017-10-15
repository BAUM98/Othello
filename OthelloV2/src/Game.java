
/**
 * Main game method, has methods to run a game, 
 * ask for what time of game is to be played, 
 * and can switch the current player.
 * 
 * @author Alexander Baumgartner
 */

import java.util.Scanner;

public class Game {
	/**
	 * Asks the user how many players they wish to play with
	 * 
	 * @return returns 0 if 2 player, 1 if 1 player, 2 if both players should be
	 *         AI's
	 */
	public int gameType() {
		String entry;
		String line = "-----------------------------------";
		String error = line + "\n       Error: Invalid Input!\n" + line;
		boolean validPlayerEntry = false;
		while (!validPlayerEntry) {
			System.out.println("1 player, 2 players or Simulation?");
			entry = this.getEntry();
			entry = entry.toLowerCase();
			switch (entry) {
			case "1":
				return 1;
			case "2":
				return 0;
			case "simulation":
				return 2;
			}
			System.out.println(error);
		}
		return -1; // dummy return, will never actually occur
	}

	private String getEntry() {
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}

	/**
	 * This method switches the current player
	 * 
	 * @param one
	 *            first player
	 * @param two
	 *            second player
	 * @param curr
	 *            current player object
	 * @return the new current player
	 */
	public Player nextTurn(Player one, Player two, Player curr) {
		if (curr == one)
			return two;
		else
			return one;
	}

	public static void main(String[] args) {
		boolean playAgain = true;
		boolean stillValidMoves = true;
		boolean moved = true;
		Game game = new Game();
		Player playerOne = new Human("Black", 0);
		Player playerTwo = new Human("White", 0);
		Scanner scan = new Scanner(System.in);
		int choice = 0, counter = 0, endNumberGames = -1, winner = 0, blackScore = 0, whiteScore = 0, check = 0;
		double percent = 0;
		int[] frequency = new int[128];
		// Check to see how many players
		choice = game.gameType();
		switch (choice) {
		case 0:
			break;
		case 1:
			playerOne = new Human("Black", choice);
			playerTwo = new Computer("White", choice);
			break;
		case 2:
			playerOne = new Computer("Black", choice);
			playerTwo = new Computer("White", choice);
			System.out.println("How many games would you like to run?");
			endNumberGames = scan.nextInt();
			System.out.println("Simulating...");
			break;
		}
		while (playAgain) {
			Player currentPlayer = playerOne;
			Board b = new Board();
			stillValidMoves = true;
			while (stillValidMoves) {
				currentPlayer.makeMove(b);
				if (!moved)
					break;
				currentPlayer = game.nextTurn(playerOne, playerTwo, currentPlayer);
				check = b.validMovesForPlayers(playerOne, playerTwo, currentPlayer);
				switch (check) {
				case -1:
					break;
				case 0:
					stillValidMoves = false;
					break;
				case 1:
				case 2:
					currentPlayer = game.nextTurn(playerOne, playerTwo, currentPlayer);
					break;
				}
			} // end inner While
			if (choice != 2) {
				b.printBoard();
				playAgain = b.finalScore(playerOne, playerTwo);
			} else {
				whiteScore = b.score("White");
				blackScore = b.score("Black");
				frequency[blackScore - whiteScore + 64]++;
				counter++;
				if (counter == endNumberGames) {
					playAgain = false;
				} else if (counter % 1000 == 0)
					System.out.println(counter + " runs");
			}
		} // end outer While
		if (choice != 2)
			System.out.println("Thanks for playing!");
		else {
			System.out.println("Spread\tOccurences");
			for (int i = 0; i < frequency.length; i++) {
				if (frequency[i] != 0) {
					System.out.println((i - 64) + "\t" + frequency[i]);
				}

			}
		}
	}

}
