package de.ruegnerlukas.jxclipboard.base;

import de.ruegnerlukas.jxclipboard.base.content.ContentModule;
import de.ruegnerlukas.jxclipboard.base.toolbar.ToolbarModule;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleView;
import de.ruegnerlukas.simpleapplication.core.presentation.utils.Anchors;
import javafx.scene.layout.Pane;

public class BaseModuleView implements ModuleView {


	/**
	 * The exact height of the toolbar.
	 */
	private static final int TOOLBAR_HEIGHT = 40;




	@Override
	public void initialize(final Pane pane) {

		final ToolbarModule toolbarModule = new ToolbarModule();
		toolbarModule.setMinHeight(TOOLBAR_HEIGHT);
		toolbarModule.setPrefHeight(TOOLBAR_HEIGHT);
		toolbarModule.setMaxHeight(TOOLBAR_HEIGHT);
		Anchors.setAnchors(toolbarModule, 0, null, 0, 0);

		final ContentModule contentModule = new ContentModule();
		Anchors.setAnchors(contentModule, 0, 0, 0, 0);

		pane.getChildren().addAll(toolbarModule, contentModule);
	}


}
