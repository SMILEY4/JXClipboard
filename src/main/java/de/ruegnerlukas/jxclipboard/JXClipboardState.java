package de.ruegnerlukas.jxclipboard;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class JXClipboardState extends SuiState {


	@Getter
	private final List<String> savedEntries = new ArrayList<>();

}
