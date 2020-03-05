package de.ruegnerlukas.jxclipboard.base.content;

import de.ruegnerlukas.simpleapplication.core.presentation.module.Module;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleController;

public class ContentModule extends Module {


	/**
	 * The default constructor
	 */
	public ContentModule() {
		super(new ContentModuleView(), new ModuleController.EmptyController());
	}

}
