package mkc.fightsimplifier.guicomponents;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import mkc.fightsimplifier.constants.Constants;
import mkc.fightsimplifier.container.FighterList;
import mkc.fightsimplifier.models.FighterModel;

public class FighterRow extends HBox {

    public static final int NUMBER_OF_CELLS = 9;
    private Label mNameLabel;
    private Label mInitLabel;
    private Label mModAttackLabel;
    private Label mModDefenceLabel;
    private Button mDeadButton;
    private FighterModel mFighter;

    public FighterRow(FighterModel fighter) {
        mFighter = fighter;
        setupRow(fighter);

        setAlignment(Pos.CENTER);
        setStyle("-fx-text-alignment: center;");
        setPrefSize(getMaxWidth(), getMaxHeight());
        getChildren().addAll(mNameLabel, mInitLabel, mModAttackLabel, mModDefenceLabel, mDeadButton);
    }


    public boolean updateRow(String action) {
        boolean removeFighter = mFighter.updateFighter(action);
        if (mFighter.isDead()) {
            mNameLabel.setStyle("-fx-text-color: red;-fx-border-color: black");
            mInitLabel.setText("-");
            mModAttackLabel.setText("-");
            mModDefenceLabel.setText("-");
            mDeadButton.setText("Ta bort");
        } else {
        	mInitLabel.setText(mFighter.getCurrentInitAsString());
        	mModAttackLabel.setText(mFighter.getAttackModifierAsString());
        	mModDefenceLabel.setText(mFighter.getDefenceModifierAsString());
        }
        return removeFighter;
    }


    private void setupRow(FighterModel fighter) {
        mNameLabel = new Label(fighter.getName());
        mInitLabel = new Label(fighter.getCurrentInitAsString());
        mModAttackLabel = new Label(fighter.getAttackModifierAsString());
        mModDefenceLabel = new Label(fighter.getAttackModifierAsString());

        mDeadButton = new Button("Död");
        mDeadButton.setOnAction(e -> FighterList.actionPerformed(Constants.DEAD, mFighter.getName()));
        mDeadButton.setStyle("-fx-base: #ff0000; -fx-text-alignment: center;");
    }

    public Label getNameLabel() {
        return mNameLabel;
    }

    public Label getInitLabel() {
        return mInitLabel;
    }

    public Label getModAttackLabel() {
        return mModAttackLabel;
    }

    public Label getModDefenceLabel() {
        return mModDefenceLabel;
    }

    public Button getDeadButton() {
        return mDeadButton;
    }

    public FighterModel getFighter() {
        return mFighter;
    }


	public void newRound() {
        mFighter.newRound();
        if (mFighter.isDead()) {
            mNameLabel.setStyle("-fx-text-color: red;-fx-border-color: black");
            mInitLabel.setText("-");
            mModAttackLabel.setText("-");
            mModDefenceLabel.setText("-");
            mDeadButton.setText("Ta bort");
        } else {
        	mInitLabel.setText(mFighter.getCurrentInitAsString());
        	mModAttackLabel.setText(mFighter.getAttackModifierAsString());
        	mModDefenceLabel.setText(mFighter.getDefenceModifierAsString());
        }
	}
}
