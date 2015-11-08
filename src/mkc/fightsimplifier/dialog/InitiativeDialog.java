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
import mkc.fightsimplifier.guicomponents.NumberTextField;

public class InitiativeDialog {

	private static Button mOkButton;
	private static Button mCancelButton;
	private static Stage mDialog;
	private static int mAnswer;
	private static NumberTextField mInit;

	public static int display(String title, String message) {
		return display(title, message, 0);
	}

	public static int display(String title, String message, int startValue) {
		initialize(title);

		final Label label = new Label(message);

		final HBox buttonLayout = new HBox(10);
		buttonLayout.getChildren().addAll(mOkButton, mCancelButton);
		buttonLayout.setAlignment(Pos.CENTER);

		mInit = new NumberTextField(startValue, false);
		mInit.setPrefSize(mInit.getMaxWidth(), mInit.getMaxHeight());

		final VBox layout = new VBox(10);
		layout.getChildren().addAll(label, mInit, buttonLayout);
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

		mOkButton = new Button("Ok");
		mOkButton.setOnAction(e -> {
			mAnswer = Integer.parseInt(mInit.getText());
			mDialog.close();
		});

		mCancelButton = new Button("Avbryt");
		mCancelButton.setOnAction(e -> {
			mAnswer = -1;
			mDialog.close();
		});

		mDialog.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			if (event.getCode() == KeyCode.ENTER ) {
				event.consume();
				mOkButton.fire();
			} else if (event.getCode() == KeyCode.ESCAPE) {
				event.consume();
				mCancelButton.fire();
			}
		});
	}
}