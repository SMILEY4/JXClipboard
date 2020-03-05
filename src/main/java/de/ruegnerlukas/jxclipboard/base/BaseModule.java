package de.ruegnerlukas.jxclipboard.base;

import de.ruegnerlukas.simpleapplication.core.presentation.module.Module;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleController;

public class BaseModule extends Module {


	/**
	 * The default constructor
	 */
	public BaseModule() {
		super(new BaseModuleView(), new ModuleController.EmptyController());
	}


}
