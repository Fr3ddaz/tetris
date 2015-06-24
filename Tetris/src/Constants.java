/**
 * Class containing some constants.
 */
import java.awt.Color;


public class Constants {

	// All the different types of shapes.
	public enum Type {Void, Straight, L, RevL, T, S, Z, Square};
	
	// All the different kind of movements.
	public enum Movement {MoveLeft, MoveRight, RotateLeft, RotateRight, Down};
	
	// Some different predefined colors corresponding to the shapes in this class (BLACK = Void, Straight = RED , ... , CYAN = Square).
	public static final Color[] colors = {Color.BLACK, Color.RED, Color.YELLOW, Color.MAGENTA, Color.GRAY, Color.BLUE, Color.GREEN, Color.CYAN};
}
