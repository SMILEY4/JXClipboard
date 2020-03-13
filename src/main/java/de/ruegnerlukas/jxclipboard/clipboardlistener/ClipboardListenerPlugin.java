package de.ruegnerlukas.jxclipboard.clipboardlistener;

import de.ruegnerlukas.jxclipboard.base.toolbar.AddToolCommand;
import de.ruegnerlukas.jxclipboard.base.toolbar.RemoveToolCommand;
import de.ruegnerlukas.jxclipboard.base.toolbar.ToolActionEvent;
import de.ruegnerlukas.jxclipboard.clipboard.ClipboardPlugin;
import de.ruegnerlukas.simpleapplication.common.events.EventPackage;
import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EventBusListener;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;

/**
 * - Listens for changes of the clipboard content and triggers a clipboard event.
 */
public class ClipboardListenerPlugin extends Plugin {


	/**
	 * The (plugin) id of the clipboard listener plugin
	 */
	public static final String PLUGIN_ID = "jxclipboard.clipboardListener";

	/**
	 * The (plugin) name of the clipboard listener plugin
	 */
	private static final String DISPLAY_NAME = "jxclipboard.clipboardListener";

	/**
	 * The (plugin) version of the clipboard listener plugin
	 */
	private static final String PLUGIN_VERSION = "1.0";

	/**
	 * The name of the tool to save the current clipboard.
	 */
	private static final String TOOLNAME_ENABLE_LISTENER = "Auto. Enabled";


	/**
	 * The name of the event triggered when the content of the clipboard changed.
	 */
	public static final String EVENT_CLIPBOARD_CHANGED = "jxclipboard.clipboardlistener.clipboardChanged";

	/**
	 * The provider for the event service
	 */
	private final Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);

	/**
	 * Whether events for changed clipboard content are triggered.
	 */
	private volatile boolean triggerEvents = false;




	/**
	 * Default constructor.
	 */
	public ClipboardListenerPlugin() {
		super(PLUGIN_ID, DISPLAY_NAME, PLUGIN_VERSION, true);
		this.getDependencyIds().add(ClipboardPlugin.PLUGIN_ID);
	}




	@Override
	public void onLoad() {
		addEnableTool();
		startListener();
	}




	/**
	 * Adds the tool to enable/disable the listener.
	 */
	private void addEnableTool() {
		final EventService eventService = eventServiceProvider.get();
		eventService.publish(AddToolCommand.COMMAND_ID, new EventPackage<>(new AddToolCommand(TOOLNAME_ENABLE_LISTENER, true)));
		eventService.subscribe(ToolActionEvent.EVENT_ID, (EventBusListener<ToolActionEvent>) eventPackage -> {
			final ToolActionEvent event = eventPackage.getEvent();
			if (event.getToolName().equals(TOOLNAME_ENABLE_LISTENER)) {
				this.triggerEvents = event.isSelected();
			}
		});
	}




	/**
	 * Start the clipboard listener.
	 */
	private void startListener() {
		new ClipBoardListener(clipboardContent -> {
			if (triggerEvents) {
				eventServiceProvider.get().publish(EVENT_CLIPBOARD_CHANGED, new EventPackage<>(clipboardContent));
			}
		}).start();
	}




	@Override
	public void onUnload() {
		eventServiceProvider.get().publish(RemoveToolCommand.COMMAND_ID,
				new EventPackage<>(new RemoveToolCommand(TOOLNAME_ENABLE_LISTENER)));
	}

}
