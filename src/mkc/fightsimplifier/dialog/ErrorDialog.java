package mkc.fightsimplifier.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class ErrorDialog {

	private static Button mOkButton;
	private static Stage mDialog;
	private static boolean mAnswer;

	public static boolean display(String title, String message) {
		initialize(title);

		final Label label = new Label(message);

		final HBox buttonLayout = new HBox(10);
		buttonLayout.getChildren().add(mOkButton);
		buttonLayout.setAlignment(Pos.BOTTOM_RIGHT);

		final VBox layout = new VBox(10);
		layout.getChildren().addAll(label, buttonLayout);
		layout.setAlignment(Pos.CENTER);

		final Scene scene = new Scene(layout);
		mDialog.setScene(scene);
		mDialog.setResizable(true);
		mDialog.showAndWait();

		return mAnswer;
	}

	private static void initialize(String title) {
		mDialog = new Stage();
		mDialog.initModality(Modality.APPLICATION_MODAL);
		mDialog.setTitle(title);
		mDialog.setMinWidth(200);
		mDialog.setMinHeight(125);

		mOkButton = new Button("Ok");
		mOkButton.setAlignment(Pos.BOTTOM_RIGHT);
		mOkButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				event.consume();
				mDialog.close();

			}
		});

		mDialog.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE) {
				event.consume();
				mDialog.close();
			}
		});
	}
}
