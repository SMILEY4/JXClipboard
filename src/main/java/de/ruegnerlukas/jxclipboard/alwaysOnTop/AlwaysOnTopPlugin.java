package de.ruegnerlukas.jxclipboard.alwaysOnTop;

import de.ruegnerlukas.jxclipboard.base.BasePlugin;
import de.ruegnerlukas.jxclipboard.base.toolbar.AddToolCommand;
import de.ruegnerlukas.jxclipboard.base.toolbar.ToolActionEvent;
import de.ruegnerlukas.simpleapplication.common.events.EventPackage;
import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EventBusListener;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import javafx.stage.Stage;

public class AlwaysOnTopPlugin extends Plugin {


	/**
	 * The (plugin) id of the plugin
	 */
	public static final String PLUGIN_ID = "jxclipboard.alwaysOnTop";

	/**
	 * The (plugin) name of the plugin
	 */
	private static final String DISPLAY_NAME = "jxclipboard.alwaysOnTop";

	/**
	 * The (plugin) version of the plugin
	 */
	private static final String PLUGIN_VERSION = "1.0";

	/**
	 * The name of the tool.
	 */
	private static final String TOOLNAME_ALWAYS_ON_TOP = "Always On Top";

	/**
	 * The provider for the event service
	 */
	private final Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);


	/**
	 * The provider for the primary stage
	 */
	private final Provider<Stage> primaryStageProvider = new Provider<>(ApplicationConstants.PROVIDED_PRIMARY_STAGE);




	/**
	 * Default constructor.
	 */
	public AlwaysOnTopPlugin() {
		super(PLUGIN_ID, DISPLAY_NAME, PLUGIN_VERSION, true);
		this.getDependencyIds().add(BasePlugin.PLUGIN_ID);
	}




	@Override
	public void onLoad() {
		final EventService eventService = eventServiceProvider.get();
		eventService.publish(AddToolCommand.COMMAND_ID, new EventPackage<>(new AddToolCommand(TOOLNAME_ALWAYS_ON_TOP, true)));
		eventService.subscribe(ToolActionEvent.EVENT_ID, (EventBusListener<ToolActionEvent>) eventPackage -> {
			final ToolActionEvent event = eventPackage.getEvent();
			if (event.getToolName().equals(TOOLNAME_ALWAYS_ON_TOP)) {
				onToolEvent(event.isSelected());
			}
		});
	}




	/**
	 * Called when the user interacted with the tool button.
	 *
	 * @param enabled whether the tool is enabled or disabled
	 */
	private void onToolEvent(final boolean enabled) {
		primaryStageProvider.get().setAlwaysOnTop(enabled);
	}




	@Override
	public void onUnload() {

	}

}
