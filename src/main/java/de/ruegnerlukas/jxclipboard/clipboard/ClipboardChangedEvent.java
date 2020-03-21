package de.ruegnerlukas.jxclipboard.clipboard;

import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClipboardChangedEvent extends Publishable {


	/**
	 * The new content of the clipboard.
	 */
	private String content;

}
