package de.ruegnerlukas.jxclipboard.alwaysOnTop;

import de.ruegnerlukas.jxclipboard.base.BasePlugin;
import de.ruegnerlukas.jxclipboard.base.Toolbar;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginInformation;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.toggleButton;

@Slf4j
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
	 * The provider for the sui registry
	 */
	private final Provider<SuiRegistry> suiRegistryProvider = new Provider<>(SuiRegistry.class);

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
		final SuiRegistry suiRegistry = suiRegistryProvider.get();
		suiRegistry.inject(Toolbar.TOOL_INJECTION_POINT,
				toggleButton()
						.id("tool.always-on-top")
						.textContent("Always on top")
						.eventChecked(".", event -> onAlwaysOnTop(event.isChecked()))
		);
	}




	private void onAlwaysOnTop(final boolean enabled) {
		log.debug("Set stage always on top: {}.", enabled);
		primaryStageProvider.get().setAlwaysOnTop(enabled);
	}




	@Override
	public void onUnload() {

	}

}
