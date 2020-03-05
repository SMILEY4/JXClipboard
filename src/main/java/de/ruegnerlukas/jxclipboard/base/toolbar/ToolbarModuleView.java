package de.ruegnerlukas.jxclipboard.base.toolbar;

import de.ruegnerlukas.simpleapplication.common.events.EventSource;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedCommand;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedEvent;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleView;
import de.ruegnerlukas.simpleapplication.core.presentation.utils.Anchors;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.List;

public class ToolbarModuleView implements ModuleView {


	/**
	 * The javafx toolbar control.
	 */
	private ToolBar toolbar;

	/**
	 * The event source for {@link ToolActionEvent}s.
	 */
	private EventSource<ToolActionEvent> toolActionEvent;

	/**
	 * The event source for {@link AddToolCommand}s.
	 */
	private EventSource<AddToolCommand> addToolCommand;




	@Override
	public void initialize(final Pane pane) {
		toolbar = new ToolBar();
		Anchors.setAnchors(toolbar, 0);
		pane.getChildren().add(toolbar);

		toolActionEvent = new EventSource<>();

		addToolCommand = new EventSource<>();
		addToolCommand.subscribe(this::onAddToolCommand);
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
			tool.getToggleButton().setOnAction(btnEvent -> {
				toolActionEvent(tool.getName(), tool.getToggleButton().isSelected());
			});
		} else {
			toolbar.getItems().add(tool.getButton());
			tool.getButton().setOnAction(btnEvent -> {
				toolActionEvent(tool.getName(), true);
			});
		}
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
		return List.of(ExposedEvent.global(ToolActionEvent.EVENT_ID, toolActionEvent));
	}




	@Override
	public List<ExposedCommand> getExposedCommands() {
		return List.of(ExposedCommand.global(AddToolCommand.COMMAND_ID, addToolCommand));
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
