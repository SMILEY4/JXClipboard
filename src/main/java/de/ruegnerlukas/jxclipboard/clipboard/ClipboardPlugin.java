package de.ruegnerlukas.jxclipboard.clipboard;

import de.ruegnerlukas.jxclipboard.JXClipboardState;
import de.ruegnerlukas.jxclipboard.base.BasePlugin;
import de.ruegnerlukas.jxclipboard.base.Toolbar;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginInformation;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import lombok.extern.slf4j.Slf4j;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.button;

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
	 * The id of the extension point for inserting custom {@link ClipboardWriter}s
	 */
	public static final String EXTENSION_POINT_CLIPBOARD_WRITER = "ep.clipboard.writer";

	/**
	 * The id of the extension point for inserting custom {@link ClipboardReader}s
	 */
	public static final String EXTENSION_POINT_CLIPBOARD_READER = "ep.clipboard.reader";


	/**
	 * The provider for the sui registry
	 */
	private final Provider<SuiRegistry> suiRegistryProvider = new Provider<>(SuiRegistry.class);

	/**
	 * The provider for the state
	 */
	private final Provider<JXClipboardState> jxClipboardStateProvider = new Provider<>(JXClipboardState.class);


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

		this.writer = this::writeToClipboard;
		this.reader = () -> readFromClipboard().orElse(null);

		final SuiRegistry suiRegistry = suiRegistryProvider.get();
		suiRegistry.inject(Toolbar.TOOL_INJECTION_POINT,
				button()
						.id("tool.save-clipbard")
						.textContent("Save")
						.eventAction(".", event -> saveCurrentClipboard())
		);
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
		Optional.ofNullable(reader.read()).ifPresent(
				content -> jxClipboardStateProvider.get().update(JXClipboardState.class,
						state -> state.getSavedEntries().add(0, content)));
	}




	@Override
	public void onUnload() {
	}

}
