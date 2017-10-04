import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		boolean playAgain = true;
		boolean stillValidMoves = true;
		boolean validPlayerEntry = false;
		boolean moved = true;
		Player playerOne = new Player("Black");
		Player playerTwo = new Player("White");
		String entry;
		Scanner scan = new Scanner(System.in);
		int choice = 0, counter = 0, endNumberGames = -1, winner = 0, blackWins = 0, whiteWins = 0;
		// Check to see how many players
		System.out.println("1 player, 2 players or Simulation?");
		while (!validPlayerEntry) {

			entry = scan.nextLine();
			entry.toLowerCase();
			if (entry.equals("1")) {
				playerTwo.makeAI();
				validPlayerEntry = true;
			} else if (entry.equals("simulation")) {
				playerOne.makeAI();
				playerTwo.makeAI();
				choice = 1;
				System.out.println("How many games would you like to run?");
				endNumberGames = scan.nextInt();
				validPlayerEntry = true;
			} else if (entry.equals("2")) {
				validPlayerEntry = true;
			} else
				System.out.println("Error: Invalid Entry!");
		}
		while (playAgain) {
			Player currentPlayer = playerOne;
			Board b = new Board();
			while (stillValidMoves) {
				// //if(entry.equals("pass")) {
				// //playerOne.nextTurn(playerTwo);
				// //continue;
				// //}
				// //else if(entry.equals("quit"))
				// break;
				// else if(entry.equals("hint")) {
				// if(playerOne.isCurrentPlayer())
				// b.validMovesLeft(playerOne.getColor(), true);
				// else if(playerTwo.isCurrentPlayer())
				// b.validMovesLeft(playerOne.getColor(), true);
				// }
				// else {
				// r = Integer.parseInt(entry.substring(0, 1));
				// c = Integer.parseInt(entry.substring(2));
				currentPlayer.makeMove(b, choice);
				// if (!moved) {
				// System.out.println("Error: Invalid Move!");
				// continue;
				// }
				if (!moved)
					break;
				currentPlayer = currentPlayer.nextTurn(playerOne, playerTwo, currentPlayer);

				// oneHasMoves = b.validMovesLeft(playerOne.getColor(), false);
				// twoHasMoves = b.validMovesLeft(playerTwo.getColor(), false);
				//
				// if (!oneHasMoves && !twoHasMoves)
				// stillValidMoves = false;
				// else if (!oneHasMoves && playerOne.isCurrentPlayer() && twoHasMoves) {
				// playerOne.nextTurn(playerTwo);
				// } else if (!twoHasMoves && playerTwo.isCurrentPlayer() && oneHasMoves) {
				// playerOne.nextTurn(playerTwo);
				// }
				currentPlayer = b.validMovesForPlayers(playerOne, playerTwo, currentPlayer);
				if (currentPlayer.getColor().equals("Empty"))
					stillValidMoves = false;
			}
			if (choice != 1) {
				b.printBoard();
				playAgain = b.finalScore(playerOne, playerTwo);
				stillValidMoves = true;
			} else {
				winner = b.winner();
				if (winner == 0)
					blackWins++;
				else if (winner == 1)
					whiteWins++;
				counter++;
				stillValidMoves = true;
			}
			int c = counter % 10000;
			if (counter == endNumberGames)
				break;
			else if(c == 0)
				System.out.println(counter);
		}
		if (choice != 1)
			System.out.println("Thanks for playing!");
		else
			System.out.println("W: " + whiteWins + " B: " + blackWins);

	}

}
