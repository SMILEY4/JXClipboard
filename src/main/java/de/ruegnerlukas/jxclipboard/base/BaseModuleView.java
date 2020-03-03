package de.ruegnerlukas.jxclipboard.base;

import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedCommand;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ExposedEvent;
import de.ruegnerlukas.simpleapplication.core.presentation.module.ModuleView;
import de.ruegnerlukas.simpleapplication.core.presentation.utils.Anchors;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.util.List;

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

		final ScrollPane contentScrollPane = new ScrollPane();
		contentScrollPane.setFitToHeight(true);
		Anchors.setAnchors(contentScrollPane, TOOLBAR_HEIGHT, 0, 0, 0);
		contentScrollPane.setContent(contentModule);

		pane.getChildren().addAll(toolbarModule, contentScrollPane);

	}




	@Override
	public List<ExposedEvent> getExposedEvents() {
		return null;
	}




	@Override
	public List<ExposedCommand> getExposedCommands() {
		return null;
	}

}
