package mkc.fightsimplifier;

import java.util.List;

import com.google.common.collect.Lists;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mkc.fightsimplifier.constants.Constants;
import mkc.fightsimplifier.container.FighterList;
import mkc.fightsimplifier.dialog.AboutDialog;
import mkc.fightsimplifier.dialog.CreateFightersDialog;
import mkc.fightsimplifier.dialog.SettingsDialog;
import mkc.fightsimplifier.guicomponents.FighterRow;
import mkc.fightsimplifier.guicomponents.NumberTextField;
import mkc.fightsimplifier.models.FighterModel;

public class FightSimplifier extends Application {

    private final static FighterList mFighterList = new FighterList();
    private final static GridPane mFightersPane = new GridPane();
    private static Stage mMainWindow;
    private BorderPane mWindowPain;
    private static NumberTextField mStartInit;
    private static Button mStartInitButton;
    private static final ComboBox<String> mFighters = new ComboBox<String>();
    private static Button mAttackButton;
    private static Button mDefendButton;
    private static Button mDefenciveManoverButton;
    private static Button mJutsuButton;
    private static Button mWaitButton;
    private static Button mWaitFiveButton;
    private static NumberTextField mSpecInit;
    private static Button mSpecAttackButton;
    private static ScrollPane mScrollPane = new ScrollPane();
    private static Label mNameLabel;
    private static Label mInitLabel;
    private static Label mAttackModLabel;
    private static Label mDefenceModLabel;
    private static Button mResetRoundButton;


