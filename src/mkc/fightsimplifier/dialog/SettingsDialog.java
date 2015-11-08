package mkc.fightsimplifier.dialog;

import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mkc.fightsimplifier.guicomponents.NumberTextField;

public class SettingsDialog {

	public static NumberTextField sAttackInit = new NumberTextField("5", false);
	public static NumberTextField sDefenceInit = new NumberTextField("0", false);
	public static NumberTextField sDefenciveManeuverInit = new NumberTextField("1", false);
	public static NumberTextField sJutsuInit = new NumberTextField("5", false);
	public static NumberTextField sKiInit = new NumberTextField("5", false);
	public static NumberTextField sSpecDefaultInit = new NumberTextField("1", false);

	private static Stage mDialog;
	private static Button mDoneButton;

	public static void display() {
		initialize();

		final Label attackLabel = new Label("Initiativ attack:");
		final Label defenceLabel = new Label("Initiativ försvar:");
		final Label defenciveManeuverLabel = new Label("Initiativ denfensiv manöver:");
		final Label jutsuLabel = new Label("Default initiativ jutsu:");
		final Label kiLabel = new Label("Default initiativ ki:");
		final Label specLabel = new Label("Default initiativ spec:");

		attackLabel.setPrefSize(attackLabel.getMaxWidth(), attackLabel.getMaxHeight());
		defenceLabel.setPrefSize(defenceLabel.getMaxWidth(), defenceLabel.getMaxHeight());
		defenciveManeuverLabel.setPrefSize(defenciveManeuverLabel.getMaxWidth(), defenciveManeuverLabel.getMaxHeight());
		jutsuLabel.setPrefSize(jutsuLabel.getMaxWidth(), jutsuLabel.getMaxHeight());
		kiLabel.setPrefSize(kiLabel.getMaxWidth(), kiLabel.getMaxHeight());
		specLabel.setPrefSize(specLabel.getMaxWidth(), specLabel.getMaxHeight());

        final GridPane pane = new GridPane();
        pane.add(attackLabel, 0, 0);
        pane.add(sAttackInit, 1, 0);
        pane.add(defenceLabel, 0, 1);
        pane.add(sDefenceInit, 1, 1);
        pane.add(defenciveManeuverLabel, 0, 2);
        pane.add(sDefenciveManeuverInit, 1, 2);
        pane.add(jutsuLabel, 0, 3);
        pane.add(sJutsuInit, 1, 3);
        pane.add(kiLabel, 0, 4);
        pane.add(sKiInit, 1, 4);
        pane.add(specLabel, 0, 5);
        pane.add(sSpecDefaultInit, 1, 5);
        pane.add(mDoneButton, 1, 6);
        GridPane.setHalignment(mDoneButton, HPos.RIGHT);

        final Scene scene = new Scene(pane);
		mDialog.setScene(scene);
		mDialog.showAndWait();
	}

	private static void initialize() {
		mDialog = new Stage();
		mDialog.initModality(Modality.APPLICATION_MODAL);
		mDialog.setTitle("Inställningar");
		mDialog.setMinWidth(250);

		mDoneButton = new Button("Klar");
		mDoneButton.setOnAction(e -> {
			e.consume();
			mDialog.close();
		});

		mDialog.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				mDoneButton.fire();
			}
		});
	}
}