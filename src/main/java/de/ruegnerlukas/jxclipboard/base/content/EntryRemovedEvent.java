package de.ruegnerlukas.jxclipboard.base.content;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EntryRemovedEvent {


	/**
	 * The id of this event type
	 */
	public static final String EVENT_ID = "jxclipboard.content.entryremoved";


	/**
	 * The unique identifier of the entry.
	 */
	private final String entryId;


	/**
	 * Whether the entry was removed by the user
	 */
	private final boolean removedByUser;

}
