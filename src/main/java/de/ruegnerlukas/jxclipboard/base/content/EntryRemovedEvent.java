package de.ruegnerlukas.jxclipboard.base.content;

import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EntryRemovedEvent extends Publishable {


	/**
	 * The unique identifier of the entry.
	 */
	private final String entryId;


	/**
	 * Whether the entry was removed by the user
	 */
	private final boolean removedByUser;

}
