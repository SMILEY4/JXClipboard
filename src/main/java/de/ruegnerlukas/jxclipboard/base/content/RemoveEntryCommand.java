package de.ruegnerlukas.jxclipboard.base.content;

import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RemoveEntryCommand extends Publishable {


	/**
	 * The unique identifier of the entry.
	 */
	private final String entryId;

}
