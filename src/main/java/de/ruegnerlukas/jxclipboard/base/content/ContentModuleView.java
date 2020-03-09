package de.ruegnerlukas.jxclipboard.base.content;

import de.ruegnerlukas.simpleapplication.common.events.EventSource;
import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EmptyEvent;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedCommand;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedEvent;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleView;
import de.ruegnerlukas.simpleapplication.core.presentation.utils.Anchors;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class ContentModuleView implements ModuleView {


	/**
	 * The {@link VBox} containing all entries
	 */
	@FXML
	public VBox boxContent;

	/**
	 * The command to add entries to the content list.
	 */
	private final EventSource<AddEntryCommand> addEntryCommand = new EventSource<>();


	/**
	 * The command to add entries to the content list.
	 */
	private final EventSource<RemoveEntryCommand> removeEntryCommand = new EventSource<>();


	/**
	 * The event when an entry was removed from the list
	 */
	private final EventSource<EntryRemovedEvent> entryRemovedEvent = new EventSource<>();




	@Override
	public void initialize(final Pane pane) {
		addEntryCommand.subscribe(event -> addEntry(event.getEntryId(), event.getNode(), event.isRemovable()));
		removeEntryCommand.subscribe(event -> removeEntry(event.getEntryId(), false));
	}




	/**
	 * Adds a new entry with the given data.
	 *
	 * @param id        the id of the entry
	 * @param node      the javafx node
	 * @param removable whether the entry is removable by the user
	 */
	private void addEntry(final String id, final Node node, final boolean removable) {
		final boolean exists = boxContent.getChildren().stream()
				.map(child -> (ContentEntry) child)
				.anyMatch(e -> e.getEntryId().equals(id));
		if (!exists) {
			final ContentEntry entry = new ContentEntry(id, node, removable);
			entry.getRemoveEvent().subscribe(event -> removeEntry(entry.getEntryId(), true));
			boxContent.getChildren().add(entry);
		}
	}




	/**
	 * Removes the entry with the given id.
	 *
	 * @param id     the id of the entry
	 * @param byUser whether the user is removing the entry
	 */
	private void removeEntry(final String id, final boolean byUser) {
		final Optional<ContentEntry> entry = boxContent.getChildren().stream()
				.map(child -> (ContentEntry) child)
				.filter(e -> e.getEntryId().equals(id))
				.findFirst();
		entry.ifPresent(e -> {
			boxContent.getChildren().remove(e);
			entryRemovedEvent.trigger(new EntryRemovedEvent(id, byUser));
		});
	}




	@Override
	public List<ExposedEvent> getExposedEvents() {
		return List.of(ExposedEvent.global(EntryRemovedEvent.EVENT_ID, entryRemovedEvent));
	}




	@Override
	public List<ExposedCommand> getExposedCommands() {
		return List.of(
				ExposedCommand.global(AddEntryCommand.COMMAND_ID, addEntryCommand),
				ExposedCommand.global(RemoveEntryCommand.COMMAND_ID, removeEntryCommand)
		);
	}




	static class ContentEntry extends AnchorPane {


		/**
		 * The height of an entry
		 */
		private static final int HEIGHT = 44;

		/**
		 * The default width of an entry
		 */
		private static final int WIDTH = 10000;

		/**
		 * The id of this entry.
		 */
		@Getter
		private final String entryId;

		/**
		 * The javafx node.
		 */
		@Getter
		private final Node node;

		/**
		 * Whether this entry is removable by the user.
		 */
		@Getter
		private final boolean removable;


		/**
		 * The event when the user requests to remove this entry.
		 */
		@Getter
		private final EventSource<EmptyEvent> removeEvent = new EmptyEvent.EmptyEventSource();




		/**
		 * @param entryId        the id of the entry
		 * @param node      the javafx node
		 * @param removable whether the entry is removable by the user
		 */
		public ContentEntry(final String entryId, final Node node, final boolean removable) {
			this.entryId = entryId;
			this.node = node;
			this.removable = removable;

			this.setMinSize(0, HEIGHT);
			this.setPrefSize(WIDTH, HEIGHT);
			this.setMaxSize(WIDTH, HEIGHT);
			this.setStyle("-fx-background-color: #bbbbbb; -fx-background-radius: 5;");

			Anchors.setAnchors(node, 2, 2, 2, removable ? HEIGHT : 2);
			this.getChildren().add(node);

			if (removable) {
				final Button button = new Button("X");
				button.setMinSize(HEIGHT, HEIGHT);
				button.setPrefSize(HEIGHT, HEIGHT);
				button.setMaxSize(HEIGHT, HEIGHT);
				button.setOnAction(e -> removeEvent.trigger(EmptyEvent.INSTANCE));
				Anchors.setAnchors(button, 2, 2, null, 2);
				this.getChildren().add(button);
			}
		}


	}

}
