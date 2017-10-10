import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		boolean playAgain = true;
		boolean stillValidMoves = true;
		boolean moved = true;
		Player playerOne = new Player("Black");
		Player playerTwo = new Player("White");
		Scanner scan = new Scanner(System.in);
		int choice = 0, counter = 0, endNumberGames = -1, winner = 0, blackWins = 0, whiteWins = 0;
		// Check to see how many players
		choice = playerOne.numOfPlayers(playerTwo);
		if(choice == 0) {
		System.out.println("How many games would you like to run?");
		endNumberGames = scan.nextInt();
		System.out.println("Simulating...");
		}

		while (playAgain) {
			Player currentPlayer = playerOne;
			Board b = new Board();
			stillValidMoves = true;
			while (stillValidMoves) {
				currentPlayer.makeMove(b, choice);
				if (!moved)
					break;
				currentPlayer = currentPlayer.nextTurn(playerOne, playerTwo, currentPlayer);
				currentPlayer = b.validMovesForPlayers(playerOne, playerTwo, currentPlayer);
				if (currentPlayer.getColor().equals("Empty"))
					stillValidMoves = false;
			}
			if (choice != 0) {
				b.printBoard();
				playAgain = b.finalScore(playerOne, playerTwo);
			} else {
				winner = b.winner();
				if (winner == 0)
					blackWins++;
				else if (winner == 1)
					whiteWins++;
				counter++;
			}
			if (counter == endNumberGames)
				break;
		}
		if (choice != 0)
			System.out.println("Thanks for playing!");
		else
			System.out.println("W: " + whiteWins + " B: " + blackWins);

	}

}
