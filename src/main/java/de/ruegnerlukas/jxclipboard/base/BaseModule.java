package de.ruegnerlukas.jxclipboard.base;

import de.ruegnerlukas.simpleapplication.core.presentation.module.Module;

public class BaseModule extends Module {


	/**
	 * The default constructor
	 */
	public BaseModule() {
		super(new BaseModuleView(), new BaseModuleController());
	}


}
