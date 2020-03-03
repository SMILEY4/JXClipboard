package de.ruegnerlukas.jxclipboard.base;

import de.ruegnerlukas.simpleapplication.core.presentation.module.Module;

public class ToolbarModule extends Module {


	/**
	 * The default constructor
	 */
	public ToolbarModule() {
		super(new ToolbarModuleView(), new ToolbarModuleController());
	}

}