    public static void main(String[] args) {
        System.setProperty("glass.accessible.force", "false");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mMainWindow = primaryStage;
        mMainWindow.setTitle("Fightsimplifier");
        mFighters.editableProperty().set(false);
        mFighters.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                String name = mFighters.getSelectionModel().getSelectedItem();
                FighterModel fighter = mFighterList.getFighter(name);

                if (fighter == null || fighter.getCurrentInitiative() <= 0) {
                    setButtonsActive(false);
                } else {
                    setButtonsActive(true);
                    mStartInit.setValue(fighter.getStartInitiative());
                }
            }

        });

        initializeActionPane();
        initializeDisplayRow();
        updateFightersPane();

        mScrollPane.setContent(mFightersPane);

        mWindowPain = new BorderPane();
        mWindowPain.setTop(createMenuPane());
        mWindowPain.setCenter(mScrollPane);

        mMainWindow.setScene(new Scene(mWindowPain, 1200, 800));
        mMainWindow.show();
    }

    public static void addFighter(FighterModel fighter) {
        mFighterList.addFighters(Lists.newArrayList(fighter));
    }

    static public void updateFightersPane() {
        mFightersPane.getChildren().clear();
        mFighters.getItems().clear();
        final List<FighterRow> fighterRows = FighterList.getFighterRowsSorted();
        mFightersPane.add(mNameLabel, 0, 0, 3, 1);
        mFightersPane.add(mInitLabel, 3, 0, 1, 1);
        mFightersPane.add(mAttackModLabel, 4, 0, 1, 1);
        mFightersPane.add(mDefenceModLabel, 5, 0, 1, 1);
        mFightersPane.add(mResetRoundButton, 6, 0, 2, 1);
        int rowIndex = 1;
        if (!fighterRows.isEmpty()) {
            for (final FighterRow row : fighterRows) {
                mFightersPane.add(row.getNameLabel(), 0, rowIndex, 3, 1);
                mFightersPane.add(row.getInitLabel(), 3, rowIndex, 1, 1);
                mFightersPane.add(row.getModAttackLabel(), 4, rowIndex, 1, 1);
                mFightersPane.add(row.getModDefenceLabel(), 5, rowIndex, 1, 1);
                mFightersPane.add(row.getDeadButton(), 6, rowIndex, 2, 1);
                mFighters.getItems().add(row.getFighter().getName());
                rowIndex++;
            }
            FighterModel fighter = fighterRows.get(0).getFighter();
			mFighters.setValue(fighter.getName());
            mStartInit.setValue(fighter.getStartInitiative());
            setButtonsActive(fighter.getCurrentInitiative() > 0);
        } else {
            mFighters.setValue("");
            setButtonsActive(false);
        }
        for (rowIndex++; rowIndex < 10; rowIndex++) {
            mFightersPane.add(new Label(""), 0, rowIndex);
        }

        mFightersPane.add(mAttackButton, 0, rowIndex, 1, 1);
        mFightersPane.add(mDefendButton, 1, rowIndex, 1, 1);
        mFightersPane.add(mDefenciveManoverButton, 2, rowIndex, 2, 1);
        mFightersPane.add(mJutsuButton, 4, rowIndex, 1, 1);
        mFightersPane.add(mWaitButton, 5, rowIndex, 1, 1);
        mFightersPane.add(mWaitFiveButton, 6, rowIndex, 1, 1);
        rowIndex++;

        mFightersPane.add(mFighters, 0, rowIndex, 3, 1);
        mFightersPane.add(mSpecInit, 3, rowIndex, 2, 1);
        mFightersPane.add(mSpecAttackButton, 5, rowIndex);
        rowIndex++;

        mFightersPane.add(mStartInit, 3, rowIndex, 2, 1);
        mFightersPane.add(mStartInitButton, 5, rowIndex);
        mMainWindow.requestFocus();
        mAttackButton.requestFocus();
    }

    private static void initializeDisplayRow() {
        if (mNameLabel == null) {
            mNameLabel = new Label("Namn");
            mInitLabel = new Label("Init");
            mAttackModLabel = new Label("Attack");
            mDefenceModLabel = new Label("Försvar");
            mResetRoundButton = new Button("Ny runda");

            mNameLabel.setMaxWidth(Double.MAX_VALUE);
            mInitLabel.setMaxWidth(Double.MAX_VALUE);
            mAttackModLabel.setMaxWidth(Double.MAX_VALUE);
            mDefenceModLabel.setMaxWidth(Double.MAX_VALUE);
            mResetRoundButton.setMaxWidth(Double.MAX_VALUE);

            mResetRoundButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FighterList.resetRound();
                }
            });
        }
    }

    private void initializeActionPane() {
        mAttackButton = new Button("Attack");
        mDefendButton = new Button("Försvar");
        mDefenciveManoverButton = new Button("Undvikande manöver");
        mJutsuButton = new Button("Jutsu");
        mWaitButton = new Button("Vänta");
        mWaitFiveButton = new Button("Vänta 5");

        mAttackButton.setMaxWidth(Double.MAX_VALUE);
        mDefendButton.setMaxWidth(Double.MAX_VALUE);
        mDefenciveManoverButton.setMaxWidth(Double.MAX_VALUE);
        mJutsuButton.setMaxWidth(Double.MAX_VALUE);
        mWaitButton.setMaxWidth(Double.MAX_VALUE);
        mWaitFiveButton.setMaxWidth(Double.MAX_VALUE);

        mAttackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FighterList.actionPerformed(Constants.ATTACK, (String) mFighters.getSelectionModel().getSelectedItem());
            }
        });
        mDefendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FighterList.actionPerformed(Constants.DEFEND, (String) mFighters.getSelectionModel().getSelectedItem());
            }
        });
        mDefenciveManoverButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FighterList.actionPerformed(Constants.DEFENCIVE_MANEUVER, (String) mFighters.getSelectionModel().getSelectedItem());
            }
        });
        mJutsuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FighterList.actionPerformed(Constants.JUTSU, (String) mFighters.getSelectionModel().getSelectedItem());
            }
        });
        mWaitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FighterList.actionPerformed(Constants.WAIT, (String) mFighters.getSelectionModel().getSelectedItem());
            }
        });
        mWaitFiveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FighterList.actionPerformed(Constants.WAIT_FIVE, (String) mFighters.getSelectionModel().getSelectedItem());
            }
        });

        mSpecInit = new NumberTextField(SettingsDialog.sSpecDefaultInit.getValue(), true);
        mSpecAttackButton = new Button("Valfri");
        mSpecAttackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                FighterModel fighter = mFighterList.getFighter(mFighters.getSelectionModel().getSelectedItem());
                fighter.setSpecInitModifier(mSpecInit.getValue());
                FighterList.actionPerformed(Constants.SPEC, fighter.getName());
            }
        });

        mStartInit = new NumberTextField(0, true);
        mStartInitButton = new Button("Start init");
        mStartInitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                FighterModel fighter = mFighterList.getFighter(mFighters.getSelectionModel().getSelectedItem());
                fighter.setStartInitiative(mStartInit.getValue());
            }
        });

        mAttackButton.defaultButtonProperty().bind(mAttackButton.focusedProperty());
        mDefendButton.defaultButtonProperty().bind(mDefendButton.focusedProperty());
        mDefenciveManoverButton.defaultButtonProperty().bind(mDefenciveManoverButton.focusedProperty());
        mJutsuButton.defaultButtonProperty().bind(mJutsuButton.focusedProperty());
        mWaitButton.defaultButtonProperty().bind(mWaitButton.focusedProperty());
        mWaitFiveButton.defaultButtonProperty().bind(mWaitFiveButton.focusedProperty());
        mSpecAttackButton.defaultButtonProperty().bind(mSpecAttackButton.focusedProperty());
        mStartInitButton.defaultButtonProperty().bind(mStartInitButton.focusedProperty());
        mFighters.setOnKeyPressed((KeyEvent E) -> {
            mFighters.show();
            E.consume();
        });
        mFighters.setMaxWidth(Double.MAX_VALUE);
    }

    private Node createMenuPane() {
        final MenuBar menuBar = new MenuBar();
        final Menu arkivMenu = new Menu("Arkiv");
        final Menu fightMenu = new Menu("Strid");
        final Menu aboutMenu = new Menu("Om");

        final MenuItem settingsMenuItem = new MenuItem("Inställningar");
        settingsMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));
        settingsMenuItem.setOnAction(t -> SettingsDialog.display());
        arkivMenu.getItems().add(settingsMenuItem);

        final MenuItem addFightersMenuItem = new MenuItem("Lägg till fighters");
        addFightersMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        addFightersMenuItem.setOnAction(t -> {
            mFighterList.addFighters(CreateFightersDialog.display());
            updateFightersPane();
        });
        fightMenu.getItems().add(addFightersMenuItem);

        final MenuItem aboutMenuItem = new MenuItem("Om");
        aboutMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        aboutMenuItem.setOnAction(t -> AboutDialog.display());
        aboutMenu.getItems().add(aboutMenuItem);


        menuBar.getMenus().addAll(arkivMenu, fightMenu, aboutMenu);
        VBox topPane = new VBox();
        topPane.getChildren().add(menuBar);
        return topPane;
    }

    private static void setButtonsActive(boolean active) {
        mAttackButton.setDisable(!active);
        mDefendButton.setDisable(!active);
        mDefenciveManoverButton.setDisable(!active);
        mJutsuButton.setDisable(!active);
        mWaitButton.setDisable(!active);
        mWaitFiveButton.setDisable(!active);
        mSpecAttackButton.setDisable(!active);
        mSpecInit.setDisable(!active);
    }
}
