package de.ruegnerlukas.jxclipboard.base.content;

import de.ruegnerlukas.simpleapplication.common.callbacks.EmptyCallback;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AddEntryCommand extends Publishable {


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




	public static AddEntryCommand clipboardEntry(final String id, final String content, final EmptyCallback onCopyCallback) {

		final Label label = new Label(content);
		label.setMinWidth(0);
		label.setPrefWidth(10000);
		label.setMaxWidth(10000);

		final Button button = new Button("C");
		button.setMinSize(35, 35);
		button.setPrefSize(35, 35);
		button.setMaxSize(35, 35);
		button.setOnAction(e -> onCopyCallback.execute());

		final HBox box = new HBox();
		box.setAlignment(Pos.CENTER_LEFT);
		box.getChildren().addAll(label, button);

		return AddEntryCommand.builder()
				.entryId(id)
				.removable(true)
				.node(box)
				.build();

	}


}
