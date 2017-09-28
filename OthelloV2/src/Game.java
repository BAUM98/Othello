import java.util.Scanner;

public class Game {

	
	public static void main(String[] args) {
		boolean playAgain = true;
		boolean stillValidMoves = true;
		boolean validPlayerEntry = false;
		boolean moveBool = false;
		boolean oneHasMoves = true; 
		boolean twoHasMoves = true;
		boolean moved = true;
		Player playerOne = new Player("Black");
		Player playerTwo = new Player("White");
		String entry;
		Scanner scan = new Scanner(System.in);
		int r = -1;
		int c = -1;
		int choice = 0;
		int counter = 0;
		int endNumberGames = -1;
		int winner = 0;
		int blackWins = 0;
		int whiteWins = 0;
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
			} 
			else if (entry.equals("2")){
				validPlayerEntry = true;
			}
			else
				System.out.println("Error: Invalid Entry!");
		}
		playerOne.switchPlayer();
		while (playAgain) {
			Board b = new Board();
			while (stillValidMoves) {
				if (playerOne.isCurrentPlayer())
					entry = playerOne.returnMove(b);	
				else
					entry = playerTwo.returnMove(b);
				if(entry.equals("pass")) {
					playerOne.switchPlayer();
					playerTwo.switchPlayer();
					continue;
				}
				else if(entry.equals("quit"))
					break;
				else if(entry.equals("hint")) {
					if(playerOne.isCurrentPlayer())
						b.validMovesLeft(playerOne.getColor(), true);
					else if(playerTwo.isCurrentPlayer())
						b.validMovesLeft(playerOne.getColor(), true);
				}
				else {
					r = Integer.parseInt(entry.substring(0, 1));
					c = Integer.parseInt(entry.substring(2));
					if(playerOne.isCurrentPlayer())
						moved = b.setPiece(r, c, playerOne.getColor());
					else {
						moved = b.setPiece(r, c, playerTwo.getColor());
					}
				}
				if(!moved) {
					System.out.println("Error: Invalid Move!");
					continue;
				}
				playerOne.switchPlayer();
				playerTwo.switchPlayer();
				//b.printBoard();
				
				oneHasMoves = b.validMovesLeft(playerOne.getColor(), false);
				twoHasMoves = b.validMovesLeft(playerTwo.getColor(), false);
				
				if(!oneHasMoves && !twoHasMoves)
					stillValidMoves = false;
				else if(!oneHasMoves && playerOne.isCurrentPlayer() && twoHasMoves) {
					playerOne.switchPlayer();
					playerTwo.switchPlayer();
				}
				else if(!twoHasMoves && playerTwo.isCurrentPlayer() && oneHasMoves) {
					playerOne.switchPlayer();
					playerTwo.switchPlayer();
				}
				
				
				
			}
			if(choice != 1) {
				System.out.println("Player 1 score: " + b.score("Black"));
				System.out.println("Player 2 score: " + b.score("White"));
				winner = b.finalScore();
				if(winner == 0)
					System.out.println("Black Wins!");
				else
					System.out.println("White Wins!");
				break;
			}
			else {
				winner = b.finalScore();
				if(winner == 0)
					blackWins++;
				if(winner == 1)
					whiteWins++;
				counter++;
				stillValidMoves = true;
			}
			System.out.println("W: " + whiteWins + " B: " + blackWins);
			if (counter == endNumberGames)
				break;
		}

	}

}
