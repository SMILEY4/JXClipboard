package de.ruegnerlukas.jxclipboard.alwaysOnTop;

import de.ruegnerlukas.jxclipboard.base.BasePlugin;
import de.ruegnerlukas.jxclipboard.base.toolbar.AddToolCommand;
import de.ruegnerlukas.jxclipboard.base.toolbar.ToolActionEvent;
import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginInformation;
import javafx.stage.Stage;

import java.util.Set;

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
		super(PluginInformation.builder()
				.id(PLUGIN_ID)
				.version(PLUGIN_VERSION)
				.displayName(DISPLAY_NAME)
				.autoload(true)
				.dependencyIds(Set.of(BasePlugin.PLUGIN_ID))
				.build());
	}




	@Override
	public void onLoad() {
		final EventService eventService = eventServiceProvider.get();
		eventService.publish(new AddToolCommand(TOOLNAME_ALWAYS_ON_TOP, true));
		eventService.subscribe(Channel.type(ToolActionEvent.class), publishable -> {
			final ToolActionEvent event = (ToolActionEvent) publishable;
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
