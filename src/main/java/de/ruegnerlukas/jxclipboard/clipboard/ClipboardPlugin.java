package de.ruegnerlukas.jxclipboard.clipboard;

import de.ruegnerlukas.jxclipboard.base.BasePlugin;
import de.ruegnerlukas.jxclipboard.base.content.AddEntryCommand;
import de.ruegnerlukas.jxclipboard.base.toolbar.AddToolCommand;
import de.ruegnerlukas.jxclipboard.base.toolbar.ToolActionEvent;
import de.ruegnerlukas.jxclipboard.clipboardlistener.ClipboardListenerPlugin;
import de.ruegnerlukas.simpleapplication.common.events.EventPackage;
import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EventBusListener;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import lombok.extern.slf4j.Slf4j;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * - Adds a button to the toolbar to save the current content of the clipboard as a new entry to the list.
 * - listens for clipboard events and saves the content of the event as a new entry to the list.
 */
@Slf4j
public class ClipboardPlugin extends Plugin {


	/**
	 * The (plugin) id of the clipboard plugin
	 */
	public static final String PLUGIN_ID = "jxclipboard.clipboard";

	/**
	 * The (plugin) name of the clipboard plugin
	 */
	private static final String DISPLAY_NAME = "jxclipboard.clipboard";

	/**
	 * The (plugin) version of the clipboard plugin
	 */
	private static final String PLUGIN_VERSION = "1.0";

	/**
	 * The name of the tool to save the current clipboard.
	 */
	private static final String TOOLNAME_SAVE_CLIPBOARD = "Save Clipboard";

	/**
	 * The provider for the event service
	 */
	private final Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);




	/**
	 * Default constructor.
	 */
	public ClipboardPlugin() {
		super(PLUGIN_ID, DISPLAY_NAME, PLUGIN_VERSION, true);
		this.getDependencyIds().add(BasePlugin.PLUGIN_ID);
	}




	@Override
	public void onLoad() {
		addSaveClipboardTool();
		eventServiceProvider.get().subscribe(ClipboardListenerPlugin.EVENT_CLIPBOARD_CHANGED, eventPackage -> {
			saveClipboard((String) eventPackage.getEvent());
		});
	}




	/**
	 * Adds the tool to save the current clipboard to the toolbar.
	 */
	private void addSaveClipboardTool() {
		final EventService eventService = eventServiceProvider.get();
		eventService.publish(AddToolCommand.COMMAND_ID, new EventPackage<>(new AddToolCommand(TOOLNAME_SAVE_CLIPBOARD, false)));
		eventService.subscribe(ToolActionEvent.EVENT_ID, (EventBusListener<ToolActionEvent>) eventPackage -> {
			final ToolActionEvent event = eventPackage.getEvent();
			if (event.getToolName().equals(TOOLNAME_SAVE_CLIPBOARD)) {
				saveCurrentClipboard();
			}
		});
	}




	/**
	 * Saves the current content of the clipboard and adds a new entry to the content list.
	 */
	private void saveCurrentClipboard() {
		readClipboardString().ifPresent(this::saveClipboard);
	}




	/**
	 * Saves the given content of the clipboard and adds a new entry to the content list.
	 *
	 * @param content the content of the clipboard
	 */
	private void saveClipboard(final String content) {
		final String entryId = UUID.randomUUID().toString();
		final AddEntryCommand command = AddEntryCommand.clipboardEntry(entryId, content, () -> saveToClipboard(content));
		eventServiceProvider.get().publish(AddEntryCommand.COMMAND_ID, new EventPackage<>(command));
	}




	/**
	 * Get the current text content from the clipboard.
	 *
	 * @return the content as a string
	 */
	private Optional<String> readClipboardString() {
		String content = null;
		try {
			content = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException ignored) {
		}
		return Optional.ofNullable(content);
	}




	/**
	 * Saves the given string to the system clipboard.
	 *
	 * @param content the content to save to the clipboard
	 */
	private void saveToClipboard(final String content) {
		StringSelection selection = new StringSelection(content);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
	}




	@Override
	public void onUnload() {
	}

}
