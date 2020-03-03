package de.ruegnerlukas.jxclipboard.base;

import de.ruegnerlukas.simpleapplication.core.presentation.module.Module;

public class ContentModule extends Module {


	/**
	 * The default constructor
	 */
	public ContentModule() {
		super(new ContentModuleView(), new ContentModuleController());
	}

}
