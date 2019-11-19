package Tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square {
	private Rectangle _square;

	/**
	 * Makes the square with a specified size and sets the borders around the
	 * squares to be white for the border.
	 */
	public Square() {
		_square = new Rectangle(Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
		_square.setStroke(Color.WHITE);
	}

	/**
	 * Sets the X value of the square
	 */
	public void setX(double x) {
		_square.setX(x);
	}

	/**
	 * Sets the Y value of the square
	 */
	public void setY(double y) {
		_square.setY(y);
	}

	/**
	 * Returns the square as a rectangle piece Used to add the piece to the pane.
	 */
	public Rectangle getSquare() {
		return _square;
	}

	/**
	 * Sets the color of the square piece used in the piece class to set each piece
	 * to a different color
	 */

	public void setColor(Color color) {
		_square.setFill(color);
	}

	/**
	 * Returns the X value of the square
	 */
	public double getX() {
		return _square.getX();
	}

	/**
	 * Returns the Y value of the square
	 */
	public double getY() {
		return _square.getY();
	}

}
