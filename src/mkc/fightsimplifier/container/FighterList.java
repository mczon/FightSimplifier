package mkc.fightsimplifier.container;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import mkc.fightsimplifier.FightSimplifier;
import mkc.fightsimplifier.guicomponents.FighterRow;
import mkc.fightsimplifier.listeners.FighterActionListener;
import mkc.fightsimplifier.models.FighterModel;

public class FighterList implements FighterActionListener {

	static private final Map<String, FighterRow> mFighterRows = Maps.newHashMap();

	public static void actionPerformed(String action, String fighter) {

		FighterRow fighterRow = mFighterRows.get(fighter);
		if (fighterRow != null) {
			boolean removeFighter = fighterRow.updateRow(action);
			if (removeFighter) {
				mFighterRows.remove(fighter);
			}

			FightSimplifier.updateFightersPane();
		}
	}

	@Override
	public void addFighters(List<FighterModel> fighters) {
		for (final FighterModel fighter : fighters) {
			final String name = fighter.getName();
			if (!"".equals(name) && nameAvailable(name)) {
				mFighterRows.put(name, new FighterRow(fighter));
			}
		}
	}

	public static List<FighterRow> getFighterRowsSorted() {
		List<FighterRow> rows = Lists.newArrayList(mFighterRows.values());
		rows.sort((row1, row2) -> {
			FighterModel fighter1 = row1.getFighter();
			FighterModel fighter2 = row2.getFighter();

			int retValue = Integer.compare(fighter1.getCurrentInitiative(), fighter2.getCurrentInitiative());
			if (retValue == 0) {
				retValue = Integer.compare(fighter1.getStartInitiative(), fighter2.getStartInitiative());
			}
			return retValue * -1;
		});
		return rows;
	}

	static public boolean nameAvailable(String name) {
		return !mFighterRows.containsKey(name);
	}

	public FighterModel getFighter(String selectedItem) {
		FighterRow fighterRow = mFighterRows.get(selectedItem);
		FighterModel fighter = null;
		if (fighterRow != null) {
			fighter = fighterRow.getFighter();
		}

		return fighter;
	}

    public static void resetRound() {
        for (FighterRow row : mFighterRows.values()) {
            row.newRound();
        }
        FightSimplifier.updateFightersPane();
    }
}
