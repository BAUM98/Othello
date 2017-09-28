import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		boolean playAgain = true;
		boolean stillValidMoves = true;
		boolean validPlayerEntry = false;
		boolean moveBool = false;
		boolean oneHasMoves = true; 
		boolean twoHasMoves = true;
		Player playerOne = new Player("Black");
		Player playerTwo = new Player("White");
		String entry;
		Scanner scan = new Scanner(System.in);
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
				validPlayerEntry = true;
			} 
			else if (entry.equals("2")){
				validPlayerEntry = true;
			}
			else
				System.out.println("Error: Invalid Entry!");
		}
		playerOne.setCurrentPlayer();
		while (playAgain) {
			Board b = new Board();
			while (stillValidMoves) {
				if (playerOne.isCurrentPlayer())
					moveBool = playerOne.makeMove(b);	
				else
					moveBool = playerTwo.makeMove(b);

					playerOne.setCurrentPlayer();
					playerTwo.setCurrentPlayer();

				if(!moveBool)
					break;
				oneHasMoves = playerOne.stillValidMoves(b);
				twoHasMoves = playerTwo.stillValidMoves(b);
				
				if(!oneHasMoves && !twoHasMoves)
					stillValidMoves = false;
				else if (!oneHasMoves && playerOne.isCurrentPlayer() && twoHasMoves) {
					playerOne.setCurrentPlayer();
					playerTwo.setCurrentPlayer();
				}
				else if (!twoHasMoves && playerTwo.isCurrentPlayer() && oneHasMoves) {
					playerOne.setCurrentPlayer();
					playerTwo.setCurrentPlayer();
				}
					
			}
		}

	}

}
