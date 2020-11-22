package de.ruegnerlukas.jxclipboard;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JXClipboardState extends SuiState {


	private final List<String> savedEntries = new ArrayList<>();




	public List<String> getSavedEntries() {
		return Collections.unmodifiableList(savedEntries);
	}




	public void addEntry(final String entry) {
		if (savedEntries.contains(entry)) {
			savedEntries.remove(entry);
		}
		savedEntries.add(0, entry);
	}




	public void removeEntry(final String entry) {
		savedEntries.remove(entry);
	}

}
