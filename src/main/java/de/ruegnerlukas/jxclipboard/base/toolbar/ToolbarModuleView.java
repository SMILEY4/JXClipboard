package de.ruegnerlukas.jxclipboard.base.toolbar;

import de.ruegnerlukas.simpleapplication.core.events.PublishableEvent.PublishableEventSource;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedCommand;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedEvent;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleView;
import de.ruegnerlukas.simpleapplication.core.presentation.utils.Anchors;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class ToolbarModuleView implements ModuleView {


	/**
	 * The javafx toolbar control.
	 */
	private ToolBar toolbar;

	/**
	 * The event source for {@link ToolActionEvent}s.
	 */
	private PublishableEventSource toolActionEvent;

	/**
	 * The event source for {@link AddToolCommand}s.
	 */
	private PublishableEventSource addToolCommand;

	/**
	 * The event source for {@link AddToolCommand}s.
	 */
	private PublishableEventSource removeToolCommand;




	@Override
	public void initialize(final Pane pane) {
		toolbar = new ToolBar();
		Anchors.setAnchors(toolbar, 0);
		pane.getChildren().add(toolbar);

		toolActionEvent = new PublishableEventSource(ToolActionEvent.class);

		addToolCommand = new PublishableEventSource(AddToolCommand.class);
		addToolCommand.subscribe(cmd -> Platform.runLater(() -> onAddToolCommand((AddToolCommand) cmd)));

		removeToolCommand = new PublishableEventSource(RemoveToolCommand.class);
		removeToolCommand.subscribe(cmd -> Platform.runLater(() -> onRemoveToolCommand((RemoveToolCommand) cmd)));
	}




	/**
	 * Handles the {@link AddToolCommand}.
	 *
	 * @param command the {@link AddToolCommand}
	 */
	private void onAddToolCommand(final AddToolCommand command) {
		final Tool tool = new Tool(command.getToolName(), command.isToggle());
		if (tool.isToggle()) {
			toolbar.getItems().add(tool.getToggleButton());
			tool.getToggleButton().setOnAction(btnEvent -> toolActionEvent(tool.getName(), tool.getToggleButton().isSelected()));
		} else {
			toolbar.getItems().add(tool.getButton());
			tool.getButton().setOnAction(btnEvent -> toolActionEvent(tool.getName(), true));
		}
	}




	/**
	 * Handles the {@link RemoveToolCommand}.
	 *
	 * @param command the {@link RemoveToolCommand}
	 */
	private void onRemoveToolCommand(final RemoveToolCommand command) {
		findToolNode(command.getToolName()).ifPresent(node -> {
			toolbar.getItems().remove(node);
		});
	}




	/**
	 * Finds the node in the toolbar with the given name
	 *
	 * @param toolName the name of the tool
	 * @return the node.
	 */
	private Optional<Node> findToolNode(final String toolName) {
		return toolbar.getItems().stream().filter(item -> {
			if (item instanceof Button) {
				if (((Button) item).getText().equals(toolName)) {
					return true;
				}
			}
			if (item instanceof ToggleButton) {
				if (((ToggleButton) item).getText().equals(toolName)) {
					return true;
				}
			}
			return false;
		}).findFirst();
	}




	/**
	 * Triggers a new {@link ToolActionEvent}.
	 *
	 * @param name     the name of the tool
	 * @param selected whether the tool is selected
	 */
	private void toolActionEvent(final String name, final boolean selected) {
		final ToolActionEvent event = new ToolActionEvent(name, selected);
		toolActionEvent.trigger(event);
	}




	@Override
	public List<ExposedEvent> getExposedEvents() {
		return List.of(ExposedEvent.global(toolActionEvent));
	}




	@Override
	public List<ExposedCommand> getExposedCommands() {
		return List.of(
				ExposedCommand.global(addToolCommand),
				ExposedCommand.global(removeToolCommand)
		);
	}




	static class Tool {


		/**
		 * The name of the tool
		 */
		@Getter
		private final String name;

		/**
		 * Whether this tool is a toggle button
		 */
		@Getter
		private final boolean toggle;

		/**
		 * The button or null
		 */
		@Getter
		private final Button button;

		/**
		 * The toggle button or null
		 */
		@Getter
		private final ToggleButton toggleButton;




		/**
		 * @param name   the name of the tool
		 * @param toggle whether this tool is a toggle button
		 */
		public Tool(final String name, final boolean toggle) {
			this.name = name;
			this.toggle = toggle;
			if (this.toggle) {
				button = null;
				toggleButton = new ToggleButton(name);
			} else {
				button = new Button(name);
				toggleButton = null;
			}
		}


	}

}
