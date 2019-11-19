package Tetris;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PaneOrganizer {
	private BorderPane _root;
	private VBox _quit;

	/**
	 * I set up my borderpane and add my game to the pane. I also set the focus on
	 * my pane and create a quit button
	 */
	public PaneOrganizer() {
		_root = new BorderPane();
		_root.setStyle("-fx-background-color: black;");
		Pane myPane = new Pane();
		myPane.setPrefSize(Constants.PANE_WIDTH, Constants.PANE_HEIGHT);
		new Game(myPane);
		myPane.requestFocus();
		_root.setCenter(myPane);
		this.createQuit();

	}

	/**
	 * This returns my borderpane to be used in my app class
	 */
	public BorderPane getRoot() {
		return _root;
	}

	/**
	 * I make my quit button and position it and add it to the pane
	 */
	public void createQuit() {
		_quit = new VBox();
		Button b1 = new Button("Quit");
		_quit.getChildren().addAll(b1);
		_root.setBottom(_quit);
		_quit.setAlignment(Pos.CENTER);
		b1.setOnAction(new ClickHandler());
		_quit.setFocusTraversable(false);
		b1.setFocusTraversable(false);
	}

	/**
	 * When the quit button is clicked, the system exits.
	 */
	private class ClickHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			System.exit(0);
		}
	}

}
