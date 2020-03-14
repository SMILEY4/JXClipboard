package de.ruegnerlukas.jxclipboard.clipboard;

import de.ruegnerlukas.jxclipboard.base.BasePlugin;
import de.ruegnerlukas.jxclipboard.base.content.AddEntryCommand;
import de.ruegnerlukas.jxclipboard.base.toolbar.AddToolCommand;
import de.ruegnerlukas.jxclipboard.base.toolbar.RemoveToolCommand;
import de.ruegnerlukas.jxclipboard.base.toolbar.ToolActionEvent;
import de.ruegnerlukas.jxclipboard.clipboardlistener.ClipboardListenerPlugin;
import de.ruegnerlukas.simpleapplication.common.events.EventPackage;
import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EventBusListener;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.extensions.ExtensionPoint;
import de.ruegnerlukas.simpleapplication.core.extensions.ExtensionPointService;
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
	 * The id of the extension point for inserting custom {@link ClipboardWriter}s
	 */
	public static final String EXTENSION_POINT_CLIPBOARD_WRITER = "ep.clipboard.writer";

	/**
	 * The id of the extension point for inserting custom {@link ClipboardReader}s
	 */
	public static final String EXTENSION_POINT_CLIPBOARD_READER = "ep.clipboard.reader";


	/**
	 * The clipboard reader. Can be customized through the extension point.
	 */
	private ClipboardWriter writer;

	/**
	 * The clipboard writer. Can be customized through the extension point.
	 */
	private ClipboardReader reader;




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
		setupClipboardFunctions();
		setupClipboardListener();
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
	 * Adds the listener for clipboard events.
	 */
	private void setupClipboardListener() {
		eventServiceProvider.get().subscribe(ClipboardListenerPlugin.EVENT_CLIPBOARD_CHANGED, eventPackage -> {
			saveClipboard((String) eventPackage.getEvent());
		});
	}




	/**
	 * Setup clipboard reader, writer and the extension points.
	 */
	private void setupClipboardFunctions() {

		final ExtensionPointService extensionPointService = new Provider<>(ExtensionPointService.class).get();

		this.writer = this::writeToClipboard;
		ExtensionPoint extensionClipboardWriter = new ExtensionPoint(EXTENSION_POINT_CLIPBOARD_WRITER);
		extensionClipboardWriter.addSupportedTypeAllowNull(ClipboardWriter.class, writer -> {
			if (writer == null) {
				this.writer = this::writeToClipboard;
			} else {
				this.writer = writer;
			}
		});
		extensionPointService.register(extensionClipboardWriter);

		this.reader = () -> readFromClipboard().orElse(null);
		ExtensionPoint extensionClipboardReader = new ExtensionPoint(EXTENSION_POINT_CLIPBOARD_READER);
		extensionClipboardReader.addSupportedTypeAllowNull(ClipboardReader.class, reader -> {
			if (reader == null) {
				this.reader = () -> readFromClipboard().orElse(null);
			} else {
				this.reader = reader;
			}
		});
		extensionPointService.register(extensionClipboardReader);

	}




	/**
	 * Writes the given string to the system clipboard.
	 *
	 * @param content the content to save to the clipboard
	 */
	private void writeToClipboard(final String content) {
		StringSelection selection = new StringSelection(content);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
	}




	/**
	 * Reads the current text content from the clipboard.
	 *
	 * @return the content as a string
	 */
	private Optional<String> readFromClipboard() {
		String content = null;
		try {
			content = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException ignored) {
		}
		return Optional.ofNullable(content);
	}




	/**
	 * Saves the current content of the clipboard and adds a new entry to the content list.
	 */
	private void saveCurrentClipboard() {
		Optional.ofNullable(reader.read()).ifPresent(this::saveClipboard);
	}

	/**
	 * Saves the given content of the clipboard and adds a new entry to the content list.
	 *
	 * @param content the content of the clipboard
	 */
	private void saveClipboard(final String content) {
		final String entryId = UUID.randomUUID().toString();
		final AddEntryCommand command = AddEntryCommand.clipboardEntry(entryId, content, () -> writer.write(content));
		eventServiceProvider.get().publish(AddEntryCommand.COMMAND_ID, new EventPackage<>(command));
	}




	@Override
	public void onUnload() {
		eventServiceProvider.get().publish(RemoveToolCommand.COMMAND_ID,
				new EventPackage<>(new RemoveToolCommand(TOOLNAME_SAVE_CLIPBOARD)));
	}

}
