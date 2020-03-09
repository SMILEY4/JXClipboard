package de.ruegnerlukas.jxclipboard.base.content;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RemoveEntryCommand {


	/**
	 * The id of this command type
	 */
	public static final String COMMAND_ID = "jxclipboard.content.removeentry";


	/**
	 * The unique identifier of the entry.
	 */
	private final String entryId;

}
