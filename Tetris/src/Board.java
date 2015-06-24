/**
 * Board class for tetris. Describes the playing board and all the interactions within it.
 * 
 * @author Fredrik Ollinen Johansson
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	// Added to suppress warning.
	private static final long serialVersionUID = 1L;

	// Hight and width (in blocks) of playing board.
	private final int height = 20;
	private final int width = 10;
	private final int speedIncreaseInterval = 10;

	// Non final fields.
	private Constants.Type[][] board;
	private Timer timer;
	private boolean running;
	private boolean finished;
	private boolean gameOver;
	private int score;
	private int level;
	private int linesEliminated;
	private Shape fallingPiece;
	private Random rand;

	/**
	 * Constructor of Object Board.
	 * @param main The JFrame this JPanel should be added to.
	 */
	public Board(Main main) {
		board = new Constants.Type[height][width];
		setFocusable(true);
		timer = new Timer(400, this);
		addKeyListener(new InputListener(this));
		rand = new Random();
	}

	/**
	 * Start the game from the beginning.
	 */
	public void start() {
		for (int i = 0; i < board.length; i++) {
			Arrays.fill(board[i], Constants.Type.Void);
		}
		running = true;
		finished = false;
		gameOver = false;
		score = 0;
		level = 1;
		linesEliminated = 0;
		timer.start();
		run();
	}
	
	/**
	 * Set state to gameover (Only used when it is gameover and we have sent a request to restart the game).
	 * @param gameOver Set it to true in order to override the gameover state.
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * Is called in between every new block generated.
	 */
	private void run() {
		checkAndRemoveLines();
		generateNewPiece();
		if (linesEliminated >= speedIncreaseInterval && level <= 10) {
			timer.setDelay(timer.getDelay() - 30);
			linesEliminated = 0;
			level++;
		}
		if (board[fallingPiece.getCoordinates()[0].getY()][fallingPiece
				.getCoordinates()[0].getX()] != Constants.Type.Void
				|| board[fallingPiece.getCoordinates()[1].getY()][fallingPiece
						.getCoordinates()[1].getX()] != Constants.Type.Void
				|| board[fallingPiece.getCoordinates()[2].getY()][fallingPiece
						.getCoordinates()[2].getX()] != Constants.Type.Void
				|| board[fallingPiece.getCoordinates()[3].getY()][fallingPiece
						.getCoordinates()[3].getX()] != Constants.Type.Void) {
			timer.stop();
			gameOver = true;
			repaint();
		}
	}

	/**
	 * Check if there is any lines which is full, removes them and adds the appropiate score.
	 */
	private void checkAndRemoveLines() {
		int counter = 0;
		int lines = 0;
		for (int i = 19; i >= 0; i--) {
			counter = 0;
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] != Constants.Type.Void) {
					counter++;
				}
			}
			if (counter == 10) {
				lines++;
				for (int j = i; j >= 1; j--) {
					for (int k = 0; k < board[0].length; k++) {
						board[j][k] = board[j - 1][k];
					}
				}
				for (int j = 0; j < board[0].length; j++) {
					board[0][j] = Constants.Type.Void;
				}
				i++;
			}
		}
		if (lines == 1) {
			linesEliminated++;
			score += 40;
		} else if (lines == 2) {
			linesEliminated += 2;
			score += 100;
		} else if (lines == 3) {
			linesEliminated += 3;
			score += 300;
		} else if (lines == 4) {
			linesEliminated += 4;
			score += 1200;
		}
	}

	/**
	 * Called with each repaint call. Reads the model and displays it.
	 * @param g The abstract Graphics base class.
	 */
	public void paint(Graphics g) {
		if (!gameOver) {
			super.paint(g);

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					drawSquare(g, i * squareHeight(), j * squareWidth(),
							board[i][j]);
				}
			}

			for (int i = 0; i < fallingPiece.getCoordinates().length; i++) {
				drawSquare(
						g,
						fallingPiece.getCoordinates()[i].getY()
								* squareHeight(),
						fallingPiece.getCoordinates()[i].getX() * squareWidth(),
						fallingPiece.getType());
			}
			g.setColor(Color.ORANGE);
			g.drawString("Score: " + score + " Level: " + level, width + 2, height + 2);
		} else {
			g.setColor(Color.ORANGE);
			g.drawString("Score: " + score + " Level: " + level + " GAME OVER", width + 2,
					height + 2);
		}

	}

	private void generateNewPiece() {
		int index = Math.abs(rand.nextInt()%7);
		System.out.println(index);
		switch (index) {
		case 0:
			fallingPiece = new Shape(Constants.Type.Straight);
			break;
		case 1:
			fallingPiece = new Shape(Constants.Type.L);
			break;
		case 2:
			fallingPiece = new Shape(Constants.Type.RevL);
			break;
		case 3:
			fallingPiece = new Shape(Constants.Type.T);
			break;
		case 4:
			fallingPiece = new Shape(Constants.Type.S);
			break;
		case 5:
			fallingPiece = new Shape(Constants.Type.Z);
			break;
		case 6:
			fallingPiece = new Shape(Constants.Type.Square);
			break;
		default:
			return;
		}
	}

	/**
	 * Returns a boolean telling us whether the game is running or not. Not much practical use unless pause will be implemented.
	 * @return true if game is running, false otherwise.
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Returns the width of a square. It depends on the size of the window.
	 * @return The width of a square in pixels.
	 */
	private int squareWidth() {
		return (int) getSize().getWidth() / width;
	}

	/**
	 * Returns the height of a square. It depends on the size of the window.
	 * @return The height of a square in pixels.
	 */
	int squareHeight() {
		return (int) getSize().getHeight() / height;
	}

	/**
	 * Make a move from the predefined moves in Constants.
	 * @param movement The movement to make.
	 */
	public void move(Constants.Movement movement) {
		tryMove(movement);
	}

	/**
	 * Try a move. It will not be completed if it is not possible or we have reaced the bottom of the board.
	 * @param movement The movement to make.
	 * @return true if a move was made, false otherwise.
	 */
	private boolean tryMove(Constants.Movement movement) {
		Coordinate[] newCoordinates = new Coordinate[4];
		Shape newShape;
		int max;
		int yMax;
		int yMin;
		int xMax;
		int xMin;
		switch (movement) {
		case MoveLeft:
			xMax = Integer.MIN_VALUE;
			xMin = Integer.MAX_VALUE;
			yMax = Integer.MIN_VALUE;
			yMin = Integer.MAX_VALUE;
			for (int i = 0; i < fallingPiece.getCoordinates().length; i++) {
				newCoordinates[i] = new Coordinate(
						fallingPiece.getCoordinates()[i].getY(),
						fallingPiece.getCoordinates()[i].getX() - 1);
				if (newCoordinates[i].getY() > yMax) {
					yMax = newCoordinates[i].getY();
				}
				if (newCoordinates[i].getY() < yMin) {
					yMin = newCoordinates[i].getY();
				}
				if (newCoordinates[i].getX() > xMax) {
					xMax = newCoordinates[i].getX();
				}
				if (newCoordinates[i].getX() < xMin) {
					xMin = newCoordinates[i].getX();
				}
			}
			if (xMax < 10
					&& xMin >= 0
					&& yMax < 20
					&& yMin >= 0
					&& board[newCoordinates[0].getY()][newCoordinates[0].getX()] == Constants.Type.Void
					&& board[newCoordinates[1].getY()][newCoordinates[1].getX()] == Constants.Type.Void
					&& board[newCoordinates[2].getY()][newCoordinates[2].getX()] == Constants.Type.Void
					&& board[newCoordinates[3].getY()][newCoordinates[3].getX()] == Constants.Type.Void) {
				fallingPiece = new Shape(fallingPiece.getType(), newCoordinates);
				// May be needing validate() here.
				repaint();
			} else {
				return false;
			}
			break;
		case MoveRight:
			xMax = Integer.MIN_VALUE;
			xMin = Integer.MAX_VALUE;
			yMax = Integer.MIN_VALUE;
			yMin = Integer.MAX_VALUE;
			for (int i = 0; i < fallingPiece.getCoordinates().length; i++) {
				newCoordinates[i] = new Coordinate(
						fallingPiece.getCoordinates()[i].getY(),
						fallingPiece.getCoordinates()[i].getX() + 1);
				if (newCoordinates[i].getY() > yMax) {
					yMax = newCoordinates[i].getY();
				}
				if (newCoordinates[i].getY() < yMin) {
					yMin = newCoordinates[i].getY();
				}
				if (newCoordinates[i].getX() > xMax) {
					xMax = newCoordinates[i].getX();
				}
				if (newCoordinates[i].getX() < xMin) {
					xMin = newCoordinates[i].getX();
				}
			}
			if (xMax < 10
					&& xMin >= 0
					&& yMax < 20
					&& yMin >= 0
					&& board[newCoordinates[0].getY()][newCoordinates[0].getX()] == Constants.Type.Void
					&& board[newCoordinates[1].getY()][newCoordinates[1].getX()] == Constants.Type.Void
					&& board[newCoordinates[2].getY()][newCoordinates[2].getX()] == Constants.Type.Void
					&& board[newCoordinates[3].getY()][newCoordinates[3].getX()] == Constants.Type.Void) {
				fallingPiece = new Shape(fallingPiece.getType(), newCoordinates);
				// May be needing validate() here.
				repaint();
			} else {
				return false;
			}
			break;
		case RotateLeft:
			newShape = fallingPiece.rotateLeft();
			xMax = Integer.MIN_VALUE;
			xMin = Integer.MAX_VALUE;
			yMax = Integer.MIN_VALUE;
			yMin = Integer.MAX_VALUE;
			for (int i = 0; i < newShape.getCoordinates().length; i++) {
				if (newShape.getCoordinates()[i].getY() > yMax) {
					yMax = newShape.getCoordinates()[i].getY();
				}
				if (newShape.getCoordinates()[i].getY() < yMin) {
					yMin = newShape.getCoordinates()[i].getY();
				}
				if (newShape.getCoordinates()[i].getX() > xMax) {
					xMax = newShape.getCoordinates()[i].getX();
				}
				if (newShape.getCoordinates()[i].getX() < xMin) {
					xMin = newShape.getCoordinates()[i].getX();
				}
			}
			if (xMax < 10
					&& xMin >= 0
					&& yMax < 20
					&& yMin >= 0
					&& board[newShape.getCoordinates()[0].getY()][newShape
							.getCoordinates()[0].getX()] == Constants.Type.Void
					&& board[newShape.getCoordinates()[1].getY()][newShape
							.getCoordinates()[1].getX()] == Constants.Type.Void
					&& board[newShape.getCoordinates()[2].getY()][newShape
							.getCoordinates()[2].getX()] == Constants.Type.Void
					&& board[newShape.getCoordinates()[3].getY()][newShape
							.getCoordinates()[3].getX()] == Constants.Type.Void) {
				fallingPiece = newShape;
				repaint();
			}
			break;
		case RotateRight:
			newShape = fallingPiece.rotateRight();
			xMax = Integer.MIN_VALUE;
			xMin = Integer.MAX_VALUE;
			yMax = Integer.MIN_VALUE;
			yMin = Integer.MAX_VALUE;
			for (int i = 0; i < newShape.getCoordinates().length; i++) {
				if (newShape.getCoordinates()[i].getY() > yMax) {
					yMax = newShape.getCoordinates()[i].getY();
				}
				if (newShape.getCoordinates()[i].getY() < yMin) {
					yMin = newShape.getCoordinates()[i].getY();
				}
				if (newShape.getCoordinates()[i].getX() > xMax) {
					xMax = newShape.getCoordinates()[i].getX();
				}
				if (newShape.getCoordinates()[i].getX() < xMin) {
					xMin = newShape.getCoordinates()[i].getX();
				}
			}
			if (xMax < 10
					&& xMin >= 0
					&& yMax < 20
					&& yMin >= 0
					&& board[newShape.getCoordinates()[0].getY()][newShape
							.getCoordinates()[0].getX()] == Constants.Type.Void
					&& board[newShape.getCoordinates()[1].getY()][newShape
							.getCoordinates()[1].getX()] == Constants.Type.Void
					&& board[newShape.getCoordinates()[2].getY()][newShape
							.getCoordinates()[2].getX()] == Constants.Type.Void
					&& board[newShape.getCoordinates()[3].getY()][newShape
							.getCoordinates()[3].getX()] == Constants.Type.Void) {
				fallingPiece = newShape;
				repaint();
			}
			break;
		case Down:
			max = Integer.MIN_VALUE;
			for (int i = 0; i < fallingPiece.getCoordinates().length; i++) {
				newCoordinates[i] = new Coordinate(
						fallingPiece.getCoordinates()[i].getY() + 1,
						fallingPiece.getCoordinates()[i].getX());
				if (newCoordinates[i].getY() > max) {
					max = newCoordinates[i].getY();
				}
			}
			if (max < 20
					&& board[newCoordinates[0].getY()][newCoordinates[0].getX()] == Constants.Type.Void
					&& board[newCoordinates[1].getY()][newCoordinates[1].getX()] == Constants.Type.Void
					&& board[newCoordinates[2].getY()][newCoordinates[2].getX()] == Constants.Type.Void
					&& board[newCoordinates[3].getY()][newCoordinates[3].getX()] == Constants.Type.Void) {
				fallingPiece = new Shape(fallingPiece.getType(), newCoordinates);
				// May be needing validate() here.
				repaint();
			} else {
				finished = true;
				for (int i = 0; i < fallingPiece.getCoordinates().length; i++) {
					board[fallingPiece.getCoordinates()[i].getY()][fallingPiece
							.getCoordinates()[i].getX()] = fallingPiece
							.getType();
				}
				return false;
			}
			break;
		default:
			return false;
		}
		return true;
	}

	/**
	 * Draw a square within the board.
	 * @param g The abstract Graphics base class.
	 * @param y The y coordinate of the top-left corner
	 * @param x The x coordinate of the top-left corner.
	 * @param type The type of block
	 */
	public void drawSquare(Graphics g, int y, int x, Constants.Type type) {
		Color color = Constants.colors[type.ordinal()];
		g.setColor(color);
		g.fillRect(x, y, squareWidth(), squareHeight());
	}

	/**
	 * Runs every time timer throws an action.
	 * @param e The event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (finished) {
			finished = false;
			run();
		} else {
			move(Constants.Movement.Down);
		}
	}
}
