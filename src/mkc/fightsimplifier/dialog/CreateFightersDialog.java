package mkc.fightsimplifier.dialog;

import java.util.List;

import com.google.common.collect.Lists;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mkc.fightsimplifier.container.FighterList;
import mkc.fightsimplifier.guicomponents.NumberTextField;
import mkc.fightsimplifier.models.FighterModel;

public class CreateFightersDialog {

    private static Button mCreateButton;
	private static Button mClearButton;
	private static Button mAddRowButton;
	private static Button mRemoveRowButton;
	private static Stage mDialog;
	private static ScrollPane mScrollPane = new ScrollPane();

	private static List<CreationFighterRow> mRows;
	private static BorderPane mLayout;
	private static GridPane mFighterPanel;
    private static Label mNameDuplicate;

	public static List<FighterModel> display() {
		initialize();

		final HBox titlePane = new HBox(3);
		final Label nameLabel = new Label("Namn");
		final Label initLabel = new Label("Init");

		nameLabel.setPrefSize(nameLabel.getMaxWidth(), nameLabel.getMaxHeight());
		initLabel.setPrefSize(initLabel.getMaxWidth(), initLabel.getMaxHeight());
		nameLabel.setAlignment(Pos.CENTER);
		initLabel.setAlignment(Pos.CENTER);

		titlePane.getChildren().addAll(nameLabel, initLabel);

		mFighterPanel = new GridPane();
		mFighterPanel.add(nameLabel, 0, 0);
		mFighterPanel.add(initLabel, 1, 0);

		int rowIndex = 1;
		for (CreationFighterRow row : mRows) {
			mFighterPanel.add(row.getName(), 0, rowIndex);
			mFighterPanel.add(row.getInit(), 1, rowIndex);
			rowIndex++;
		}

		final VBox createFighterPanel = new VBox(5);
        createFighterPanel.getChildren().addAll(titlePane, mScrollPane);

        final HBox endRow = new HBox(10);
        endRow.getChildren().addAll(mNameDuplicate, mAddRowButton, mRemoveRowButton, mCreateButton, mClearButton);
        endRow.setPrefSize(nameLabel.getMaxWidth(), nameLabel.getMaxHeight());


		mScrollPane.setVmax(1);
		mScrollPane.setPrefSize(mScrollPane.getMaxWidth(), mScrollPane.getMaxHeight());
		mScrollPane.setMinSize(mScrollPane.getMaxWidth(), mScrollPane.getMaxHeight());
		mScrollPane.setContent(mFighterPanel);

		mLayout = new BorderPane();
		mLayout.setCenter(createFighterPanel);
        mLayout.setBottom(endRow);

		final Scene scene = new Scene(mLayout, mLayout.getMaxWidth(), mLayout.getMaxHeight());
		mDialog.setScene(scene);
		mDialog.showAndWait();

		return createFighters();
	}

	private static List<FighterModel> createFighters() {
		final List<FighterModel> fighters = Lists.newArrayList();
		for (final CreationFighterRow row : mRows) {
			if (row.mName.getText() != null && !"".equals(row.mName.getText())) {
				fighters.add(new FighterModel(row.mName.getText(), row.mInit.getValue()));
			}
		}
		return fighters;
	}

	private static void initialize() {
		mDialog = new Stage();
		mDialog.initModality(Modality.WINDOW_MODAL);
		mDialog.setTitle("Skapa anfallare");

		mClearButton = new Button("Töm");
		mClearButton.setOnAction(e -> {
			creationReset();
		});

        mCreateButton = new Button("Skapa");
        mCreateButton.setOnAction(e -> {
			e.consume();
			mDialog.close();
		});

		mAddRowButton = new Button("+");
		mAddRowButton.setOnAction(e -> {
			final CreationFighterRow row = new CreationFighterRow(new TextField(""), new NumberTextField("", false));
            row.getName().textProperty().addListener(new NameChangedListener());
			mFighterPanel.add(row.getName(), 0, mRows.size() + 1);
			mFighterPanel.add(row.getInit(), 1, mRows.size() + 1);
			mRows.add(row);
		});

		mRemoveRowButton = new Button("-");
		mRemoveRowButton.setOnAction(e -> {
			if (!mRows.isEmpty()) {
				if (mRows.size() > 1) {
				final CreationFighterRow row = mRows.get(mRows.size() - 1);
				mFighterPanel.getChildren().removeAll(row.getName(), row.getInit());
				mRows.remove(mRows.size() - 1);
				}
			}
		});

		mClearButton.setPrefSize(mClearButton.getMaxWidth(), mClearButton.getMaxHeight());
        mCreateButton.setPrefSize(mCreateButton.getMaxWidth(), mCreateButton.getMaxHeight());
		mAddRowButton.setPrefSize(mAddRowButton.getMaxWidth(), mAddRowButton.getMaxHeight());
		mRemoveRowButton.setPrefSize(mRemoveRowButton.getMaxWidth(), mRemoveRowButton.getMaxHeight());

		mDialog.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				mClearButton.fire();
				mDialog.close();
			}
			if (event.getCode() == KeyCode.ENTER) {
                mCreateButton.fire();
			}
		});


		mRows = Lists.newArrayList();
		for (int i = 0; i < 10; ++i) {
			TextField name = new TextField("Name");
			name.setPrefSize(name.getMaxWidth(), name.getMaxHeight());
            name.textProperty().addListener(new NameChangedListener());
			final CreationFighterRow fr = new CreationFighterRow(name, new NumberTextField(0, false));
			mRows.add(fr);
		}

        mNameDuplicate = new Label("Namndublett existerar");
        mNameDuplicate.setTextFill(Color.RED);
        mNameDuplicate.setVisible(false);
        mNameDuplicate.setPrefSize(mNameDuplicate.getMaxWidth(), mNameDuplicate.getMaxHeight());

		creationReset();

	}

	private static void creationReset() {
		for (final CreationFighterRow fr : mRows) {
			fr.mInit.setText("");
			fr.mName.setText("");
		}
	}

	private static class CreationFighterRow extends HBox{
		TextField mName;
		NumberTextField mInit;

		public CreationFighterRow(TextField name, NumberTextField init) {
			mName = name;
			mInit = init;

			mName.setPrefSize(mName.getMaxWidth(), mName.getMaxHeight());
			mInit.setPrefSize(mInit.getMaxWidth(), mInit.getMaxHeight());

			getChildren().addAll(mName, mInit);
		}

		public TextField getName() {
			return mName;
		}

		public NumberTextField getInit() {
			return mInit;
		}
	}

    private static class NameChangedListener implements ChangeListener<String> {

        @Override
         public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
            if (!"".equals(newValue) && newValue != null) {
                if (!nameAvailable(newValue)) {
                    mCreateButton.setDisable(true);
                    mNameDuplicate.setVisible(true);
                } else {
                    mCreateButton.setDisable(false);
                    mNameDuplicate.setVisible(false);
                }
            }
         }

        private boolean nameAvailable(String name) {
            boolean available = FighterList.nameAvailable(name);
            if (available) {
                int counter = 0;
                for (CreationFighterRow row : mRows) {
                    if (name.equals(row.mName.getText())) {
                        counter++;
                    }
                }
                available = counter == 1;
            }
            return available;
        }


    }
}


