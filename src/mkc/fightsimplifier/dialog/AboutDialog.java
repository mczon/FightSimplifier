package mkc.fightsimplifier.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutDialog {
	public static void display() {
		final Label title = new Label("Stridsprogram");
		final Label text = new Label("Ett enkelt program för att underlätta strider i rollspel.");

		final Label releaseNoticeTitle = new Label("Releasenotice 0.3:");
		final Label releaseNoticeText = new Label("\t- Skapat en enkel prototyp\n\t- Separerat knappar och karaktärer");

		final Label todoTitle = new Label("Kommande");
		final Label todoText = new Label("\t-Loggning\n\t-Ångra knapp\n\tmm...");

		final Label creator = new Label("Skapat av Mårten Carlzon");


		title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		text.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		releaseNoticeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		releaseNoticeText.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		todoTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		todoText.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		creator.setFont(Font.font("Arial", FontWeight.LIGHT,FontPosture.ITALIC, 10));

		creator.setAlignment(Pos.BOTTOM_RIGHT);
		creator.setPadding(new Insets(0, 20, 0, 0));

		final VBox top = new VBox(5);
		top.getChildren().addAll(title, text);
		top.setPadding(new Insets(10, 0, 0, 10));

		final VBox releaseNotice = new VBox(5);
		releaseNotice.getChildren().addAll(releaseNoticeTitle, releaseNoticeText);
		releaseNotice.setPadding(new Insets(10, 0, 0, 10));

		final VBox todo = new VBox(5);
		todo.getChildren().addAll(todoTitle, todoText);
		todo.setPadding(new Insets(10, 0, 0, 10));

		final VBox box = new VBox(10);
		box.getChildren().addAll(top,
				releaseNotice,
				todo);

		final BorderPane border = new BorderPane();
		border.setLeft(box);

		border.setBottom(creator);
		BorderPane.setAlignment(creator, Pos.BOTTOM_RIGHT);
		BorderPane.setMargin(creator, new Insets(0, 0, 10, 0));

		final Scene scene = new Scene(border);
		final Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Om");
		stage.setMinWidth(200);
		stage.setMinHeight(125);

		stage.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				event.consume();
				stage.close();
			}
		});


		stage.setScene(scene);
		stage.showAndWait();
	}
}
