package de.ruegnerlukas.jxclipboard.base.content;

import javafx.scene.Node;
import javafx.scene.control.Label;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AddEntryCommand {


	/**
	 * The id of this command type
	 */
	public static final String COMMAND_ID = "jxclipboard.content.addentry";


	/**
	 * The unique identifier of the entry.
	 */
	private final String entryId;

	/**
	 * The javafx node to add.
	 */
	private final Node node;


	/**
	 * Whether the entry can be removed by the user.
	 */
	private final boolean removable;


	/*
	 * TODO: priority: int
	 * 		higher -> entry shows up on top
	 * 		lower -> entry at bottom
	 * 		equal priority -> newer entry on top
	 */




	/**
	 * Creates a command to add a label as a new entry.
	 *
	 * @param id        the id of the entry
	 * @param removable whether the entry is removable by the user.
	 * @param text      the text of the label
	 * @return the created {@link AddEntryCommand}
	 */
	public static AddEntryCommand labelEntry(final String id, final boolean removable, final String text) {
		return AddEntryCommand.builder()
				.entryId(id)
				.removable(removable)
				.node(new Label(text))
				.build();
	}

}
