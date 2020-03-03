package de.ruegnerlukas.jxclipboard.base;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.IntegerProvider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.StringProvider;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import javafx.geometry.Dimension2D;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseApplication extends Plugin {


	/**
	 * The (plugin) id of the application base
	 */
	private static final String PLUGIN_ID = "jxclipboard.base";

	/**
	 * The (plugin) name of the application base
	 */
	private static final String DISPLAY_NAME = "jxclipboard.base";

	/**
	 * The (plugin) version of the application base
	 */
	private static final String PLUGIN_VERSION = "1.0";

	/**
	 * The provider for the view service
	 */
	private final Provider<ViewService> viewServiceProvider = new Provider<>(ViewService.class);

	/**
	 * The provider for the width of the application frame.
	 */
	private final IntegerProvider applicationFrameWidth = new IntegerProvider("jfclipboard.framewidth");

	/**
	 * The provider for the height of the application frame.
	 */
	private final IntegerProvider applicationFrameHeight = new IntegerProvider("jfclipboard.frameheight");

	/**
	 * The provider for the application name.
	 */
	private final StringProvider applicationNameProvider = new StringProvider("jfclipboard.name");

	/**
	 * The provider for the application version.
	 */
	private final StringProvider applicationVersionProvider = new StringProvider("jfclipboard.version");




	/**
	 * The default constructor
	 */
	public BaseApplication() {
		super(PLUGIN_ID, DISPLAY_NAME, PLUGIN_VERSION, true);
		this.getDependencyIds().add(ApplicationConstants.COMPONENT_VIEW_SYSTEM);
	}




	@Override
	public void onLoad() {

		final View baseView = View.builder()
				.id("jxclipboard.baseview")
				.size(new Dimension2D(applicationFrameWidth.get(), applicationFrameHeight.get()))
				.title(applicationNameProvider.get() + " (v" + applicationVersionProvider.get() + ")")
				.node(new BaseModule())
				.build();

		final ViewService viewService = viewServiceProvider.get();
		viewService.registerView(baseView);
		viewService.showView(baseView.getId());
	}




	@Override
	public void onUnload() {
	}

}
