package de.ruegnerlukas.jxclipboard.clipboardlistener;

import de.ruegnerlukas.jxclipboard.base.toolbar.AddToolCommand;
import de.ruegnerlukas.jxclipboard.base.toolbar.RemoveToolCommand;
import de.ruegnerlukas.jxclipboard.base.toolbar.ToolActionEvent;
import de.ruegnerlukas.jxclipboard.clipboard.ClipboardChangedEvent;
import de.ruegnerlukas.jxclipboard.clipboard.ClipboardPlugin;
import de.ruegnerlukas.jxclipboard.clipboard.ClipboardWriter;
import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.extensions.ExtensionPointService;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginInformation;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Set;

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
	 * The provider for the event service
	 */
	private final Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);

	/**
	 * The provider for the extension point service
	 */
	private final Provider<ExtensionPointService> extensionPointService = new Provider<>(ExtensionPointService.class);

	/**
	 * The clipboard listener
	 */
	private ClipBoardListener listener;

	/**
	 * Whether events for changed clipboard content are triggered.
	 */
	private volatile boolean triggerEvents = false;




	/**
	 * Default constructor.
	 */
	public ClipboardListenerPlugin() {
		super(PluginInformation.builder()
				.id(PLUGIN_ID)
				.version(PLUGIN_VERSION)
				.displayName(DISPLAY_NAME)
				.autoload(true)
				.dependencyIds(Set.of(ClipboardPlugin.PLUGIN_ID))
				.build());
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
		eventService.publish(new AddToolCommand(TOOLNAME_ENABLE_LISTENER, true));
		eventService.subscribe(Channel.type(ToolActionEvent.class), publishable -> {
			final ToolActionEvent event = (ToolActionEvent) publishable;
			if (event.getToolName().equals(TOOLNAME_ENABLE_LISTENER)) {
				this.triggerEvents = event.isSelected();
				extensionPointService.get().find(ClipboardPlugin.EXTENSION_POINT_CLIPBOARD_WRITER).ifPresent(extensionPoint -> {
					if (event.isSelected()) {
						extensionPoint.provide(ClipboardWriter.class, (ClipboardWriter) content -> {
							StringSelection selection = new StringSelection(content);
							Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
							clipboard.setContents(selection, listener);
						});
					} else {
						extensionPoint.provide(ClipboardWriter.class, null);
					}
				});
			}
		});
	}




	/**
	 * Start the clipboard listener.
	 */
	private void startListener() {
		this.listener = new ClipBoardListener(clipboardContent -> {
			if (triggerEvents) {
				eventServiceProvider.get().publish(new ClipboardChangedEvent(clipboardContent));
			}
		});
		this.listener.start();
	}




	@Override
	public void onUnload() {
		eventServiceProvider.get().publish(new RemoveToolCommand(TOOLNAME_ENABLE_LISTENER));
	}

}
