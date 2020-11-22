package de.ruegnerlukas.jxclipboard.clipboardlistener;

import de.ruegnerlukas.jxclipboard.JXClipboardState;
import de.ruegnerlukas.jxclipboard.base.Toolbar;
import de.ruegnerlukas.jxclipboard.clipboard.ClipboardPlugin;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginInformation;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;

import java.util.Set;

import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.toggleButton;

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
	 * The provider for the sui registry
	 */
	private final Provider<SuiRegistry> suiRegistryProvider = new Provider<>(SuiRegistry.class);

	/**
	 * The provider for the state
	 */
	private final Provider<JXClipboardState> jxClipboardStateProvider = new Provider<>(JXClipboardState.class);

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
		final SuiRegistry suiRegistry = suiRegistryProvider.get();
		suiRegistry.inject(Toolbar.TOOL_INJECTION_POINT,
				toggleButton()
						.id("tool.auto-save")
						.textContent("Auto")
						.eventChecked(".", event -> triggerEvents = event.isChecked())
		);
		startListener();
	}




	/**
	 * Start the clipboard listener.
	 */
	private void startListener() {
		this.listener = new ClipBoardListener(clipboardContent -> {
			if (triggerEvents) {
				jxClipboardStateProvider.get().update(JXClipboardState.class,
						state -> state.addEntry(clipboardContent));
			}
		});
		this.listener.start();
	}




	@Override
	public void onUnload() {
	}

}
