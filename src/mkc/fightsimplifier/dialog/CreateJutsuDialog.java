package mkc.fightsimplifier.dialog;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mkc.fightsimplifier.container.FighterList;
import mkc.fightsimplifier.guicomponents.NumberTextField;
import mkc.fightsimplifier.models.FighterModel;

public class CreateJutsuDialog {
	private static Button mCreateButton;
	private static Button mDoneButton;
	private static Stage mDialog;
	private static FighterModel mOwner;
	private static FighterModel mJutsu;


	private static TextField mName;
	private static NumberTextField mInitiative;

	public static FighterModel display(FighterModel owner) {
		initialize(owner);

		final HBox titlePane = new HBox(3);
		final Label nameLabel = new Label("Jutsus namn");
		final Label initLabel = new Label("Init till aktivering");

		nameLabel.setPrefSize(nameLabel.getMaxWidth(), nameLabel.getMaxHeight());
		initLabel.setPrefSize(initLabel.getMaxWidth(), initLabel.getMaxHeight());
		nameLabel.setAlignment(Pos.CENTER);
		initLabel.setAlignment(Pos.CENTER);

		titlePane.getChildren().addAll(nameLabel, initLabel);

		final HBox entryPane = new HBox(3);
		entryPane.getChildren().addAll(mName, mInitiative);

		final VBox test = new VBox(5);
		test.getChildren().addAll(titlePane, entryPane);

		final HBox buttonLayout = new HBox(10);
		buttonLayout.getChildren().addAll(mCreateButton, mDoneButton);
		buttonLayout.setAlignment(Pos.BOTTOM_RIGHT);

		final VBox layout = new VBox(5);
		layout.getChildren().addAll(test, buttonLayout);

		final Scene scene = new Scene(layout);
		mDialog.setScene(scene);
		mDialog.showAndWait();

		return mJutsu;
	}

	private static void initialize(FighterModel owner) {
		mOwner = owner;
		mDialog = new Stage();
		mDialog.initModality(Modality.APPLICATION_MODAL);
		mDialog.setTitle("Skapa jutsu");
		mDialog.setMinWidth(250);
		mJutsu = null;

        mDoneButton = new Button("Avbryt");
		mDoneButton.setOnAction(e -> {
			e.consume();
			mDialog.close();
		});

		mCreateButton = new Button("Skapa");
		mCreateButton.setOnAction(e -> {
            if (FighterList.nameAvailable(mName.getText())) {
				mJutsu = new FighterModel(mName.getText(),
						mOwner.getCurrentInitiative() - mInitiative.getValue(), 0, 0, true, mOwner.getName());

				if (mJutsu != null) {
					e.consume();
					mDialog.close();
				}
			}
		});

		mDialog.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				mDoneButton.fire();
			}
			if (event.getCode() == KeyCode.ENTER) {
				mCreateButton.fire();
			}
		});

		mName = new TextField("Namn");
		mInitiative = new NumberTextField("0", false);

		mName.setPrefSize(mName.getMaxWidth(), mName.getMaxHeight());
		mInitiative.setPrefSize(mInitiative.getMaxWidth(), mInitiative.getMaxHeight());
	}
}
