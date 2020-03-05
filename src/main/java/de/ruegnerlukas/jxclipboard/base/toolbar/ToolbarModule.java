package de.ruegnerlukas.jxclipboard.base.toolbar;

import de.ruegnerlukas.simpleapplication.core.presentation.module.Module;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleController;

public class ToolbarModule extends Module {


	/**
	 * The default constructor
	 */
	public ToolbarModule() {
		super(new ToolbarModuleView(), new ModuleController.EmptyController());
	}

}
