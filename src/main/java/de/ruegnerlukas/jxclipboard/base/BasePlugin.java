package de.ruegnerlukas.jxclipboard.base;

import de.ruegnerlukas.jxclipboard.JXClipboardState;
import de.ruegnerlukas.simpleapplication.common.eventbus.EventBus;
import de.ruegnerlukas.simpleapplication.common.eventbus.SubscriptionData;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.IntegerProvider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.StringProvider;
import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginInformation;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.style.SuiWindowBaseStyle;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.anchorPane;
import static de.ruegnerlukas.simpleapplication.core.simpleui.core.node.WindowRootElement.windowRoot;

@Slf4j
public class BasePlugin extends Plugin {


	/**
	 * The (plugin) id of the application base
	 */
	public static final String PLUGIN_ID = "jxclipboard.base";

	/**
	 * The (plugin) name of the application base
	 */
	private static final String DISPLAY_NAME = "jxclipboard.base";

	/**
	 * The (plugin) version of the application base
	 */
	private static final String PLUGIN_VERSION = "1.0";

	/**
	 * The provider for the width of the application frame.
	 */
	private final IntegerProvider appFrameWidth = new IntegerProvider("jfclipboard.framewidth");

	/**
	 * The provider for the height of the application frame.
	 */
	private final IntegerProvider appFrameHeight = new IntegerProvider("jfclipboard.frameheight");

	/**
	 * The provider for the application name.
	 */
	private final StringProvider appNameProvider = new StringProvider("jfclipboard.name");

	/**
	 * The provider for the application version.
	 */
	private final StringProvider appVersionProvider = new StringProvider("jfclipboard.version");




	/**
	 * The default constructor
	 */
	public BasePlugin() {
		super(PluginInformation.builder()
				.id(PLUGIN_ID)
				.version(PLUGIN_VERSION)
				.displayName(DISPLAY_NAME)
				.autoload(true)
				.dependencyIds(Set.of())
				.build());
	}




	@Override
	public void onLoad() {
		final EventBus eventBus = new Provider<>(EventBus.class).get();
		eventBus.subscribe(
				SubscriptionData.anyType(Tags.contains(ApplicationConstants.EVENT_APPLICATION_STARTED_TYPE)),
				e -> createViews(new Provider<Stage>(ApplicationConstants.PROVIDED_PRIMARY_STAGE).get()));
	}




	@Override
	public void onUnload() {
	}




	private void createViews(final Stage stage) {
		final JXClipboardState jxClipboardState = new Provider<>(JXClipboardState.class).get();
		final SuiSceneController controller = new SuiSceneController(
				jxClipboardState,
				windowRoot(stage)
						.windowBaseStyle(SuiWindowBaseStyle.cssStylesheet(Resource.externalRelative("src/main/resources/style/base.css")))
						.title(appNameProvider.get() + " (" + appVersionProvider.get() + ")")
						.size(appFrameWidth.get(), appFrameHeight.get())
						.content(JXClipboardState.class, this::createUI)
		);
		controller.show();
	}




	private NodeFactory createUI(final JXClipboardState state) {
		return anchorPane()
				.id("root")
				.styleClass("app-root")
				.style(Resource.externalRelative("src/main/resources/style/jfxclipboard.css"))
				.items(
						new Toolbar(),
						new ContentArea()
				);
	}


}
