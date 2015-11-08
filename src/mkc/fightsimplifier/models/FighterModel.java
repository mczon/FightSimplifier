package mkc.fightsimplifier.models;

import mkc.fightsimplifier.FightSimplifier;
import mkc.fightsimplifier.constants.Constants;
import mkc.fightsimplifier.dialog.CreateJutsuDialog;
import mkc.fightsimplifier.dialog.InitiativeDialog;
import mkc.fightsimplifier.dialog.SettingsDialog;

public class FighterModel implements Comparable<FighterModel> {

	private String mName;
	private int mCurrentInitiative;
	private int mStartInitiative;
	private int mAttackModifier;
	private int mDefenceModifier;
	private int mSpecInitModifier;
	private boolean mJutsu;
	private boolean mIsDead;

	private String mOwner;
    private int mWaitTime;

	public FighterModel(String name, int initiative) {
		mName = name;
		mStartInitiative = initiative;
		mCurrentInitiative = initiative;
		mAttackModifier = 0;
		mDefenceModifier = 0;
		mJutsu = false;
		mIsDead = false;
	}

	public FighterModel(String name, int initiative, int attackModifier, int defenceModifier) {
		mName = name;
		mStartInitiative = initiative;
		mCurrentInitiative = initiative;
		mAttackModifier = attackModifier;
		mDefenceModifier = defenceModifier;
		mJutsu = false;
		mIsDead = false;
	}

	public FighterModel(String name, int initiative, int attackModifier, int defenceModifier, boolean isJutsu) {
		mName = name;
		mStartInitiative = initiative;
		mCurrentInitiative = initiative;
		mAttackModifier = attackModifier;
		mDefenceModifier = defenceModifier;
		mJutsu = isJutsu;
		mIsDead = false;
	}

	public FighterModel(String name, int initiative, int attackModifier, int defenceModifier, boolean isJutsu, String owner) {
		mName = name;
		mStartInitiative = initiative;
		mCurrentInitiative = initiative;
		mAttackModifier = attackModifier;
		mDefenceModifier = defenceModifier;
		mJutsu = isJutsu;
		mOwner = owner;
		mIsDead = false;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public int getCurrentInitiative() {
		return mCurrentInitiative;
	}

	public String getCurrentInitAsString() {
		return String.format("%d", mCurrentInitiative);
	}

	public void setCurrentInitiative(int initiative) {
		mCurrentInitiative = initiative;
	}

	public int getStartInitiative() {
		return mStartInitiative;
	}

	public int decreaseInitiative() {
		mCurrentInitiative -= 5;
		return mCurrentInitiative;
	}

	public int decreaseInitiative(int initMod) {
		mCurrentInitiative -= initMod;
		return mCurrentInitiative;
	}

	public void setStartInitiative(int initiative) {
		mStartInitiative = initiative;
	}

	public void resetInitiative() {
		if (mCurrentInitiative < 0) {
			mCurrentInitiative = mStartInitiative + mCurrentInitiative;
		} else {
			mCurrentInitiative = mStartInitiative;
		}
	}

	public int getAttackModifier() {
		return mAttackModifier;
	}

	public String getAttackModifierAsString() {
		return String.format("%d", mAttackModifier);
	}

	public void setAttackModifier(int modifier) {
		mAttackModifier = modifier;
	}

	public int getDefenceModifier() {
		return mDefenceModifier;
	}

	public String getDefenceModifierAsString() {
		return String.format("%d", mDefenceModifier);
	}

	public void setDefenceModifier(int modifier) {
		mDefenceModifier = modifier;
	}

	public String getSpecModifierAsString() {
		return String.format("%d", mSpecInitModifier);
	}

	public void setSpecInitModifier(int modifier) {
		mSpecInitModifier = modifier;
	}

	public void increaseAttackModifier(int value) {
		mAttackModifier += value;
	}

	public void increaseDefenceModifier(int value) {
		mDefenceModifier += value;
	}

	public void increaseAttackModifier() {
		mAttackModifier += 25;
	}

	public void increaseDefenceModifier() {
		mDefenceModifier += 25;
	}

	public void resetModifiers() {
		mAttackModifier = 0;
		mDefenceModifier = 0;
	}

	public boolean isJutsu() {
		return mJutsu;
	}

	public void setJutsu(boolean jutsu) {
		mJutsu = jutsu;
	}

	public boolean isDead() {
		return mIsDead;
	}

	public void setIsDead(boolean isDead) {
		mIsDead = isDead;
	}

	public String getOwner() {
		return mOwner;
	}

	public void newRound() {
		resetInitiative();
		resetModifiers();
	}

	public boolean updateFighter(String action) {
		boolean remove = false;
		if (Constants.ATTACK.equals(action)) {
			if (isJutsu()) {
				remove = true;
			} else {
				increaseAttackModifier();
				decreaseInitiative(SettingsDialog.sAttackInit.getValue());
			}
		} else if (Constants.DEFEND.equals(action)) {
			decreaseInitiative(SettingsDialog.sDefenceInit.getValue());
			increaseAttackModifier();
		} else if (Constants.DEFENCIVE_MANEUVER.equals(action)) {
			// Increase defence modifier
            increaseDefenceModifier();
		} else if (Constants.WAIT.equals(action)) {
			// Wait dialog
            final int init = InitiativeDialog.display("Handling: vänta", "Hur många init ska fightern vänta?");
            if (init > 0) {
				decreaseInitiative(init);
			}
        } else if (Constants.WAIT_FIVE.equals(action)) {
            decreaseInitiative();
		} else if (Constants.JUTSU.equals(action)) {
			// Jutsu dialog
			FighterModel jutsu = CreateJutsuDialog.display(this);
            if (jutsu != null) {
                decreaseInitiative(SettingsDialog.sJutsuInit.getValue());
				increaseAttackModifier();
                FightSimplifier.addFighter(jutsu);
			}
		} else if (Constants.KI_ATTACK.equals(action)) {
			final int initMod = InitiativeDialog.display("Handling: ki attack", "Om hur många init aktiveras ki-attacken?",
					SettingsDialog.sKiInit.getValue());
			if (initMod != -1) {
				decreaseInitiative(initMod);
				increaseAttackModifier();
			}
		} else if (Constants.SPEC.equals(action)) {
			decreaseInitiative(mSpecInitModifier);
		} else if (action.equals(Constants.DEAD)) {
			if (isDead()) {
				remove = true;
			} else {
				setIsDead(true);
			}
		} else if (Constants.SPEC.equals(action)) {
			decreaseInitiative(mSpecInitModifier);
		}

		return remove;
	}

	@Override
	public int compareTo(FighterModel other) {
		final int currentInitiativeCompare = Integer.compare(other.getCurrentInitiative(), getCurrentInitiative());
		if (currentInitiativeCompare == 0) {
			if (isJutsu() && !other.isJutsu()) {
				return -1;
			} else if (!isJutsu() && other.isJutsu()) {
				return 1;
			} else {
				final int startInitiativeCompare = Integer.compare(getStartInitiative(), other.getStartInitiative());
				if (startInitiativeCompare == 0) {
					return other.getName().compareTo(getName());
                } else {
					return startInitiativeCompare;
				}
			}
		} else {
			return currentInitiativeCompare;
		}
	}
}
