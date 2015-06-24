/**
 * Describes a shape (a comination of blocks) in the teris game.
 * 
 * @author Fredrik Ollinen Johansson
 */

public class Shape {

	// Fields.
	private Constants.Type type;
	private int[][] rotationMatrixRight;
	private int[][] rotationMatrixLeft;
	private Coordinate[] coordinates;
	private Coordinate center;

	/**
	 * Constructor for object Shape. Will instantiate the coordinates on the top
	 * of the board.
	 * 
	 * @param type
	 *            The type of the shape as defined in Constants.
	 */
	public Shape(Constants.Type type) {
		this.type = type;
		coordinates = new Coordinate[4];
		rotationMatrixRight = new int[2][2];
		rotationMatrixRight[0][0] = 0;
		rotationMatrixRight[0][1] = 1;
		rotationMatrixRight[1][0] = -1;
		rotationMatrixRight[1][1] = 0;
		rotationMatrixLeft = new int[2][2];
		rotationMatrixLeft[0][0] = 0;
		rotationMatrixLeft[0][1] = -1;
		rotationMatrixLeft[1][0] = 1;
		rotationMatrixLeft[1][1] = 0;
		switch (type) {
		case Straight:
			coordinates[0] = new Coordinate(0, 5);
			coordinates[1] = new Coordinate(1, 5);
			coordinates[2] = new Coordinate(2, 5);
			coordinates[3] = new Coordinate(3, 5);
			center = coordinates[1];
			break;
		case L:
			coordinates[0] = new Coordinate(0, 4);
			coordinates[1] = new Coordinate(1, 4);
			coordinates[2] = new Coordinate(2, 4);
			coordinates[3] = new Coordinate(2, 5);
			center = coordinates[1];
			break;
		case RevL:
			coordinates[0] = new Coordinate(0, 5);
			coordinates[1] = new Coordinate(1, 5);
			coordinates[2] = new Coordinate(2, 5);
			coordinates[3] = new Coordinate(2, 4);
			center = coordinates[1];
			break;
		case T:
			coordinates[0] = new Coordinate(0, 4);
			coordinates[1] = new Coordinate(1, 4);
			coordinates[2] = new Coordinate(2, 4);
			coordinates[3] = new Coordinate(1, 5);
			center = coordinates[1];
			break;
		case S:
			coordinates[0] = new Coordinate(1, 5);
			coordinates[1] = new Coordinate(1, 6);
			coordinates[2] = new Coordinate(2, 4);
			coordinates[3] = new Coordinate(2, 5);
			center = coordinates[0];
			break;
		case Z:
			coordinates[0] = new Coordinate(1, 4);
			coordinates[1] = new Coordinate(1, 5);
			coordinates[2] = new Coordinate(2, 5);
			coordinates[3] = new Coordinate(2, 6);
			center = coordinates[1];
			break;
		case Square:
			coordinates[0] = new Coordinate(0, 4);
			coordinates[1] = new Coordinate(0, 5);
			coordinates[2] = new Coordinate(1, 4);
			coordinates[3] = new Coordinate(1, 5);
			center = coordinates[0]; // Should not be used.
			break;
		default:
			break;
		}
	}

	/**
	 * Constructor for object Shape.
	 * 
	 * @param type
	 *            The type of the shape as defined in Constants.
	 * @param coordinates
	 *            The coordinates of the shape within the board.
	 */
	public Shape(Constants.Type type, Coordinate[] coordinates) {
		this.type = type;
		this.coordinates = coordinates;
		rotationMatrixRight = new int[2][2];
		rotationMatrixRight[0][0] = 0;
		rotationMatrixRight[0][1] = 1;
		rotationMatrixRight[1][0] = -1;
		rotationMatrixRight[1][1] = 0;
		rotationMatrixLeft = new int[2][2];
		rotationMatrixLeft[0][0] = 0;
		rotationMatrixLeft[0][1] = -1;
		rotationMatrixLeft[1][0] = 1;
		rotationMatrixLeft[1][1] = 0;
		switch (type) {
		case Straight:
			center = coordinates[1];
			break;
		case L:
			center = coordinates[1];
			break;
		case RevL:
			center = coordinates[1];
			break;
		case T:
			center = coordinates[1];
			break;
		case S:
			center = coordinates[0];
			break;
		case Z:
			center = coordinates[1];
			break;
		case Square:
			center = coordinates[0]; // Should not be used.
			break;
		default:
			break;
		}
	}

	/**
	 * Returns the type of this shape as defined in constants.
	 * 
	 * @return The type of this shape.
	 */
	public Constants.Type getType() {
		return type;
	}

	/**
	 * Returns an array of the coordinates of this shape.
	 * 
	 * @return An array of the coordinates of this shape.
	 */
	public Coordinate[] getCoordinates() {
		return coordinates;
	}

	/**
	 * Returns the center of the shape.
	 * 
	 * @return The center of the shape.
	 */
	public Coordinate getCenter() {
		return center;
	}

	/**
	 * Rotates the shape right and returns the new shape.
	 * @return The new shape rotated right.
	 */
	public Shape rotateRight() {
		Shape shape;
		switch (type) {
		case Square:
			shape = new Shape(type, coordinates);
			break;
		default:
			Coordinate[] newCoordinates = new Coordinate[4];
			for (int i = 0; i < coordinates.length; i++) {
				newCoordinates[i] = new Coordinate(
						((coordinates[i].getY() - center.getY())
								* rotationMatrixRight[0][0] + (coordinates[i].getX() - center.getX())
								* rotationMatrixRight[1][0])
								+ center.getY(),
						((coordinates[i].getY() - center.getY())
								* rotationMatrixRight[0][1] + (coordinates[i]
								.getX() - center.getX())
								* rotationMatrixRight[1][1]) + center.getX());
			}
			shape = new Shape(type, newCoordinates);
		}
		return shape;
	}

	/**
	 * Rotates the shape left and returns the new shape.
	 * @return The new shape rotated left.
	 */
	public Shape rotateLeft() {
		Shape shape;
		switch (type) {
		case Square:
			shape = new Shape(type, coordinates);
			break;
		default:
			Coordinate[] newCoordinates = new Coordinate[4];
			for (int i = 0; i < coordinates.length; i++) {
				newCoordinates[i] = new Coordinate(
						((coordinates[i].getY() - center.getY())
								* rotationMatrixLeft[0][0] + (coordinates[i].getX() - center.getX())
								* rotationMatrixLeft[1][0])
								+ center.getY(),
						((coordinates[i].getY() - center.getY())
								* rotationMatrixLeft[0][1] + (coordinates[i]
								.getX() - center.getX())
								* rotationMatrixLeft[1][1]) + center.getX());
			}
			shape = new Shape(type, newCoordinates);
		}
		return shape;
	}
}
