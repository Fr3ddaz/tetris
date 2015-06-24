/**
 * The input listener that handles all the input from the user.
 * @author Fredrik Ollinen Johansson
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputListener implements KeyListener {

	// It needs an instance if the board (same as the on in main).
	Board board;

	/**
	 * Constructor for InputListener object.
	 * 
	 * @param board
	 *            The board running.
	 */
	public InputListener(Board board) {
		this.board = board;
	}

	/**
	 * Does nothing. keyTyped is not needed for a game such as Tetris, but needs
	 * to have an override since KeyListener is an interface.
	 * 
	 * @param e
	 *            The key event.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// Do nothing!
	}

	/**
	 * Handles the input.
	 * 
	 * @param e
	 *            The key event.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (board.isRunning()) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT:
				board.move(Constants.Movement.MoveLeft);
				break;
			case KeyEvent.VK_RIGHT:
				board.move(Constants.Movement.MoveRight);
				break;
			case KeyEvent.VK_DOWN:
				board.move(Constants.Movement.RotateRight);
				break;
			case KeyEvent.VK_UP:
				board.move(Constants.Movement.RotateLeft);
				break;
			case KeyEvent.VK_SPACE:
				board.move(Constants.Movement.Down);
				break;
			case KeyEvent.VK_ENTER:
				board.setGameOver(false);
				board.start();
				break;
			}
		} else {
			return;
		}

	}

	/**
	 * Does nothing. keyReleased is not needed for a game such as Tetris, but
	 * needs to have an override since KeyListener is an interface.
	 * 
	 * @param e
	 *            The key event.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// Do nothing!
	}

}
