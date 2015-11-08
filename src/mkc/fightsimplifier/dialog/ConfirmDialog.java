package mkc.fightsimplifier.dialog;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmDialog {

	private static Button mYesButton;
	private static Button mNoButton;
	private static Stage mDialog;
	private static boolean mAnswer;

	public static boolean display(String title, String message) {
		initialize(title);

		final Label label = new Label(message);

		final HBox buttonLayout = new HBox(10);
		buttonLayout.getChildren().addAll(mYesButton, mNoButton);
		buttonLayout.setAlignment(Pos.CENTER);

		final VBox layout = new VBox(10);
		layout.getChildren().addAll(label, buttonLayout);
		layout.setAlignment(Pos.CENTER);

		final Scene scene = new Scene(layout);
		mDialog.setScene(scene);
		mDialog.showAndWait();

		return mAnswer;
	}

	private static void initialize(String title) {
		mDialog = new Stage();
		mDialog.initModality(Modality.APPLICATION_MODAL);
		mDialog.setTitle(title);
		mDialog.setMinWidth(200);
		mDialog.setMinHeight(125);

		mYesButton = new Button("Ja");
		mYesButton.setOnAction(e -> {
			mAnswer = true;
			mDialog.close();
		});

		mNoButton = new Button("Nej");
		mNoButton.setOnAction(e -> {
			mAnswer = false;
			mDialog.close();
		});

		mDialog.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				event.consume();
				mYesButton.fire();
			} else if (event.getCode() == KeyCode.ESCAPE) {
				event.consume();
				mNoButton.fire();
			}
		});
	}
}
