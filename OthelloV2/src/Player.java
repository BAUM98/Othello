import java.util.ArrayList;


public abstract class Player {

	protected String color;

	public abstract boolean playAgain();

	public abstract String getColor();

	protected abstract boolean submitCoords(Board b, String entry);

	protected abstract String returnMove(Board b);

	public abstract boolean makeMove(Board b);

	protected int numOfAI;

	public Player() {
		super();
	}

}