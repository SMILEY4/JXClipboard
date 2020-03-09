package de.ruegnerlukas.jxclipboard;

import de.ruegnerlukas.jxclipboard.base.BaseApplication;
import de.ruegnerlukas.jxclipboard.base.content.AddEntryCommand;
import de.ruegnerlukas.jxclipboard.base.content.EntryRemovedEvent;
import de.ruegnerlukas.jxclipboard.base.toolbar.AddToolCommand;
import de.ruegnerlukas.jxclipboard.base.toolbar.ToolActionEvent;
import de.ruegnerlukas.simpleapplication.common.events.EventPackage;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TestPlugin extends Plugin {


	/**
	 * The provider for the event service.
	 */
	private final Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);




	public TestPlugin() {
		super("test.plugin", "Test Plugin", "0.1", true);
		this.getDependencyIds().add(BaseApplication.PLUGIN_ID);
	}




	@Override
	public void onLoad() {
		final EventService eventService = eventServiceProvider.get();

		eventService.publish(AddToolCommand.COMMAND_ID, new EventPackage<>(new AddToolCommand("Test Btn", false)));
		eventService.publish(AddToolCommand.COMMAND_ID, new EventPackage<>(new AddToolCommand("Toggle", true)));

		eventService.subscribe(ToolActionEvent.EVENT_ID, eventPackage -> {
			ToolActionEvent event = (ToolActionEvent) eventPackage.getEvent();
			log.info("[ToolActionEvent] {}: {}.", event.getToolName(), event.isSelected());
		});

		eventService.publish(AddEntryCommand.COMMAND_ID, new EventPackage<>(
				AddEntryCommand.labelEntry("entryA", false, "Fixed Entry A")));

		eventService.publish(AddEntryCommand.COMMAND_ID, new EventPackage<>(
				AddEntryCommand.labelEntry("entryB", true, "Entry B")));

		eventService.publish(AddEntryCommand.COMMAND_ID, new EventPackage<>(
				AddEntryCommand.labelEntry("entryC", true, "Entry C")));

		eventService.publish(AddEntryCommand.COMMAND_ID, new EventPackage<>(
				AddEntryCommand.labelEntry("entryD", true, "Entry D")));


		eventService.subscribe(EntryRemovedEvent.EVENT_ID, eventPackage -> {
			EntryRemovedEvent event = (EntryRemovedEvent) eventPackage.getEvent();
			log.info("[EntryRemovedEvent] {}: {}.", event.getEntryId(), event.isRemovedByUser());
		});
	}




	@Override
	public void onUnload() {

	}

}
