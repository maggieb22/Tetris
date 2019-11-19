package Tetris;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Piece {
	private Square[] _piece;
	private Pane _pane;
	private Color _color;
	private Boolean _check;
	private Square[][] _board;

	/**
	 * Creates a new array with size 4 that is filled with squares
	 * makes an instance of the pane so that way I can access it in this class
	 * makes a new board so that way I can access it in this class
	 */
	public Piece(Pane pane, Square[][] board) {
		_piece = new Square[4];
		_pane = pane;
		_board = board;
	}

	/**
	 * This is a switch statement that will randomly generate a number between 0-7
	 * Based on the number a different piece will be made and each piece has a different color
	 */
	public double[][] choosePiece() {
		double[][] array = null;
		int rand_int = (int) (Math.random() * 7);
		switch (rand_int) {
		case 0:
			array = Constants.PIECE_ONE;
			_color = Color.RED;
			System.out.println("piece one");
			break;
		case 1:
			array = Constants.PIECE_TWO;
			_color = Color.PINK;
			System.out.println("piece two");
			break;
		case 2:
			array = Constants.PIECE_THREE;
			_color = Color.ORANGE;
			System.out.println("piece three");
			break;
		case 3:
			array = Constants.PIECE_FOUR;
			_color = Color.YELLOW;
			System.out.println("piece four");
			break;
		case 4:
			array = Constants.PIECE_FIVE;
			_color = Color.GREEN;
			System.out.println("piece five");
			break;
		case 5:
			array = Constants.PIECE_SIX;
			_color = Color.TURQUOISE;
			System.out.println("piece six");
			break;
		case 6:
			array = Constants.PIECE_SEVEN;
			_color = Color.ROYALBLUE;
			System.out.println("piece seven");
			break;
		default:
			break;
		}
		return array;
	}

	/**
	 * This will use the piece chosen in the switch statement to make a new piece
	 * I then set the location and the color of the piece and add it to the pane
	 */
	public void createPiece() {
		double[][] array = this.choosePiece();
		for (int row = 0; row < Constants.NUM_SQUARE; row++) {
			Square square = new Square();
			_piece[row] = square;
			square.setX(array[row][0]);
			square.setY(array[row][1]);
			square.setColor(_color);
			_pane.getChildren().addAll(square.getSquare());
		}
	}

	/**
	 * This moves all the squares in the piece to the left over one square
	 */
	public void moveLeft() {
		for (int row = 0; row < Constants.NUM_SQUARE; row++) {
			_piece[row].setX(_piece[row].getX() - Constants.SQUARE_SIZE);
		}
	}

	/**
	 * This moves all the squares in the piece to the right over one square
	 */
	public void moveRight() {
		for (int row = 0; row < Constants.NUM_SQUARE; row++) {
			_piece[row].setX(_piece[row].getX() + Constants.SQUARE_SIZE);
		}
	}

	/**
	 * This checks to see if the piece can move any more to the left
	 * if the location to the left of any of the squares in the piece is full
	 * the boolean returns false. I call this in my game class, and if it returns false
	 * then the move left method will not be executed.
	 */
	public Boolean checkLeftLimit() {
		_check = true;
		for (int row = 0; row < Constants.NUM_SQUARE; row++) {
			int x = (int) (_piece[row].getX() - Constants.SQUARE_SIZE) / Constants.SQUARE_SIZE;
			int y = (int) _piece[row].getY() / Constants.SQUARE_SIZE;
			if (_board[y][x] != null) {
				_check = false;
			}
		}
		return _check;
	}

	/**
	 * This method does the same thing as the move left method, but it checks to see if the squares 
	 * to the right of the piece are empty or not. It is also called in the game class and if it returns false
	 * then the move right method will not be executed. 
	 */
	public Boolean checkRightLimit() {
		_check = true;
		for (int row = 0; row < Constants.NUM_SQUARE; row++) {
			int x = (int) (_piece[row].getX() + Constants.SQUARE_SIZE) / Constants.SQUARE_SIZE;
			int y = (int) _piece[row].getY() / Constants.SQUARE_SIZE;
			if (_board[y][x] != null) {
				_check = false;
			}
		}
		return _check;
	}

	/**
	 * I set the center of rotation to be the x and y value of the first index spot of my piece
	 * for each square in my piece I get the location of where it is currently.
	 * Then I make the new location which is a relation of the center of rotations and where the location
	 * currently is. I then set a new x and y location for all of the squares in my piece.
	 */
	public void rotate() {
		double centerOfRotationX = _piece[1].getX();
		double centerOfRotationY = _piece[1].getY();
		for (int row = 0; row < Constants.NUM_SQUARE; row++) {
			double locX = _piece[row].getX();
			double locY = _piece[row].getY();
			double newXLoc = centerOfRotationX - centerOfRotationY + locY;
			double newYLoc = centerOfRotationY + centerOfRotationX - locX;
			_piece[row].setX(newXLoc);
			_piece[row].setY(newYLoc);
		}
	}

	/**
	 * This method checks to make sure that my piece can rotate. The first part of the method is exactly the
	 * same as the rotate method. the second part checks to see if the new square that the piece will move into is full or not
	 * if it is full, it returns false. This is called in my game class, and if it returns true the piece will move and if it 
	 * returns false the piece will not move. 
	 * 
	 * This is also where I tell the square not to move. The squares color is red so I say if the color is red, don't rotate.
	 */
	public Boolean checkRotate() {
		_check = true;
		double centerOfRotationX = _piece[1].getX();
		double centerOfRotationY = _piece[1].getY();
		for (int row = 0; row < Constants.NUM_SQUARE; row++) {
			double locX = _piece[row].getX();
			double locY = _piece[row].getY();
			double newXLoc = centerOfRotationX - centerOfRotationY + locY;
			double newYLoc = centerOfRotationY + centerOfRotationX - locX;
			if (_board[(int) (newYLoc / Constants.SQUARE_SIZE)][(int) (newXLoc / Constants.SQUARE_SIZE)] != null) {
				_check = false;
			}
			if (_color == Color.RED) {
				_check = false;
			}
		}
		return _check;
	}

	/**
	 * This moves each square in my piece down one square
	 */
	public void moveDown() {
		for (int row = 0; row < Constants.NUM_SQUARE; row++) {
			_piece[row].setY(_piece[row].getY() + Constants.SQUARE_SIZE);
		}
	}

	/**
	 * This checks to see if the square that my piece is about to move into is full or not
	 * if it full it returns false. This is called in my game class and only if it returns true the piece
	 * will then move down one square
	 */
	public Boolean checkDown() {
		_check = true;
		for (int row = 0; row < Constants.NUM_SQUARE; row++) {
			int x = (int) (_piece[row].getX()) / Constants.SQUARE_SIZE;
			int y = (int) (_piece[row].getY() + Constants.SQUARE_SIZE) / Constants.SQUARE_SIZE;
			if (_board[y][x] != null) {
				_check = false;
			}
		}
		return _check;
	}

	/**
	 * This gets the location of the squares in my piece and then adds them to the board
	 */
	public void addToArray() {
		for (int row = 0; row < Constants.NUM_SQUARE; row++) {
			double xLoc = _piece[row].getX() / Constants.SQUARE_SIZE;
			double yLoc = _piece[row].getY() / Constants.SQUARE_SIZE;
			_board[(int) yLoc][(int) xLoc] = _piece[row];
		}
	}

	/**
	 * This is used to check the y location of my pieces to see if they are within the game pane 
	 * if the location where the pieces are is null, then that means that the piece has reached the top of the screen
	 * this is used in my game class to end the game.
	 */
	public Boolean checkYLoc() {
		_check = false;
		for (int row = 0; row < Constants.NUM_SQUARE; row++) {
			int x = (int) (_piece[row].getX()) / Constants.SQUARE_SIZE;
			int y = (int) (_piece[row].getY()) / Constants.SQUARE_SIZE;
			if (_board[y][x] != null) {
				_check = true;
			}
		}
		return _check;
	}
}
