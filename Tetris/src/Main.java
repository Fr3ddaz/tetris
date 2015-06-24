/**
 * Main class for tetris. Contains the main method and an instance of board.
 */
import java.awt.BorderLayout;

import javax.swing.JFrame;


public class Main extends JFrame {

	// Added to suppress warning.
	private static final long serialVersionUID = 1L;

	/**
	 * Main constructor.
	 */
    public Main() {
        Board board = new Board(this);
        add(board, BorderLayout.CENTER);
        board.start();
        setSize(400, 880);
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

    /**
     * Main method.
     * @param args Arguments.
     */
    public static void main(String[] args) {

        Main game = new Main();
        game.setLocationRelativeTo(null);
        game.setVisible(true);

    } 
}
