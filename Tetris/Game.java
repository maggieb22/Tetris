package Tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Game {
	private Pane _pane;
	private Square[][] _board;
	private Piece _piece;
	private Timeline _timeline;
	private KeyHandler _keyhandler;
	private VBox _endLabelPane;
	private Label _myEndLabel;
	private Boolean _pause;
	private Label _scoreLabel;
	private int _score;

	/**
	 * In my game, I add my pane and then I make my board an array, and then make my
	 * piece I call on my piece class to create my first piece. I then set up the
	 * border, make a new keyhandler abd add it to the pane. I set up my timeline
	 * and set _pause = true which is used to pause the game. When the game starts
	 * _pause is false and then when I pause it is is set to true.
	 */
	public Game(Pane pane) {
		_pane = pane;
		_board = new Square[24][14];
		_piece = new Piece(pane, _board);
		_piece.createPiece();
		this.setUpSquares();

		_keyhandler = new KeyHandler();
		pane.addEventHandler(KeyEvent.KEY_PRESSED, _keyhandler);
		pane.setFocusTraversable(true);
		this.setUpTimeline();
		_pause = true;

		_score = 0;
		_scoreLabel = new Label("score: " + Integer.toString(_score));
		pane.getChildren().addAll(_scoreLabel);
	}

	/**
	 * This sets up my border squares. For the entire array, I set the pieces on the
	 * edge to be new squares I set the locations and colors, and then add it to the
	 * array and to the pane.
	 */
	private void setUpSquares() {
		for (int row = 0; row < 24; row++) {
			for (int col = 0; col < 14; col++) {

				if (row < 2 || row > 21 || col < 2 || col > 11) {
					Square rect = new Square();
					_board[row][col] = rect;

					rect.setX(col * Constants.SQUARE_SIZE);
					rect.setY(row * Constants.SQUARE_SIZE);
					rect.setColor(Color.GREY);
					_pane.getChildren().add(rect.getSquare());
				}
			}
		}
	}

	/**
	 * For all my keypresses, I first to check to see if the game is paused or not
	 * If the game is paused, the other keyhandlers won't work. I then check to see
	 * if the move is valid. If it is I make the move To pause the game I stop the
	 * timeline if the game is running and if the game is paused I start the
	 * timeline again.
	 */
	private class KeyHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent e) {
			KeyCode keyPressed = e.getCode();

			if (keyPressed == KeyCode.LEFT) {
				if (_pause) {
					if (_piece.checkLeftLimit()) {
						_piece.moveLeft();
					}
				}
			}
			if (keyPressed == KeyCode.RIGHT) {
				if (_pause) {
					if (_piece.checkRightLimit()) {
						_piece.moveRight();
					}
				}
				e.consume();
			}
			if (keyPressed == KeyCode.UP) {
				if (_pause) {
					if (_piece.checkRotate()) {
						_piece.rotate();
					}
				}
			}
			if (keyPressed == KeyCode.DOWN) {
				if (_pause) {
					if (_piece.checkDown()) {
						_piece.moveDown();
					}
				}
			}
			if (keyPressed == KeyCode.SPACE) {
				if (_pause) {
					while (_piece.checkDown()) {
						_piece.moveDown();
					}
				}
			}
			if (keyPressed == KeyCode.P) {
				if (_pause) {
					_timeline.stop();
					_pause = false;

				} else {
					_timeline.play();
					_pause = true;
				}
			}
		}
	}

	/**
	 * This is where I set up the timeline.
	 */
	private void setUpTimeline() {
		KeyFrame kf = new KeyFrame(Duration.seconds(0.1), new FallHandler());
		_timeline = new Timeline(kf);
		_timeline.setCycleCount(Animation.INDEFINITE);
		_timeline.play();
	}

	/**
	 * My fall handler checks to see if my piece can move down, and if it can it
	 * does. If this doesn't run then the piece is added to the array and I check to
	 * see if any lines can be removed and then I create a new piece. After these
	 * run I check to see if the game can end.
	 */
	private class FallHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			if (_piece.checkDown()) {
				_piece.moveDown();
			} else {
				_piece.addToArray();
				Game.this.removeLine();
				_piece.createPiece();
			}
			Game.this.endGame();
		}
	}

	/**
	 * If the piece is generated in a spot that is already full (when it hits the
	 * top of the screen), the timeline will pause, I remove the event handler and
	 * then I make label that says Game Over and add it to the pane.
	 */
	public void endGame() {
		if (_piece.checkYLoc()) {
			_timeline.stop();
			_pane.removeEventHandler(KeyEvent.KEY_PRESSED, _keyhandler);
			
			_endLabelPane = new VBox();
			_myEndLabel = new Label("Game Over!!!");
			_endLabelPane.getChildren().addAll(_myEndLabel);
			_endLabelPane.setLayoutX(Constants.END_X);
			_endLabelPane.setLayoutY(Constants.END_Y);
			_pane.getChildren().add(_endLabelPane);
		}
	}

	/**
	 * I check all of my rows inside the border (2-21) and if checkline returns
	 * true, I run the rest of the method I go through all of the columns inside the
	 * border and I remove the square from the pane. Then I take my score and I add
	 * 10 points to it. This will total to 100 points since it will add 10 points
	 * for each square removed, and there are 10 squares. Then I check my rows from
	 * bottom to top and if the spot is filled with a square I move it down one
	 * position on the board. I then set the rows to be one less than they were
	 * before so that way it matches with how the board was before I removed the
	 * pieces.
	 */
	private void removeLine() {
		for (int row = 2; row < 22; row++) {
			if (this.checkLine(row)) {
				for (int col = 2; col < 12; col++) {
					_pane.getChildren().remove(_board[row][col].getSquare());
					_score = _score + 10;
					_scoreLabel.setText("Score: " + Integer.toString(_score));
				}
				
				for (int row2 = row; row2 > 2; row2--) {
					for (int col = 2; col < 12; col++) {
						if (_board[row2][col] != null) {
							_board[row2][col].setY(_board[row2][col].getY() + Constants.SQUARE_SIZE);
						}
						_board[row2][col] = _board[row2 - 1][col];

					}
				}

			}

		}

	}

	/**
	 * This method checks all of the columns inside my board and if they aren't full
	 * it returns false, but if the row is full it returns true. This is used for my
	 * removeLine method.
	 */
	private Boolean checkLine(int row) {
		for (int col = 2; col < 12; col++) {
			if (_board[row][col] == null) {
				return false;
			}
		}
		return true;
	}
}