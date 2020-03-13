package de.ruegnerlukas.jxclipboard;

import de.ruegnerlukas.jxclipboard.base.BasePlugin;
import de.ruegnerlukas.jxclipboard.clipboardlistener.ClipboardListenerPlugin;
import de.ruegnerlukas.jxclipboard.clipboard.ClipboardPlugin;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.IntegerFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.StringFactory;
import de.ruegnerlukas.simpleapplication.core.application.Application;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConfiguration;

public class JXClipboard {


	/**
	 * The default width of the application.
	 */
	private static final int FRAME_WIDTH = 400;

	/**
	 * The default width of the application.
	 */
	private static final int FRAME_HEIGHT = 600;




	/**
	 * The entry point of the application
	 *
	 * @param args the program arguments
	 */
	public static void main(final String[] args) {
		final ApplicationConfiguration configuration = new ApplicationConfiguration();
		configuration.setShowViewAtStartup(false);

		configuration.getProviderFactories().add(new StringFactory("jfclipboard.name", "JFClipboard"));
		configuration.getProviderFactories().add(new StringFactory("jfclipboard.version", "0.1"));
		configuration.getProviderFactories().add(new IntegerFactory("jfclipboard.framewidth", FRAME_WIDTH));
		configuration.getProviderFactories().add(new IntegerFactory("jfclipboard.frameheight", FRAME_HEIGHT));

		configuration.getPlugins().add(new BasePlugin());
		configuration.getPlugins().add(new ClipboardPlugin());
		configuration.getPlugins().add(new ClipboardListenerPlugin());

		new Application(configuration).run();
	}

}
